package com.example.tp1.integration;

import com.example.tp1.entity.*;
import com.example.tp1.service.AnnonceService;
import com.example.tp1.repository.*;
import com.example.tp1.util.EntityManagerProducer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GlobalFlowTest {

    static AnnonceService service;
    static Long userId;
    static Long catId;
    static Long annonceId;

    @BeforeAll
    static void init() {
        service = new AnnonceService();
        EntityManager em = EntityManagerProducer.getEntityManager();
        em.getTransaction().begin();

        long uniqueId = System.currentTimeMillis();

        User u = new User();
        u.setUsername("TestFlow_" + uniqueId);
        u.setEmail("flow_" + uniqueId + "@test.com");
        u.setPassword("pass");
        new UserRepository(em).save(u);
        userId = u.getId();

        Category c = new Category();
        c.setLabel("Integration_" + uniqueId);
        new CategoryRepository(em).save(c);
        catId = c.getId();

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @Order(1)
    void testCreationAnnonce() {
        Annonce a = new Annonce();
        a.setTitle("Vente Vélo");
        a.setDescription("Vélo rouge très rapide");
        a.setAdress("Lyon");
        a.setMail("velo@lyon.fr");

        // Action : Création
        Annonce created = service.createAnnonce(a, userId, catId);
        annonceId = created.getId();

        assertNotNull(annonceId);
        assertEquals(AnnonceStatus.DRAFT, created.getStatus());
    }

    @Test
    @Order(2)
    void testPublication() {
        // Action : Publication
        service.publishAnnonce(annonceId);

        // Vérification
        Annonce a = service.getAnnonce(annonceId);
        assertEquals(AnnonceStatus.PUBLISHED, a.getStatus());
    }

    @Test
    @Order(3)
    void testRecherche() {
        // Action : Recherche "Vélo"
        List<Annonce> results = service.searchAnnonces("Vélo", null, AnnonceStatus.PUBLISHED, 1, 10);

        assertFalse(results.isEmpty());
        assertEquals("Vente Vélo", results.get(0).getTitle());
    }
}