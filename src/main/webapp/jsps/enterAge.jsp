<%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 5/4/2020
  Time: 7:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>EnterAge</title>
</head>
<body>
<form method="post">
    <div class="container">
        <div class="offset-md-3 col-md-3">
            <div class="form-group">
                <label for="yearOfBirth">Enter date of birth</label>
                <input type="date" class="form-control" id="yearOfBirth" name="yearOfBirth">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </div>
</form>

</body>
</html>
