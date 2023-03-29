<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<script lang="ts">
	import {onMount} from 'svelte';
	import { page } from '$app/stores';
	import Ducan from '../../store.js';
	import { base } from "$app/paths";
	import {serverUrl, pozoviRestServis} from '../../commons.js';

	Ducan.set(11);

	let dd: string;

	onMount(() => {
		console.log("1: ", $page.url.origin);

		dd=`${$page.url.origin}${base}/about2?id=1`;
	});

	function inicijalnoNapuni() {
		var projektUrl=`${serverUrl}createDB`;
	
		pozoviRestServis(projektUrl, createDB_);
	}

	function createDB_(data: any) {	
		//postaviOK(JSON.stringify(data));
		
		//projektiTC();
	}						
</script>

<div>
	<h1>About this app</h1>

	<p>
		<button on:click={inicijalnoNapuni} class="button button2">Inicijalno napuni</button>
	</p>
	
	<a href="{dd}" >##############</a>

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

<style>
	.button {
		background-color: #4CAF50; /* Green */
		border: none;
		color: white;
		padding: 15px 32px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
	}

	.button2 {background-color: #008CBA;}
</style>