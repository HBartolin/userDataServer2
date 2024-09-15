<script lang="ts">
  let { msg, alertMsg } = $props();
</script>

<div class="toast toast-center toast-top z-50">
  <div class="alert {alertMsg}">
    <span>{msg}</span>
  </div>
</div>
