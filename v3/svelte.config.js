import adapter from '@sveltejs/adapter-static' /* @sveltejs/adapter-auto' */;
import preprocess from 'svelte-preprocess';
const dev = process.argv.includes('dev');

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://github.com/sveltejs/svelte-preprocess
	// for more information about preprocessors
	preprocess: preprocess(),

	kit: {
		paths: {assets: "", base: dev ? '' : "/v3"},
		adapter: adapter(),
	}
};

export default config;
