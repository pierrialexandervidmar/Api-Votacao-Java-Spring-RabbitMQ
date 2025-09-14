package com.api.votacao.services;

import com.api.votacao.dtos.VotoRequest;
import com.api.votacao.dtos.VotoResponse;
import com.api.votacao.entities.Candidato;
import com.api.votacao.entities.Voto;
import com.api.votacao.repositories.CandidatoRepository;
import com.api.votacao.repositories.VotoRepository;
import com.api.votacao.services.exceptions.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas operações relacionadas à entidade {@link Voto}.
 * <p>
 * Esta classe orquestra a validação de candidatos, o envio de eventos para o RabbitMQ
 * e a persistência de votos no banco de dados. Faz uso do {@link VotoRepository} e
 * do {@link CandidatoRepository} para interação com a camada de persistência.
 * </p>
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
@Service
public class VotoService {

    /**
     * Componente do Spring AMQP responsável por enviar mensagens para o RabbitMQ.
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Repositório responsável pelo acesso e persistência da entidade {@link Voto}.
     */
    @Autowired
    private VotoRepository repository;

    /**
     * Repositório responsável pelo acesso e persistência da entidade {@link Candidato}.
     */
    @Autowired
    private CandidatoRepository repositoryCandidato;

    /**
     * Processa um voto recebido via API.
     * <p>
     * Este método:
     * <ol>
     *     <li>Valida se o candidato informado existe.</li>
     *     <li>Publica o voto no RabbitMQ para processamento assíncrono.</li>
     *     <li>Retorna uma resposta {@link VotoResponse} para o cliente.</li>
     * </ol>
     * </p>
     *
     * @param request objeto {@link VotoRequest} contendo os dados do voto
     * @return um {@link VotoResponse} representando o resultado do processamento
     * @throws ResourceNotFoundException se o candidato informado não existir
     */
    public VotoResponse processaVoto(VotoRequest request) {
        // busca se o candidato existe
        Candidato candidato = repositoryCandidato.findById(request.getIdCandidato())
                .orElseThrow(() -> new ResourceNotFoundException("Entidade do candidato não encontrada"));

        // faz o processamento do voto para o rabbit
        rabbitTemplate.convertAndSend("computar-voto.ex", "", request);

        // responde no formato esperado
        return new VotoResponse(candidato);
    }

    /**
     * Persiste um voto diretamente no banco de dados.
     * <p>
     * Este método:
     * <ol>
     *     <li>Valida se o candidato informado existe.</li>
     *     <li>Cria uma instância de {@link Voto} associada ao candidato.</li>
     *     <li>Salva a entidade {@link Voto} no repositório.</li>
     * </ol>
     * </p>
     *
     * @param request objeto {@link VotoRequest} contendo os dados do voto
     * @throws ResourceNotFoundException se o candidato informado não existir
     */
    public void save(VotoRequest request) {
        Candidato candidato = repositoryCandidato.findById(request.getIdCandidato())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));

        Voto voto = new Voto();
        voto.setCandidato(candidato);
        repository.save(voto);
    }
}
