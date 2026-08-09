package br.com.restauranthub.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 120)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false,length = 50)
    private String categoria;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Produto() {
    }

    @PrePersist
    public void prePersist(){

        LocalDateTime agora = LocalDateTime.now();

        createdAt = agora;
        updatedAt = agora;

        if(ativo == null){
            ativo = true;
        }

    }

    @PreUpdate
    public void preUpdate(){

        updatedAt = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}