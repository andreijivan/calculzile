<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <link rel="icon" href="https://www.sspolitehnica.ro/wp-content/uploads/2017/07/pt-2-32x32.png" sizes="32x32">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css"/>
    <title>Poli Orders</title>
    <nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#" id="totalOrders" ><span id="noOfOrders">0</span> comenzi</a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <button class="btn btn-outline-success" type="submit" id="seeAllOrders">Vezi toate comenzile</button>
                <div class="dropdown">
                    <button class="btn btn-outline-success dropdown-toggle" type="button" id="dropdownFinalizedButton"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Vezi comenzi finalizate
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" id="plataCash">Plata Cash</a>
                        <a class="dropdown-item" href="#" id="plataCard">Plata Card</a>
                        <a class="dropdown-item" href="#" id="plataBanca">Plata Banca</a>
                        <a class="dropdown-item" href="#" id="incasariTotale">Incasari Totale</a>
                    </div>
                </div>
                <button class="btn btn-outline-success" type="submit" id="seeVirtualOrders">Vezi comenzi produse
                    virtuale
                </button>
                <button class="btn btn-outline-success" type="submit" id="seeDeletedOrders">Vezi comenzi anulate
                </button>
                <div class="dropdown">
                    <button class="btn btn-outline-success dropdown-toggle" type="button" id="dropdownExportButton"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Exporta
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" id="xls">XLS</a>
                        <a class="dropdown-item" href="#" id="pdf">PDF</a>
                    </div>
                </div>
                <div class="dropdown">
                    <button class="btn btn-outline-success dropdown-toggle" type="button" id="dropdownSortButton"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Sorteaza
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" id="seeLocalOrders">Comenzi locale</a>
                        <a class="dropdown-item" href="#" id="seeNationalOrders">Comenzi nationale</a>
                        <a class="dropdown-item" href="#" id="seeInternationalOrders">Comenzi internationale</a>
                    </div>
                </div>

                <a href="${pageContext.request.contextPath}/logout">
                    <button class="btn btn-outline-success" type="submit" name="logoutButton">Logout</button>
                </a>
                <button class="btn btn-outline-success" type="submit" id="seeArchivedOrders">Arhiva
                </button>

            </ul>
            <div class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="text" placeholder="Search Cod comanda" aria-label="Search" name="searchOrder" id="searchInputElement">
                <button class="btn btn-outline-success" type="button" id="searchButton">Search</button>
            </div>
        </div>
    </nav>
</head>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="//cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<body>
<div id="tableDiv">
</div>
</body>
</html>

<script>
    $(document).ready(function () {
        getTable("showAllOrders");
    })

    $("#seeAllOrders").click(function () {
        getTable("showAllOrders");
    })

    $("#seeVirtualOrders").click(function () {
        getTable("showVirtualOrders");
    })

    $("#searchButton").click(function () {
        let searchedValue = $("#searchInputElement").val();
        getTable("searchOrder?searchValue=" + searchedValue);
    })
    $("#seeLocalOrders").click(function () {
        getTable("showLocalOrders");
    })
    $("#seeNationalOrders").click(function () {
        getTable("showNationalOrders");
    })
    $("#seeInternationalOrders").click(function () {
        getTable("showInternationalOrders");
    })
    $("#seeDeletedOrders").click(function () {
        getTable("showDeletedOrders");
    })
    $("#plataCash").click(function () {
        getTable("showCashOrders");
    })
    $("#plataCard").click(function () {
        getTable("showCardOrders");
    })
    $("#plataBanca").click(function () {
        getTable("showBankOrders");
    })
    $("#xls").click(function () {
        $("#tableDiv").table2excel({
            filename: "SituatieLunara.xls"
        });
    });
    $("#incasariTotale").click(function () {
        getTable("showTotalRevenue");
    })

    function getTable(url) {
        $("#tableDiv").html("Loading...");
        $.ajax({
            type: "GET",
            url: "https://polishoporders.herokuapp.com/" + url,
            contentType: "application/json",
            success: function (data) {
                if(data === "NOT_FOUND")
                    alert("Codul cautat nu a fost gasit");
                else {
                    $("#tableDiv").html(data);
                }
            },
            error: function () {
                $("#tableDiv").html("A aparut o eroare. Reincercati");
            }
        });

    }

</script>



