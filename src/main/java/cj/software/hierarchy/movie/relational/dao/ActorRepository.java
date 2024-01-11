package cj.software.hierarchy.movie.relational.dao;

import cj.software.hierarchy.movie.relational.entity.Actor;
import cj.software.hierarchy.movie.relational.entity.Actor_;
import cj.software.hierarchy.movie.spring.Trace;
import cj.software.hierarchy.movie.spring.TraceAtLogLevel;
import cj.software.hierarchy.movie.spring.TraceSize;
import cj.software.util.function.PureFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.StandardLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Validated
public class ActorRepository {
    private final Logger logger = LogManager.getFormatterLogger();

    @Autowired
    private NativeQueryExecutor nativeQueryExecutor;

    @PersistenceContext
    private EntityManager entityManager;

    @Trace
    public Actor searchActorByNames(
            @Trace
            String givenName,

            @Trace
            String familyName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = cb.createQuery(Actor.class);
        Root<Actor> from = criteriaQuery.from(Actor.class);
        Predicate eqGiven = cb.equal(from.get(Actor_.GIVEN_NAME), givenName);
        Predicate eqFamily = cb.equal(from.get(Actor_.FAMILY_NAME), familyName);
        criteriaQuery.where(eqFamily, eqGiven);
        TypedQuery<Actor> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(2);
        List<Actor> resultSet = query.getResultList();
        int size = resultSet.size();
        Actor result = switch (size) {
            case 0 -> null;
            case 1 -> resultSet.get(0);
            default -> throw new IllegalArgumentException(
                    String.format("%d actors with given name %s and family name %s found", size, givenName, familyName));
        };
        return result;
    }

    @TraceSize
    @Transactional
    public List<Actor> generateManyActors(Collection<Actor> actors) {
        List<Actor> result = new ArrayList<>();
        for (Actor actor : actors) {
            entityManager.persist(actor);
            result.add(actor);
        }
        return result;
    }

    @Transactional
    @Trace
    public Actor createActor(
            @Trace
            String givenName,

            @Trace
            String familyName) {
        Actor result = new Actor();
        result.setGivenName(givenName);
        result.setFamilyName(familyName);
        entityManager.persist(result);
        return result;
    }

    @Trace
    public long getNumActors() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = cb.createQuery(Long.class);
        Root<Actor> from = critQuery.from(Actor.class);
        critQuery.select(cb.count(from));
        TypedQuery<Long> query = entityManager.createQuery(critQuery);
        long result = query.getSingleResult();
        return result;
    }

    public void deleteAndRestoreIndices(PureFunction interimFunction) throws Exception {
        nativeQueryExecutor.executeUpdate("drop index if exists idxActorNames");
        logger.info("index temporarily deleted");
        try {
            interimFunction.doit();
            logger.info("restoring index now...");
        } finally {
            nativeQueryExecutor.executeUpdate("create index idxActorNames on actor (given_name, family_name);");
            logger.info("index restored");
        }
    }

    @TraceAtLogLevel(level = StandardLevel.DEBUG)
    public Actor selectRandomActor(int index) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Actor> critQuery = cb.createQuery(Actor.class);
        Root<Actor> from = critQuery.from(Actor.class);
        Order idAsc = cb.asc(from.get(Actor_.id));
        critQuery.select(from).orderBy(idAsc);
        TypedQuery<Actor> query = entityManager.createQuery(critQuery);
        query.setMaxResults(1);
        query.setFirstResult(index);
        Actor result = query.getSingleResult();
        return result;
    }
}
