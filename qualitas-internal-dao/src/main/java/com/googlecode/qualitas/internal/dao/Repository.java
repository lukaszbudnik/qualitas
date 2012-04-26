package com.googlecode.qualitas.internal.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class Repository.
 */
public class Repository {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(Repository.class);

    /** The entity manageer. */
    @PersistenceContext
    protected EntityManager em;

    /**
     * Persist.
     * 
     * @param <T>
     *            the generic type
     * @param entity
     *            the entity
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <T> void persist(T entity) {
        em.persist(entity);
    }

    /**
     * Merge.
     * 
     * @param <T>
     *            the generic type
     * @param entity
     *            the entity
     * @return the t
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <T> T merge(T entity) {
        return em.merge(entity);
    }

    /**
     * Find by id.
     * 
     * @param <T>
     *            the generic type
     * @param clazz
     *            the clazz
     * @param id
     *            the id
     * @return the t
     */
    public <T> T findById(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    /**
     * Gets the single result by singular attribute.
     * 
     * @param <T>
     *            the generic type
     * @param <S>
     *            the generic type
     * @param clazz
     *            the clazz
     * @param attribute
     *            the attribute
     * @param value
     *            the value
     * @return the single result by singular attribute
     */
    public <T, S> T getSingleResultBySingularAttribute(Class<T> clazz,
            SingularAttribute<T, S> attribute, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), value));

        T entity = null;
        try {
            entity = em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            LOG.debug("Select single returned no data for " + clazz + " attribute " + attribute
                    + " and value " + value);
        }

        return entity;
    }

    /**
     * Gets the result list by singular attribute.
     * 
     * @param <T>
     *            the generic type
     * @param <S>
     *            the generic type
     * @param clazz
     *            the clazz
     * @param attribute
     *            the attribute
     * @param value
     *            the value
     * @return the result list by singular attribute
     */
    public <T, S> List<T> getResultListBySingularAttribute(Class<T> clazz,
            SingularAttribute<T, S> attribute, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), value));
        return em.createQuery(criteriaQuery).getResultList();
    }

}
