package com.example.tp1;

import com.example.tp1.entity.Annonce;
import com.example.tp1.entity.AnnonceStatus;
import com.example.tp1.service.AnnonceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AnnonceList", value = "/AnnonceList")
public class AnnonceList extends HttpServlet {

    private AnnonceService annonceService;

    @Override
    public void init() throws ServletException {
        this.annonceService = new AnnonceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int size = 10; // 10 annonces par page
        String keyword = request.getParameter("search");

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Annonce> listeAnnonces = annonceService.searchAnnonces(keyword, null, null, page, size);
        long totalAnnonces = annonceService.countAnnonces(keyword, null, null);

        int totalPages = (int) Math.ceil((double) totalAnnonces / size);

        request.setAttribute("annonces", listeAnnonces);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", keyword);

        this.getServletContext().getRequestDispatcher("/AnnonceList.jsp").forward(request, response);
    }
}