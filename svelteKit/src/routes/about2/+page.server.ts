//import { dev } from '$app/environment';
//import type { PageServerLoad } from './$types';
import Ducan from '../../store.js';

var lValue: number;

export const prerender = true;

export const load = async () => {
   Ducan.subscribe(value => {
    console.log("dddd: " + 11);
    lValue=11;
  });

  return await {lValue};
}

/*export const load = (async ({ fetch, url}) => {
    //console.log( url.search.substring(3));

    //let q = url.searchParams.get('id');
    //const res = await fetch(`/api/items/${params.id}`);
    const item = {aas: "fgfgf"} ;
   
    return { item };
  }) satisfies PageServerLoad;
*/


