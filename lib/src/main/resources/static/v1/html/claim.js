var dataRezultatSO = {};
var claimTablicaBody="claimTablicaBody";
var osobaClaimActualTablicaBody2="osobaClaimActualTablicaBody2";
var osobaClaimActualTablica="osobaClaimActualTablica";
var osobaClaimActualTablicaThead2="osobaClaimActualTablicaThead2";
var divOsobaClaimActualTablica2="divOsobaClaimActualTablica2";
var skriveniOsobaClaimActualTablica2="skriveniOsobaClaimActualTablica2";
var osobaClaimPlannedTablicaBody="osobaClaimPlannedTablicaBody";
var osobaClaimPlannedTablicaThead="osobaClaimPlannedTablicaThead";
var divOsobaClaimPlannedTablica="divOsobaClaimPlannedTablica";
var skriveniOsobaClaimPlannedTablica="skriveniOsobaClaimPlannedTablica";
var claimActual2 = {};
var claimPlanned = {};

function claim() {
    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");
    var projektUrl_=`${serverUrl}claim?idProjektDetalji=${idProjektDetalji_}`;

    pozoviRestServis(projektUrl_, claimRest2_);
}

function claimRest2_(data) {
    if(data.message!==undefined) {
        postaviGresku(data.message);
    } else if(data.greska != "") {
        postaviGresku(data.greska);
    } else {
        dataRezultatSO=data.rezultat;

        if(data.greska.length>0) {
            postaviGresku(data.greska);
        } else if(dataRezultatSO.length>0) {
            claimActual2 = {};
            claimPlanned = {};
            
            var divOsobaClaimActualTablica2_=document.getElementById(divOsobaClaimActualTablica2);
            var skriveniOsobaClaimActualTablica2_=document.getElementById(skriveniOsobaClaimActualTablica2);
            divOsobaClaimActualTablica2_.innerHTML=skriveniOsobaClaimActualTablica2_.innerHTML;
            var divOsobaClaimPlannedTablica_=document.getElementById(divOsobaClaimPlannedTablica);
            var skriveniOsobaClaimPlannedTablica_=document.getElementById(skriveniOsobaClaimPlannedTablica);
            divOsobaClaimPlannedTablica_.innerHTML=skriveniOsobaClaimPlannedTablica_.innerHTML;

            var claimTablicaBody_ = document.getElementById(claimTablicaBody);
            claimTablicaBody_.innerHTML="";

            dataRezultatSO.forEach((redak, i) => ucitajRedakClaimTablica(redak, i));

            ucitajTableClaimActual2();
            ucitajTableClaimPlanned();

            $('#noviClaim').modal('hide');
            $('#urediClaim').modal('hide');
            $('#noviPlannedClaim').modal('hide');
            $('#urediPlannedClaim').modal('hide');
        }
    }
}

