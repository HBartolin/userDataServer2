<script lang="ts">
    import { onMount } from 'svelte';
    import { SvelteToast, toast } from '@zerodevx/svelte-toast'

    export let errorMsg: string = "Nedostaje argument 'errorMsg'.";
    export let durationMS: number = 100000;

    $: console.log(errorMsg);

    onMount(() => {
        toast.pop(0);
        toast.push(errorMsg.toString());
    });

    const options = {
        'duration': durationMS,
    };
</script>

<div class="wrap-red">
	<SvelteToast {options} />
</div>
