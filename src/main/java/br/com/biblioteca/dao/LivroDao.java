package br.com.biblioteca.dao;

import br.com.biblioteca.conexao.ConexaoBanco;
import br.com.biblioteca.model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LivroDao {

    public void salvar(Livro livro) {

        // Validação dos dados dos livros.
        if (livro.getTitulo().isEmpty()
                || livro.getAutor().isEmpty()
                || livro.getGenero().isEmpty()) {

            System.out.println("Preencha todos os dados do livro.");
            return;
        }

        String sql =
                "INSERT INTO livro (titulo, autor, genero, status, id_dono) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getGenero());

            String status;

            if (livro.isDisponivel()) {
                status = "DISPONIVEL";
            } else {
                status = "INDISPONIVEL";
            }

            ps.setString(4, status);
            ps.setInt(5, livro.getIdDono());

            ps.executeUpdate();

            System.out.println("Livro cadastrado com sucesso!");

            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao cadastrar livro:");
            System.out.println(e.getMessage());

        }
    }

    public void listarLivros() {

        String sql = "SELECT * FROM livro";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nLIVROS CADASTRADOS\n");

            while (rs.next()) {

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Título: " + rs.getString("titulo"));
                System.out.println("Autor: " + rs.getString("autor"));
                System.out.println("Gênero: " + rs.getString("genero"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Dono: " + rs.getInt("id_dono"));

                System.out.println("---------------------------");
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao listar livros:");
            System.out.println(e.getMessage());

        }
    }
    public void alterarStatus(int idLivro, String novoStatus) {

        String sql =
                "UPDATE livro SET status = ? WHERE id = ?";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(1, novoStatus);
            ps.setInt(2, idLivro);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Status do livro atualizado com sucesso!");
            } else {
                System.out.println("Livro não encontrado.");
            }

            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao atualizar status:");
            System.out.println(e.getMessage());

        }
    }
}
