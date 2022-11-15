var aktivni="aktivni";
var neaktivni="neaktivni";
var dataRezultatSO;
var redakClaim_;
var redakContract_;
var projektTablica="projektTablicaBody";

function projekti() {
    try {
        projektiTC();
    } catch(e) {
        postaviGresku(e);
    }
}

function projektiTC() {
    hideGreska();     

    var aktivni_=document.getElementById(aktivni);
    var neaktivni_=document.getElementById(neaktivni);
    var projektUrl=`${serverUrl}projekti?`;

    if(aktivni_.checked) {
        projektUrl+="status=A";
    } else if(neaktivni_.checked) {
        projektUrl+="status=N";
    } 

    pozoviRestServis(projektUrl, projektiRest_);
}

function projektiRest_(data) {
    dataRezultatSO=data.rezultat;

    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    obrisiRetke2(projektTablica);

    dataRezultatSO.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    tablicaPagination(data); 
}

function ucitajRedakProjektneTablice(redak, iRedak) {
    var table = document.getElementById(projektTablica);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);
    var cell4 = row.insertCell(i++);
    var cell5 = row.insertCell(i++);

    cell1.innerHTML = redak.id;
    cell1.className="text-right";
    cell2.innerHTML = redak.claim;
    cell3.innerHTML = redak.contract;
    cell4.innerHTML = redak.status;
    
    var gumbiZaRedak_=document.getElementById("gumbiZaRedak");
    gumbiZaRedak_ = gumbiZaRedak_.cloneNode(true);
    gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakId}', 'g'), redak.id);
    gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakTs}', 'g'), redak.ts);
    gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakClaim}', 'g'), redak.claim);
    gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakContract}', 'g'), redak.contract); 
    cell5.innerHTML=gumbiZaRedak_.innerHTML;
}

function urediOsobuOtvori() {
    hideGreska();
   
    window.open("/v1/html/sifarnikOsoba.html", "_parent");
}

function pretrazi() {
    hideGreska(); 

    var pretrazi_=document.getElementById("pretrazi");
    pretrazi_.style['display'] = 'none';

    var trazi_=document.getElementById("trazi");
    trazi_.style['display'] = 'block';
}

function onProjektTrazi() {
    var aktivni_=document.getElementById(aktivni);
    aktivni_.checked=true;
    aktivni_.checked=false;

    var projektTrazi_ = document.getElementById("projektTrazi");
    var projektUrl_=`${serverUrl}traziProjekt?trazi=${projektTrazi_.value}`;

    pozoviRestServis(projektUrl_, projektTraziRest_);
}

function projektTrazi() {
    hideGreska(); 

    var pretrazi_=document.getElementById("pretrazi");
    pretrazi_.style['display'] = 'block';

    var trazi_=document.getElementById("trazi");
    trazi_.style['display'] = 'none';
}

function projektTraziRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    obrisiRetke2(projektTablica);

    if(data.rezultat.length>0) {
        data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));
    }

    tablicaPagination(data); 
}

function projektTablicaPrethodno() {
    projektTablicaSlijedecePrethodno(0);
}

function projektTablicaSlijedece() {
    projektTablicaSlijedecePrethodno(1);
}

function projektTablicaSlijedecePrethodno(smjer) {
    var aktivni_=document.getElementById(aktivni);
    var neaktivni_=document.getElementById(neaktivni);
    var pTablicaPrethodno_=document.getElementById("pTablicaPrethodno");
    var pTablicaSlijedece_=document.getElementById("pTablicaSlijedece");

    var projektUrl=`${serverUrl}tablicaProjekti?pageNumber=`;
    
    if(smjer==0) {
        projektUrl+=pTablicaPrethodno_.innerText;
    } else {
        projektUrl+=pTablicaSlijedece_.innerText;
    }

    if(aktivni_.checked) {
        projektUrl+="&status=A";
    } else if(neaktivni_.checked) {
        projektUrl+="&status=N";
    } 

    pozoviRestServis(projektUrl, projektTablicaSlijedecePrethodnoRest_);
}

function projektTablicaSlijedecePrethodnoRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    } 
    
    obrisiRetke2(projektTablica);

    data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    tablicaPagination(data);
}

function noviProjekt() {
    hideGreska();
    
    var inputClaim = document.getElementById("inputClaim");
    var inputContract = document.getElementById("inputContract");
    var projektUrl_=`${serverUrl}noviProjekt?claim=${inputClaim.value}&contract=${inputContract.value}`;

    pozoviRestServis(projektUrl_, noviProjektRest_);
}

function noviProjektRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    } else {
        inputClaim.value="";
        inputContract.value="";

        $('#noviProjekt').modal('hide');  
    }

    obrisiRetke2(projektTablica);

    data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    var aktivni_=document.getElementById(aktivni);
    aktivni_.checked=true;
    
    if(data.ok.length>0) {
        postaviOK(data.ok);
    } 
}

function prikaziProjekt(id) {
    hideGreska();
   
    window.open(`/v1/html/projektDetalji.html?id=${id}`, "_parent");
}

function zatvoriOtvoriProjekt(id, ts, pojoAdress) {
    hideGreska();

    var aktivni_=document.getElementById(aktivni);
    var neaktivni_=document.getElementById(neaktivni);

    if(aktivni_.checked) {
        pojoAdress+="&status=A";
    } else if(neaktivni_.checked) {
        pojoAdress+="&status=N";
    } 

    pozoviRestServis(pojoAdress, zatvoriOtvoriProjektFRest_);
}

function zatvoriOtvoriProjektFRest_(data) {
    if(data.greska.length>0) {
        postaviGresku(data.greska);
    }

    obrisiRetke2(projektTablica);

    data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    if(data.ok.length>0) {
        postaviOK(data.ok);
    }

    tablicaPagination(data);
}

function otvoriProjekt(id, ts) {
    try {
        zatvoriOtvoriProjekt(id, ts, `${serverUrl}otvoriProjekt/${id}?ts=${ts}`);
    } catch(e) {
        postaviGresku(e);
    }
}

function zatvoriProjekt(id, ts) {
    try {
        zatvoriOtvoriProjekt(id, ts, `${serverUrl}zatvoriProjekt/${id}?ts=${ts}`);
    } catch(e) {
        postaviGresku(e);
    }
}

function traziZatvori() {
    window.addEventListener('mouseup', (event) => {
        var trazi_=document.getElementById("trazi");

        if(event.target!=trazi_ && event.target.parentNode!=trazi_) {
            trazi_.style['display'] = 'none';

            var pretrazi_=document.getElementById("pretrazi");
            pretrazi_.style['display'] = 'block';
        }
    });
}

function uExel(id, redakClaim, redakContract) {
    hideGreska();
    redakClaim_=redakClaim;
    redakContract_=redakContract;
    var projektUrl_=`${serverUrl}uExcel/${id}`;

    pozoviRestServis(projektUrl_, pozoviExcel_);
}

function pozoviExcel_(data) {     
    redakContract_=redakContract_.replace(new RegExp('/', 'g'), '.');
    var blob = b64toBlob(data.rezultat, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');

    anchor = document.createElement('a');
    anchor.download = `${redakContract_}_${redakClaim_}_${getFormattedDate()}.xlsx`;
    anchor.href = (window.webkitURL || window.URL).createObjectURL(blob);
    anchor.dataset.downloadurl = ['text/plain', anchor.download, anchor.href].join(':');
    anchor.click();
}

function getFormattedDate() {
    var date = new Date();

    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();

    month = (month < 10 ? "0" : "") + month;
    day = (day < 10 ? "0" : "") + day;
    hour = (hour < 10 ? "0" : "") + hour;
    min = (min < 10 ? "0" : "") + min;
    sec = (sec < 10 ? "0" : "") + sec;

    return `${date.getFullYear()}-${month}-${day}_${hour}.${min}.${sec}`;
}

function b64toBlob(b64Data, contentType, sliceSize) {
  contentType = contentType || '';
  sliceSize = sliceSize || 512;

  var byteCharacters = atob(b64Data);
  var byteArrays = [];

  for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
    var slice = byteCharacters.slice(offset, offset + sliceSize);
    var byteNumbers = new Array(slice.length);

    for(var i = 0; i < slice.length; i++) {
    	byteNumbers[i] = slice.charCodeAt(i);
    }

    var byteArray = new Uint8Array(byteNumbers);

    byteArrays.push(byteArray);
  }

  var blob = new Blob(byteArrays, {type: contentType});

  return blob;
}

function urediPodugovaraca() {
    hideGreska();
 
    window.open("/v1/html/sifarnikPodugovaraca.html", "_parent");
}

function inicijalnoNapuni() {
	hideGreska();
	var projektUrl=`${serverUrl}createDB`;
	
	pozoviRestServis(projektUrl, createDB_);
}

function createDB_(data) {	
	postaviOK(JSON.stringify(data));
	
	projektiTC();
}
