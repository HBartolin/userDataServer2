<script lang="ts">
  let { msg, alertMsg } = $props();

  let showModal = $state(true);
</script>

{#if showModal}
  <div class="toast toast-center toast-top z-50 mt-24">
    <div class="alert {alertMsg} ">
      <span>{msg}</span>

      <button type="button" onclick={() => (showModal = false)}>
        <svg
          class="w-3 h-3"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 14 14">
          <path
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
        </svg>
      </button>
    </div>
  </div>
{/if}

<style>
</style>
