<script lang="ts">
    import {onMount} from 'svelte';
    import {serverUrl, pozoviRestServis} from '../common.js';
    import MojCatch from "../MojCatch.svelte";
    import DisplayAlert from "../DisplayAlert.svelte";

    type UrlType = {
        id: number
    }

    export let params: UrlType;

    let id: number;
    $: id=params.id;
    let ssData={};
    let rsData: Promise<any>;

    onMount(() => { 
        var projektUrl_=`${serverUrl}projektDatalji/${id}`;
        
        rsData=pozoviRestServis(projektUrl_, projekDetaljiRest_);

        return rsData;
    })

    function projekDetaljiRest_(data) { 
        ssData=data;
    }
</script>

<div class="container">
    {#await rsData}
        <DisplayAlert msg="Radim.." />
    {:catch e}
        <MojCatch errorMsg={e} />
    {/await}

    <h3>{JSON.stringify(ssData)}</h3>
</div>