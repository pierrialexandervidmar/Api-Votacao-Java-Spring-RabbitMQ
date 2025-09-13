package com.api.votacao.dtos;

import com.api.votacao.entities.Candidato;
import com.api.votacao.entities.Voto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class VotoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCandidato;
    private String nomeCandidato;
    private Instant dataHora;  // quando o voto foi processado
    private String mensagem;   // ex.: "Voto computado com sucesso"

    public VotoResponse(Optional<Voto> byId) {
    }

    public VotoResponse(Long idCandidato, String nomeCandidato, Instant dataHora, String mensagem) {
        this.idCandidato = idCandidato;
        this.nomeCandidato = nomeCandidato;
        this.dataHora = dataHora;
        this.mensagem = mensagem;
    }

    public VotoResponse(Candidato entity) {
        this.idCandidato = entity.getId();
        this.nomeCandidato = entity.getNome();
        this.dataHora = Instant.now();
        this.mensagem = "Voto computado com sucesso";
    }

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VotoResponse that)) return false;
        return Objects.equals(idCandidato, that.idCandidato) &&
                Objects.equals(nomeCandidato, that.nomeCandidato) &&
                Objects.equals(dataHora, that.dataHora) &&
                Objects.equals(mensagem, that.mensagem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCandidato, nomeCandidato, dataHora, mensagem);
    }
}
