<script lang="ts">
  import BartToast from "./BartToast.svelte";

   let {errorMsg} = $props();  // durationMS

   console.log(errorMsg);
</script>

<BartToast msg={errorMsg} alertMsg="alert-error" />
    

