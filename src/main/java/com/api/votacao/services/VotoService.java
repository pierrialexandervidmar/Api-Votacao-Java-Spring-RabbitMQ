package com.api.votacao.services;

import com.api.votacao.dtos.VotoRequest;
import com.api.votacao.dtos.VotoResponse;
import com.api.votacao.entities.Candidato;
import com.api.votacao.repositories.CandidatoRepository;
import com.api.votacao.repositories.VotoRepository;
import com.api.votacao.services.exceptions.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private VotoRepository repository;

    private CandidatoRepository repositoryCandidato;

    public VotoResponse processaVoto(VotoRequest request) {

        // busca se o candidato existe
        Candidato candidato = repositoryCandidato.findById(request.getIdCandidato())
                .orElseThrow(() -> new ResourceNotFoundException("Entidade do candidato não encontrada"));

        // faz o processamento do voto para o rabbit
        rabbitTemplate.convertAndSend("computar-voto.ex", "", request);

        // responde no formato esperado
        return new VotoResponse (candidato);
    }
}
