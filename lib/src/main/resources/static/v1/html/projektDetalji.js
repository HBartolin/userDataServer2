var dataRezultatSO;

function projekDetalji() {
    var url = new URL(document.URL);
    var id = url.searchParams.get("id");
    var projektUrl_=`${serverUrl}projektDatalji/${id}`;

    pozoviRestServis(projektUrl_, projekDetaljiRest_);
}

function projekDetaljiRest_(data) {    
    if(data.greska === undefined) {
		postaviGresku(data.message);
	} else { 
        dataRezultatSO=data.rezultat;
        
        if(data.greska.length>0) {
            postaviGresku(data.greskaList);
        } else {
            var projektContract=document.getElementById("projekt.contract");
            projektContract.value=dataRezultatSO.projekt.contract;
            
            var projektNaziv_=document.getElementById("projektNaziv");
            projektNaziv_.innerHTML=dataRezultatSO.projekt.contract;

            var projektClaim=document.getElementById("projekt.claim");
            projektClaim.value=dataRezultatSO.projekt.claim;
            
            var totalRevenue=document.getElementById("totalRevenue");
            totalRevenue.value=formatMoney(dataRezultatSO.totalRevenue);
            
            var costPs=document.getElementById("costPs");
            costPs.value=formatMoney(dataRezultatSO.costPs);
            
            var gpPs=document.getElementById("gpPs");
            var gpPsValue_=dataRezultatSO.totalRevenue-dataRezultatSO.costPs;
            gpPs.value=formatMoney(gpPsValue_);
            
            var gpPosto=document.getElementById("gpPosto");
            var gpPostoValue_=gpPsValue_/dataRezultatSO.totalRevenue*100;
            gpPosto.value=formatMoney(gpPostoValue_);
                        
            var costPlanned=document.getElementById("costPlanned");
            costPlanned.value=formatMoney(dataRezultatSO.costPlanned);
            
            var gpPlanned=document.getElementById("gpPlanned");
            var gpPlannedValue_=dataRezultatSO.totalRevenue-dataRezultatSO.costPlanned;
            gpPlanned.value=formatMoney(gpPlannedValue_);
            
            var gpPlannedPosto=document.getElementById("gpPlannedPosto");
            var gpPlannedPostoValue_=(gpPlannedValue_/dataRezultatSO.totalRevenue*100).toFixed(2);
            gpPlannedPosto.value=gpPlannedPostoValue_;
            
            var gpMinus=document.getElementById("gpMinus");
            var gpMinusValue_=gpPlannedPostoValue_-gpPostoValue_+4;
            gpMinus.value=formatMoney(gpMinusValue_);

            var costActual_=document.getElementById("costActual");
            costActual_.value=formatMoney(dataRezultatSO.costActual);
            
            var gpActual=document.getElementById("gpActual");
            var gpActualValue_=dataRezultatSO.totalRevenue-dataRezultatSO.costActual;
            gpActual.value=formatMoney(gpActualValue_);
            
            var gpActualPosto=document.getElementById("gpActualPosto");
            gpActualPosto.value=formatMoney(gpActualValue_/dataRezultatSO.totalRevenue*100);
            
            $('#urediPD').modal('hide');
        }  
    }
}

function prikaziClaim() {
	hideGreska();
	
	var idProjektDetalji=dataRezultatSO.id;
	
    window.open(`/v1/html/claim.html?idProjektDetalji=${idProjektDetalji}`, "_parent");
}

function uredi() {
    hideGreska();

    var iTotalRevenue_=document.getElementById("iTotalRevenue");
    iTotalRevenue_.value=dataRezultatSO.totalRevenue;

    var iCostPs_=document.getElementById("iCostPs");
    iCostPs_.value=dataRezultatSO.costPs;
}

function uredi2() {
    hideGreska();

    var iTotalRevenue_=document.getElementById("iTotalRevenue");
    var iCostPs_=document.getElementById("iCostPs");

    var url = new URL(document.URL);
    var id = url.searchParams.get("id");
    var projektUrl_=`${serverUrl}urediProjektDatalji/${id}?totalRevenue=${iTotalRevenue_.value}&costPs=${iCostPs_.value}`;

    pozoviRestServis(projektUrl_, projekDetaljiRest_);
}

function prikaziPodugovarac() {
    hideGreska();
 
    var idProjektDetalji=dataRezultatSO.id;
	
    window.open(`/v1/html/podugovarac.html?idProjektDetalji=${idProjektDetalji}`, "_parent");
}
