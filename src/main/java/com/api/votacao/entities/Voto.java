package com.api.votacao.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa um voto realizado no sistema de votação.
 * <p>
 * A entidade {@code Voto} contém um identificador único, uma referência ao
 * candidato votado e a data/hora em que o voto foi registrado.
 * </p>
 *
 * Cada voto está associado a exatamente um {@link Candidato} (relacionamento {@code ManyToOne}).
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
@Entity
public class Voto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do voto.
     * Gerado automaticamente pela estratégia {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Candidato associado a este voto.
     * Relacionamento do tipo {@code ManyToOne}.
     */
    @ManyToOne
    private Candidato candidato;

    /**
     * Data e hora em que o voto foi registrado.
     */
    private LocalDateTime dataHora;

    /**
     * Construtor padrão da entidade {@code Voto}.
     */
    public Voto() {
    }

    /**
     * Construtor que inicializa a entidade {@code Voto} apenas com o ID.
     *
     * @param id identificador do voto
     */
    public Voto(Long id) {
        this.id = id;
    }

    /**
     * Construtor que inicializa a entidade {@code Voto} com todos os atributos.
     *
     * @param id        identificador do voto
     * @param candidato candidato associado ao voto
     * @param dataHora  data e hora do registro do voto
     */
    public Voto(Long id, Candidato candidato, LocalDateTime dataHora) {
        this.id = id;
        this.candidato = candidato;
        this.dataHora = dataHora;
    }

    /**
     * Obtém o identificador único do voto.
     *
     * @return o ID do voto
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador único do voto.
     *
     * @param id o novo ID do voto
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o candidato associado a este voto.
     *
     * @return candidato votado
     */
    public Candidato getCandidato() {
        return candidato;
    }

    /**
     * Define o candidato associado a este voto.
     *
     * @param candidato novo candidato associado
     */
    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    /**
     * Obtém a data e hora em que o voto foi registrado.
     *
     * @return data e hora do voto
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Define a data e hora em que o voto foi registrado.
     *
     * @param dataHora nova data e hora do voto
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Compara este voto com outro objeto, utilizando apenas o {@code id}.
     *
     * @param o objeto a ser comparado
     * @return {@code true} se os IDs forem iguais, caso contrário {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return Objects.equals(id, voto.id);
    }

    /**
     * Calcula o código hash do voto com base no {@code id}.
     *
     * @return código hash do voto
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
