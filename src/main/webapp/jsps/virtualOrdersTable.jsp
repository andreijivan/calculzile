<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css"/>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<jsp:include page="EditForm.jsp"/>

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
                        <c:if test="${order.state eq 'finalizat'}">style="color: white; border-color: rebeccapurple; background-color: rebeccapurple;"</c:if>
                        data-cod_comanda="${order.codComanda}">Finalizat
                </button>
                <button class="btn btn-outline-success butonAnulat" type="submit"
                        data-cod_comanda="${order.codComanda}">Anulat
                </button>

             <%--   <a href="" class="btn btn-outline-success butonEdit" type="submit"
                   data-cod_comanda="${order.codComanda}" data-toggle="modal" data-target="#modalEditForm"
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
                </a>--%>

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
/*    $(".butonFinalizat").click(function () {
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
    })*/
    $(".butonAnulat").click(function () {
        let codComanda = $(this).data("cod_comanda");
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/deleteVirtualOrder",
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

<%--
<script>
    $('#modalEditForm').on('show.bs.modal', function (e) {
        let opener = e.relatedTarget;
        $('#modalEditForm').find('[id="statusClient"]').val($(opener).attr('clientStatus'));
        $('#modalEditForm').find('[id="codComandaClient"]').val($(opener).attr('clientOrderNumber'));
        $('#modalEditForm').find('[id="dataComandaClient"]').val($(opener).attr('clientOrderDate'));
        $('#modalEditForm').find('[id="numeClient"]').val($(opener).attr('clientName'));
        $('#modalEditForm').find('[id="produseClient"]').val($(opener).attr('clientProducts'));
     /*   console.log($(opener).attr('clientProducts'));*/
        $('#modalEditForm').find('[id="adresaClient"]').val($(opener).attr('clientAddress'));
        $('#modalEditForm').find('[id="localitateClient"]').val($(opener).attr('clientCity'));
        $('#modalEditForm').find('[id="codPostalClient"]').val($(opener).attr('clientPostalCode'));
        $('#modalEditForm').find('[id="taraClient"]').val($(opener).attr('clientCountry'));
        $('#modalEditForm').find('[id="telefonClient"]').val($(opener).attr('clientPhone'));
        $('#modalEditForm').find('[id="emailClient"]').val($(opener).attr('clientEmail'));
        $('#modalEditForm').find('[id="observatiiClient"]').val($(opener).attr('clientObs'));
        $('#modalEditForm').find('[id="valoareProduseClient"]').val($(opener).attr('clientProductsValue'));
        $('#modalEditForm').find('[id="incasatClient"]').val($(opener).attr('clientTotalPaid'));
        $('#modalEditForm').find('[id="costLivrareClient"]').val($(opener).attr('clientShippingCost'));
    });
</script>--%>
