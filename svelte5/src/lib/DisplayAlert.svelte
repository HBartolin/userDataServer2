<script lang="ts">
    import { onMount } from 'svelte';
    import { SvelteToast, toast } from '@zerodevx/svelte-toast'

    export let msg: string = "Nedostaje poruka.";

    onMount(() => {
       toast.pop(0);
       toast.push(msg);
    })

    const options = {
    };
</script>
	
<div class="wrap-green">
	<SvelteToast {options} />
</div>