function ucitajTableClaimPlanned() {
    var osobaClaimPlannedTablicaBody_=document.getElementById(osobaClaimPlannedTablicaBody);
    var thead="<tr> <th>Planned</th> ";
    let sortDatumiObj2 = {};

    Object.keys(claimPlanned).forEach((osobaRedak, i) => {
        thead += `<th colspan="2">${osobaRedak}</th> `;
        let datumi=claimPlanned[osobaRedak]; 
    
        Object.keys(datumi).forEach((datumRedak, i) => sortDatumiObj2[datumRedak]=datumi[datumRedak].mjesec);
    });

    thead += "</tr> <tr> <th>Datum</th> ";

    Object.keys(claimPlanned).forEach((osobaRedak, i) => thead += "<th>Planned</th> <th>Actual</th> ");

    thead += "</tr>";

    var osobaClaimPlannedTablicaThead_ = document.getElementById(osobaClaimPlannedTablicaThead);
    osobaClaimPlannedTablicaThead_.innerHTML=thead;

    var userAktual = {};

    dataRezultatSO.forEach((redak, iRetka) => {
        var mjesecActal={};

        if(redak.osobaClaimActualList!=null) {
	        redak.osobaClaimActualList.forEach((redak2, iRetka2) => {
	            if(mjesecActal[redak2.sifarnikDatuma.mjesec.mjesec]===undefined) {
	                mjesecActal[redak2.sifarnikDatuma.mjesec.mjesec] = redak2.sati;
	            } else {
	                var sat=mjesecActal[redak2.sifarnikDatuma.mjesec.mjesec] + redak2.sati;
	                mjesecActal[redak2.sifarnikDatuma.mjesec.mjesec] = sat;
	            }
	        });
        }

        userAktual[redak.sifarnikOsoba.prezimeIme] = mjesecActal;
    });

    var sortDatumiPlannedArray = Object.keys(sortDatumiObj2).sort().reverse();
    
    sortDatumiPlannedArray.forEach((redak, iRetka) => {
        var row = osobaClaimPlannedTablicaBody_.insertRow(-1);
        var bb=0;
        var cell1 = row.insertCell(bb++);
        cell1.innerHTML=`<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#urediPlannedClaim" onclick="urediPlannedClaim('${redak}')">${redak}</button>`;
       
        Object.keys(claimPlanned).forEach((osobaRedak, i) => {
            var ddatumi=claimPlanned[osobaRedak];
            var ddatum=ddatumi[redak];

            var cell2N = row.insertCell(bb++);
            if(!(ddatum === undefined)) {
                cell2N.innerHTML = `${ddatum.sati}h`;
            }

            var cell3N = row.insertCell(bb++);
            cell3N.innerHTML = "";

            if(userAktual[osobaRedak]!==undefined) {
                var mo=userAktual[osobaRedak];

                if(mo[redak]!==undefined) {
                    cell3N.innerHTML = `${mo[redak]}h`;
                    
                    if(ddatum !== undefined) {
                        if(mo[redak]>ddatum.sati) {
                            cell2N.className="text-danger";
                            cell3N.className="text-danger";
                        } else if(mo[redak]==ddatum.sati) {
                            cell2N.className="text-success";
                            cell3N.className="text-success";
                        }
                    }
                }
            }
        });
    }); 
}

function ucitajTableClaimActual2() { 
    var tableClaimActualBody2 = document.getElementById(osobaClaimActualTablicaBody2);
    var thead="<tr> <th>Actual</th> <th>Datum</th> ";
    let sortDatumiObj2 = {};

    Object.keys(claimActual2).forEach((osobaRedak, i) => {
        thead += `<th>${osobaRedak}</th> `;
        let datumi=claimActual2[osobaRedak]; 
    
        Object.keys(datumi).forEach((datumRedak, i) => sortDatumiObj2[datumRedak]=datumi[datumRedak].mjesec);
    });

    thead += "</tr>";

    var osobaClaimActualTablicaThead2_ = document.getElementById(osobaClaimActualTablicaThead2);
    osobaClaimActualTablicaThead2_.innerHTML=thead;

    var sortDatumiArray2 = Object.keys(sortDatumiObj2).sort().reverse();
    var sortDatumiVelus2 = Object.values(sortDatumiObj2).sort().reverse();
    
    var actualHederSreden=srediActualHeader2(sortDatumiVelus2);
    var ii=0;

    sortDatumiArray2.forEach((redak, iRetka) => {
        var row2 = tableClaimActualBody2.insertRow(-1);
        var bb=0;
        var cell1 = row2.insertCell(bb++);
        var lokal=actualHederSreden[ii++];

        if(lokal.length>0) {
            cell1.innerHTML=`<b>${lokal.substring(0, lokal.indexOf('-', 5))}</b>`;
        }

        var cell2 = row2.insertCell(bb++);
        cell2.innerHTML = `<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#urediClaim" onclick="urediClaim('${redak}')">${redak}</button>`;
        
        Object.keys(claimActual2).forEach((osobaRedak, i) => {
        	var cell2N = row2.insertCell(bb++);
        	
        	var ddatumi=claimActual2[osobaRedak];
            var ddatum=ddatumi[redak];

            if(!(ddatum === undefined)) {
                cell2N.innerHTML += `${ddatum.sati}h (${formatMoney(ddatum.cijena)} €)`;
            }
        });
    });
}

