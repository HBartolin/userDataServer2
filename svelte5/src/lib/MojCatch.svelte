<script lang="ts">
  import BartToast from "./BartToast.svelte";

   let {errorMsg, durationMS} = $props();

   console.log(errorMsg);
</script>

<BartToast msg={errorMsg} alertMsg="alert-error" />
    

