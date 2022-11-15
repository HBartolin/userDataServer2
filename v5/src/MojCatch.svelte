<script>
    export let errorMsg = "Nedostaje argument 'errorMsg'.";

    $: console.log(errorMsg);
</script>

<div id="greska" class="alert alert-danger position-fixed" style="z-index: 1" role="alert" >
    {errorMsg}
</div>