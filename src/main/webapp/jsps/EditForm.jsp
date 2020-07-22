<%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 7/10/2020
  Time: 11:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Edit Form</title>
</head>
<body>
<div class="modal fade" id="modalEditForm" tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold">Edit order</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body mx-3">
                <form id="modalForm33">

                    <div class="md-form mb-5">
                        <input type="text" id="statusClient" name="status" class="form-control validate">
                        <label data-error="wrong" data-success="right">Status</label>
                    </div>
                    <div class="md-form mb-5">
                        <input type="text" id="codComandaClient" name="codComanda" class="form-control validate">
                        <label data-error="wrong" data-success="right">Cod Comanda</label>
                    </div>
                    <div class="md-form mb-5">
                        <input type="text" id="dataComandaClient" name="dataComanda" class="form-control validate">
                        <label data-error="wrong" data-success="right">Data Comanda</label>
                    </div>
                <div class="md-form mb-5">
                    <input type="text" id="numeClient" name="client" class="form-control validate">
                    <label data-error="wrong" data-success="right">Client</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="produseClient" name="produse" class="form-control validate">
                    <label data-error="wrong" data-success="right">Produse</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="adresaClient" name="adresa" class="form-control validate">
                    <label data-error="wrong" data-success="right">Adresa</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="localitateClient" name="localitate" class="form-control validate">
                    <label data-error="wrong" data-success="right">Localitate</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="codPostalClient" name="codPostal" class="form-control validate">
                    <label data-error="wrong" data-success="right">Cod Postal</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="taraClient" name="tara" class="form-control validate">
                    <label data-error="wrong" data-success="right">Tara</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="telefonClient" name="telefon" class="form-control validate">
                    <label data-error="wrong" data-success="right">Telefon</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="emailClient" name="email" class="form-control validate">
                    <label data-error="wrong" data-success="right">Email</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="observatiiClient" name="observatii" class="form-control validate">
                    <label data-error="wrong" data-success="right">Observatii</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="valoareProduseClient" name="valoareProduse" class="form-control validate">
                    <label data-error="wrong" data-success="right">Valoare Produse</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="incasatClient" name="incasat" class="form-control validate">
                    <label data-error="wrong" data-success="right">Incasat</label>
                </div>
                <div class="md-form mb-5">
                    <input type="text" id="costLivrareClient" name="valoareLivrare" class="form-control validate">
                    <label data-error="wrong" data-success="right">Cost Livrare</label>
                </div>
                </form>
            </div>
            <div class="modal-footer d-flex justify-content-center">
                <button class="btn btn-outline-success butonSaveEdit" type="submit"
                        data-cod_comanda="${order.codComanda}" data-dismiss="modal"
                >Save Changes</button>
            </div>
        </div>
    </div>
</div>
</body>

</html>
<script>
    $(".butonSaveEdit").click(function () {
        let dataPrelucrata ={}
       let formData = $('#modalForm33').serializeArray()
        $.each(formData,function (e,field) {
            dataPrelucrata[field.name]=field.value;
        })
        $.ajax({
            type: "POST",
            url: "https://polishoporders.herokuapp.com/modifyOrder",
            contentType: "application/json",
            data:JSON.stringify(dataPrelucrata),

            success: function (data) {
                $("#modalEditForm").modal("hide");
                console.log(data);
                $("#tableDiv").html(data);
            },
            error: function () {
                $("#modalEditForm").modal("hide");
                $("#tableDiv").html("A aparut o eroare. Reincercati");
            }
        });
    })
</script>