function srediActualHeader2(sortDatumiVelus2) {
    var actualHederSreden=[];
    var maxLenght=sortDatumiVelus2.length;
    
    for(var i=0; i<sortDatumiVelus2.length; i++) {
        if(maxLenght-1>i) {
            if(sortDatumiVelus2[i]==sortDatumiVelus2[i+1]) {
                actualHederSreden[i]="";
            } else {
                actualHederSreden[i]=sortDatumiVelus2[i];
            }
        } else {
            actualHederSreden[i]=sortDatumiVelus2[i];
        }
        
    }

    return actualHederSreden;
}

function ucitajRedakClaimTablica(redak, iRedak) {
    var url = new URL(document.URL);
	var idClaim=url.searchParams.get("idProjektDetalji");
    var table = document.getElementById(claimTablicaBody);
    var row = table.insertRow(-1);

    var i=0;
    var cell1 = row.insertCell(i++);
    var cell2 = row.insertCell(i++);
    var cell3 = row.insertCell(i++);
    var cell4 = row.insertCell(i++);
    var cell5 = row.insertCell(i++);

    cell1.innerHTML = redak.id;
    cell1.className="text-right";
    cell2.innerHTML = redak.sifarnikOsoba.prezimeIme;
    cell3.innerHTML = formatMoney(redak.osobaClaimPlanned);
    cell4.innerHTML = formatMoney(redak.osobaClaimActual);
    cell5.innerHTML = '<div class="btn-group" role="group"> ' + 
        `<button type="button" class="btn btn-outline-primary" onclick="prikaziBand(${redak.sifarnikOsoba.id}, ${idClaim})">Prikaži Band</button> ` +
        '</div>'; 

    var userDate = {};

    if(redak.osobaClaimActualList!=null) {
	    redak.osobaClaimActualList.forEach((osobaClaimActual, iRedak2) => userDate[osobaClaimActual.sifarnikDatuma.datumPetak]={"id":osobaClaimActual.id, "sati": osobaClaimActual.sati, "cijena": osobaClaimActual.cijena, 
	            "mjesec": osobaClaimActual.sifarnikDatuma.mjesec.mjesec, "ts":osobaClaimActual.ts, "prezimeIme":redak.sifarnikOsoba.prezimeIme, "idSifarnikOsoba":redak.sifarnikOsoba.id} );
    }

    claimActual2[redak.sifarnikOsoba.prezimeIme] = userDate;
    var userPlannedDate = {};

    if(redak.osobaClaimPlannedList!=null) {
	    redak.osobaClaimPlannedList.forEach((osobaClaimPlanned, iRedak2) => userPlannedDate[osobaClaimPlanned.sifarnikMjeseca.mjesec]={"id":osobaClaimPlanned.id, "sati": osobaClaimPlanned.sati, "ts":osobaClaimPlanned.ts});
    }

    claimPlanned[redak.sifarnikOsoba.prezimeIme] = userPlannedDate;
}

function otvoriProjektDetalji() {
    hideGreska();

    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");

    window.open(`/v1/html/projektDetalji.html?id=${idProjektDetalji_}`, "_parent");
}

function urediPlannedClaim(datum) {
    hideGreska();
    var iPlannedDatum_=document.getElementById("iPlannedDatum");
    iPlannedDatum_.innerText=datum;
    var iUrediPlannedClaim_=document.getElementById("iUrediPlannedClaim");
    iUrediPlannedClaim_.innerHTML="";

    Object.keys(claimPlanned).forEach((osobaRedak, iRedak) => {
        var dani=claimPlanned[osobaRedak];
        var userDate=dani[datum];

        dataRezultatSO.forEach((osobaRedak2, iRedak) => {
            if(osobaRedak==osobaRedak2.sifarnikOsoba.prezimeIme) {
                if(userDate === undefined) {
                    userDate={idSifarnikOsoba: osobaRedak2.sifarnikOsoba.id, sati: 0, id: 0, ts: 0};
                }

                var txt='<div class="form-group col-md-6"> ';
                txt += `<label for="${osobaRedak2.sifarnikOsoba.id}_${userDate.id}_${userDate.ts}">${osobaRedak}</label> `;
                txt += `<input type="input" class="form-control" id="${osobaRedak2.sifarnikOsoba.id}_${userDate.id}_${userDate.ts}" value="${userDate.sati}"></input> `;
                txt += '</div> ';

                iUrediPlannedClaim_.innerHTML += txt;
            }
        });
    });
}

