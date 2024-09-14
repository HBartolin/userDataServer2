<script lang="ts">
  import { pozoviRestServis, serverUrl } from "$lib/common";
  import { onMount } from "svelte";
  import search16 from "$lib/img/search16.png";
  import MojCatch from "$lib/MojCatch.svelte";
  import DisplayAlert from "$lib/DisplayAlert.svelte";
  import { type INTP } from "$lib/common.js";
  import type {
    ChangeEventHandler,
    KeyboardEventHandler,
  } from "svelte/elements";

  let dataRezultatSO: Array<INTP> = $state([]);
  let adiPromise: Promise<string> | undefined = $state();
  let displayAlertMessage = $state();
  let shownTrazi: boolean = $state(false);
  let traziVaule = $state("");
  let aktivniFild = false;
  let neaktivniFild = false;
  let sviFild = false;
  let noviProjectCollectionDialog: HTMLDialogElement;
  let inputClaim = $state("");
  let inputContract = $state("");
  let greska2 = $state([]);

  onMount(() => {
    displayAlertMessage = "Dohvaćam podatak.";
    noviProjectCollectionDialog = document.getElementById(
      "noviProject-confirmation-dialog",
    ) as HTMLDialogElement;

    adiPromise = projektiTC();
  });

  const ocAktiv: ChangeEventHandler<HTMLInputElement> = async () => {
    setFieldFalse();
    aktivniFild = true;

    adiPromise = projektiTC();
  };

  const ocSvi: ChangeEventHandler<HTMLInputElement> = async () => {
    setFieldFalse();
    sviFild = true;

    adiPromise = projektiTC();
  };

  const ocNeaktiv: ChangeEventHandler<HTMLInputElement> = async () => {
    setFieldFalse();
    neaktivniFild = true;

    adiPromise = projektiTC();
  };

  const setFieldFalse: Function = () => {
    aktivniFild = false;
    neaktivniFild = false;
    sviFild = false;
  };

  const traziButton: KeyboardEventHandler<HTMLInputElement> = async () => {
    displayAlertMessage = "Tražim..";
    var projektUrl_ = `${serverUrl}traziProjekt?trazi=${traziVaule}`;

    adiPromise = pozoviRestServis(projektUrl_, projektTraziRest_);
  };

  const inicijalnoNapuni: Function = async () => {
    displayAlertMessage = "Dodajem podatak.";
    var projektUrl = `${serverUrl}createDB`;

    adiPromise = pozoviRestServis(projektUrl, projektiTC);
  };

  const projektiTC: Function = async () => {
    var projektUrl = `${serverUrl}projekti?`;

    if (aktivniFild) {
      projektUrl += "status=A";
    } else if (neaktivniFild) {
      projektUrl += "status=N";
    }

    await pozoviRestServis(projektUrl, projektiRest_);
  };

  const projektiRest_: Function = async (data: any) => {
    dataRezultatSO = data.rezultat;
  };

  const prikaziProjekt: Function = (id: string) => {
    window.open(`projektDetalji/?id=${id}`, "_parent");
  };

  const pretrazi: Function = () => {
    traziVaule = "";
    shownTrazi = true;
  };

  const projektTrazi: Function = () => {
    shownTrazi = false;
    traziVaule = "";
  };

  const projektTraziRest_: Function = async (data: any) => {
    dataRezultatSO = data.rezultat;
  };

  const noviProjectModalShown: Function = () => {
    greska2 = [];
    inputClaim = "";
    inputContract = "";

    noviProjectCollectionDialog.showModal();
  };

  const noviProjectClose: Function = () => {
    noviProjectCollectionDialog.close();
  };

  const noviProjekt: Function = () => {
    var projektUrl_ = `${serverUrl}noviProjekt?claim=${inputClaim}&contract=${inputContract}`;

    pozoviRestServis(projektUrl_, noviProjektRest_);
  };

  const noviProjektRest_: Function = (data: any) => {
    if (data.greska.length > 0) {
      greska2 = data.greska;
    } else {
      dataRezultatSO = data.rezultat;

      noviProjectCollectionDialog.close();
    }
  };
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
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M4 6h16M4 12h8m-8 6h16"
          />
        </svg>
      </div>
      <!-- svelte-ignore a11y_no_noninteractive_tabindex -->
      <ul
        tabindex="0"
        class="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow"
      >
        <details class="dropdown">
          <summary class="btn btn-outline btn-primary">Uredi</summary>
          <ul
            class="menu dropdown-content bg-base-100 rounded-box z-[1] w-52 p-2 shadow margi"
          >
            <li><a _onclick="urediOsobuOtvori()">Uredi osobu</a></li>
            <li><a _onclick="urediPodugovaraca()">Uredi podugovarača</a></li>
          </ul>
        </details>

        <button
          class="btn btn-outline btn-primary mt-4"
          type="submit"
          onclick={() => noviProjectModalShown()}>Novi projekt</button
        >
        <button
          class="btn btn-outline btn-primary mt-4"
          type="submit"
          onclick={() => inicijalnoNapuni()}>Inicijalno napuni</button
        >
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
        <summary class="btn btn-outline btn-primary">Uredi</summary>
        <ul
          class="menu dropdown-content bg-base-100 rounded-box z-[1] w-52 p-2 shadow margi"
        >
          <!-- svelte-ignore a11y_missing_attribute -->
          <li><a _onclick="urediOsobuOtvori()">Uredi osobu</a></li>
          <!-- svelte-ignore a11y_missing_attribute -->
          <li><a _onclick="urediPodugovaraca()">Uredi podugovarača</a></li>
        </ul>
      </details>

      <button
        class="btn btn-outline btn-primary mx-2"
        type="submit"
        onclick={() => noviProjectModalShown()}>Novi projekt</button
      >
      <button
        class="btn btn-outline btn-primary"
        type="submit"
        onclick={() => inicijalnoNapuni()}>Inicijalno napuni</button
      >
    </div>
  </div>
  <div class="navbar-end">
    {#if !shownTrazi}
      <!-- svelte-ignore a11y_click_events_have_key_events -->
      <!-- svelte-ignore a11y_no_static_element_interactions -->
      <!-- svelte-ignore a11y_missing_attribute -->
      <a onclick={() => pretrazi()}><img src={search16} /></a>
    {:else}
      <div id="trazi" class="">
        <!-- svelte-ignore a11y_autofocus -->
        <input
          type="text"
          id="projektTrazi"
          bind:value={traziVaule}
          onkeyup={traziButton}
          class="input input-bordered input-primary input-xs max-w-xs"
          autofocus
        />
        <button
          onclick={() => projektTrazi()}
          class="btn btn-outline btn-primary"
          type="submit">Zatvori</button
        >
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
            <input
              class="radio radio-xs"
              type="radio"
              name="inlineRadioOptions"
              id="aktivni"
              onchange={ocAktiv}
              checked
            />
            <label class="" for="aktivni">Aktivni</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="radio radio-xs"
              type="radio"
              name="inlineRadioOptions"
              id="neaktivni"
              onchange={ocNeaktiv}
            />
            <label class="" for="neaktivni">Neaktivni</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="radio radio-xs"
              type="radio"
              name="inlineRadioOptions"
              id="svi"
              onchange={ocSvi}
            />
            <label class="" for="svi">Svi</label>
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
                <button
                  type="button"
                  class="btn btn-outline btn-primary join-item"
                  onclick={() => prikaziProjekt(cell.id)}>Prikaži</button
                >
                <button
                  type="button"
                  class="btn btn-outline btn-primary join-item"
                  _onclick="uExel({cell.id}, '{cell.claim}', '{cell.contract}')"
                  >U Excel</button
                >
                <button
                  type="button"
                  class="btn btn-outline btn-error join-item"
                  _onclick="zatvoriProjekt({cell.id}, {cell.ts})"
                  >Zatvori</button
                >
                <button
                  type="button"
                  class="btn btn-outline btn-primary join-item"
                  _onclick="otvoriProjekt({cell.id}, {cell.ts})">Otvori</button
                >
              </div>
            </div>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
  <div class="btn-group" role="group">
    <button
      type="button"
      id="projektTablicaPrethodno"
      _onclick="projektTablicaPrethodno()"
      class="btn btn-outline-secondary"
      >&laquo;
      <div id="pTablicaPrethodno" style="display: none;"></div></button
    >
    <button
      type="button"
      id="projektTablicaSlijedece"
      _onclick="projektTablicaSlijedece()"
      class="btn btn-outline-secondary"
      >&raquo;
      <div id="pTablicaSlijedece" style="display: none;"></div></button
    >
  </div>
</div>

<dialog id="noviProject-confirmation-dialog" class="modal">
  <div class="modal-box">
    <div class="my-8 text-xl font-bold">Novi projekt</div>
    {#each greska2 as g2}
      <div role="alert" class="alert alert-error my-4">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-6 w-6 shrink-0 stroke-current"
          fill="none"
          viewBox="0 0 24 24"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
          />
        </svg>
        <span>{g2}</span>
      </div>
    {/each}
    <label class="input input-bordered flex items-center my-4">
      <div class="mr-1">Claim:</div>
      <input
        type="text"
        class="grow"
        id="inputClaim"
        bind:value={inputClaim}
        autofocus
      />
    </label>
    <label class="input input-bordered flex items-center my-4">
      <div class="mr-1">Contract:</div>
      <input
        type="text"
        class="grow"
        id="inputContract"
        bind:value={inputContract}
      />
    </label>
    <div class="modal-action my-8">
      <button
        type="button"
        class="btn btn-outline btn-primary"
        onclick={() => noviProjekt()}>Novi projekt</button
      >
      <button
        type="button"
        class="btn btn-outline"
        onclick={() => noviProjectClose()}>Odustani</button
      >
    </div>
  </div>
</dialog>
