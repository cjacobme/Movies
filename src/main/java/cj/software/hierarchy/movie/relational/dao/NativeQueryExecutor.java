package cj.software.hierarchy.movie.relational.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class NativeQueryExecutor {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int executeUpdate(String statement) {
        Query query = entityManager.createNativeQuery(statement);
        int result = query.executeUpdate();
        return result;
    }
}
