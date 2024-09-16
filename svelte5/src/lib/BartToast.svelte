<script lang="ts">
  let { msg, alertMsg } = $props();

  let showModal = $state(true);
</script>

{#if showModal}
  <div class="toast toast-center toast-top z-50">
    <div class="alert {alertMsg}">
      {msg}

      <button type="button" onclick={() => (showModal = false)}>
        X
      </button>
    </div>
  </div>
{/if}

<style>
  .nnoowhite-space {white-space: pre-line;}
</style>
