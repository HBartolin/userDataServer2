<script lang="ts">
  let { msg, alertMsg } = $props();

  let showModal = $state(true);
</script>

{#if showModal}
  <div class="toast toast-center toast-top z-50">
    <div class="alert {alertMsg}">
      <div class="">
        <div class="card place-items-center">{msg}</div>
        <div class="card place-items-center">
          <!-- svelte-ignore a11y_consider_explicit_label -->
          <button type="button" onclick={() => (showModal = false)} class="pt-3">
            <svg
              class="w-4 h-4 "
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
    </div>
  </div>
{/if}

<style>
</style>
