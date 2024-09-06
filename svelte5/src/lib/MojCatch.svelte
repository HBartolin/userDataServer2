<script lang="ts">
  import BartToast from "./BartToast.svelte";
//    import { onMount } from 'svelte';
 //   import { SvelteToast, toast } from '@zerodevx/svelte-toast'

    export let errorMsg: string = "Nedostaje argument 'errorMsg'.";
    export let durationMS: number = 100000;

    $: console.log(errorMsg);

//    onMount(() => {
   //     toast.pop(0);
   //     toast.push(errorMsg.toString());
 //   });

   // const options = {
     //   'duration': durationMS,
  //  };
    // <SvelteToast {options} />  class="wrap-red"
</script>

<BartToast msg={errorMsg} alertMsg="alert-error" />
    

