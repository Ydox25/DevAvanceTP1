package com.example.tp1.repository;

import com.example.tp1.entity.*;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AnnonceRepositoryTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private AnnonceRepository repo;

    @BeforeAll
    static void init() { emf = Persistence.createEntityManagerFactory("MasterAnnoncePU"); }

    @AfterAll
    static void close() { emf.close(); }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        repo = new AnnonceRepository(em);
        em.getTransaction().begin();
        // Nettoyage préventif
        em.createQuery("DELETE FROM Annonce").executeUpdate();
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.close();
    }

    @Test
    void testSearchAndPagination() {
        // GIVEN : 15 annonces
        for (int i = 0; i < 15; i++) {
            Annonce a = new Annonce();
            a.setTitle("Java Dev " + i);
            a.setDescription("Description");
            a.setAdress("Paris");
            a.setStatus(AnnonceStatus.PUBLISHED);
            em.persist(a);
        }
        em.flush();

        // WHEN : Page 1 (10 résultats)
        List<Annonce> page1 = repo.findByCriteria("Java", null, AnnonceStatus.PUBLISHED, 1, 10);
        assertEquals(10, page1.size(), "Page 1 incomplète");

        // WHEN : Page 2 (5 résultats)
        List<Annonce> page2 = repo.findByCriteria("Java", null, AnnonceStatus.PUBLISHED, 2, 10);
        assertEquals(5, page2.size(), "Page 2 incorrecte");
    }
}