package br.com.biblioteca.dao;

import br.com.biblioteca.db.DB;
import br.com.biblioteca.db.DbException;
import br.com.biblioteca.model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivroDao {

    public void salvar(Livro livro) {
        if (livro.getTitulo().isEmpty() || livro.getAutor().isEmpty() || livro.getGenero().isEmpty()) {
            throw new DbException("Preencha todos os dados do livro.");
        }

        String sql = "INSERT INTO livro (titulo, autor, genero, status, id_dono) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getGenero());
            ps.setString(4, livro.isDisponivel() ? "DISPONIVEL" : "INDISPONIVEL");
            ps.setInt(5, livro.getIdDono());

            ps.executeUpdate();
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    // --- NOVO MÉTODO PARA EXCLUIR O LIVRO (O "D" DO CRUD) ---
    public void deletar(int idLivro) {
        String sql = "DELETE FROM livro WHERE id = ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, idLivro);
            ps.executeUpdate();

            DB.closeStatement(ps);
        } catch (Exception e) {
            throw new DbException("Erro ao deletar livro: " + e.getMessage());
        }
    }

    public void listarLivros() {
        String sql = "SELECT * FROM livro";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Título: " + rs.getString("titulo"));
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao listar livros: " + e.getMessage());
        }
    }

    public void alterarStatus(int idLivro, String novoStatus) {
        String sql = "UPDATE livro SET status = ? WHERE id = ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, novoStatus);
            ps.setInt(2, idLivro);
            ps.executeUpdate();

            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao atualizar status: " + e.getMessage());
        }
    }

    public int contarLivros() {
        String sql = "SELECT COUNT(*) FROM livro";
        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int total = 0;
            if (rs.next()) total = rs.getInt(1);

            DB.closeResultSet(rs);
            DB.closeStatement(ps);
            return total;

        } catch (Exception e) {
            throw new DbException("Erro ao contar livros: " + e.getMessage());
        }
    }

    public int contarDisponiveis() {
        String sql = "SELECT COUNT(*) FROM livro WHERE status = 'DISPONIVEL'";
        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int total = 0;
            if (rs.next()) total = rs.getInt(1);

            DB.closeResultSet(rs);
            DB.closeStatement(ps);
            return total;

        } catch (Exception e) {
            throw new DbException("Erro ao contar disponíveis: " + e.getMessage());
        }
    }

    public List<Livro> buscarUltimosLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro ORDER BY id DESC LIMIT 4";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setGenero(rs.getString("genero"));
                livros.add(livro);
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao buscar últimos livros: " + e.getMessage());
        }
        return livros;
    }

    public List<Livro> buscarTodosLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro ORDER BY titulo";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                livros.add(montarLivro(rs));
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao buscar todos os livros: " + e.getMessage());
        }
        return livros;
    }

    public List<Livro> pesquisarLivros(String pesquisa) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE titulo LIKE ? OR autor LIKE ? OR genero LIKE ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%" + pesquisa + "%");
            ps.setString(2, "%" + pesquisa + "%");
            ps.setString(3, "%" + pesquisa + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                livros.add(montarLivro(rs));
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao pesquisar livros: " + e.getMessage());
        }
        return livros;
    }

    public Livro buscarPorId(int idLivro) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        Livro livro = null;

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idLivro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                livro = montarLivro(rs);
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao buscar livro por ID: " + e.getMessage());
        }
        return livro;
    }

    public List<Livro> buscarLivrosDoUsuario(int idUsuario) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE id_dono = ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                livros.add(montarLivro(rs));
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao buscar livros do usuário: " + e.getMessage());
        }
        return livros;
    }

    private Livro montarLivro(ResultSet rs) throws Exception {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setGenero(rs.getString("genero"));
        livro.setIdDono(rs.getInt("id_dono"));
        livro.setDisponivel("DISPONIVEL".equals(rs.getString("status")));
        return livro;
    }
}