package com.example.tp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AnnonceList", value = "/AnnonceList")
public class AnnonceList extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AnnonceDAO dao = new AnnonceDAO();
            List<Annonce> listeAnnonces = dao.findAll();
            request.setAttribute("annonces", listeAnnonces);
            this.getServletContext().getRequestDispatcher("/AnnonceList.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}