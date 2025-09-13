package com.api.votacao.dtos;

import com.api.votacao.entities.Candidato;

import java.io.Serializable;
import java.util.Objects;

public class CandidatoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private Integer totalVotos;

    public CandidatoResponse() {
    }

    public CandidatoResponse(Long id, String nome, Integer totalVotos) {
        this.id = id;
        this.nome = nome;
        this.totalVotos = totalVotos;
    }

    // Construtor de conveniência a partir da entidade
    public CandidatoResponse(Candidato entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.totalVotos = (entity.getVotos() != null) ? entity.getVotos().size() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Integer totalVotos) {
        this.totalVotos = totalVotos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidatoResponse that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(totalVotos, that.totalVotos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, totalVotos);
    }
}
