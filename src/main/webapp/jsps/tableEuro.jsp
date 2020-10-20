<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css"/>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<jsp:include page="EditFormEuro.jsp"/>

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
                <button class="btn btn-outline-success butonFinalizatEuro" type="submit"
                        <c:if test="${order.state eq 'finalizat'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
                        data-cod_comanda="${order.codComanda}">Finalizat
                </button>
                <button class="btn btn-outline-success butonAnulatEuro" type="submit"
                        <c:if test="${order.state eq 'anulat'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
                        data-cod_comanda="${order.codComanda}">Anulat
                </button>
                <button class="btn btn-outline-success butonPregatitEuro"
                        <c:if test="${order.state eq 'pregatit'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
                        input type="checkbox" data-toggle="toggle" data-cod_comanda="${order.codComanda}">Pregatit
                </button>

                <button class="btn btn-outline-success butonLivratEuro"
                        <c:if test="${order.state eq 'livrat'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
                        input type="checkbox" data-toggle="toggle" data-cod_comanda="${order.codComanda}">Livrat
                </button>

                <a href="" class="btn btn-outline-success butonEditEuro" type="submit"
                   data-cod_comanda="${order.codComanda}" data-toggle="modal" data-target="#modalEditFormEuro"
                   clientOrderNumber="${order.codComanda}"
                   clientOrderDate="${order.dataComanda}"
                   clientStatus="${order.status}"
                   clientName="${order.client}"
                   clientProducts="${fn:escapeXml(order.produse)}"
                   clientAddress="${order.adresa}"
                   clientCity="${order.localitate}"
                   clientPostalCode="${order.codPostal}"
                   clientCountry="${order.tara}"
                   clientPhone="${order.telefon}"
                   clientEmail="${order.email}"
                   clientObs="${order.observatii}"
                   clientProductsValue="${order.valoareProduse}"
                   clientTotalPaid="${order.incasat}"
                   clientShippingCost="${order.valoareLivrare}">Edit
                </a>

            </td>
            <td><c:out value="${order.codComanda}"/></td>
            <td><c:out value="${order.dataComanda}"/></td>
            <td><c:out value="${order.client}"/></td>
            <td><c:out value="${order.produse}"/></td>
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

<script>
    $(".butonFinalizatEuro").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/updateOrderTableEuro",
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
    $(".butonAnulatEuro").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/deleteEuroOrder",
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
    $(".butonPregatitEuro").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/changeStatePregatitEuro",
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
    $(".butonLivratEuro").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/changeStateLivratEuro",
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
</script>
<script>
    $("#noOfOrders").html("${fn:length(orders)}")
</script>

<script>
    $('#modalEditFormEuro').on('show.bs.modal', function (e) {
        let opener = e.relatedTarget;
        $('#modalEditFormEuro').find('[id="statusClient"]').val($(opener).attr('clientStatus'));
        $('#modalEditFormEuro').find('[id="codComandaClient"]').val($(opener).attr('clientOrderNumber'));
        $('#modalEditFormEuro').find('[id="dataComandaClient"]').val($(opener).attr('clientOrderDate'));
        $('#modalEditFormEuro').find('[id="numeClient"]').val($(opener).attr('clientName'));
        $('#modalEditFormEuro').find('[id="produseClient"]').val($(opener).attr('clientProducts'));
     /*   console.log($(opener).attr('clientProducts'));*/
        $('#modalEditFormEuro').find('[id="adresaClient"]').val($(opener).attr('clientAddress'));
        $('#modalEditFormEuro').find('[id="localitateClient"]').val($(opener).attr('clientCity'));
        $('#modalEditFormEuro').find('[id="codPostalClient"]').val($(opener).attr('clientPostalCode'));
        $('#modalEditFormEuro').find('[id="taraClient"]').val($(opener).attr('clientCountry'));
        $('#modalEditFormEuro').find('[id="telefonClient"]').val($(opener).attr('clientPhone'));
        $('#modalEditFormEuro').find('[id="emailClient"]').val($(opener).attr('clientEmail'));
        $('#modalEditFormEuro').find('[id="observatiiClient"]').val($(opener).attr('clientObs'));
        $('#modalEditFormEuro').find('[id="valoareProduseClient"]').val($(opener).attr('clientProductsValue'));
        $('#modalEditFormEuro').find('[id="incasatClient"]').val($(opener).attr('clientTotalPaid'));
        $('#modalEditFormEuro').find('[id="costLivrareClient"]').val($(opener).attr('clientShippingCost'));
    });
</script>