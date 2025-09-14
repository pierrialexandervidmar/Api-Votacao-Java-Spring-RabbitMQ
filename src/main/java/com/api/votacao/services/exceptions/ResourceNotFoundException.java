package com.api.votacao.services.exceptions;

/**
 * Exceção personalizada lançada quando um recurso não é encontrado no sistema.
 * <p>
 * Esta exceção estende {@link RuntimeException} e é utilizada em cenários onde
 * operações de busca em repositórios ou serviços não retornam o recurso esperado.
 * </p>
 * <p>
 * Exemplos de uso:
 * <ul>
 *     <li>Quando um {@code Candidato} não existe para o ID informado.</li>
 *     <li>Quando um {@code Voto} não é encontrado no banco de dados.</li>
 * </ul>
 * </p>
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói uma nova {@code ResourceNotFoundException} com a mensagem detalhando a causa.
     *
     * @param msg mensagem explicando o motivo da exceção,
     *            geralmente indicando qual recurso não foi encontrado
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
