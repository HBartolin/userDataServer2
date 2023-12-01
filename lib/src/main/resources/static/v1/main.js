var serverUrl=`${document.location.origin}/api/`;
var greska="greska";

function postaviGresku(e) {
    console.log(e);
    
    var msg="";
	e.forEach(i => msg+="<div class='alert alert-danger' role='alert'>" + i + "</div> ");
	
    //var msg="Error: " + e; 
    var greskaElements = document.getElementsByName(greska);

    greskaElements.forEach((i) => {   
        i.style['display'] = 'block';
        i.innerHTML = msg; 
    });
}

function postaviOK(msg) {
    var okModalMsg=document.getElementById("okModalMsg");
    okModalMsg.innerHTML = msg;

    $('#okModal').modal("show");
}

function hideGreska() {
    var greskaElements=document.getElementsByName(greska);

    greskaElements.forEach((i) => {
        i.style['display'] = 'none';
        i.innerHTML = "";
    });
}

function pozoviRestServis(mojUrl, ucitaj) {
	console.log(mojUrl);
	
    fetch(mojUrl)
        .then((response) => response.json())
        .then(ucitaj)
        .catch((error) => postaviGresku(error));
}

function pozoviRestServisPut(mojUrl, sendData, ucitaj) {
    console.log(mojUrl);
    console.table(sendData);

	fetch(mojUrl, {
        method: 'PUT',
        body: JSON.stringify(sendData),
        headers: new Headers({
            'Content-Type': 'application/json; charset=UTF-8'
        })
    })
    .then((response) => response.json())
    .then(ucitaj)
    .catch((error) => postaviGresku(error));
}

function tablicaPagination(data) {
    var projektTablicaPrethodno_=document.getElementById("projektTablicaPrethodno");
    projektTablicaPrethodno_.disabled = true;
    projektTablicaPrethodno_.classList.remove("btn-secondary");
    projektTablicaPrethodno_.classList.remove("btn-outline-secondary");
    var projektTablicaSlijedece_=document.getElementById("projektTablicaSlijedece");
    projektTablicaSlijedece_.disabled = true;
    projektTablicaSlijedece_.classList.remove("btn-secondary");
    projektTablicaSlijedece_.classList.remove("btn-outline-secondary");
    var pTablicaPrethodno_=document.getElementById("pTablicaPrethodno");
    var pTablicaSlijedece_=document.getElementById("pTablicaSlijedece");

    var pageNumber=data.rezultatPage.pageNumber;
    var totalPages=data.rezultatPage.totalPages;

    if(0==pageNumber && (1==totalPages || 0==totalPages)) {
        pTablicaPrethodno_.innerText=0;
        pTablicaSlijedece_.innerText=1;

        projektTablicaPrethodno_.classList.add("btn-outline-secondary");
        projektTablicaSlijedece_.classList.add("btn-outline-secondary");
    } else if(0==pageNumber) {
        pTablicaPrethodno_.innerText=0;
        pTablicaSlijedece_.innerText=pageNumber+1;

        projektTablicaPrethodno_.classList.add("btn-outline-secondary");
        projektTablicaSlijedece_.disabled = false;
        projektTablicaSlijedece_.classList.add("btn-secondary");
    } else if(totalPages==pageNumber+1){
        pTablicaPrethodno_.innerText=pageNumber-1;
        pTablicaSlijedece_.innerText=pageNumber;

        projektTablicaPrethodno_.disabled = false;
        projektTablicaPrethodno_.classList.add("btn-secondary");
        projektTablicaSlijedece_.classList.add("btn-outline-secondary");
    } else {
        pTablicaPrethodno_.innerText=pageNumber-1;
        pTablicaSlijedece_.innerText=pageNumber+1;

        projektTablicaPrethodno_.disabled = false;
        projektTablicaPrethodno_.classList.add("btn-secondary");
        projektTablicaSlijedece_.disabled = false;
        projektTablicaSlijedece_.classList.add("btn-secondary");
    }
}

function obrisiRetke2(tablica) {
    var table = document.getElementById(tablica);
    table.innerHTML='';
}

function formatMoney(val) {
	return (val).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}


