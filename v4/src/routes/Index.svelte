<script lang="ts">
    import {onMount} from 'svelte';
    //import { bind, debug } from 'svelte/internal';
    import {serverUrl, pozoviRestServis} from '../common.js';
    import MojCatch from "../MojCatch.svelte";
    import DisplayAlert from "../DisplayAlert.svelte";
    import Search from "../Search.svelte";
    import DialogOK from "../DialogOK.svelte";
    import {push, pop, replace} from 'svelte-spa-router';
    import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink,
    Dropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem
  } from 'sveltestrap';

  let isOpen = false;

  function handleUpdate(event) {
    isOpen = event.detail.isOpen;
  }
  
    let prikaziMsg="";
    let dialogOk;
    var aktivni="aktivni";
    var neaktivni="neaktivni";
    var dataRezultatSO=[];
    // var redakClaim_;
    // var redakContract_;
    // var projektTablica="projektTablicaBody";
    let rsData;
    const fullProjektDetalji=`/projektDetalji/`;

    onMount(() => { 
      return projektiTC();
    })

    function prikaziButton(id) {
      push(fullProjektDetalji + id)
      //window.open(fullProjektDetalji + id, "_parent");
    }

    // function projekti() {
    //     // try {
    //         return projektiTC();
    //     // } catch(e) {
    //     //     postaviGresku(e);
    //     // }
    // }

    function projektiTC() {
       // hideGreska();     

        let aktivni_=document.getElementById(aktivni) as HTMLInputElement;
        var neaktivni_=document.getElementById(neaktivni) as HTMLInputElement;
        var projektUrl=`${serverUrl}projekti?`;

        if(aktivni_.checked) {
            projektUrl+="status=A";
        } else if(neaktivni_.checked) {
            projektUrl+="status=N";
        } 

        rsData=pozoviRestServis(projektUrl, projektiRest_);

        return rsData;
    }

    function projektiRest_(data) {
        dataRezultatSO=data.rezultat;
        

        // if(data.greska.length>0) {
        //     postaviGresku(data.greska);
        // }

        // obrisiRetke2(projektTablica);

        // dataRezultatSO.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

        // tablicaPagination(data); 

        return dataRezultatSO;
    }

    // function ucitajRedakProjektneTablice(redak, iRedak) {
    //     var table = document.getElementById(projektTablica);
    //     var row = table.insertRow(-1);

    //     var i=0;
    //     var cell1 = row.insertCell(i++);
    //     var cell2 = row.insertCell(i++);
    //     var cell3 = row.insertCell(i++);
    //     var cell4 = row.insertCell(i++);
    //     var cell5 = row.insertCell(i++);

    //     cell1.innerHTML = redak.id;
    //     cell1.className="text-right";
    //     cell2.innerHTML = redak.claim;
    //     cell3.innerHTML = redak.contract;
    //     cell4.innerHTML = redak.status;
        
    //     var gumbiZaRedak_=document.getElementById("gumbiZaRedak");
    //     gumbiZaRedak_ = gumbiZaRedak_.cloneNode(true);
    //     gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakId}', 'g'), redak.id);
    //     gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakTs}', 'g'), redak.ts);
    //     gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakClaim}', 'g'), redak.claim);
    //     gumbiZaRedak_.innerHTML=gumbiZaRedak_.innerHTML.replace(new RegExp('{redakContract}', 'g'), redak.contract); 
    //     cell5.innerHTML=gumbiZaRedak_.innerHTML;
    // }

    function urediOsobuOtvori(): void {
      //   hideGreska();
    
         window.open("/v1/html/sifarnikOsoba.html", "_parent");
     }

    function pretrazi() {
    //     hideGreska(); 

         var pretrazi_=document.getElementById("pretrazi");
         pretrazi_.style['display'] = 'none';

         var trazi_=document.getElementById("trazi");
         trazi_.style['display'] = 'block';
    }

     function onProjektTrazi() {
    //     var aktivni_=document.getElementById(aktivni);
    //     aktivni_.checked=true;
    //     aktivni_.checked=false;

    //     var projektTrazi_ = document.getElementById("projektTrazi");
    //     var projektUrl_=`${serverUrl}traziProjekt?trazi=${projektTrazi_.value}`;

    //     pozoviRestServis(projektUrl_, projektTraziRest_);
     }

     function projektTrazi() {
    //     hideGreska(); 

    //     var pretrazi_=document.getElementById("pretrazi");
    //     pretrazi_.style['display'] = 'block';

    //     var trazi_=document.getElementById("trazi");
    //     trazi_.style['display'] = 'none';
     }

    // function projektTraziRest_(data) {
    //     if(data.greska.length>0) {
    //         postaviGresku(data.greska);
    //     }

    //     obrisiRetke2(projektTablica);

    //     if(data.rezultat.length>0) {
    //         data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));
    //     }

    //     tablicaPagination(data); 
    // }

     function projektTablicaPrethodno() {
    //     projektTablicaSlijedecePrethodno(0);
     }

    function projektTablicaSlijedece() {
    //     projektTablicaSlijedecePrethodno(1);
   }

    // function projektTablicaSlijedecePrethodno(smjer) {
    //     var aktivni_=document.getElementById(aktivni);
    //     var neaktivni_=document.getElementById(neaktivni);
    //     var pTablicaPrethodno_=document.getElementById("pTablicaPrethodno");
    //     var pTablicaSlijedece_=document.getElementById("pTablicaSlijedece");

    //     var projektUrl=`${serverUrl}tablicaProjekti?pageNumber=`;
        
    //     if(smjer==0) {
    //         projektUrl+=pTablicaPrethodno_.innerText;
    //     } else {
    //         projektUrl+=pTablicaSlijedece_.innerText;
    //     }

    //     if(aktivni_.checked) {
    //         projektUrl+="&status=A";
    //     } else if(neaktivni_.checked) {
    //         projektUrl+="&status=N";
    //     } 

    //     pozoviRestServis(projektUrl, projektTablicaSlijedecePrethodnoRest_);
    // }

    // function projektTablicaSlijedecePrethodnoRest_(data) {
    //     if(data.greska.length>0) {
    //         postaviGresku(data.greska);
    //     } 
        
    //     obrisiRetke2(projektTablica);

    //     data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    //     tablicaPagination(data);
    // }

    function noviProjekt() {
    //     hideGreska();
        
    //     var inputClaim = document.getElementById("inputClaim");
    //     var inputContract = document.getElementById("inputContract");
    //     var projektUrl_=`${serverUrl}noviProjekt?claim=${inputClaim.value}&contract=${inputContract.value}`;

    //     pozoviRestServis(projektUrl_, noviProjektRest_);
    }

    // function noviProjektRest_(data) {
    //     if(data.greska.length>0) {
    //         postaviGresku(data.greska);
    //     } else {
    //         inputClaim.value="";
    //         inputContract.value="";

    //         // $('#noviProjekt').modal('hide');  
    //     }

    //     obrisiRetke2(projektTablica);

    //     data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    //     var aktivni_=document.getElementById(aktivni);
    //     aktivni_.checked=true;
        
    //     if(data.ok.length>0) {
    //         postaviOK(data.ok);
    //     } 
    // }

    // function prikaziProjekt(id) {
    //     hideGreska();
    
    //     window.open(`/v1/html/projektDetalji.html?id=${id}`, "_parent");
    // }

    // function zatvoriOtvoriProjekt(id, ts, pojoAdress) {
    //     hideGreska();

    //     var aktivni_=document.getElementById(aktivni);
    //     var neaktivni_=document.getElementById(neaktivni);

    //     if(aktivni_.checked) {
    //         pojoAdress+="&status=A";
    //     } else if(neaktivni_.checked) {
    //         pojoAdress+="&status=N";
    //     } 

    //     pozoviRestServis(pojoAdress, zatvoriOtvoriProjektFRest_);
    // }

    // function zatvoriOtvoriProjektFRest_(data) {
    //     if(data.greska.length>0) {
    //         postaviGresku(data.greska);
    //     }

    //     obrisiRetke2(projektTablica);

    //     data.rezultat.forEach((redak, i) => ucitajRedakProjektneTablice(redak, i));

    //     if(data.ok.length>0) {
    //         postaviOK(data.ok);
    //     }

    //     tablicaPagination(data);
    // }

    // function otvoriProjekt(id, ts) {
    //     try {
    //         zatvoriOtvoriProjekt(id, ts, `${serverUrl}otvoriProjekt/${id}?ts=${ts}`);
    //     } catch(e) {
    //         postaviGresku(e);
    //     }
    // }

    // function zatvoriProjekt(id, ts) {
    //     try {
    //         zatvoriOtvoriProjekt(id, ts, `${serverUrl}zatvoriProjekt/${id}?ts=${ts}`);
    //     } catch(e) {
    //         postaviGresku(e);
    //     }
    // }

    // function traziZatvori() {
    //     window.addEventListener('mouseup', (event) => {
    //         var trazi_=document.getElementById("trazi");

    //         if(event.target!=trazi_ && event.target.parentNode!=trazi_) {
    //             trazi_.style['display'] = 'none';

    //             var pretrazi_=document.getElementById("pretrazi");
    //             pretrazi_.style['display'] = 'block';
    //         }
    //     });
    // }

    // function uExel(id, redakClaim, redakContract) {
    //     hideGreska();
    //     redakClaim_=redakClaim;
    //     redakContract_=redakContract;
    //     var projektUrl_=`${serverUrl}uExcel/${id}`;

    //     pozoviRestServis(projektUrl_, pozoviExcel_);
    // }

    // function pozoviExcel_(data) {     
    //     redakContract_=redakContract_.replace(new RegExp('/', 'g'), '.');
    //     var blob = b64toBlob(data.rezultat, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');

    //     anchor = document.createElement('a');
    //     anchor.download = `${redakContract_}_${redakClaim_}_${getFormattedDate()}.xlsx`;
    //     anchor.href = (window.webkitURL || window.URL).createObjectURL(blob);
    //     anchor.dataset.downloadurl = ['text/plain', anchor.download, anchor.href].join(':');
    //     anchor.click();
    // }

    // function getFormattedDate() {
    //     var date = new Date();

    //     let month = date.getMonth() + 1;
    //     var day = date.getDate();
    //     var hour = date.getHours();
    //     var min = date.getMinutes();
    //     var sec = date.getSeconds();

    //     month = +(month < 10 ? "0" : "") + month;
    //     day = +(day < 10 ? "0" : "") + day;
    //     hour = +(hour < 10 ? "0" : "") + hour;
    //     min = +(min < 10 ? "0" : "") + min;
    //     sec = +(sec < 10 ? "0" : "") + sec;

    //     return `${date.getFullYear()}-${month}-${day}_${hour}.${min}.${sec}`;
    // }

    // function b64toBlob(b64Data, contentType, sliceSize) {
    // contentType = contentType || '';
    // sliceSize = sliceSize || 512;

    // var byteCharacters = atob(b64Data);
    // var byteArrays = [];

    // for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
    //     var slice = byteCharacters.slice(offset, offset + sliceSize);
    //     var byteNumbers = new Array(slice.length);

    //     for(var i = 0; i < slice.length; i++) {
    //         byteNumbers[i] = slice.charCodeAt(i);
    //     }

    //     var byteArray = new Uint8Array(byteNumbers);

    //     byteArrays.push(byteArray);
    // }

    // var blob = new Blob(byteArrays, {type: contentType});

    // return blob;
    // }

    function urediPodugovaraca() {
    //     hideGreska();
    
        window.open("/v1/html/sifarnikPodugovaraca.html", "_parent");
   }

    function inicijalnoNapuni() {
        //hideGreska();
        var projektUrl=`${serverUrl}createDB`;
        
        rsData=pozoviRestServis(projektUrl, createDB_);

        return rsData;
    }

    function createDB_(data) {	
        //postaviOK(JSON.stringify(data));
        rsData=projektiTC();
        prikaziMsg=JSON.stringify(data); 
        dialogOk.dialogOkToggle();
        
        return rsData;
    }
