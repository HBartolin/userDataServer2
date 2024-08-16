export const serverUrl=`http://localhost:8090/api/`;

    export function pozoviRestServis(mojUrl: string, ucitaj: any) {
	    console.log(mojUrl);
	
        fetch(mojUrl)
            .then((response) => response.json())
            .then(ucitaj);
    }
