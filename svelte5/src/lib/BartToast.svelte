<script lang="ts">
     export let msg: string = "Nedostaje poruka.";
     export let alertMsg: string;
</script>

<div class="toast toast-center toast-middle">
    <div class="alert {alertMsg}">
      <span>{msg}</span>
    </div>
</div>