package com.api.votacao.resources;

import com.api.votacao.dtos.CandidatoRequest;
import com.api.votacao.dtos.CandidatoResponse;
import com.api.votacao.entities.Candidato;
import com.api.votacao.services.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/candidato")
public class CandidatoResource {

    @Autowired
    private CandidatoService candidatoService;

    @PostMapping
    public ResponseEntity<CandidatoResponse> create (@RequestBody Candidato request) {
        CandidatoResponse response = candidatoService.create(request);

        return ResponseEntity.ok(response);
    }
}
