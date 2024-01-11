package cj.software.hierarchy.movie.relational.dao;

import cj.software.hierarchy.movie.relational.entity.Movie;
import cj.software.hierarchy.movie.relational.entity.Movie_;
import cj.software.hierarchy.movie.spring.Trace;
import cj.software.hierarchy.movie.spring.TraceSize;
import cj.software.util.function.PureFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

@Repository
public class MovieRepository {
    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private NativeQueryExecutor nativeQueryExecutor;

    @PersistenceContext
    private EntityManager entityManager;

    @Trace
    public Movie searchMovieByTitle(
            @Trace
            String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = cb.createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        Predicate eqTitle = cb.equal(from.get(Movie_.TITLE), title);
        criteriaQuery.where(eqTitle);
        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(2);
        List<Movie> resultSet = query.getResultList();
        int size = resultSet.size();
        Movie result = switch (size) {
            case 0 -> null;
            case 1 -> resultSet.get(0);
            default -> throw new IllegalArgumentException(
                    String.format("%d movies found for title %s", size, title));
        };
        return result;
    }

    @Trace
    public long getNumMovies() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = cb.createQuery(Long.class);
        Root<Movie> from = critQuery.from(Movie.class);
        critQuery.select(cb.count(from));
        TypedQuery<Long> query = entityManager.createQuery(critQuery);
        long result = query.getSingleResult();
        return result;
    }

    @Transactional
    @Trace
    public Movie createMovie(
            @Trace
            String title) {
        Movie result = new Movie();
        result.setTitle(title);
        entityManager.persist(result);
        return result;
    }

    @Transactional
    @TraceSize
    public List<Movie> generateManyMovies(Collection<Movie> movies) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            entityManager.persist(movie);
            result.add(movie);
        }
        return result;
    }

    public void deleteAndRestoreIndices(PureFunction pureFunction) throws Exception {
        nativeQueryExecutor.executeUpdate("drop index if exists idxMovieTitle");
        logger.info("index temporarily deleted");
        try {
            pureFunction.doit();
            logger.info("restoring index now...");
        } finally {
            nativeQueryExecutor.executeUpdate("create index idxMovieTitle on movie (title)");
        }
    }

    @Transactional(readOnly = true)
    public void iterateAll(BiConsumer<Movie, Long> consumer) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> critQuery = cb.createQuery(Movie.class);
        Root<Movie> from = critQuery.from(Movie.class);
        Order orderId = cb.asc(from.get(Movie_.id));
        critQuery.select(from).orderBy(orderId);
        TypedQuery<Movie> query = entityManager.createQuery(critQuery);
        Stream<Movie> resultStream = query.getResultStream();
        ConsumerSender consumerSender = new ConsumerSender(consumer);
        resultStream.forEach(consumerSender::forward);
    }

    private static class ConsumerSender {
        private final BiConsumer<Movie, Long> biConsumer;
        private long counter = 0L;

        ConsumerSender(BiConsumer<Movie, Long> biConsumer) {
            this.biConsumer = biConsumer;
        }

        void forward(Movie movie) {
            biConsumer.accept(movie, counter);
            counter++;
        }
    }
}
