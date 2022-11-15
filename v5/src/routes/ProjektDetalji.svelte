<script>
    import {onMount} from 'svelte';
    import {serverUrl, pozoviRestServis} from '../common.js';
    import MojCatch from "../MojCatch.svelte";
    import DisplayAlert from "../DisplayAlert.svelte";

    export let params = {};
    let id;
    $: id=params.id;
    let ssData={};
    let rsData;

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