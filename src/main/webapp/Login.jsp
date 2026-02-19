<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Connexion</title>
  <style>
    body { font-family: sans-serif; background-color: #f5f5f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
    .login-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 300px; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; }
    input { width: 100%; padding: 8px; box-sizing: border-box; }
    button { width: 100%; padding: 10px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:hover { background: #45a049; }
    .error { color: red; margin-bottom: 10px; font-size: 0.9em; }
  </style>
</head>
<body>

<div class="login-container">
  <h2 style="text-align: center">Connexion</h2>

  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>

  <form action="Login" method="POST">
    <div class="form-group">
      <label>Nom d'utilisateur :</label>
      <input type="text" name="username" required>
    </div>
    <div class="form-group">
      <label>Mot de passe :</label>
      <input type="password" name="password" required>
    </div>
    <button type="submit">Se connecter</button>
  </form>
</div>

</body>
</html>