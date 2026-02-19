package com.example.tp1.repository;

import com.example.tp1.entity.User;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private UserRepository repo;

    @BeforeAll
    static void init() { emf = Persistence.createEntityManagerFactory("MasterAnnoncePU"); }

    @AfterAll
    static void close() { emf.close(); }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        repo = new UserRepository(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.close();
    }

    @Test
    @Order(1)
    void testCreateAndFind() {
        User u = new User();
        u.setUsername("JunitUser");
        u.setEmail("junit@test.com");
        u.setPassword("secret");

        repo.save(u);
        assertNotNull(u.getId());

        User found = repo.findByUsername("JunitUser");
        assertEquals("junit@test.com", found.getEmail());
    }
}