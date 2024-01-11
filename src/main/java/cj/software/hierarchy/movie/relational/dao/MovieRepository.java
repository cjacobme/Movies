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

    private int getNumMoviesNotRolesAdded() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = cb.createQuery(Long.class);
        Root<Movie> from = critQuery.from(Movie.class);
        Predicate roleNotAdded = cb.equal(from.get(Movie_.ROLES_ADDED), false);
        critQuery.select(cb.count(from)).where(roleNotAdded);
        TypedQuery<Long> query = entityManager.createQuery(critQuery);
        long size = query.getSingleResult();
        int result = (int) size;
        return result;
    }

    @Transactional
    @Trace
    public Movie createMovie(
            @Trace
            String title) {
        Movie result = new Movie(title);
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

    public <T> void iterateAllRolesNotAdded(BiConsumer<Movie, T> consumer, T additionalInfo) {
        int numMoviesNotRolesAdded = this.getNumMoviesNotRolesAdded();
        int pageSize = 10;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> critQuery = cb.createQuery(Movie.class);
        Root<Movie> from = critQuery.from(Movie.class);
        Order orderId = cb.asc(from.get(Movie_.id));
        Predicate roleNotAdded = cb.equal(from.get(Movie_.ROLES_ADDED), false);
        critQuery.select(from).where(roleNotAdded).orderBy(orderId);
        ConsumerSender<T> consumerSender = new ConsumerSender<>(consumer, additionalInfo);
        while (numMoviesNotRolesAdded > 0) {
            TypedQuery<Movie> query = entityManager.createQuery(critQuery);
            query.setFirstResult(0);
            query.setMaxResults(pageSize);
            List<Movie> movies = query.getResultList();
            for (Movie movie : movies) {
                consumerSender.forward(movie);
            }
            numMoviesNotRolesAdded = this.getNumMoviesNotRolesAdded();
        }
    }

    private static class ConsumerSender<T> {
        private final BiConsumer<Movie, T> biConsumer;
        private final T additionalInfo;

        ConsumerSender(BiConsumer<Movie, T> biConsumer, T additionalInfo) {
            this.biConsumer = biConsumer;
            this.additionalInfo = additionalInfo;
        }

        void forward(Movie movie) {
            biConsumer.accept(movie, additionalInfo);
        }
    }
}
