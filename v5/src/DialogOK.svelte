<svelte:options accessors/>

<script>
    import { Button, Modal, ModalBody, ModalFooter, ModalHeader  } from 'sveltestrap';

    let open = false;
    export let dialogOkToggle = () => (open = !open);
    export let prikaziMsg="";
</script>


    <Modal isOpen={open} toggle={dialogOkToggle}>
        <ModalHeader toggle={dialogOkToggle}>Poruka</ModalHeader>
        <ModalBody>
            {prikaziMsg}
        </ModalBody>
        <ModalFooter>
            <Button color="primary" on:click={dialogOkToggle}>OK</Button>
        </ModalFooter>
    </Modal>
