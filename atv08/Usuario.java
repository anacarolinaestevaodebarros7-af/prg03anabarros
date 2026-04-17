/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atv08;

/**
 *
 * @author curso
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private Long id;
    private PerfilUsuario perfil;
    private String nomeUsuario;
    private String email;
    private String senha;
    private LocalDateTime ultimoLogin;
    private Boolean ativo;

    // Lista interna para manter logs e sessões gerados pelo próprio usuário
    private List<LogAuditoria> logs = new ArrayList<>();
    private List<Sessao> sessoes = new ArrayList<>();

    // Contador estático para IDs de sessão e log
    private static long contadorSessao = 1;
    private static long contadorLog = 1;

    // Construtor padrão
    public Usuario() {
        this.ativo = true;
    }

    // Construtor completo
    public Usuario(Long id, PerfilUsuario perfil, String nomeUsuario, String email, String senha, Boolean ativo) {
        this.id = id;
        this.perfil = perfil;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.logs = new ArrayList<>();
        this.sessoes = new ArrayList<>();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PerfilUsuario getPerfil() { return perfil; }
    public void setPerfil(PerfilUsuario perfil) { this.perfil = perfil; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public List<LogAuditoria> getLogs() { return logs; }
    public List<Sessao> getSessoes() { return sessoes; }

    // -------------------------
    // Método opcional: logar()
    // -------------------------
    public boolean logar(String senhaInformada) {
        if (!this.ativo) {
            System.out.println("[AVISO] Usuário '" + nomeUsuario + "' está inativo.");
            LogAuditoria log = new LogAuditoria(contadorLog++, this, "tentativa de login - usuário inativo", "127.0.0.1");
            logs.add(log);
            return false;
        }

        if (this.senha.equals(senhaInformada)) {
            this.ultimoLogin = LocalDateTime.now();
            LogAuditoria log = new LogAuditoria(contadorLog++, this, "login realizado com sucesso", "127.0.0.1");
            logs.add(log);
            System.out.println("[LOGIN] Usuário '" + nomeUsuario + "' autenticado com sucesso.");
            return true;
        } else {
            LogAuditoria log = new LogAuditoria(contadorLog++, this, "tentativa de login - senha incorreta", "127.0.0.1");
            logs.add(log);
            System.out.println("[FALHA] Senha incorreta para o usuário '" + nomeUsuario + "'.");
            return false;
        }
    }

    // -------------------------
    // Método opcional: criarSessao()
    // -------------------------
    public Sessao criarSessao() {
        String token = UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(0, 16);
        Sessao novaSessao = new Sessao(contadorSessao++, this, token);
        sessoes.add(novaSessao);

        LogAuditoria log = new LogAuditoria(contadorLog++, this, "nova sessão criada - token: " + token, "127.0.0.1");
        logs.add(log);

        System.out.println("[SESSÃO] Nova sessão criada para '" + nomeUsuario + "' | Token: " + token);
        return novaSessao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", email='" + email + '\'' +
                ", perfil=" + (perfil != null ? perfil.getDescricao() : "null") +
                ", ultimoLogin=" + ultimoLogin +
                ", ativo=" + ativo +
                '}';
    }
}