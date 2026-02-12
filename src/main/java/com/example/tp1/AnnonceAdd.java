package com.example.tp1;

import com.example.tp1.entity.Annonce;
import com.example.tp1.entity.Category;
import com.example.tp1.entity.User;
import com.example.tp1.service.AnnonceService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AnnonceAdd", value = "/AnnonceAdd")
public class AnnonceAdd extends HttpServlet {

    private AnnonceService annonceService;

    @Override
    public void init() throws ServletException {
        this.annonceService = new AnnonceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        chargerCategories(request);
        this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String adress = request.getParameter("adress");
        String mail = request.getParameter("mail");
        String catIdStr = request.getParameter("categoryId");

        Map<String, String> errors = new HashMap<>();

        if (title == null || title.trim().length() < 5) {
            errors.put("title", "Le titre doit contenir au moins 5 caractères.");
        }
        if (description == null || description.trim().length() < 10) {
            errors.put("description", "La description doit être plus détaillée (min 10 car.).");
        }
        if (mail == null || !mail.contains("@")) {
            errors.put("mail", "L'adresse email n'est pas valide.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("oldTitle", title);
            request.setAttribute("oldDesc", description);
            request.setAttribute("oldAdress", adress);
            request.setAttribute("oldMail", mail);
            request.setAttribute("oldCatId", catIdStr);

            chargerCategories(request); // Important : recharger la liste pour le select
            this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
            return;
        }

        try {
            Long categoryId = Long.parseLong(catIdStr);

            Annonce annonce = new Annonce();
            annonce.setTitle(title);
            annonce.setDescription(description);
            annonce.setAdress(adress);
            annonce.setMail(mail);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("Login");
                return;
            }

            annonceService.createAnnonce(annonce, user.getId(), categoryId);
            response.sendRedirect("AnnonceList?status=success");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur technique", e);
        }
    }

    private void chargerCategories(HttpServletRequest request) {
        List<Category> categories = annonceService.getAllCategories();
        request.setAttribute("categories", categories);
    }
}