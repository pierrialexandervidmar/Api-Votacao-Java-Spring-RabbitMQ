package com.api.votacao.resources;

import com.api.votacao.dtos.CandidatoResponse;
import com.api.votacao.entities.Candidato;
import com.api.votacao.services.CandidatoService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

/**
 * Recurso REST responsável por expor endpoints para gerenciamento de {@link Candidato}.
 * <p>
 * Endpoints:
 * <ul>
 *     <li><b>POST /api/candidato</b> &mdash; cria um novo candidato.</li>
 *     <li><b>GET  /api/candidato</b> &mdash; lista candidatos de forma paginada.</li>
 * </ul>
 * </p>
 *
 * <p><b>Observações de paginação:</b> os parâmetros padrão suportados por {@link Pageable}
 * são {@code page}, {@code size} e {@code sort}. Exemplo:
 * <pre>
 * GET /api/candidato?page=0&size=20&sort=nome,asc
 * </pre>
 * </p>
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
@RestController
@RequestMapping(value = "/api/candidato")
public class CandidatoResource {

    /**
     * Serviço de domínio para operações com {@link Candidato}.
     */
    @Autowired
    private CandidatoService candidatoService;

    /**
     * Cria um novo candidato.
     *
     * <p><b>Exemplo de requisição:</b></p>
     * <pre>
     * POST /api/candidato
     * Content-Type: application/json
     *
     * {
     *   "nome": "Candidato A"
     * }
     * </pre>
     *
     * @param request entidade {@link Candidato} contendo os dados para criação
     * @return {@link ResponseEntity} com {@link CandidatoResponse} do candidato criado
     */
    @PostMapping
    public ResponseEntity<CandidatoResponse> create(@RequestBody Candidato request) {
        CandidatoResponse response = candidatoService.create(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retorna uma lista paginada de candidatos.
     *
     * <p><b>Exemplos:</b></p>
     * <pre>
     * GET /api/candidato
     * GET /api/candidato?page=0&size=10
     * GET /api/candidato?sort=nome,asc
     * </pre>
     *
     * @param pageable objeto {@link Pageable} com informações de página, tamanho e ordenação
     * @return {@link ResponseEntity} contendo {@link Page} de {@link CandidatoResponse}
     */
    @GetMapping
    public ResponseEntity<Page<CandidatoResponse>> findAll(Pageable pageable) {
        Page<CandidatoResponse> list = candidatoService.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }
}
