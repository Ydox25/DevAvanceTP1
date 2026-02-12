package com.example.tp1.repository;

import com.example.tp1.entity.Category;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoryRepository {

    private EntityManager em;

    public CategoryRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Category category) {
        if (category.getId() == null) {
            em.persist(category);
        } else {
            em.merge(category);
        }
    }

    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    public void delete(Long id) {
        Category category = findById(id);
        if (category != null) {
            em.remove(category);
        }
    }
}