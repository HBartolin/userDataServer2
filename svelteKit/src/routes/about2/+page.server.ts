//import { dev } from '$app/environment';
//import type { PageServerLoad } from './$types';
import Ducan from '../../store.js';
import {serverUrl, pozoviRestServis} from '../../commons.js';

var lValue: any;

export const prerender = true;

export const load = async () => {
    Ducan.subscribe(async value => {
    var id=1;

    //var uUrl="http://localhost:8090/api/projektDatalji/1";
    var projektUrl_=`${serverUrl}projektDatalji/${id}`;
		
		pozoviRestServis(projektUrl_, projekDetaljiRest_);
  });

  return await {lValue};
}

function projekDetaljiRest_(data: string) { 
  lValue=data;
}

/*export const load = (async ({ fetch, url}) => {
    //console.log( url.search.substring(3));

    //let q = url.searchParams.get('id');
    //const res = await fetch(`/api/items/${params.id}`);
    const item = {aas: "fgfgf"} ;
   
    return { item };
  }) satisfies PageServerLoad;
*/


