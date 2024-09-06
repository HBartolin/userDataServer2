<script lang="ts">
    import BartToast from "./BartToast.svelte";
    //import { onMount } from 'svelte';
   // import { SvelteToast, toast } from '@zerodevx/svelte-toast'

    export let msg: string = "Nedostaje poruka.";

  //  onMount(() => {
   //    toast.pop(0);
   //    toast.push(msg);
  //  })

    //const options = {
    //};

    // <SvelteToast {options} />
</script>
	
<BartToast {msg} alertMsg="alert-success" />
