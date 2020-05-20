<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <link rel="icon" href="https://www.sspolitehnica.ro/wp-content/uploads/2017/07/pt-2-32x32.png" sizes="32x32">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsps/css.css" />

    <title>Poli Orders</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Bine ai venit Andrei. Ai 100 comenzi neprocesate.</a>
</nav>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <button class="btn btn-outline-success" type="submit">Vezi toate comenzile</button>
            <button class="btn btn-outline-success" type="submit">Elimina produse virtuale</button>
            <button class="btn btn-outline-success" type="submit">Vezi comenzi finalizate</button>
            <button class="btn btn-outline-success" type="submit">Vezi comenzi produse virtuale</button>
            <div class="dropdown">
                <button class="btn btn-outline-success dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Sorteaza
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#">Comenzi locale</a>
                    <a class="dropdown-item" href="#">Comenzi nationale</a>
                    <a class="dropdown-item" href="#">Comenzi internationale</a>
                </div>
            </div>
            <button class="btn btn-outline-success" type="submit" name="logoutButton">Logout</button>
        </ul>
        <form class="form-inline my-2 my-lg-0" method="post">
            <input class="form-control mr-sm-2" type="text" placeholder="Search Cod comanda" aria-label="Search" name="searchOrder">
            <button class="btn btn-outline-success" type="submit" name="searchButton" value="searchButton">Search</button>
        </form>
    </div>
</nav>
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">Status</th>
        <th scope="col">Nr</th>
        <th scope="col">Cod comanda</th>
        <th scope="col">Data</th>
        <th scope="col">Client</th>
        <th scope="col">Produse</th>
        <th scope="col">Adresa</th>
        <th scope="col">Localitate</th>
        <th scope="col">Cod Postal</th>
        <th scope="col">Tara</th>
        <th scope="col">Numar telefon</th>
        <th scope="col">Email</th>
        <th scope="col">Observatii</th>
        <th scope="col">Valoare produse</th>
        <th scope="col">Incasat</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.status}" /></td>
            <td><c:out value="${order.nr}" /></td>
            <td><c:out value="${order.codComanda}" /></td>
            <td><c:out value="${order.dataComanda}" /></td>
            <td><c:out value="${order.client}" /></td>
            <td><c:out value="${order.produse}" /></td>
            <td><c:out value="${order.adresa}" /></td>
            <td><c:out value="${order.localitate}" /></td>
            <td><c:out value="${order.codPostal}" /></td>
            <td><c:out value="${order.tara}" /></td>
            <td><c:out value="${order.telefon}" /></td>
            <td><c:out value="${order.email}" /></td>
            <td><c:out value="${order.observatii}" /></td>
            <td><c:out value="${order.valoareProduse}" /></td>
            <td><c:out value="${order.incasat}" /></td>
        </tr>
    </c:forEach>
    </thead>
    <tbody>
    </tbody>
</table>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>

