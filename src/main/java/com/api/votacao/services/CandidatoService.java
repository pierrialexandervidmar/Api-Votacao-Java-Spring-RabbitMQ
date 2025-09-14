package com.api.votacao.services;

import com.api.votacao.dtos.CandidatoResponse;
import com.api.votacao.entities.Candidato;
import com.api.votacao.repositories.CandidatoRepository;
import com.api.votacao.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelas operações de negócio relacionadas à entidade {@link Candidato}.
 * <p>
 * Oferece métodos para buscar candidatos com paginação, consultar por ID e criar novos registros.
 * A comunicação com a camada de persistência é realizada através do {@link CandidatoRepository}.
 * </p>
 *
 * <p><b>Principais responsabilidades:</b></p>
 * <ul>
 *     <li>Listar candidatos de forma paginada.</li>
 *     <li>Buscar um candidato específico pelo identificador.</li>
 *     <li>Criar e persistir novos candidatos.</li>
 * </ul>
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
@Service
public class CandidatoService {

    /**
     * Repositório responsável pelo acesso à tabela de candidatos.
     */
    @Autowired
    private CandidatoRepository repository;

    /**
     * Retorna uma lista paginada de candidatos.
     *
     * @param pageable objeto {@link Pageable} contendo informações de paginação e ordenação
     * @return uma página de {@link CandidatoResponse} contendo os candidatos encontrados
     */
    public Page<CandidatoResponse> findAllPaged(Pageable pageable) {
        Page<Candidato> list = repository.findAll(pageable);
        return list.map(CandidatoResponse::new);
    }

    /**
     * Busca um candidato existente pelo seu identificador.
     *
     * @param id identificador único do candidato
     * @return um {@link CandidatoResponse} contendo os dados do candidato encontrado
     * @throws ResourceNotFoundException se não existir candidato com o ID informado
     */
    public CandidatoResponse findById(Long id) {
        Candidato entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entidade do canditado não encontrada"));

        return new CandidatoResponse(entity);
    }

    /**
     * Cria e persiste um novo candidato no banco de dados.
     *
     * @param entity entidade {@link Candidato} a ser criada
     * @return um {@link CandidatoResponse} contendo os dados do candidato recém-criado
     */
    public CandidatoResponse create(Candidato entity) {
        CandidatoResponse candidatoResponse = new CandidatoResponse(repository.save(entity));
        return candidatoResponse;
    }
}
