<script lang="ts">
     let {msg, alertMsg}=$props();
</script>

<div class="toast toast-center top-40">
    <div class="alert {alertMsg}">
      <span>{msg}</span>
    </div>
</div>