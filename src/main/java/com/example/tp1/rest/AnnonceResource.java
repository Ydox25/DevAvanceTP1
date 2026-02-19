package com.example.tp1.rest;

import com.example.tp1.dto.AnnonceCreateDTO;
import com.example.tp1.dto.AnnonceDTO;
import com.example.tp1.entity.Annonce;
import com.example.tp1.service.AnnonceService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/annonces")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnonceResource {

    private final AnnonceService annonceService = new AnnonceService();

    // 1. GET /api/annonces (Liste paginée)
    @GET
    public Response getAnnonces(@QueryParam("page") @DefaultValue("1") int page,
                                @QueryParam("size") @DefaultValue("10") int size) {
        List<Annonce> annonces = annonceService.searchAnnonces(null, null, null, page, size);

        // Mapping Entity -> DTO
        List<AnnonceDTO> dtos = annonces.stream().map(this::mapToDTO).collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    // 2. GET /api/annonces/{id} (Détail)
    @GET
    @Path("/{id}")
    public Response getAnnonce(@PathParam("id") Long id) {
        Annonce annonce = annonceService.getAnnonce(id);
        if (annonce == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Annonce non trouvée\"}").build();
        }
        return Response.ok(mapToDTO(annonce)).build();
    }

    // 3. POST /api/annonces (Création)
    @POST
    public Response createAnnonce(AnnonceCreateDTO dto) {
        Annonce newAnnonce = new Annonce();
        newAnnonce.setTitle(dto.getTitle());
        newAnnonce.setDescription(dto.getDescription());
        newAnnonce.setAdress(dto.getAdress());
        newAnnonce.setMail(dto.getMail());

        Annonce created = annonceService.createAnnonce(newAnnonce, dto.getAuthorId(), dto.getCategoryId());

        return Response.status(Response.Status.CREATED).entity(mapToDTO(created)).build();
    }

    // 4. PUT /api/annonces/{id} (Mise à jour)
    @PUT
    @Path("/{id}")
    public Response updateAnnonce(@PathParam("id") Long id, AnnonceCreateDTO dto) {
        Annonce annonceModifiee = new Annonce();
        annonceModifiee.setId(id);
        annonceModifiee.setTitle(dto.getTitle());
        annonceModifiee.setDescription(dto.getDescription());
        annonceModifiee.setAdress(dto.getAdress());
        annonceModifiee.setMail(dto.getMail());

        try {
            annonceService.updateAnnonce(annonceModifiee, dto.getCategoryId());
            return Response.ok("{\"message\":\"Annonce mise à jour avec succès\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    // 5. DELETE /api/annonces/{id} (Suppression)
    @DELETE
    @Path("/{id}")
    public Response deleteAnnonce(@PathParam("id") Long id) {
        try {
            annonceService.deleteAnnonce(id);
            return Response.noContent().build(); // 204 No Content : standard REST pour un delete réussi
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // 6. PATCH /api/annonces/{id} (Mise à jour partielle) - BONUS
    @PATCH
    @Path("/{id}")
    public Response patchAnnonce(@PathParam("id") Long id, AnnonceCreateDTO patchDto) {
        try {
            Annonce updatedAnnonce = annonceService.patchAnnonce(id, patchDto);
            return Response.ok(mapToDTO(updatedAnnonce)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Erreur lors de la mise à jour partielle\"}").build();
        }
    }

    // --- Méthode utilitaire de mapping Entity -> DTO ---
    private AnnonceDTO mapToDTO(Annonce a) {
        AnnonceDTO dto = new AnnonceDTO();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setDescription(a.getDescription());
        dto.setAdress(a.getAdress());
        dto.setMail(a.getMail());
        dto.setDate(a.getDate());
        dto.setStatus(a.getStatus());

        if (a.getCategory() != null) dto.setCategoryLabel(a.getCategory().getLabel());
        if (a.getAuthor() != null) dto.setAuthorName(a.getAuthor().getUsername());

        return dto;
    }
}