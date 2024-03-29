package cj.software.hierarchy.movie.relational.dao;

import cj.software.hierarchy.movie.relational.entity.Movie;
import cj.software.hierarchy.movie.relational.entity.Movie_;
import cj.software.hierarchy.movie.spring.Trace;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MovieRepository {

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
}
