package com.example.tp1.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuration principale de l'API REST.
 * Définit le préfixe "/api" pour toutes les routes REST.
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    // annotées avec @Path dans ce package et ses sous-packages.
}