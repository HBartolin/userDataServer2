var dataRezultatSO;
var sifarnikPodugovaracaTablicaBody="sifarnikPodugovaracaTablicaBody";

function urediPodugovarace() {
    var projektUrl_=`${serverUrl}urediPodugovarace`;

    pozoviRestServis(projektUrl_, sifarnikPodugovaracaRest_);
}

function sifarnikPodugovaracaRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
    	dataRezultatSO=data.rezultat;
        obrisiRetke2(sifarnikPodugovaracaTablicaBody);

        dataRezultatSO.forEach((redak, i) => ucitajRedakSifarnikPodugovaracaTablica(redak, i));

        $('#editSifarnikPodugovarac').modal('hide');
    }
}

function ucitajRedakSifarnikPodugovaracaTablica(redak, i) {
    var table = document.getElementById(sifarnikPodugovaracaTablicaBody);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);

    cell1.innerHTML = redak.id;
    cell1.className = "text-right";
    cell2.innerHTML = redak.naziv;
    cell3.innerHTML = '<div class="btn-group" role="group">' +
                        `<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editSifarnikPodugovarac" onclick="prikaziSifarnikPodugovarac(${redak.id})">Prikaži</button>` +
                    '</div>';
}

function noviPodugovarac() {
    hideGreska();

    var inputNaziv_=document.getElementById("inputNaziv");
    inputNaziv_.value="";
    var inputId_=document.getElementById("inputId");
    inputId_.innerText="";
}

function noviPodugovarac2() {
    hideGreska();

    var inputId_=document.getElementById("inputId");
    var inputNaziv_=document.getElementById("inputNaziv");

    var projektUrl_=`${serverUrl}editirajSifarnikPodugovaraca?id=${inputId_.innerText}&naziv=${inputNaziv_.value}`;

    pozoviRestServis(projektUrl_, sifarnikPodugovaracaRest_);
}

function prikaziSifarnikPodugovarac(id) {
    hideGreska();

    var inputNaziv_=document.getElementById("inputNaziv");
    var inputId_=document.getElementById("inputId");

    dataRezultatSO.forEach((redak, i) => {
        if(redak.id==id) {
            inputNaziv_.value=redak.naziv;
            inputId_.innerText=id;
        }
    });
}