var dataRezultatSO;
var dataRezultatSO2;
var podugovaracTablicaBody="podugovaracTablicaBody";
var purchaseOrderTablicaBody="purchaseOrderTablicaBody";
var url = new URL(document.URL);

function podugovaraci() {
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

	var projektUrl_2=`${serverUrl}purchaseOrders?idProjektDetalji=${idProjektDetalji_}`;
	
    pozoviRestServis(projektUrl_2, purchaseOrdersRest_);

    var projektUrl_=`${serverUrl}podugovaraci?idProjektDetalji=${idProjektDetalji_}`;

    pozoviRestServis(projektUrl_, podugovaraciRest_);
    
    inputPurchaseOrderSelect();

    inputNazivljeSelect();
}

function inputNazivljeSelect() {
    var projektUrl_=`${serverUrl}sifarnikPodugovaracaSelect`;

    pozoviRestServis(projektUrl_, inputNazivljeSelectRest_);
}

function inputNazivljeSelectRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
        let inputNazivlje_ = document.getElementById('inputNazivlje');

        data.rezultat.forEach((redak, i) => {
            var option=document.createElement('option');
            option.text=redak.naziv;
            option.value=redak.id;
            inputNazivlje_.add(option);
        });
    }
}

function purchaseOrdersRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
        obrisiRetke2(purchaseOrderTablicaBody);
        dataRezultatSO2=data.rezultat;

        dataRezultatSO2.forEach((redak, i) => ucitajPurchaseOrderTablicaBody(redak, i));
    }
}

function ucitajPurchaseOrderTablicaBody(redak, i) {
    var table = document.getElementById(purchaseOrderTablicaBody);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    cell1.className="text-right";
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);
    cell3.className="text-right";
    var cell4 = row.insertCell(i++);
    cell4.className="text-right";
    var cell5 = row.insertCell(i++);
    cell5.className="text-right";
    var cell6 = row.insertCell(i++);
    var cell7 = row.insertCell(i++);
    
    cell1.innerHTML = redak.id;
    cell2.innerHTML = redak.sifarnikPodugovaraca.naziv;
    cell3.innerHTML = formatMoney(redak.total);
    cell4.innerHTML = formatMoney(redak.actual);
    cell5.innerHTML = formatMoney(redak.planned);
    cell6.innerHTML = redak.po;
    cell7.innerHTML = `<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editPO" onclick="postojeciPO(${redak.id})">Uredi</button>`;
}

function inputPurchaseOrderSelect() {
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

    var projektUrl_=`${serverUrl}purchaseOrders?idProjektDetalji=${idProjektDetalji_}`;
    
    pozoviRestServis(projektUrl_, inputPurchaseOrderSelectRest_);
}

function inputPurchaseOrderSelectRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
        var iPurchaseOrder_ = document.getElementById('iPurchaseOrder');

        for (i = 1; i < iPurchaseOrder_.options.length; i++) {
            iPurchaseOrder_.options[i] = null;
        }

        data.rezultat.forEach((redak, i) => {
            var option=document.createElement('option');
            option.text=redak.sifarnikPodugovaraca.naziv;
            option.value=redak.id;
            iPurchaseOrder_.add(option);
        });
    }
}

function podugovaraciRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
        $('#editPodugovarac').modal('hide');
        obrisiRetke2(podugovaracTablicaBody);
        dataRezultatSO=data.rezultat;

        dataRezultatSO.forEach((redak, i) => ucitajPodugovaracTablicaBody(redak, i));
    }

    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

    var projektUrl_2=`${serverUrl}purchaseOrders?idProjektDetalji=${idProjektDetalji_}`;
	
    pozoviRestServis(projektUrl_2, purchaseOrdersRest_);
}

function ucitajPodugovaracTablicaBody(redak, i) {
    var table = document.getElementById(podugovaracTablicaBody);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);
    var cell4 = row.insertCell(i++);
    var cell5 = row.insertCell(i++);
    var cell6 = row.insertCell(i++);
    var cell7 = row.insertCell(i++);
    var cell8 = row.insertCell(i++);

    cell1.innerHTML = redak.id;
    cell1.className="text-right";
    cell2.innerHTML = redak.claimPodugovarac.sifarnikPodugovaraca.naziv;
    cell3.innerHTML = redak.datumPlanned;
    cell4.innerHTML = redak.datumActual;
    cell5.innerHTML = formatMoney(redak.cijena);
    cell5.className="text-right";
    cell6.innerHTML = redak.invoiceNumber;
    cell7.innerHTML = redak.claimPodugovarac.po;
    cell8.innerHTML = `<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editPodugovarac" onclick="postojeciPodugovarac(${redak.id})">Uredi</button>`;
}

function otvoriProjektDetalji() {
    hideGreska();

    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

    window.open(`/v1/html/projektDetalji.html?id=${idProjektDetalji_}`, "_parent");
}

