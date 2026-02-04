package com.example.tp1;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World! :";
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String nom = request.getParameter("nom");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (nom != null && !nom.trim().isEmpty()) {
            out.println("<h1>" + message + " " + nom + " !</h1>");
        } else {
            out.println("<h1>" + message + "</h1>");
        }

        out.println("<h3>Saisi ton nom :</h3>");
        out.println("<form action='hello-servlet' method='GET'>");
        out.println("  <input type='text' name='nom' required>");
        out.println("  <input type='submit' value='Envoyer'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}