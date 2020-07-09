var produse = 3000;
document.getElementById('produse').innerHTML = produse;
function modifyProduseCell(target) {
    var newProduse = prompt("Enter new value of Produse", produse);
    produse = newProduse;
    
}