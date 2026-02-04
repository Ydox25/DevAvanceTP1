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
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String adress = request.getParameter("adress");
        String mail = request.getParameter("mail");


        try (Connection c = ConnectionDB.getInstance()) {

            String sql = "INSERT INTO annonce (title, description, adress, mail, date) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, description);
                pstmt.setString(3, adress);
                pstmt.setString(4, mail);

                pstmt.executeUpdate();

                System.out.println("Annonce insérée avec succès");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de l'insertion dans la bd", e);
        }

        response.sendRedirect("AnnonceAdd?status=success");
    }
}
