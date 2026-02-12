<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:useBean id="errors" scope="request" type="java.util.Map<java.lang.String, java.lang.String>"/>
<jsp:useBean id="oldTitle" scope="request" type="java.lang.String"/>
<jsp:useBean id="oldDesc" scope="request" type="java.lang.String"/>
<jsp:useBean id="oldAdress" scope="request" type="java.lang.String"/>
<jsp:useBean id="oldMail" scope="request" type="java.lang.String"/>
<jsp:useBean id="oldCatId" scope="request" type="java.lang.String"/>
<jsp:useBean id="categories" scope="request" type="java.util.List<com.example.tp1.entity.Category>"/>

<html>
<head>
    <title>Ajout d'une annonce</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }

        .header {
            margin-bottom: 30px;
            border-bottom: 2px solid #e0e0e0;
            padding-bottom: 20px;
        }

        .header h1 {
            color: #333;
            font-size: 24px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="text"],
        textarea,
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            font-size: 14px;
            color: #666;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus,
        textarea:focus,
        select:focus {
            outline: none;
            border-color: #555;
            box-shadow: 0 0 5px rgba(85, 85, 85, 0.2);
        }

        textarea {
            resize: vertical;
            min-height: 120px;
        }

        .btn-submit {
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            font-weight: 500;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }

        .btn-submit:hover {
            background-color: #45a049;
        }

        /* Styles spécifiques pour les erreurs */
        .error-msg {
            color: #D32F2F;
            font-size: 0.85em;
            margin-top: 5px;
            display: block;
            font-weight: 500;
        }

        .input-error {
            border-color: #D32F2F !important;
            background-color: #fff8f8;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Ajout d'une annonce</h1>
    </div>

    <form action="AnnonceAdd" method="POST">

        <div class="form-group">
            <label for="title">Titre :</label>
            <input type="text" id="title" name="title"
                   value="${oldTitle}"
                   class="${not empty errors['title'] ? 'input-error' : ''}"
                   placeholder="Titre de l'annonce">

            <c:if test="${not empty errors['title']}">
                <span class="error-msg">${errors['title']}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label for="description">Description :</label>
            <textarea id="description" name="description"
                      class="${not empty errors['description'] ? 'input-error' : ''}"
                      placeholder="Description de l'annonce">${oldDesc}</textarea>

            <c:if test="${not empty errors['description']}">
                <span class="error-msg">${errors['description']}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label for="adress">Adresse :</label>
            <input type="text" id="adress" name="adress" value="${oldAdress}" required>
        </div>

        <div class="form-group">
            <label for="mail">Email :</label>
            <input type="text" id="mail" name="mail"
                   value="${oldMail}"
                   class="${not empty errors['mail'] ? 'input-error' : ''}"
                   placeholder="Ex: contact@exemple.com">

            <c:if test="${not empty errors['mail']}">
                <span class="error-msg">${errors['mail']}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label for="categoryId">Catégorie :</label>
            <select id="categoryId" name="categoryId" required>
                <c:forEach items="${categories}" var="c">
                    <option value="${c.id}" ${String.valueOf(c.id).equals(oldCatId) ? 'selected' : ''}>
                            ${c.label}
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn-submit">Création de l'annonce</button>
    </form>
</div>
</body>
</html>