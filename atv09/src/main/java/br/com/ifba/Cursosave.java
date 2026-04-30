package br.com.ifba;

import br.com.ifba.curso.entity.Curso;
import jakarta.persistence.EntityManager;

/**
 * Classe de exemplo para salvar um Curso usando JPA + Hibernate.
 * Demonstra como o EntityManager.persist() substitui todo o
 * código JDBC manual.
 *
 * @author PRG03 - IFBA
 */
public class CursoSave {

    public static void main(String[] args) {

        // 1. Criar um objeto Curso
        Curso curso = new Curso();
        curso.setNome("Análise e Desenvolvimento de Sistemas");
        curso.setCodigoCurso("ADS");
        curso.setAtivo(true);

        // 2. Obter o EntityManager
        EntityManager em = JPAUtil.getEntityManager();

        try {
            // 3. Iniciar transação
            em.getTransaction().begin();

            // 4. SALVANDO UM CURSO — só isso! Sem SQL manual.
            em.persist(curso);

            // 5. Confirmar transação
            em.getTransaction().commit();

            System.out.println("Curso salvo com sucesso! ID gerado: " + curso.getId());

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao salvar curso: " + e.getMessage());
        } finally {
            em.close();
        }

        JPAUtil.close();
    }
}