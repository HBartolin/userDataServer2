<script lang="ts">
    import { onMount } from 'svelte';
   // import { SvelteToast, toast } from '@zerodevx/svelte-toast'

    export let msg: string = "Nedostaje poruka.";

    onMount(() => {
   //    toast.pop(0);
   //    toast.push(msg);
    })

    const options = {
    };

    // <SvelteToast {options} />
</script>
	
<div class="wrap-green">
	<div class="toast toast-top toast-end">
    <div class="alert alert-success">
      <span>{msg}</span>
    </div>
  </div>
</div>
