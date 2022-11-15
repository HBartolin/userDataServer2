var dataRezultatSO;
var osobaValutaTablicaBody="osobaValutaTablicaBody";
var idProjektDetalji="idProjektDetalji";

function sveOsobeValute() {
    var url = new URL(document.URL);
    var id = url.searchParams.get("idSifarnikOsoba");
    var projektUrl_=`${serverUrl}osobaValuta?idSifarnikOsoba=${id}`;

    var var1_ = url.searchParams.get("var1");
    var nav0_=document.getElementById("nav0");
    var nav1_=document.getElementById("nav1");

    if(var1_==1) {
        nav0_.style['display'] = 'none';
        nav1_.style['display'] = 'block';
    } else {
        nav0_.style['display'] = 'block';
        nav1_.style['display'] = 'none';
    }

    pozoviRestServis(projektUrl_, osobaValutaRest_);
}

function osobaValutaRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    } 

    dataRezultatSO=data.rezultat;

    if(dataRezultatSO.length>0) {
        dataRezultatSO.forEach((redak, i) => ucitajRedakOsobaValutaTablica(redak, i));

        var prvi=dataRezultatSO[0];
        var osobaDetalji_=document.getElementById("osobaDetalji");
        osobaDetalji_.innerText=`- ${prvi.sifarnikOsoba.imePrezime}`;

        var osobaDetalji1_=document.getElementById("osobaDetalji1");
        osobaDetalji1_.innerText=`- ${prvi.sifarnikOsoba.imePrezime}`;
    }
    
    tablicaPagination(data);
}

function ucitajRedakOsobaValutaTablica(redak, iRedak) {
    var table = document.getElementById(osobaValutaTablicaBody);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);
    var cell4 = row.insertCell(i++);
    var cell5 = row.insertCell(i++);
    var cell6 = row.insertCell(i++);

    cell1.innerHTML = redak.id;
    cell1.className="text-right";
    cell2.innerHTML = redak.sifarnikDatumaOd.datumPetak;
    cell3.innerHTML = redak.sifarnikDatumaDo.datumPetak;
    cell4.innerHTML = redak.band;
    cell5.innerHTML = redak.cijena
    cell6.innerHTML = '<div class="btn-group" role="group">' +
        `<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#novaOsobaValuta" onclick="urediOsobaValuta(${redak.id})">Uredi</button>` +
        '</div>';
}

function novaOsobaValuta() {
    hideGreska();

    var band_=document.getElementById("iBand");
    band_.value='';
    var cijena_=document.getElementById("iCijena");
    cijena_.value='';
    var datumOd_=document.getElementById("iDatumOd");
    datumOd_.value='';
    var datumDo_=document.getElementById("iDatumDo");
    datumDo_.value='';
    var id_=document.getElementById("iId");
    id_.innerText='';
    var ts_=document.getElementById("iTs");
    ts_.innerText=0;
}

function urediOsobaValuta(id) {
    hideGreska();

    var band_=document.getElementById("iBand");
    var cijena_=document.getElementById("iCijena");
    var datumOd_=document.getElementById("iDatumOd");
    var datumDo_=document.getElementById("iDatumDo");
    var id_=document.getElementById("iId");
    var ts_=document.getElementById("iTs");

    dataRezultatSO.forEach((redak, iRedak) => {
        if(redak.id==id) {
            band_.value=redak.band;
            cijena_.value=redak.cijena;
            datumOd_.value=redak.sifarnikDatumaOd.datumPetak;  
            datumDo_.value=redak.sifarnikDatumaDo.datumPetak;
            id_.innerText=redak.id;
            ts_.innerText=redak.ts;
        }
    });
}

function urediDatume() {
	hideGreska();
    
    var id_=document.getElementById("iId");
    var ts_=document.getElementById("iTs");
	var url = new URL(document.URL);
    var idSifarnikOsoba = url.searchParams.get("idSifarnikOsoba");
    var band_=document.getElementById("iBand");
    var cijena_=document.getElementById("iCijena");
    var sifarnikDatumaOd_=document.getElementById("iDatumOd");
    var sifarnikDatumaDo_=document.getElementById("iDatumDo");

    var projektUrl_=`${serverUrl}unesiOsobaValuta?id=${id_.innerText}&ts=${ts_.innerText}&idSifarnikOsoba=${idSifarnikOsoba}&band=${band_.value}&cijena=${cijena_.value}&sifarnikDatumaOd=${sifarnikDatumaOd_.value}&sifarnikDatumaDo=${sifarnikDatumaDo_.value}`;

    pozoviRestServis(projektUrl_, urediDatumeRest_);
}

function urediDatumeRest_(data) {
	if(data.greska === undefined) {
		postaviGresku(data.message);
	} else { 
		if(data.greska.length>0) {
	        postaviGresku(data.greska);
	    } else {
	        $('#novaOsobaValuta').modal('hide');  
	    }
	}

    obrisiRetke2(osobaValutaTablicaBody);

    dataRezultatSO=data.rezultat;

    if(dataRezultatSO.length>0) {
        dataRezultatSO.forEach((redak, i) => ucitajRedakOsobaValutaTablica(redak, i));
    }
    
    tablicaPagination(data);
}

function tablicaOsobaValutaPrethodno() {
	tablicaOsobaValutaSlijedecePrethodno(0);
}

function tablicaOsobaValutaSlijedece() {
	tablicaOsobaValutaSlijedecePrethodno(1);
}

function tablicaOsobaValutaSlijedecePrethodno(smjer) {
	var pTablicaPrethodno_=document.getElementById("pTablicaPrethodno");
    var pTablicaSlijedece_=document.getElementById("pTablicaSlijedece");
    var url = new URL(document.URL);
    var id = url.searchParams.get("idSifarnikOsoba");
    
    var projektUrl=`${serverUrl}tablicaOsobaValuta?idSifarnikOsoba=${id}&pageNumber=`;
    
    if(smjer==0) {
        projektUrl+=pTablicaPrethodno_.innerText;
    } else {
        projektUrl+=pTablicaSlijedece_.innerText;
    }

    pozoviRestServis(projektUrl, tablicaOsobaValutaSlijedecePrethodnoRest_);
}

function tablicaOsobaValutaSlijedecePrethodnoRest_(data) {
	if(data.greska.length>0) {
        postaviGresku(data.greska);
    } 
    
	obrisiRetke2(osobaValutaTablicaBody);
	
	dataRezultatSO=data.rezultat;

	dataRezultatSO.forEach((redak, i) => ucitajRedakOsobaValutaTablica(redak, i));

    tablicaPagination(data);
}

function otvoriProjektDetalji() {
    hideGreska();

    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get(idProjektDetalji);

    window.open(`/v1/html/projektDetalji.html?id=${idProjektDetalji_}`, "_parent");
}

function otvoriClaim() {
    hideGreska();

    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get(idProjektDetalji);

    window.open(`/v1/html/claim.html?idProjektDetalji=${idProjektDetalji_}`, "_parent");
}