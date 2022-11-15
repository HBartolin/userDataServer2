<script>
  import svelteLogo from './assets/svelte.svg'
  import Counter from './lib/Counter.svelte'
  import { Button, Col, Row } from 'sveltestrap'
  import Index from './routes/Index.svelte'
  import NotFound from './routes/NotFound.svelte'
  import ProjektDetalji from './routes/ProjektDetalji.svelte'
  import Router, {link} from 'svelte-spa-router' // use:link
  

  const routes = {
    // Exact path
    '/': Index,
    '/projektDetalji/:id': ProjektDetalji,

    // Using named parameters, with last being optional
    // '/author/:first/:last?': Author,

    // Wildcard parameter
    // '/book/*': Book,

    // Catch-all
    // This is optional, but if present it must be the last
    '*': NotFound,
}
</script>

<Router {routes}/>
