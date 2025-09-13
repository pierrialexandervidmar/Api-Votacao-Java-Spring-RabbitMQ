package com.api.votacao.dtos;

import com.api.votacao.entities.Candidato;

import java.io.Serializable;
import java.util.Objects;

public class VotoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCandidato;

    public VotoRequest() {
    }

    public VotoRequest(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public VotoRequest(Candidato entity) {
        this.idCandidato = entity.getId();
    }

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VotoRequest that = (VotoRequest) o;
        return Objects.equals(idCandidato, that.idCandidato);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCandidato);
    }
}
