<script>
	import search16 from '$lib/img/search16.png';
</script>


<div class="container">
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item active" aria-current="page">Projekti</li>
            </ol>
          </nav>
          <div class="btn-group" role="group">
            <button id="btnGroupDrop1" type="button" class="btn btn-primary mb-3 ml-3 dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Uredi
            </button>
            <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
              <a class="dropdown-item" _onclick="urediOsobuOtvori()">Uredi osobu</a>
              <a class="dropdown-item" _onclick="urediPodugovaraca()">Uredi podugovarača</a>
            </div>
          </div>
          <button class="btn btn-primary mb-3 ml-3" type="submit" data-toggle="modal" data-target="#noviProjekt" _onclick="hideGreska()">Novi projekt</button>
          <button class="btn btn-primary mb-3 ml-3" type="submit" _onclick="inicijalnoNapuni()">Inicijalno napuni</button>
        </div>
        <div id="pretrazi" class="pretrazi">
            <img src={search16} _onclick="pretrazi()" >
        </div>
        <div id="trazi" class="form-inline my-2 my-lg-0" style="display: none">
          <input type="text" id="projektTrazi" _onKeyUp="onProjektTrazi()" class="form-control mr-sm-2" autofocus>
          <button _onclick="projektTrazi()" class="btn btn-outline-success my-2 my-sm-0" type="submit">Zatvori</button>
        </div>
      </nav>

      <div name="greska" style="display: none"></div>
   
      <table class="table table-sm table-striped">
        <thead>
            <tr>
                <th class="text-right">Id</th>
                <th>Claim</th>
                <th>Contract</th>
                <th>Status</th>
                <th>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="aktivni" value="option1" _onchange="projekti()" checked>
                    <label class="form-check-label" for="aktivni">Aktivni</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="neaktivni" value="option2" _onchange="projekti()">
                    <label class="form-check-label" for="neaktivni">Neaktivni</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="svi" value="option3" _onchange="projekti()">
                    <label class="form-check-label" for="svi">Svi</label>
                  </div>
                </th>
            </tr>
        </thead>
        <tbody id="projektTablicaBody"></tbody>
      </table>
      <div class="btn-group" role="group">
        <button type="button" id="projektTablicaPrethodno" _onclick="projektTablicaPrethodno()" class="btn btn-outline-secondary">&laquo;<div id="pTablicaPrethodno" style="display: none;"></div></button>
        <button type="button" id="projektTablicaSlijedece" _onclick="projektTablicaSlijedece()" class="btn btn-outline-secondary">&raquo;<div id="pTablicaSlijedece" style="display: none;"></div></button>
      </div>
    </div>

    <div class="modal fade" id="noviProjekt" tabindex="-1" role="dialog" aria-labelledby="noviProjektLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="noviProjektLabel">Novi projekt</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div name="greska" style="display: none"></div>
          <div class="modal-body">
            <div class="form-group row">
              <label for="inputClaim" class="col-sm-2 col-form-label">Claim:</label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="inputClaim" autofocus>
              </div>
            </div>
            <div class="form-group row">
              <label for="inputContract" class="col-sm-2 col-form-label">Contract:</label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="inputContract">
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" _onclick="noviProjekt()">Novi projekt</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Odustani</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="okModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Poruka</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div id="okModalMsg" class="modal-body">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Zatvori</button>
          </div>
        </div>
      </div>
    </div>

    <div id="gumbiZaRedak" style="display: none;">
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent_redakId}" aria-controls="navbarSupportedContent_redakId}" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent_redakId}">
          <div class="btn-group" role="group">
            <button type="button" class="btn btn-outline-primary" _onclick="prikaziProjekt(redakId})">Prikaži</button>
            <button type="button" class="btn btn-outline-primary" _onclick="uExel(redakId}, 'redakClaim}', 'redakContract}')">U Excel</button>
            <button type="button" class="btn btn-outline-danger" _onclick="zatvoriProjekt(redakId}, redakTs})">Zatvori</button>
            <button type="button" class="btn btn-outline-primary" _onclick="otvoriProjekt(redakId}, redakTs})">Otvori</button>
          </div>
        </div>
      </nav>
    </div>

