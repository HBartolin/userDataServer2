<script lang="ts">
  import { pozoviRestServis, serverUrl } from '$lib/common';
  import { onMount } from 'svelte';
  import search16 from '$lib/img/search16.png';
  import MojCatch from "$lib/MojCatch.svelte";
  import DisplayAlert from "$lib/DisplayAlert.svelte";
    import type { KeyboardEventHandler } from 'svelte/elements';

  var aktivni="aktivni";
  var neaktivni="neaktivni";
  let dataRezultatSO=$state([]);
  let adiPromise: Promise<string>=$state(undefined);
  let displayAlertMessage=$state();
  let shownTrazi: boolean=$state(false);
  let traziVaule=$state();

  onMount(() => {
    displayAlertMessage="Dohvaćam podatak.";
		adiPromise=projektiTC();
	});

  const inicijalnoNapuni: Function = async () => {
    displayAlertMessage="Dodajem podatak.";
    var projektUrl=`${serverUrl}createDB`;
    
    adiPromise=pozoviRestServis(projektUrl, projektiTC);
  }

  const projektiTC: Function = async () => {
    var aktivni_=document.getElementById(aktivni);
    var neaktivni_=document.getElementById(neaktivni);
    var projektUrl=`${serverUrl}projekti?`;

    if(aktivni_?.checked) {
        projektUrl+="status=A";
    } else if(neaktivni_?.checked) {
        projektUrl+="status=N";
    } 

    await pozoviRestServis(projektUrl, projektiRest_);
  }

  const projektiRest_: Function = async (data: any) => {
    dataRezultatSO=data.rezultat;
  }

  const prikaziProjekt: Function = (id: string) => {   
    window.open(`projektDetalji/?id=${id}`, "_parent");
}

  const pretrazi: Function = () => {
    traziVaule="";
    shownTrazi=true;
  }

  const projektTrazi: Function = () => {
    shownTrazi=false;
    traziVaule="";
  }

  const onProjektTrazi: Function = async (e: KeyboardEventHandler<HTMLInputElement>) => {
    if(e.key==="Backspace") {
      traziVaule=traziVaule.substring(0, traziVaule.length-1);
    } else {
      traziVaule+=e.key;
    }
       console.log(traziVaule);
    var projektUrl_=`${serverUrl}traziProjekt?trazi=${traziVaule}`;

    await pozoviRestServis(projektUrl_, projektTraziRest_);
  }

  const projektTraziRest_: Function = async (data: any) => {
    dataRezultatSO=data.rezultat;
  }
</script>

{#await adiPromise}
    <DisplayAlert msg={displayAlertMessage} />
{:catch e}
    <MojCatch errorMsg={e} />
{/await}

<div class="navbar bg-base-100">
  <div class="navbar-start">
    <div class="dropdown">
      <div tabindex="0" role="button" class="btn btn-ghost lg:hidden">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor">
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M4 6h16M4 12h8m-8 6h16" />
        </svg>
      </div>
      <ul
        tabindex="0"
        class="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow">
        <details class="dropdown">
          <summary class="btn btn-primary">Uredi</summary>
          <ul class="menu dropdown-content bg-base-100 rounded-box z-[1] w-52 p-2 shadow margi">
            <li><a _onclick="urediOsobuOtvori()">Uredi osobu</a></li>
            <li><a _onclick="urediPodugovaraca()">Uredi podugovarača</a></li>
          </ul>
        </details>
        
        <button class="btn btn-primary mt-4" type="submit" _onclick="hideGreska()">Novi projekt</button>
        <button class="btn btn-primary mt-4" type="submit" onclick={() => inicijalnoNapuni()}>Inicijalno napuni</button>
      </ul>
    </div>
    <div class="btn btn-ghost breadcrumbs text-sm">
      <ul>
        <li><a>Projekti</a></li>
      </ul>
    </div>
  </div>
  <div class="navbar-center hidden lg:flex">
    <div class="menu menu-horizontal px-1">
      <details class="dropdown">
        <summary class="btn btn-primary">Uredi</summary>
        <ul class="menu dropdown-content bg-base-100 rounded-box z-[1] w-52 p-2 shadow margi">
          <li><a _onclick="urediOsobuOtvori()">Uredi osobu</a></li>
          <li><a _onclick="urediPodugovaraca()">Uredi podugovarača</a></li>
        </ul>
      </details>
      
      <button class="btn btn-primary mx-2" type="submit" _onclick="hideGreska()">Novi projekt</button>
      <button class="btn btn-primary" type="submit" onclick={() => inicijalnoNapuni()}>Inicijalno napuni</button>
    </div>
  </div>
  <div class="navbar-end">
    {#if !shownTrazi}
      <a onclick={() => pretrazi()} ><img src={search16} ></a>
    {:else}
      <div id="trazi" class="">
        <input type="text" id="projektTrazi" onkeyup={onProjektTrazi} class="input input-bordered input-primary input-xs max-w-xs" autofocus>
        <button onclick={() => projektTrazi()} class="btn btn-primary" type="submit">Zatvori</button>
      </div>
    {/if}
  </div>
</div>

<div class="container overflow-x-auto">   
      <table class="table table-zebra table-sm">
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
          <tr class="hover">
            <td>{cell.id}</td>
            <td>{cell.claim}</td>
            <td>{cell.contract}</td>
            <td>{cell.status}</td>
            <td>
              <div class="" id="navbarSupportedContent_{cell.id}">
                <div class="join">
                  <button type="button" class="btn btn-outline btn-primary join-item" onclick={() => prikaziProjekt(cell.id)}>Prikaži</button>
                  <button type="button" class="btn btn-outline btn-primary join-item" _onclick="uExel({cell.id}, '{cell.claim}', '{cell.contract}')">U Excel</button>
                  <button type="button" class="btn btn-outline btn-error join-item" _onclick="zatvoriProjekt({cell.id}, {cell.ts})">Zatvori</button>
                  <button type="button" class="btn btn-outline btn-primary join-item" _onclick="otvoriProjekt({cell.id}, {cell.ts})">Otvori</button>
                </div>
              </div>
            </td>
          </tr>
          {/each}          
        </tbody>
      </table>
      <div class="btn-group" role="group">
        <button type="button" id="projektTablicaPrethodno" _onclick="projektTablicaPrethodno()" class="btn btn-outline-secondary">&laquo;<div id="pTablicaPrethodno" style="display: none;"></div></button>
        <button type="button" id="projektTablicaSlijedece" _onclick="projektTablicaSlijedece()" class="btn btn-outline-secondary">&raquo;<div id="pTablicaSlijedece" style="display: none;"></div></button>
      </div>
    </div>

    

   

