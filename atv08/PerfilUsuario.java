package com.mycompany.atv08;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author curso
 */
import java.util.ArrayList;
import java.util.List;

public class PerfilUsuario {
    private Long id;
    private String descricao;
    private List<String> permissoes;

    // Construtor padrão
    public PerfilUsuario() {
        this.permissoes = new ArrayList<>();
    }

    // Construtor completo
    public PerfilUsuario(Long id, String descricao, List<String> permissoes) {
        this.id = id;
        this.descricao = descricao;
        this.permissoes = permissoes != null ? permissoes : new ArrayList<>();
    }

    // Construtor sem permissões
    public PerfilUsuario(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.permissoes = new ArrayList<>();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<String> getPermissoes() { return permissoes; }
    public void setPermissoes(List<String> permissoes) { this.permissoes = permissoes; }

    // Método auxiliar para adicionar permissão
    public void adicionarPermissao(String permissao) {
        this.permissoes.add(permissao);
    }

    @Override
    public String toString() {
        return "PerfilUsuario{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", permissoes=" + permissoes +
                '}';
    }
}