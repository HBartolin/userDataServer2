var sifarnikOsobaTablica="sifarnikOsobaTablicaBody";
var dataRezultatSO;

function sifarniciOsoba() {
    var projektUrl_=`${serverUrl}sifarniciOsoba`;

    pozoviRestServis(projektUrl_, sifarniciOsobaRest_);
}

function sifarniciOsobaRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
        dataRezultatSO=data.rezultat;

        dataRezultatSO.forEach((redak, i) => ucitajRedakSifarniciOsobaTablica(redak, i));
    }

    tablicaPagination(data);
}

function novaOsoba() {
    hideGreska();

    var inputIme_=document.getElementById("inputIme");
    var inputPrezime_=document.getElementById("inputPrezime");
    var inputId_=document.getElementById("inputId");

    inputIme_.value="";
    inputPrezime_.value="";
    inputId_.innerText="";
}

function tablicaSifarnikOsobaSlijedece() {
    tablicaSifarnikOsobaSlijedecePrethodno(1);
}

function tablicaSifarnikOsobaPrethodno() {
    tablicaSifarnikOsobaSlijedecePrethodno(0);
}

function tablicaSifarnikOsobaSlijedecePrethodno(smjer) {
    var pTablicaPrethodno_=document.getElementById("pTablicaPrethodno");
    var pTablicaSlijedece_=document.getElementById("pTablicaSlijedece");

    var projektUrl=`${serverUrl}tablicaSifarnikOsoba?pageNumber=`;

    if(smjer==0) {
        projektUrl+=pTablicaPrethodno_.innerText;
    } else {
        projektUrl+=pTablicaSlijedece_.innerText;
    }

    pozoviRestServis(projektUrl, tablicaSifarnikOsobaSlijedecePrethodnoRest_);
}

function tablicaSifarnikOsobaSlijedecePrethodnoRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    } 
    
    obrisiRetke2(sifarnikOsobaTablica);

    data.rezultat.forEach((redak, i) => ucitajRedakSifarniciOsobaTablica(redak, i));

    tablicaPagination(data);
}

function editirajSifarnikOsoba() {
    hideGreska();

    var inputIme_=document.getElementById("inputIme");
    var inputPrezime_=document.getElementById("inputPrezime");
    var inputId_=document.getElementById("inputId"); 

    var projektUrl=`${serverUrl}editirajSifarnikOsoba?id=${inputId_.innerText}&ime=${inputIme_.value}&prezime=${inputPrezime_.value}`;

    pozoviRestServis(projektUrl, editirajSifarnikOsobaRest_);
}

function editirajSifarnikOsobaRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    } else {
        $('#editSifarnikOsoba').modal('hide');
    }

    obrisiRetke2(sifarnikOsobaTablica);

    if(data.rezultat.length>0) {
        dataRezultatSO=data.rezultat;

        dataRezultatSO.forEach((redak, i) => ucitajRedakSifarniciOsobaTablica(redak, i));
    }

    tablicaPagination(data);
}

function ucitajRedakSifarniciOsobaTablica(redak, iRedak) {
    var table = document.getElementById(sifarnikOsobaTablica);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);
    var cell4 = row.insertCell(i++);

    cell1.innerHTML = redak.id;
    cell1.className = "text-right";
    cell2.innerHTML = redak.prezime;
    cell3.innerHTML = redak.ime;
    cell4.innerHTML = '<div class="btn-group" role="group">' +
            `<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editSifarnikOsoba" onclick="prikaziSifarnikOsoba(${redak.id})">Uredi</button>` +
            `<button type="button" class="btn btn-outline-primary" onclick="prikaziOsobaValuta(${redak.id})">Detalji</button>` +
           '</div>';
}

function prikaziSifarnikOsoba(id) {
    hideGreska();

    var inputIme_=document.getElementById("inputIme");
    var inputPrezime_=document.getElementById("inputPrezime");
    var inputId_=document.getElementById("inputId");

    dataRezultatSO.forEach((redak, i) => {
        if(redak.id==id) {
            inputIme_.value=redak.ime;
            inputPrezime_.value=redak.prezime;
            inputId_.innerText=id;
        }
    });
}

function prikaziOsobaValuta(id) {
	hideGreska();
	   
    window.open(`/v1/html/osobaValuta.html?idSifarnikOsoba=${id}`, "_parent");
}