function urediClaim(datum) {
    hideGreska();
    var iDatum_=document.getElementById("iDatum");
    iDatum_.innerText=datum;
    var iUrediClaim_=document.getElementById("iUrediClaim");
    iUrediClaim_.innerHTML="";

    Object.keys(claimActual2).forEach((osobaRedak, iRedak) => {
        var dani=claimActual2[osobaRedak];
        var userDate=dani[datum];

        dataRezultatSO.forEach((osobaRedak2, iRedak) => {
            if(osobaRedak==osobaRedak2.sifarnikOsoba.prezimeIme) {
                if(userDate === undefined) {
                    userDate={idSifarnikOsoba: osobaRedak2.sifarnikOsoba.id, sati: 0, id: 0, ts: 0};
                }

                var txt='<div class="form-group col-md-6"> ';
                txt += `<label for="${osobaRedak2.sifarnikOsoba.id}_${userDate.id}_${userDate.ts}">${osobaRedak}</label> `;
                txt += `<input type="input" class="form-control" id="${osobaRedak2.sifarnikOsoba.id}_${userDate.id}_${userDate.ts}" value="${userDate.sati}"></input> `;
                txt += '</div> ';

                iUrediClaim_.innerHTML += txt;
            }
        });
    });
}

function urediPlennedClaim2() {
    hideGreska();
    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");
    var iPlannedDatum_=document.getElementById("iPlannedDatum");
    var projektUrl_=`${serverUrl}claimUpdatedPlannedByDate?idProjektDetalji=${idProjektDetalji_}&datum=${iPlannedDatum_.innerText}`;

    var sendData = [];
    var iUrediPlannedClaim_=document.getElementById("iUrediPlannedClaim");
    var elements=iUrediPlannedClaim_.querySelectorAll('[id]');

    for(var i=0; i<elements.length; i++) {
        var p_=elements[i];
        var pId_=p_.id;
        var pIpParts_=pId_.split('_'); 
        var idSifarnikOsoba_=pIpParts_[0]; 
        var idClaim_=pIpParts_[1]; 
        var idTs_=pIpParts_[2]; 
        var idSati_=p_.value;

        sendData.push({idSifarnikOsoba: idSifarnikOsoba_, idClaim: idClaim_, sati: idSati_, ts: idTs_});
    }

    pozoviRestServisPut(projektUrl_, sendData, claimRest2_);
}

function urediClaim2() {
    hideGreska();
    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");
    var iDatum_=document.getElementById("iDatum");
    var projektUrl_=`${serverUrl}claimUpdatedActualByDate?idProjektDetalji=${idProjektDetalji_}&datum=${iDatum_.innerText}`;

    var sendData = [];
    var iUrediClaim_=document.getElementById("iUrediClaim");
    var elements=iUrediClaim_.querySelectorAll('[id]');

    for(var i=0; i<elements.length; i++) {
        var p_=elements[i];
        var pId_=p_.id;
        var pIpParts_=pId_.split('_'); 
        var idSifarnikOsoba_=pIpParts_[0]; 
        var idClaim_=pIpParts_[1]; 
        var idTs_=pIpParts_[2]; 
        var idSati_=p_.value;

        sendData.push({idSifarnikOsoba: idSifarnikOsoba_, idClaim: idClaim_, sati: idSati_, ts: idTs_});
    }

    pozoviRestServisPut(projektUrl_, sendData, claimRest2_);
}

