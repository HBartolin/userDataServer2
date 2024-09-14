<script>
  import "../app.css";
</script>

<slot />

<style>
  :root {
    --MojToastContainerTop: 5rem;
    --MojToastContainerRight: auto;
    --mojToastContainerBottom: auto;
    --mojToastContainerLeft: calc(50vw - 8rem);
    --mojToastColor: mintcream;
  }

  :global(.wrap-red) {
    --toastContainerTop: var(--MojToastContainerTop);
    --toastContainerRight: var(--MojToastContainerRight);
    --toastContainerBottom: var(--mojToastContainerBottom);
    --toastContainerLeft: var(--mojToastContainerLeft);
    --toastColor: var(--mojToastColor);
    --toastBackground: rgba(255, 0, 0, 0.9);
    --toastBarBackground: rgba(255, 113, 100, 1);
  }

  :global(.wrap-green) {
    --toastContainerTop: var(--MojToastContainerTop);
    --toastContainerRight: var(--MojToastContainerRight);
    --toastContainerBottom: var(--mojToastContainerBottom);
    --toastContainerLeft: var(--mojToastContainerLeft);
    --toastColor: var(--mojToastColor);
    --toastBackground: rgba(72, 187, 120, 0.9);
    --toastBarBackground: #2f855a;
  }
</style>
