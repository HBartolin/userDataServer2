export const serverUrl = `http://localhost:8090/api/`;

export const pozoviRestServis: Function = async (
  mojUrl: string,
  ucitaj: any,
) => {
  console.log(mojUrl);

  return fetch(mojUrl)
    .then((response) => response.json())
    .then(ucitaj);
};

export interface INTP {
  id: number;
  claim: string;
  contract: string;
  status: string;
}

export interface dataInterface {
  ok: string;
  rezultat: Array<INTP>;
  rezultatPage: {};
  greska: string[];
}
