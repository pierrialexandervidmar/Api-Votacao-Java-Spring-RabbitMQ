package com.api.votacao.dtos;

import java.io.Serializable;
import java.util.Objects;

public class CandidatoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CandidatoRequest that = (CandidatoRequest) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
