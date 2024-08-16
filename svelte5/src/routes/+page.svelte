<script lang="ts">
  import { pozoviRestServis, serverUrl } from '$lib/common';
  import { onMount } from 'svelte';
  import search16 from '$lib/img/search16.png';

  var aktivni="aktivni";
  var neaktivni="neaktivni";
  let dataRezultatSO=$state([]);

  onMount(() => {
		projektiTC();
	});

  function inicijalnoNapuni() {
    var projektUrl=`${serverUrl}createDB`;
    
    pozoviRestServis(projektUrl, projektiTC);
  }

  function projektiTC() {
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

  function projektiRest_(data: any) {
    dataRezultatSO= data.rezultat;
  }
</script>

<div class="container">
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
              <a class="dropdown-item" _onclick="urediOsobuOtvori()">Uredi osobu</a>
              <a class="dropdown-item" _onclick="urediPodugovaraca()">Uredi podugovarača</a>
            </div>
          </div>
          <button class="btn btn-primary mb-3 ml-3" type="submit" data-toggle="modal" data-target="#noviProjekt" _onclick="hideGreska()">Novi projekt</button>
          <button class="btn btn-primary mb-3 ml-3" type="submit" on:click={() => inicijalnoNapuni()}>Inicijalno napuni</button>
        </div>
        <div id="pretrazi" class="pretrazi">
            <img src={search16} _onclick="pretrazi()" >
        </div>
        <div id="trazi" class="form-inline my-2 my-lg-0" style="display: none">
          <input type="text" id="projektTrazi" _onKeyUp="onProjektTrazi()" class="form-control mr-sm-2" autofocus>
          <button _onclick="projektTrazi()" class="btn btn-outline-success my-2 my-sm-0" type="submit">Zatvori</button>
        </div>
      </nav>

      <div name="greska" style="display: none"></div>
   
      <table class="table table-sm table-striped">
        <thead>
            <tr>
                <th class="text-right">Id</th>
                <th>Claim</th>
                <th>Contract</th>
                <th>Status</th>
                <th>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="aktivni" value="option1" _onchange="projekti()" checked>
                    <label class="form-check-label" for="aktivni">Aktivni</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="neaktivni" value="option2" _onchange="projekti()">
                    <label class="form-check-label" for="neaktivni">Neaktivni</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="svi" value="option3" _onchange="projekti()">
                    <label class="form-check-label" for="svi">Svi</label>
                  </div>
                </th>
            </tr>
        </thead>
        <tbody id="projektTablicaBody">
          {#each dataRezultatSO as cell}
          <tr>
            <td>{cell.id}</td>
            <td>{cell.claim}</td>
            <td>{cell.contract}</td>
            <td>{cell.status}</td>
            <td></td>
          </tr>
          {/each}          
        </tbody>
      </table>
      <div class="btn-group" role="group">
        <button type="button" id="projektTablicaPrethodno" _onclick="projektTablicaPrethodno()" class="btn btn-outline-secondary">&laquo;<div id="pTablicaPrethodno" style="display: none;"></div></button>
        <button type="button" id="projektTablicaSlijedece" _onclick="projektTablicaSlijedece()" class="btn btn-outline-secondary">&raquo;<div id="pTablicaSlijedece" style="display: none;"></div></button>
      </div>
    </div>

    

   

