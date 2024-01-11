package cj.software.hierarchy.movie.relational.dao;

import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Movie;
import cj.software.hierarchy.movie.relational.entity.Role;
import cj.software.hierarchy.movie.relational.entity.Role_;
import cj.software.hierarchy.movie.spring.Trace;
import cj.software.hierarchy.movie.spring.TraceAtLogLevel;
import org.apache.logging.log4j.spi.StandardLevel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Trace
    public Role search(
            @Trace
            String name,

            @Trace
            Actor actor,

            @Trace
            Movie movie) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = cb.createQuery(Role.class);
        Root<Role> from = criteriaQuery.from(Role.class);
        Predicate eqName = cb.equal(from.get(Role_.NAME), name);
        Predicate eqActor = cb.equal(from.get(Role_.ACTOR), actor);
        Predicate eqMovie = cb.equal(from.get(Role_.MOVIE), movie);
        criteriaQuery.where(eqName, eqActor, eqMovie);
        TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(2);
        List<Role> resultSet = query.getResultList();
        int size = resultSet.size();
        Role result = switch (size) {
            case 0 -> null;
            case 1 -> resultSet.get(0);
            default -> throw new IllegalArgumentException(
                    String.format("%d roles found for name %s actor %s movie %s",
                            size, name, actor, movie));
        };
        return result;
    }

    @Transactional
    @Trace
    public Role createRole(
            @Trace
            String name,

            @Trace
            Actor actor,

            @Trace
            Movie movie) {
        Role result = new Role();
        result.setName(name);
        result.setActor(actor);
        result.setMovie(movie);
        entityManager.persist(result);
        return result;
    }

    @TraceAtLogLevel(level = StandardLevel.DEBUG)
    public long determineNumRolesForMovie(Movie movie) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = cb.createQuery(Long.class);
        Root<Role> from = critQuery.from(Role.class);
        Predicate eqMovie = cb.equal(from.get(Role_.MOVIE), movie);
        critQuery.select(cb.count(from));
        critQuery.where(eqMovie);
        TypedQuery<Long> query = entityManager.createQuery(critQuery);
        long result = query.getSingleResult();
        return result;
    }

    @Transactional()
    @Trace
    public int saveManyRoles(List<Role> roles) {
        int result = 0;
        for (Role role : roles) {
            entityManager.persist(role);
            result++;
        }
        return result;
    }
}
