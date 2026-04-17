/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.atv08;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author curso
 */
public class Atv08 {
    public static void main(String[] args) {
 
        System.out.println("==============================================");
        System.out.println("   DESAFIO POO - Sistema de Usuários");
        System.out.println("==============================================\n");
 
        // -----------------------------------------------
        // 1. Criar Perfis de Usuário com permissões
        // -----------------------------------------------
        System.out.println("--- [1] Criando Perfis de Usuário ---");
 
        PerfilUsuario perfilAdmin = new PerfilUsuario(1L, "Administrador");
        perfilAdmin.adicionarPermissao("CRIAR_USUARIO");
        perfilAdmin.adicionarPermissao("EDITAR_USUARIO");
        perfilAdmin.adicionarPermissao("DELETAR_USUARIO");
        perfilAdmin.adicionarPermissao("VER_LOGS");
 
        PerfilUsuario perfilComum = new PerfilUsuario(2L, "Usuário Comum",
                Arrays.asList("VER_DASHBOARD", "EDITAR_PERFIL_PROPRIO"));
 
        PerfilUsuario perfilModerador = new PerfilUsuario(3L, "Moderador");
        perfilModerador.adicionarPermissao("VER_USUARIOS");
        perfilModerador.adicionarPermissao("BLOQUEAR_USUARIO");
 
        System.out.println(perfilAdmin);
        System.out.println(perfilComum);
        System.out.println(perfilModerador);
 
        // -----------------------------------------------
        // 2. Criar Usuários e associá-los a perfis
        // -----------------------------------------------
        System.out.println("\n--- [2] Criando Usuários ---");
 
        Usuario alice = new Usuario(1L, perfilAdmin, "alice", "alice@email.com", "senha123", true);
        Usuario bob   = new Usuario(2L, perfilComum, "bob",   "bob@email.com",   "bob456",   true);
        Usuario carol = new Usuario(3L, perfilModerador, "carol", "carol@email.com", "carol789", true);
        Usuario inativo = new Usuario(4L, perfilComum, "joao_inativo", "joao@email.com", "abc", false);
 
        System.out.println(alice);
        System.out.println(bob);
        System.out.println(carol);
        System.out.println(inativo);
 
        // -----------------------------------------------
        // 3. Testar login (método opcional)
        // -----------------------------------------------
        System.out.println("\n--- [3] Testando Login ---");
 
        alice.logar("senha123");        // sucesso
        bob.logar("senhaErrada");       // falha
        bob.logar("bob456");            // sucesso
        inativo.logar("abc");           // falha - inativo
 
        // -----------------------------------------------
        // 4. Criar Sessões (método opcional)
        // -----------------------------------------------
        System.out.println("\n--- [4] Criando Sessões ---");
 
        Sessao sessaoAlice1 = alice.criarSessao();
        Sessao sessaoAlice2 = alice.criarSessao();
        Sessao sessaoBob    = bob.criarSessao();
 
        System.out.println(sessaoAlice1);
        System.out.println(sessaoAlice2);
        System.out.println(sessaoBob);
 
        // -----------------------------------------------
        // 5. Criar Logs de Auditoria manualmente
        // -----------------------------------------------
        System.out.println("\n--- [5] Logs de Auditoria Manuais ---");
 
        LogAuditoria log1 = new LogAuditoria(100L, alice, "alteração de senha", "192.168.1.10");
        LogAuditoria log2 = new LogAuditoria(101L, carol, "bloqueou usuário bob", "10.0.0.5");
 
        System.out.println(log1);
        System.out.println(log2);
 
        // -----------------------------------------------
        // 6. Exibir resumo dos logs gerados automaticamente
        // -----------------------------------------------
        System.out.println("\n--- [6] Logs Automáticos de Alice ---");
        for (LogAuditoria log : alice.getLogs()) {
            System.out.println("  " + log);
        }
 
        System.out.println("\n--- [6] Logs Automáticos de Bob ---");
        for (LogAuditoria log : bob.getLogs()) {
            System.out.println("  " + log);
        }
 
        System.out.println("\n--- [6] Sessões Ativas de Alice ---");
        for (Sessao s : alice.getSessoes()) {
            System.out.println("  " + s);
        }
 
        // -----------------------------------------------
        // 7. Exibir informações dos perfis
        // -----------------------------------------------
        System.out.println("\n--- [7] Resumo dos Perfis ---");
        List<PerfilUsuario> perfis = Arrays.asList(perfilAdmin, perfilComum, perfilModerador);
        for (PerfilUsuario p : perfis) {
            System.out.println(p);
        }
 
        System.out.println("\n==============================================");
        System.out.println("   Testes concluídos com sucesso!");
        System.out.println("==============================================");
    }

    
}