function noviPlennedClaim() {
    hideGreska();
    var iNoviPlannedClaim_=document.getElementById("iNoviPlannedClaim");
    iNoviPlannedClaim_.innerHTML="";
    var iDatum3_=document.getElementById("iDatum3");
    iDatum3_.value="";

    Object.keys(claimPlanned).forEach((osobaRedak, iRedak) => {
        dataRezultatSO.forEach((osobaRedak2, iRedak) => {
            if(osobaRedak==osobaRedak2.sifarnikOsoba.prezimeIme) {
                var txt='<div class="form-group col-md-6"> ';
                txt += `<label for="${osobaRedak2.sifarnikOsoba.id}">${osobaRedak}</label> `;
                txt += `<input type="input" class="form-control" id="${osobaRedak2.sifarnikOsoba.id}"></input> `;
                txt += '</div> ';

                iNoviPlannedClaim_.innerHTML += txt;
            }
        });
    });
}

function noviPlennedClaim2() {
    hideGreska();
    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");
    var iDatum3_=document.getElementById("iDatum3");
    var projektUrl_=`${serverUrl}claimNewPlannedByDate?idProjektDetalji=${idProjektDetalji_}&datum=${iDatum3_.value}`;
    
    var sendData = {};
    var iNoviPlannedClaim_=document.getElementById("iNoviPlannedClaim");
    var elements=iNoviPlannedClaim_.querySelectorAll('[id]');

    for(var i=0; i<elements.length; i++) {
    	var p_=elements[i];
    	var pId_=p_.id;
    	var inputId_=document.getElementById(pId_);
        
        sendData[pId_]=inputId_.value;
    } 

    pozoviRestServisPut(projektUrl_, sendData, claimRest2_);
}

function noviClaim() {
    hideGreska();
    var iNoviClaim_=document.getElementById("iNoviClaim");
    iNoviClaim_.innerHTML="";
    var iDatum2_=document.getElementById("iDatum2");
    iDatum2_.value="";

    Object.keys(claimActual2).forEach((osobaRedak, iRedak) => {
        dataRezultatSO.forEach((osobaRedak2, iRedak) => {
            if(osobaRedak==osobaRedak2.sifarnikOsoba.prezimeIme) {
                var txt='<div class="form-group col-md-6"> ';
                txt += `<label for="${osobaRedak2.sifarnikOsoba.id}">${osobaRedak}</label> `;
                txt += `<input type="input" class="form-control" id="${osobaRedak2.sifarnikOsoba.id}"></input> `;
                txt += '</div> ';

                iNoviClaim_.innerHTML += txt;
            }
        });
    });
}

function noviClaim2() {
    hideGreska();
    var url = new URL(document.URL);
    var idProjektDetalji_ = url.searchParams.get("idProjektDetalji");
    var iDatum2_=document.getElementById("iDatum2");
    var projektUrl_=`${serverUrl}claimNewActualByDate?idProjektDetalji=${idProjektDetalji_}&datum=${iDatum2_.value}`;
    
    var sendData = {};
    var iNoviClaim_=document.getElementById("iNoviClaim");
    var elements=iNoviClaim_.querySelectorAll('[id]');

    for(var i=0; i<elements.length; i++) {
    	var p_=elements[i];
    	var pId_=p_.id;
    	var inputId_=document.getElementById(pId_);
        
        sendData[pId_]=inputId_.value;
    } 

    pozoviRestServisPut(projektUrl_, sendData, claimRest2_);
}

function prikaziBand(sfrOsoba, idClaim) {
    hideGreska();
    
    window.open(`/v1/html/osobaValuta.html?idSifarnikOsoba=${sfrOsoba}&idProjektDetalji=${idClaim}&var1=1`, "_parent");
}

function novoIme() {
    hideGreska();
    var projektUrl_=`${serverUrl}claimImena`;
    var sendData = [];

    dataRezultatSO.forEach((redak, i) => sendData.push(redak.sifarnikOsoba.id));

    pozoviRestServisPut(projektUrl_, sendData, novoImeRest2_);
}

function novoImeRest2_(data) {
    console.log("2222");
}