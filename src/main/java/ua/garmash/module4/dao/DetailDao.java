package ua.garmash.module4.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ua.garmash.module4.config.HibernateFactoryUtil;
import ua.garmash.module4.model.Detail;

import java.util.List;

public class DetailDao  {
    public void save(Detail value) {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(value);
        entityManager.flush();
        transaction.commit();
    }

    public void delete(Detail value) {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(value);
        entityManager.flush();
        transaction.commit();
    }

    public void deleteAll() {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        Query query = entityManager.createNativeQuery("DELETE FROM detail");
        transaction.begin();
        query.executeUpdate();
        entityManager.flush();
        transaction.commit();
    }

    public Detail getById(long id) {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Detail> query = criteriaBuilder.createQuery(Detail.class);
        final Root<Detail> from = query.from(Detail.class);
        query.select(from).where(criteriaBuilder.equal(from.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

    public List<Detail> getAll() {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Detail> query = criteriaBuilder.createQuery(Detail.class);
        final Root<Detail> from = query.from(Detail.class);
        query.select(from);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Object[]> getSummary() {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final String sqlQuery = "SELECT Count(detail.id), " +
                "Sum(detail.amountofbrokenchips), " +
                "Sum(detail.producedfuel) " +
                "FROM detail;";
        return entityManager.createNativeQuery(sqlQuery).getResultList();
    }
}