function noviPodugovarac() {
    hideGreska();

    var inputId_=document.getElementById("inputId");
    inputId_.innerText="";

    var inputTs_=document.getElementById("inputTs");
    inputTs.innerText=0;
    
    var iPurchaseOrder_=document.getElementById("iPurchaseOrder");
    iPurchaseOrder_.value="";
    
    var iCijena_=document.getElementById("iCijena");
    iCijena_.value="";

    var iDatumPlanned_=document.getElementById("iDatumPlanned");
    iDatumPlanned_.value="";
    
    var iDatumActual_=document.getElementById("iDatumActual");
    iDatumActual_.value="";

    var iInvoiceNumber_=document.getElementById("iInvoiceNumber");
    iInvoiceNumber_.value="";
}

function postojeciPodugovarac(id) {
    hideGreska();

    var inputId_=document.getElementById("inputId");
    var inputTs_=document.getElementById("inputTs");
    var iPurchaseOrder_=document.getElementById("iPurchaseOrder");
    var iCijena_=document.getElementById("iCijena");
    var iDatumPlanned_=document.getElementById("iDatumPlanned");
    var iDatumActual_=document.getElementById("iDatumActual");
    var iInvoiceNumber_=document.getElementById("iInvoiceNumber");

    dataRezultatSO.forEach((redak, i) => {
        if(redak.id==id) {
            inputId_.innerText=id;
            inputTs_.innerText=redak.ts;
            iPurchaseOrder_.value=redak.claimPodugovarac.id;
            iCijena_.value=redak.cijena;
            iDatumPlanned_.value=redak.datumPlanned;
            iDatumActual_.value=redak.datumActual;
            iInvoiceNumber_.value=redak.invoiceNumber;
        }
    });
}

function editirajPodugovarac() {
    hideGreska();

    var inputId_=document.getElementById("inputId");
    var inputTs_=document.getElementById("inputTs");
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");
    var iPurchaseOrder_=document.getElementById("iPurchaseOrder");
    var iDatumPlanned_=document.getElementById("iDatumPlanned");
    var iDatumActual_=document.getElementById("iDatumActual");
    var iCijena_=document.getElementById("iCijena");
    var iInvoiceNumber_=document.getElementById("iInvoiceNumber");

    var projektUrl_=`${serverUrl}unesiPodugovarac?id=${inputId_.innerText}&ts=${inputTs_.innerText}&idProjektDetalji=${idProjektDetalji_}&idPurchaseOrder=${iPurchaseOrder_.value}&datumPlanned=${iDatumPlanned_.value}&datumActual=${iDatumActual_.value}&cijena=${iCijena_.value}&invoiceNumber=${iInvoiceNumber_.value}`;
    
    pozoviRestServis(projektUrl_, podugovaraciRest_);
}

function noviPO() {
    hideGreska();

    var inputNazivlje_ = document.getElementById("inputNazivlje");
    inputNazivlje_.value="";
    
    var inputPO_=document.getElementById("inputPO");
    inputPO_.value="";

    var inputTotal_=document.getElementById("inputTotal");
    inputTotal_.value="";
    
    var inputId_=document.getElementById("inputId");
    inputId_.innerText="";

    var inputTs_=document.getElementById("inputTs");
    inputTs_.innerText=0;
}

function postojeciPO(id) {
    hideGreska();

    var inputNazivlje_ = document.getElementById("inputNazivlje");    
    var inputPO_=document.getElementById("inputPO");
    var inputId_=document.getElementById("inputId");
    var inputTs_=document.getElementById("inputTs");
    var inputTotal_=document.getElementById("inputTotal");
    
    dataRezultatSO2.forEach((redak, i) => {
        if(redak.id==id) {
            inputId_.innerText=id;
            inputTs_.innerText=redak.ts;
            inputNazivlje_.value=redak.sifarnikPodugovaraca.id;
            inputPO_.value=redak.po;
            inputTotal_.value=redak.total;
        }
    });
}

function editirajPO() {
    hideGreska();

    var inputNazivlje_ = document.getElementById("inputNazivlje");
    var inputPO_=document.getElementById("inputPO");
    var inputId_=document.getElementById("inputId");
    var inputTs_=document.getElementById("inputTs");
    var inputTotal_=document.getElementById("inputTotal");
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

    var projektUrl_=`${serverUrl}unesiPO?id=${inputId_.innerText}&ts=${inputTs_.innerText}&idProjektDetalji=${idProjektDetalji_}&idSifarnikPodugovaraca=${inputNazivlje_.value}&po=${inputPO_.value}&total=${inputTotal_.value}`;
    
    pozoviRestServis(projektUrl_, editirajPORest_);
}

function editirajPORest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    if(data.rezultat.length>0) {
        $('#editPO').modal('hide');
        obrisiRetke2(purchaseOrderTablicaBody);
        dataRezultatSO2=data.rezultat;

        dataRezultatSO2.forEach((redak, i) => ucitajPurchaseOrderTablicaBody(redak, i));

        var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

	    var projektUrl_2=`${serverUrl}purchaseOrders?idProjektDetalji=${idProjektDetalji_}`;
	
        pozoviRestServis(projektUrl_2, purchaseOrdersRest_);

        inputPurchaseOrderSelect();
    }
}