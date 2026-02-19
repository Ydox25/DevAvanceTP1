<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Liste des Annonces</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }

    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f5f5f5;
      padding: 20px;
    }

    .container {
      max-width: 1200px;
      margin: 0 auto;
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      padding: 30px;
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
      border-bottom: 2px solid #e0e0e0;
      padding-bottom: 20px;
    }

    .header h1 {
      color: #333;
      font-size: 28px;
    }

    .btn-add {
      background-color: #4CAF50;
      color: white;
      padding: 12px 24px;
      text-decoration: none;
      border-radius: 5px;
      font-weight: 500;
      transition: background-color 0.3s;
    }

    .btn-add:hover {
      background-color: #45a049;
    }

    table {
      border-collapse: collapse;
      width: 100%;
    }

    th, td {
      border: 1px solid #ddd;
      padding: 15px;
      text-align: left;
    }

    th {
      background-color: #555;
      color: white;font-weight: 600;
    }

    tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    tr:hover {
      background-color: #f0f0f0;
    }

    td {
      color: #666;
    }

    .actions {
      display: flex;
      gap: 12px;
    }

    .btn-edit {
      color: #1976D2;
      text-decoration: none;
      font-weight: 500;
      transition: color 0.3s;
    }

    .btn-edit:hover {
      color: #1565C0;
      text-decoration: underline;
    }

    .btn-delete {
      color: #D32F2F;
      text-decoration: none;
      font-weight: 500;
      transition: color 0.3s;
      cursor: pointer;
    }

    .btn-delete:hover {
      color: #C62828;
      text-decoration: underline;
    }

    .no-data {
      text-align: center;
      padding: 40px;
      color: #999;
      font-size: 16px;
    }
  </style>
</head>
<body>

<div class="container">
  <div class="header">
    <h1>Annonces</h1>
    <form method="GET" action="AnnonceList" style="display:inline-block; margin-left:20px;">
      <label>
        <input type="text" name="search" placeholder="Rechercher..." value="${search}">
      </label>
      <button type="submit">OK</button>
    </form>
    <a href="AnnonceAdd" class="btn-add">+ Ajouter annonce</a>
  </div>

  <table>
    <thead>
    <tr>
      <th>Titre</th>
      <th>Catégorie</th> <th>Prix/Desc</th>
      <th>Statut</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${annonces}" var="a">
      <tr>
        <td><strong>${a.title}</strong></td>
        <td>${a.category.label}</td> <td>${a.description}</td>
        <td>${a.status}</td>
        <td>
          <div class="actions">
            <a href="AnnonceUpdate?id=${a.id}" class="btn-edit">Modifier</a>
            <a href="AnnonceDelete?id=${a.id}" class="btn-delete"
               onclick="return confirm('Supprimer ?');">Supprimer</a>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div style="margin-top: 20px; text-align: center;">
    <c:if test="${currentPage > 1}">
      <a href="AnnonceList?page=${currentPage - 1}&search=${search}">Précédent</a>
    </c:if>

    <span>Page ${currentPage} sur ${totalPages}</span>

    <c:if test="${currentPage < totalPages}">
      <a href="AnnonceList?page=${currentPage + 1}&search=${search}">Suivant</a>
    </c:if>
  </div>
</div>

</body>
</html>