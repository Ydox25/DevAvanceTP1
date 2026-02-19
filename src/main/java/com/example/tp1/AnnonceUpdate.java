package com.example.tp1;

import com.example.tp1.entity.Annonce;
import com.example.tp1.service.AnnonceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AnnonceUpdate")
public class AnnonceUpdate extends HttpServlet {

    private AnnonceService annonceService;

    @Override
    public void init() throws ServletException {
        this.annonceService = new AnnonceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            Annonce annonce = annonceService.getAnnonce(id);

            if (annonce != null) {
                request.setAttribute("annonce", annonce);
                request.setAttribute("categories", annonceService.getAllCategories());
                this.getServletContext().getRequestDispatcher("/AnnonceUpdate.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("AnnonceList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String mail = request.getParameter("mail");
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));

        Annonce annonceModifiee = new Annonce();
        annonceModifiee.setId(id);
        annonceModifiee.setTitle(title);
        annonceModifiee.setDescription(description);
        annonceModifiee.setAdress(address);

        annonceService.updateAnnonce(annonceModifiee, categoryId);

        response.sendRedirect("AnnonceList");
    }
}