package com.heroku.module3.dao;

import com.heroku.module3.config.HibernateFactoryUtil;
import com.heroku.module3.model.Lecturer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class LecturerDao extends AbstractDao<Lecturer> {
    @Override
    protected void init() {
        aClass = Lecturer.class;
    }

    public List<Lecturer> getLecturerByFirstOrLastNane(String name) {
        final EntityManager entityManager = HibernateFactoryUtil.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Lecturer> query = criteriaBuilder.createQuery(aClass);
        final Root<Lecturer> from = query.from(aClass);
        List<Predicate> predicates = new ArrayList<>(2);
        if (name != null) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(from.get("firstName")), name.toUpperCase()));
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(from.get("lastName")), name.toUpperCase()));
        }
        query.select(from).where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        return entityManager.createQuery(query).getResultList();
    }
}
