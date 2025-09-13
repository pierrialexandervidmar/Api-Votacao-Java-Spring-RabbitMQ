package com.api.votacao.resources;

import com.api.votacao.dtos.VotoRequest;
import com.api.votacao.dtos.VotoResponse;
import com.api.votacao.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/voto")
public class VotoResource {

    @Autowired
    private VotoService votoService;

    @PostMapping
    public ResponseEntity<VotoResponse> processaVoto(@RequestBody VotoRequest request) {
        VotoResponse response = votoService.processaVoto(request);

        return ResponseEntity.ok().body(response);
    }
}
