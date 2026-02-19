package com.example.tp1;

import com.example.tp1.service.AnnonceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AnnonceDelete")
public class AnnonceDelete extends HttpServlet {

    private AnnonceService annonceService;

    @Override
    public void init() throws ServletException {
        this.annonceService = new AnnonceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                Long id = Long.parseLong(idStr);
                annonceService.deleteAnnonce(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("AnnonceList");
    }
}