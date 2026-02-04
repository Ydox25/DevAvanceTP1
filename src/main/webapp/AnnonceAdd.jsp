<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        textarea {
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
        textarea:focus {
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

        .btn-submit:active {
            background-color: #3d8b40;
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
            <input type="text" id="title" name="title" placeholder="Titre de l'annonce" required>
        </div>

        <div class="form-group">
            <label for="description">Description :</label>
            <textarea id="description" name="description" placeholder="Description de l'annonce" required></textarea>
        </div>

        <div class="form-group">
            <label for="adress">Adresse :</label>
            <input type="text" id="adress" name="adress" placeholder="Adresse" required>
        </div>

        <div class="form-group">
            <label for="mail">Email :</label>
            <input type="text" id="mail" name="mail" placeholder="Email" required>
        </div>

        <button type="submit" class="btn-submit">Création de l'annonce</button>
    </form>
</div>
</body>
</html>