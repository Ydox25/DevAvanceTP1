package com.example.tp1.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class TestResource {

    // 1. Test simple (GET /api/helloWorld)
    @GET
    @Path("helloWorld")
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        return Response.ok("{\"message\": \"Hello REST World!\"}").build();
    }

    // 2. Passage de paramètres (GET /api/params/{nom}?age=25)
    @GET
    @Path("params/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testParams(
            @PathParam("name") String name,      // Extrait de l'URL (/params/Yass -> name = "Yass")
            @QueryParam("age") Integer age       // Extrait de la query string (?age=23> age = 23)
    ) {
        String msg = "Bonjour " + name;
        if (age != null) {
            msg += ", tu as " + age + " ans.";
        }

        return Response.ok("{\"message\": \"" + msg + "\"}").build();
    }
}