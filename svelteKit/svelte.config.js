import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/kit/vite';

//const dev = process.argv.includes('dev');

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://kit.svelte.dev/docs/integrations#preprocessors
	// for more information about preprocessors
	preprocess: vitePreprocess(),

	kit: {
		adapter: adapter({
			pages: 'build/svelteKit',
      		assets: 'build/svelteKit',
      		fallback: null,
      		precompress: false,
      		strict: true,
			paths: {
				base: '/build',
			}
		}),
		alias: {
			'@common': './src/common',
			'@common/*': './src/common/*'
		}
	}
};

export default config;
