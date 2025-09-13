package com.api.votacao.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe uma mensagem detalhando a causa da exceção.
     *
     * @param msg A mensagem explicando o motivo da exceção, geralmente indicando qual recurso não foi encontrado.
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
