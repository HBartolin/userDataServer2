<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<script lang="ts">
	import { page } from '$app/stores';
	import {onMount} from 'svelte';

	let dd: string;

	onMount(() => {
		console.log("1: ", $page.url.origin);

		dd=`${$page.url.origin}/about2?id=11`;
	});
</script>

<div class="text-column">
	<h1>About this app</h1>

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
