<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<script lang="ts">
	import { page } from '$app/stores';
	import {onMount} from 'svelte';
	import type { PageData } from './$types';
	import {serverUrl, pozoviRestServis} from '../../commons.js';

	export let data: PageData;

	let id: string | null;
	let rsData: Promise<any>;
	let ssData={};

	onMount(() => {
		console.log("2a: ", data);

		id=$page.url.searchParams.get('id');
		console.log("2: id: ", id);

		var projektUrl_=`${serverUrl}projektDatalji/${id}`;
		
		rsData=pozoviRestServis(projektUrl_, projekDetaljiRest_);

        return rsData;
	});

	function projekDetaljiRest_(data: string) { 
        ssData=data;
    }
</script>

<div class="text-column">
	<h1>About this app</h1>
	<h2>{id}</h2>

	<h3>{JSON.stringify(ssData)}</h3>

	<p>
		This is a <a href="https://kit.svelte.dev">SvelteKit</a> app. You can make your own by typing the
		following into your command line and following the prompts:
	</p>

	<pre>npm create svelte@latest</pre>

	<p>
		The page you're looking at is purely static HTML, with no client-side interactivity needed.
		Because of that, we don't need to load any JavaScript. Try viewing the page's source, or opening
		the devtools network panel and reloading.
	</p>

	<p>
		
	</p>
</div>