</script>


{#await rsData}
    <DisplayAlert msg="Radim.." />
{:catch e}
    <MojCatch errorMsg={e} />
{/await}

<div class="container">
    <Navbar color="light" light expand="md">
      <NavbarToggler on:click={() => (isOpen = !isOpen)} />
        <Collapse {isOpen} navbar expand="md" on:update={handleUpdate}>
          <Nav class="ms-auto" navbar> 
            <NavItem>Projekti</NavItem>
            <Dropdown nav inNavbar>
              <DropdownToggle color="primary" caret>Uredi</DropdownToggle>
              <DropdownMenu end>
                <DropdownItem>Uredi osobu</DropdownItem>
                <DropdownItem>Uredi podugovarača</DropdownItem>
              </DropdownMenu>
            </Dropdown>
            <NavItem color='primary'>
              <NavLink color='primary' on:click="{() => inicijalnoNapuni()}">Inicijalno napuni</NavLink>
            </NavItem>
          </Nav>
      </Collapse>
    </Navbar>
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item active" aria-current="page">Projekti</li>
            </ol>
          </nav>
          <div class="btn-group" role="group">
            <button id="btnGroupDrop1" type="button" class="btn btn-primary mb-3 ml-3 dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Uredi
            </button>
            <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
              <a href={undefined} class="dropdown-item" on:click={() => urediOsobuOtvori()}>Uredi osobu</a>
              <a href={undefined} class="dropdown-item" on:click={() => urediPodugovaraca()}>Uredi podugovarača</a>
            </div>
          </div>
          <button class="btn btn-primary mb-3 ml-3" type="submit" data-toggle="modal" data-target="#noviProjekt" >Novi projekt</button>
          <button class="btn btn-primary mb-3 ml-3" type="submit" on:click="{() => inicijalnoNapuni()}">Inicijalno napuni</button>
        </div>
        <div id="pretrazi" class="pretrazi">
          <button type="button" on:click={() => pretrazi()} class="input-group-text">
            <Search />
          </button>
        </div>
        <div id="trazi" class="form-inline my-2 my-lg-0" style="display: none">
          <input type="text" id="projektTrazi" on:keyup={() => onProjektTrazi()} class="form-control mr-sm-2">
          <button on:click={() => projektTrazi()} class="btn btn-outline-success my-2 my-sm-0" type="submit">Zatvori</button>
        </div>
      </nav>

      <div name="greska" class="alert alert-danger" style="display: none" role="alert" ></div>
   
      <table class="table table-sm table-striped">
        <thead>
            <tr>
                <th class="text-right">Id</th>
                <th>Claim</th>
                <th>Contract</th>
                <th>Status</th>
                <th>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="aktivni" value="option1"  checked>
                    <label class="form-check-label" for="aktivni">Aktivni</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="neaktivni" value="option2" >
                    <label class="form-check-label" for="neaktivni">Neaktivni</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="svi" value="option3" >
                    <label class="form-check-label" for="svi">Svi</label>
                  </div>
                </th>
            </tr>
        </thead>
        <tbody id="projektTablicaBody">
            {#each dataRezultatSO as redak}
              <tr>
                <td class="text-end text-secondary">{redak.id}</td>
                <td class="text-end text-secondary">{redak.claim}</td>
                <td class="text-end text-secondary">{redak.contract}</td>
                <td class="text-end text-secondary">{redak.status}</td>
                <td>
                  <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent_{redak.id}" aria-controls="navbarSupportedContent_{redak.id}" aria-expanded="false" aria-label="Toggle navigation">
                      <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent_{redak.id}">
                      <div class="btn-group" role="group">
                        <button type="button" class="btn btn-outline-primary" on:click="{() => prikaziButton(redak.id)}">Prikaži</button>
                        <button type="button" class="btn btn-outline-primary" >U Excel</button>
                        <button type="button" class="btn btn-outline-danger" >Zatvori</button>
                        <button type="button" class="btn btn-outline-primary" >Otvori</button>
                      </div>
                    </div>
                  </nav>
                </td>
              </tr>
            {/each}
        </tbody>
      </table>
      <div class="btn-group" role="group">
        <button type="button" id="projektTablicaPrethodno" on:click={() => projektTablicaPrethodno()} class="btn btn-outline-secondary">&laquo;<div id="pTablicaPrethodno" style="display: none;"></div></button>
        <button type="button" id="projektTablicaSlijedece" on:click={() => projektTablicaSlijedece()} class="btn btn-outline-secondary">&raquo;<div id="pTablicaSlijedece" style="display: none;"></div></button>
      </div>
    </div>

    <div class="modal fade" id="noviProjekt" tabindex="-1" role="dialog" aria-labelledby="noviProjektLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="noviProjektLabel">Novi projekt</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div name="greska" class="alert alert-danger" style="display: none" role="alert" ></div>
          <div class="modal-body">
            <div class="form-group row">
              <label for="inputClaim" class="col-sm-2 col-form-label">Claim:</label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="inputClaim">
              </div>
            </div>
            <div class="form-group row">
              <label for="inputContract" class="col-sm-2 col-form-label">Contract:</label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="inputContract">
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" on:click={() => noviProjekt()}>Novi projekt</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Odustani</button>
          </div>
        </div>
      </div>
    </div>

    <DialogOK {prikaziMsg} bind:this={dialogOk} />
  