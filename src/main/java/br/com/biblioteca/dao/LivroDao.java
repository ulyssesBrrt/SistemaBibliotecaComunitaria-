package br.com.biblioteca.dao;

import br.com.biblioteca.conexao.ConexaoBanco;
import br.com.biblioteca.model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivroDao {

    public void salvar(Livro livro) {

        if (livro.getTitulo().isEmpty()
                || livro.getAutor().isEmpty()
                || livro.getGenero().isEmpty()) {

            System.out.println(
                    "Preencha todos os dados do livro."
            );

            return;
        }

        String sql =
                "INSERT INTO livro " +
                        "(titulo, autor, genero, status, id_dono) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(
                    1,
                    livro.getTitulo()
            );

            ps.setString(
                    2,
                    livro.getAutor()
            );

            ps.setString(
                    3,
                    livro.getGenero()
            );

            String status;

            if (livro.isDisponivel()) {

                status = "DISPONIVEL";

            } else {

                status = "INDISPONIVEL";
            }

            ps.setString(
                    4,
                    status
            );

            ps.setInt(
                    5,
                    livro.getIdDono()
            );

            ps.executeUpdate();

            System.out.println(
                    "Livro cadastrado com sucesso!"
            );

            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao cadastrar livro:"
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }

    public void listarLivros() {

        String sql =
                "SELECT * FROM livro";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            System.out.println(
                    "\nLIVROS CADASTRADOS\n"
            );

            while (rs.next()) {

                System.out.println(
                        "ID: " +
                                rs.getInt("id")
                );

                System.out.println(
                        "Título: " +
                                rs.getString("titulo")
                );

                System.out.println(
                        "Autor: " +
                                rs.getString("autor")
                );

                System.out.println(
                        "Gênero: " +
                                rs.getString("genero")
                );

                System.out.println(
                        "Status: " +
                                rs.getString("status")
                );

                System.out.println(
                        "Dono: " +
                                rs.getInt("id_dono")
                );

                System.out.println(
                        "---------------------------"
                );
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao listar livros:"
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }

    public void alterarStatus(
            int idLivro,
            String novoStatus
    ) {

        String sql =
                "UPDATE livro " +
                        "SET status = ? " +
                        "WHERE id = ?";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(
                    1,
                    novoStatus
            );

            ps.setInt(
                    2,
                    idLivro
            );

            int linhasAfetadas =
                    ps.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println(
                        "Status do livro atualizado com sucesso!"
                );

            } else {

                System.out.println(
                        "Livro não encontrado."
                );
            }

            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao atualizar status:"
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }

    public int contarLivros() {

        String sql =
                "SELECT COUNT(*) FROM livro";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao contar livros:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return 0;
    }

    public int contarDisponiveis() {

        String sql =
                "SELECT COUNT(*) FROM livro " +
                        "WHERE status = 'DISPONIVEL'";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao contar disponíveis:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return 0;
    }

    public List<Livro> buscarUltimosLivros() {

        List<Livro> livros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM livro " +
                        "ORDER BY id DESC LIMIT 4";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Livro livro =
                        new Livro();

                livro.setId(
                        rs.getInt("id")
                );

                livro.setTitulo(
                        rs.getString("titulo")
                );

                livro.setAutor(
                        rs.getString("autor")
                );

                livro.setGenero(
                        rs.getString("genero")
                );

                livros.add(livro);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar livros:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return livros;
    }

    public List<Livro> buscarTodosLivros() {

        List<Livro> livros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM livro ORDER BY titulo";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Livro livro =
                        montarLivro(rs);

                livros.add(livro);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar livros:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return livros;
    }

    public List<Livro> pesquisarLivros(
            String pesquisa
    ) {

        List<Livro> livros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM livro " +
                        "WHERE titulo LIKE ? " +
                        "OR autor LIKE ? " +
                        "OR genero LIKE ?";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(
                    1,
                    "%" + pesquisa + "%"
            );

            ps.setString(
                    2,
                    "%" + pesquisa + "%"
            );

            ps.setString(
                    3,
                    "%" + pesquisa + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Livro livro =
                        montarLivro(rs);

                livros.add(livro);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao pesquisar livros:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return livros;
    }

    public Livro buscarPorId(
            int idLivro
    ) {

        String sql =
                "SELECT * FROM livro " +
                        "WHERE id = ?";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setInt(
                    1,
                    idLivro
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Livro livro =
                        montarLivro(rs);

                rs.close();
                ps.close();
                conexao.close();

                return livro;
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar livro:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return null;
    }

    public List<Livro> buscarLivrosDoUsuario(
            int idUsuario
    ) {

        List<Livro> livros =
                new ArrayList<>();

        String sql =
                "SELECT * FROM livro " +
                        "WHERE id_dono = ?";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setInt(
                    1,
                    idUsuario
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Livro livro =
                        montarLivro(rs);

                livros.add(livro);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar livros do usuário:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return livros;
    }

    private Livro montarLivro(
            ResultSet rs
    ) throws Exception {

        Livro livro =
                new Livro();

        livro.setId(
                rs.getInt("id")
        );

        livro.setTitulo(
                rs.getString("titulo")
        );

        livro.setAutor(
                rs.getString("autor")
        );

        livro.setGenero(
                rs.getString("genero")
        );

        livro.setIdDono(
                rs.getInt("id_dono")
        );

        livro.setDisponivel(
                "DISPONIVEL".equals(
                        rs.getString("status")
                )
        );

        return livro;
    }
}