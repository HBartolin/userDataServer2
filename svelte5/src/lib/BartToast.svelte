<script lang="ts">
  let { msg, alertMsg } = $props();
</script>

<div class="toast toast-start toast-top">
  <div class="alert {alertMsg}">
    <span>{msg}</span>
  </div>
</div>
