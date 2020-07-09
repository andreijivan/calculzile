<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css"/>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<table class="table table-hover" style="width: 100%">
    <thead>
    <tr>
        <th scope="col">Status</th>
        <th scope="col">Action</th>
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
        <th scope="col">Cost livrare</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.status}"/></td>
            <td>
                <button class="btn btn-outline-success butonFinalizat" type="submit"
                        data-cod_comanda="${order.codComanda}">Finalizat
                </button>
                <button class="btn btn-outline-success butonAnulat" type="submit"
                        data-cod_comanda="${order.codComanda}">Anulat
                </button>
                <button class="btn btn-outline-success butonPregatit"
<c:if test="${order.state eq 'pregatit'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
 input type="checkbox" data-toggle="toggle" data-cod_comanda="${order.codComanda}">Pregatit</button>

                <button class="btn btn-outline-success butonLivrat"
<c:if test="${order.state eq 'livrat'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
                    input type="checkbox" data-toggle="toggle" data-cod_comanda="${order.codComanda}">Livrat</button>


            </td>
            <td><c:out value="${order.codComanda}"/></td>
            <td><c:out value="${order.dataComanda}"/></td>
            <td><c:out value="${order.client}"/></td>
            <td class="produse" data-cod_produse="${order.produse}"><c:out value="${order.produse}"/></td>
            <td><c:out value="${order.adresa}"/></td>
            <td><c:out value="${order.localitate}"/></td>
            <td><c:out value="${order.codPostal}"/></td>
            <td><c:out value="${order.tara}"/></td>
            <td><c:out value="${order.telefon}"/></td>
            <td><c:out value="${order.email}"/></td>
            <td><c:out value="${order.observatii}"/></td>
            <td><c:out value="${order.valoareProduse}"/></td>
            <td><c:out value="${order.incasat}"/></td>
            <td><c:out value="${order.valoareLivrare}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="modal fade" id="modifyTableCells" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Modifica</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="text" id ="modalID">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-success" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-outline-success">Save changes</button>
            </div>
        </div>
    </div>
</div>
<script>
    $(".butonFinalizat").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/updateOrderTable",
            contentType: "application/json",
            data: {
                "codComanda": codComanda
            },
            success: function (data) {
                console.log(data);
                $("#tableDiv").html(data);
            },
            error: function () {
                $("#tableDiv").html("A aparut o eroare. Reincercati");
            }
        });
    })
    $(".butonAnulat").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/deleteOrder",
            contentType: "application/json",
            data: {
                "codComanda": codComanda
            },
            success: function (data) {
                console.log(data);
                $("#tableDiv").html(data);
            },
            error: function () {
                $("#tableDiv").html("A aparut o eroare. Reincercati");
            }
        });
    })
    $(".butonPregatit").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/changeStatePregatit",
            contentType: "application/json",
            data: {
                "codComanda": codComanda
            },
            success: function (data) {
                console.log(data);
                $("#tableDiv").html(data);
            },
            error: function () {
                $("#tableDiv").html("A aparut o eroare. Reincercati");
            }
        });
    })
    $(".butonLivrat").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/changeStateLivrat",
            contentType: "application/json",
            data: {
                "codComanda": codComanda
            },
            success: function (data) {
                console.log(data);
                $("#tableDiv").html(data);
            },
            error: function () {
                $("#tableDiv").html("A aparut o eroare. Reincercati");
            }
        });
    })
    $("#modifyTableCells").on('shown.bs.modal', function () {
        console.log();

    })
    $(".produse").dblclick(function () {
        let codComanda = $(this).data("cod_comanda");
        let produse = $(this).data("cod_produse");
        $("#modalID").val(produse);
        $("#modifyTableCells").modal("show");
    })
</script>
<script>
    $("#noOfOrders").html("${fn:length(orders)}")
</script>