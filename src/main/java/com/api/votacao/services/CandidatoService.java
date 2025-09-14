package com.api.votacao.services;

import com.api.votacao.dtos.CandidatoRequest;
import com.api.votacao.dtos.CandidatoResponse;
import com.api.votacao.entities.Candidato;
import com.api.votacao.repositories.CandidatoRepository;
import com.api.votacao.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    public CandidatoResponse findById(Long id) {
        Candidato entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entidade do canditado não encontrada"));

        return new CandidatoResponse(entity);
    }

    public CandidatoResponse create(Candidato entity) {
        CandidatoResponse candidatoResponse = new CandidatoResponse(repository.save(entity));
        return candidatoResponse;
    }
}
