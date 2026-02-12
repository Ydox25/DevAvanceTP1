package com.example.tp1.repository;

import com.example.tp1.entity.Annonce;
import com.example.tp1.entity.AnnonceStatus;
import com.example.tp1.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AnnonceRepository {

    private EntityManager em;

    public AnnonceRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Annonce annonce) {
        if (annonce.getId() == null) {
            em.persist(annonce);
        } else {
            em.merge(annonce);
        }
    }

    public Annonce findById(Long id) {
        return em.find(Annonce.class, id);
    }

    public void delete(Long id) {
        Annonce annonce = findById(id);
        if (annonce != null) {
            em.remove(annonce);
        }
    }

    /**
     * Recherche avancée avec filtres et pagination (Exercice 3.2)
     */
    public List<Annonce> findByCriteria(String keyword, Category category, AnnonceStatus status, int page, int size) {
        StringBuilder jpql = new StringBuilder("SELECT a FROM Annonce a WHERE 1=1");

        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" AND (LOWER(a.title) LIKE :keyword OR LOWER(a.description) LIKE :keyword)");
        }
        if (category != null) {
            jpql.append(" AND a.category = :category");
        }
        if (status != null) {
            jpql.append(" AND a.status = :status");
        }

        jpql.append(" ORDER BY a.date DESC");

        TypedQuery<Annonce> query = em.createQuery(jpql.toString(), Annonce.class);

        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        }
        if (category != null) {
            query.setParameter("category", category);
        }
        if (status != null) {
            query.setParameter("status", status);
        }

        query.setFirstResult((page - 1) * size); // Offset
        query.setMaxResults(size);               // Limit

        return query.getResultList();
    }

    public long countByCriteria(String keyword, Category category, AnnonceStatus status) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(a) FROM Annonce a WHERE 1=1");

        if (keyword != null && !keyword.isEmpty()) {
            jpql.append(" AND (LOWER(a.title) LIKE :keyword OR LOWER(a.description) LIKE :keyword)");
        }
        if (category != null) {
            jpql.append(" AND a.category = :category");
        }
        if (status != null) {
            jpql.append(" AND a.status = :status");
        }

        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        }
        if (category != null) {
            query.setParameter("category", category);
        }
        if (status != null) {
            query.setParameter("status", status);
        }

        return query.getSingleResult();
    }
}