<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css"/>

<form>
    <label for="beginDate">De la</label>
    <input type="date" id="beginDate" name="beginDate">
    <label for="endDate">Pana la</label>
    <input type="date" id="endDate" name="endDate">
    <button class="btn btn-outline-success" type="button" id="searchRange">Submit</button>
</form>
<c:out value="${materialeTotal}"/>
<div id="resultsTable">
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col"></th>
        <th scope="col">Materiale</th>
        <th scope="col">Carduri membrii</th>
        <th scope="col">Donatie</th>
        <th scope="col">Bilete virtuale</th>
        <th scope="col">SubTotal</th>
        <th scope="col">Transport</th>
        <th scope="col">TOTAL</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td scope="row">Card Netopia</td>
        <td id="materialeCard"><c:out value="${materialeCard}"/></td>
        <td id="carduriCard"><c:out value="${carduriCard}"/></td>
        <td id="donatiiCard"><c:out value="${donatieCard}"/></td>
        <td id="bileteCard"><c:out value="${biletVirtualCard}"/></td>
        <td id="subTotalCard"><c:out value="${subTotalCard}"/></td>
        <td id="transportCard"><c:out value="${transportCard}"/></td>
        <td id="TotalCard"><c:out value="${TOTALCARD}"/></td>
    </tr>
    <tr>
        <td scope="row">Transfer Bancar</td>
        <td id="materialeTB"><c:out value="${materialeTB}"/></td>
        <td id="carduriTB"><c:out value="${carduriTB}"/></td>
        <td id="donatiiTB"><c:out value="${donatieTB}"/></td>
        <td id="bileteTB"><c:out value="${biletVirtualTB}"/></td>
        <td id="subTotalTB"><c:out value="${subTotalTB}"/></td>
        <td id="transportTB"><c:out value="${transportTB}"/></td>
        <td id="TotalTB"><c:out value="${TOTALTB}"/></td>
    </tr>
    <tr>
        <td scope="row">Ramburs GLS</td>
        <td id="materialeGLS"><c:out value="${materialeGLS}"/></td>
        <td id="carduriGLS"><c:out value="${carduriGLS}"/></td>
        <td id="donatiiGLS"><c:out value="${donatieGLS}"/></td>
        <td id="bileteGLS"><c:out value="${biletVirtualGLS}"/></td>
        <td id="subTotalGLS"><c:out value="${subTotalGLS}"/></td>
        <td id="transportGLS"><c:out value="${transportGLS}"/></td>
        <td id="TotalGLS"><c:out value="${TOTALGLS}"/></td>
    </tr>
    <tr>
        <td scope="row">Cash</td>
        <td id="materialeCash"><c:out value="${materialeCash}"/></td>
        <td id="carduriCash"><c:out value="${carduriCash}"/></td>
        <td id="donatiiCash"><c:out value="${donatieCash}"/></td>
        <td id="bileteCash"><c:out value="${biletVirtualCash}"/></td>
        <td id="subTotalCash"><c:out value="${subTotalCash}"/></td>
        <td id="transportCash">0</td>
        <td id="TotalCash"><c:out value="${TOTALCASH}"/></td>
    </tr>
    <tr>
        <td scope="row">TOTAL</td>
        <td id="materialeTotal"><c:out value="${materialeTotal}"/></td>
        <td id="carduriTotal"><c:out value="${carduriTotal}"/></td>
        <td id="donatiiTotal"><c:out value="${donatieTotal}"/></td>
        <td id="bileteTotal"><c:out value="${biletVirtualTotal}"/></td>
        <td id="subTotalTotal"><c:out value="${subTotalTotal}"/></td>
        <td id="transportTotal"><c:out value="${transportTotal}"/></td>
        <td id="TotalTotal"><c:out value="${TOTALTOTAL}"/></td>
    </tr>
    </tbody>
</table>
</div>

<div id="resultsProducts">
    <table class="center">
        <thead>
        <tr>
            <th scope="col">Denumire Produs</th>
            <th scope="col">Cantitate</th>
            <th scope="col">Total</th>
        </tr>
      <%--  <tr>
            <th scope="col">TOTAL</th>
        </tr>--%>
        </thead>

        <tbody>
        <c:forEach var="soldProductItem" items="${soldProductsList}">
            <tr>
                <td><c:out value="${soldProductItem.name}"/></td>
                <td><c:out value="${soldProductItem.quantity}"/></td>
                <td><c:out value="${soldProductItem.price}"/></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>

<script>
    $("#searchRange").click(function () {
        $.ajax({
            type: "GET",
            url: "https://polishoporders.herokuapp.com/rangeResults",
            contentType: "application/json",
            data: {
                startDate: $("#beginDate").val(),
                endDate: $("#endDate").val()},
            success: function (data) {
                let first = data.indexOf("<table")
                let index = "</table>"
                let end = data.indexOf(index)
                let other = data.substring(first,end+index.length)
                $("#resultsTable").html(other)
                data = data.substring(end+index.length);
                let firstSecondTable = data.indexOf("<table");
                let indexSecondTable = "</table>"
                let endSecondTable = data.indexOf(indexSecondTable)
                let otherSecondTable = data.substring(firstSecondTable,endSecondTable+indexSecondTable.length)
                $("#resultsProducts").html(otherSecondTable);

                console.log(otherSecondTable);
            },
            error: function () {
                $("#resultsProducts").html("A aparut o eroare. Reincercati");
            }
        });
    })
</script>

