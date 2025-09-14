package com.api.votacao.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Representa um candidato que pode receber votos no sistema de votação.
 * <p>
 * A entidade {@code Candidato} possui um identificador único, nome e a lista de votos associados.
 * Está mapeada para uma tabela no banco de dados através de JPA.
 * </p>
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
@Entity
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do candidato.
     * Gerado automaticamente pela estratégia {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do candidato.
     */
    private String nome;

    /**
     * Lista de votos associados a este candidato.
     * Relacionamento {@code OneToMany} mapeado pelo atributo {@code candidato} na entidade {@link Voto}.
     */
    @OneToMany(mappedBy = "candidato")
    private List<Voto> votos;

    /**
     * Construtor padrão da entidade {@code Candidato}.
     */
    public Candidato() {
    }

    /**
     * Construtor que inicializa a entidade {@code Candidato} apenas com o ID.
     *
     * @param id identificador do candidato
     */
    public Candidato(Long id) {
        this.id = id;
    }

    /**
     * Obtém o identificador único do candidato.
     *
     * @return o ID do candidato
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador único do candidato.
     *
     * @param id o novo ID do candidato
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome do candidato.
     *
     * @return o nome do candidato
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do candidato.
     *
     * @param nome o novo nome do candidato
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a lista de votos associados a este candidato.
     *
     * @return lista de votos do candidato
     */
    public List<Voto> getVotos() {
        return votos;
    }

    /**
     * Define a lista de votos associados a este candidato.
     *
     * @param votos nova lista de votos
     */
    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    /**
     * Compara este candidato com outro objeto, utilizando apenas o {@code id}.
     *
     * @param o objeto a ser comparado
     * @return {@code true} se os IDs forem iguais, caso contrário {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return Objects.equals(id, candidato.id);
    }

    /**
     * Calcula o código hash do candidato com base no {@code id}.
     *
     * @return código hash do candidato
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
