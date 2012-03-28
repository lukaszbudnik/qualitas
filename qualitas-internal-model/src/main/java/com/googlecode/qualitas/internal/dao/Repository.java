package com.googlecode.qualitas.internal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class Repository {

    @PersistenceContext
    private EntityManager em;
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public <T> void persist(T entity) {
        em.persist(entity);
    }
    
    public <T> T merge(T entity) {
        return em.merge(entity);
    }
    
    public <T> T findById(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }
    
    public <T, S> T getSingleResultBySingularAttribute(Class<T> clazz, SingularAttribute<T, S> attribute, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder
                .createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.where(criteriaBuilder.equal(
                root.get(attribute), value));
        return em.createQuery(criteriaQuery).getSingleResult();
    }
    
    public <T, S> List<T> getResultListBySingularAttribute(Class<T> clazz, SingularAttribute<T, S> attribute, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder
                .createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.where(criteriaBuilder.equal(
                root.get(attribute), value));
        return em.createQuery(criteriaQuery).getResultList();
    }

}
