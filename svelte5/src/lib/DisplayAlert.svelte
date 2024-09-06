<script lang="ts">
    import BartToast from "./BartToast.svelte";

    let {msg} = $props();
</script>
	
<BartToast {msg} alertMsg="alert-success" />
