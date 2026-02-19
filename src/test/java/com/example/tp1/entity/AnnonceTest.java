package com.example.tp1.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnnonceTest {

    @Test
    void testDefaultValues() {
        // Vérifie la règle métier : une annonce créée est DRAFT par défaut
        Annonce a = new Annonce();

        assertEquals(AnnonceStatus.DRAFT, a.getStatus(), "Le statut par défaut doit être DRAFT");
        assertNotNull(a.getDate(), "La date ne doit pas être nulle");
    }

    @Test
    void testSetterGetter() {
        Annonce a = new Annonce();
        a.setTitle("Test Titre");
        assertEquals("Test Titre", a.getTitle());
    }
}