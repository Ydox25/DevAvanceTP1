package com.example.tp1.service;

import com.example.tp1.dto.AnnonceCreateDTO;
import com.example.tp1.entity.*;
import com.example.tp1.repository.*;
import com.example.tp1.util.EntityManagerProducer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class AnnonceService {

    /**
     * Crée une nouvelle annonce (Exercice 4.1.a)
     */
    public Annonce createAnnonce(Annonce annonce, Long userId, Long categoryId) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            UserRepository userRepo = new UserRepository(em);
            CategoryRepository categoryRepo = new CategoryRepository(em);
            AnnonceRepository annonceRepo = new AnnonceRepository(em);

            User author = userRepo.findById(userId);
            Category category = categoryRepo.findById(categoryId);

            if (author == null || category == null) {
                throw new IllegalArgumentException("Utilisateur ou Catégorie introuvable");
            }

            annonce.setAuthor(author);
            annonce.setCategory(category);
            annonce.setStatus(AnnonceStatus.DRAFT);

            annonceRepo.save(annonce);

            tx.commit();
            return annonce;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Modifie une annonce existante (Exercice 4.1.b)
     */
    public void updateAnnonce(Annonce annonceModifiee, Long categoryId) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            AnnonceRepository annonceRepo = new AnnonceRepository(em);
            CategoryRepository categoryRepo = new CategoryRepository(em);

            Annonce annonceOriginale = annonceRepo.findById(annonceModifiee.getId());

            if (annonceOriginale != null) {
                annonceOriginale.setTitle(annonceModifiee.getTitle());
                annonceOriginale.setDescription(annonceModifiee.getDescription());
                annonceOriginale.setAdress(annonceModifiee.getAdress());
                annonceOriginale.setMail(annonceModifiee.getMail());

                if (categoryId != null) {
                    Category cat = categoryRepo.findById(categoryId);
                    if (cat != null) {
                        annonceOriginale.setCategory(cat);
                    }
                }

                annonceRepo.save(annonceOriginale);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Publie une annonce (Change le statut en PUBLISHED) (Exercice 4.1.c)
     */
    public void publishAnnonce(Long id) {
        updateStatus(id, AnnonceStatus.PUBLISHED);
    }

    /**
     * Archive une annonce (Exercice 4.1.d)
     */
    public void archiveAnnonce(Long id) {
        updateStatus(id, AnnonceStatus.ARCHIVED);
    }

    private void updateStatus(Long id, AnnonceStatus newStatus) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            AnnonceRepository repo = new AnnonceRepository(em);
            Annonce a = repo.findById(id);
            if (a != null) {
                a.setStatus(newStatus);
                repo.save(a);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Supprime une annonce (Exercice 4.1.e)
     */
    public void deleteAnnonce(Long id) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            new AnnonceRepository(em).delete(id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Recherche et listing (Lecture seule) (Exercice 4.1.f)
     */
    public Annonce getAnnonce(Long id) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        try {
            return new AnnonceRepository(em).findById(id);
        } finally {
            em.close();
        }
    }

    public List<Annonce> searchAnnonces(String keyword, Long categoryId, AnnonceStatus status, int page, int size) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        try {
            CategoryRepository catRepo = new CategoryRepository(em);
            Category cat = (categoryId != null) ? catRepo.findById(categoryId) : null;

            return new AnnonceRepository(em).findByCriteria(keyword, cat, status, page, size);
        } finally {
            em.close();
        }
    }

    public long countAnnonces(String keyword, Long categoryId, AnnonceStatus status) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        try {
            CategoryRepository catRepo = new CategoryRepository(em);
            Category cat = (categoryId != null) ? catRepo.findById(categoryId) : null;
            return new AnnonceRepository(em).countByCriteria(keyword, cat, status);
        } finally {
            em.close();
        }
    }

    public List<Category> getAllCategories() {
        EntityManager em = EntityManagerProducer.getEntityManager();
        try {
            return new CategoryRepository(em).findAll();
        } finally {
            em.close();
        }
    }

    public Annonce patchAnnonce(Long id, AnnonceCreateDTO patchDto) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        try {
            em.getTransaction().begin();

            Annonce annonce = em.find(Annonce.class, id);
            if (annonce == null) {
                throw new IllegalArgumentException("Annonce introuvable");
            }

            // On ne modifie QUE les champs qui ne sont pas nulls dans le DTO
            if (patchDto.getTitle() != null) {
                annonce.setTitle(patchDto.getTitle());
            }
            if (patchDto.getDescription() != null) {
                annonce.setDescription(patchDto.getDescription());
            }
            if (patchDto.getAdress() != null) {
                annonce.setAdress(patchDto.getAdress());
            }
            if (patchDto.getMail() != null) {
                annonce.setMail(patchDto.getMail());
            }
            if (patchDto.getCategoryId() != null) {
                Category category = em.find(Category.class, patchDto.getCategoryId());
                if (category != null) {
                    annonce.setCategory(category);
                }
            }

            em.merge(annonce);
            em.getTransaction().commit();
            return annonce;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}