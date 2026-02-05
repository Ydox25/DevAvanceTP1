package com.example.tp1;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "AnnonceAdd", value = "/AnnonceAdd")
public class AnnonceAdd extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Récupérer les données
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String adress = request.getParameter("adress");
            String mail = request.getParameter("mail");

            // 2. Créer l'objet
            Annonce annonce = new Annonce();
            annonce.setTitle(title);
            annonce.setDescription(description);
            annonce.setAdress(adress);
            annonce.setMail(mail);

            // 3. Utiliser le DAO pour sauver (plus propre !)
            AnnonceDAO dao = new AnnonceDAO();
            if(dao.create(annonce)) {
                System.out.println("Annonce insérée avec succès");
            } else {
                throw new ServletException("Échec de l'insertion");
            }

            response.sendRedirect("AnnonceList"); // Redirige vers la liste plutôt que de rester bloqué

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur technique", e);
        }
    }
}
