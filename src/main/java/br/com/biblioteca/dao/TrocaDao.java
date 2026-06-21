package br.com.biblioteca.dao;

import br.com.biblioteca.conexao.ConexaoBanco;
import br.com.biblioteca.model.Troca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrocaDao {

    public void solicitarTroca(Troca troca) {

        String sql =
                "INSERT INTO troca(id_livro, id_solicitante, id_dono, status) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setInt(1, troca.getIdLivro());
            ps.setInt(2, troca.getIdSolicitante());
            ps.setInt(3, troca.getIdDono());
            ps.setString(4, troca.getStatus());

            ps.executeUpdate();

            ps.close();
            conexao.close();

            System.out.println("Troca solicitada com sucesso!");

        } catch (Exception e) {

            System.out.println("Erro ao solicitar troca:");
            System.out.println(e.getMessage());

        }
    }

    public void atualizarStatus(int idTroca, String novoStatus) {

        String sql =
                "UPDATE troca SET status = ? WHERE id = ?";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(1, novoStatus);
            ps.setInt(2, idTroca);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Status da troca atualizado!");
            } else {
                System.out.println("Troca não encontrada.");
            }

            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao atualizar troca:");
            System.out.println(e.getMessage());

        }
    }

    public void listarTrocas() {

        String sql = "SELECT * FROM troca";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== TROCAS =====\n");

            while (rs.next()) {

                System.out.println("ID: " +
                        rs.getInt("id"));

                System.out.println("Livro: " +
                        rs.getInt("id_livro"));

                System.out.println("Solicitante: " +
                        rs.getInt("id_solicitante"));

                System.out.println("Dono: " +
                        rs.getInt("id_dono"));

                System.out.println("Status: " +
                        rs.getString("status"));

                System.out.println("--------------------");
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao listar trocas:");
            System.out.println(e.getMessage());

        }
    }

    public boolean existeTrocaPendente(
            int idLivro,
            int idSolicitante
    ) {

        String sql =
                "SELECT * FROM troca " +
                        "WHERE id_livro = ? " +
                        "AND id_solicitante = ? " +
                        "AND status = 'EM_ANALISE'";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setInt(1, idLivro);
            ps.setInt(2, idSolicitante);

            ResultSet rs =
                    ps.executeQuery();

            boolean existe =
                    rs.next();

            rs.close();
            ps.close();
            conexao.close();

            return existe;

        } catch (Exception e) {

            System.out.println(
                    e.getMessage()
            );
        }

        return false;
    }

    public int contarTrocados() {

        String sql =
                "SELECT COUNT(*) FROM troca " +
                        "WHERE status = 'CONCLUIDA'";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);

            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao contar trocas concluídas:");
            System.out.println(e.getMessage());

        }

        return 0;
    }

    public int contarEmAnalise() {

        String sql =
                "SELECT COUNT(*) FROM troca " +
                        "WHERE status = 'EM_ANALISE'";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);

            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao contar trocas em análise:");
            System.out.println(e.getMessage());

        }

        return 0;
    }

    public List<Troca> buscarTrocasRecebidas(
            int idDono
    ) {

        List<Troca> trocas =
                new ArrayList<>();

        String sql =
                "SELECT * FROM troca " +
                        "WHERE id_dono = ?";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setInt(1, idDono);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Troca troca =
                        new Troca();

                troca.setId(
                        rs.getInt("id")
                );

                troca.setIdLivro(
                        rs.getInt("id_livro")
                );

                troca.setIdSolicitante(
                        rs.getInt("id_solicitante")
                );

                troca.setIdDono(
                        rs.getInt("id_dono")
                );

                troca.setStatus(
                        rs.getString("status")
                );

                trocas.add(troca);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar trocas recebidas:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return trocas;
    }

    public List<Troca> buscarTrocasEnviadas(
            int idSolicitante
    ) {

        List<Troca> trocas =
                new ArrayList<>();

        String sql =
                "SELECT * FROM troca " +
                        "WHERE id_solicitante = ?";

        try {

            Connection conexao =
                    ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setInt(
                    1,
                    idSolicitante
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Troca troca =
                        new Troca();

                troca.setId(
                        rs.getInt("id")
                );

                troca.setIdLivro(
                        rs.getInt("id_livro")
                );

                troca.setIdSolicitante(
                        rs.getInt("id_solicitante")
                );

                troca.setIdDono(
                        rs.getInt("id_dono")
                );

                troca.setStatus(
                        rs.getString("status")
                );

                trocas.add(troca);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar trocas enviadas:"
            );

            System.out.println(
                    e.getMessage()
            );
        }

        return trocas;
    }
}
