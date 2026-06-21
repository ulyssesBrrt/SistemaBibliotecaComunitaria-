package br.com.biblioteca.dao;

import br.com.biblioteca.db.DB;
import br.com.biblioteca.db.DbException;
import br.com.biblioteca.model.Troca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrocaDao {

    public void solicitarTroca(Troca troca) {
        String sql = "INSERT INTO troca(id_livro, id_solicitante, id_dono, status) VALUES (?, ?, ?, ?)";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, troca.getIdLivro());
            ps.setInt(2, troca.getIdSolicitante());
            ps.setInt(3, troca.getIdDono());
            ps.setString(4, troca.getStatus());

            ps.executeUpdate();
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao solicitar troca: " + e.getMessage());
        }
    }

    public void atualizarStatus(int idTroca, String novoStatus) {
        String sql = "UPDATE troca SET status = ? WHERE id = ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, novoStatus);
            ps.setInt(2, idTroca);
            ps.executeUpdate();

            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao atualizar troca: " + e.getMessage());
        }
    }

    public boolean existeTrocaPendente(int idLivro, int idSolicitante) {
        String sql = "SELECT * FROM troca WHERE id_livro = ? AND id_solicitante = ? AND status = 'EM_ANALISE'";
        boolean existe = false;

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, idLivro);
            ps.setInt(2, idSolicitante);

            ResultSet rs = ps.executeQuery();
            existe = rs.next();

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao verificar trocas pendentes: " + e.getMessage());
        }
        return existe;
    }

    public int contarTrocados() {
        String sql = "SELECT COUNT(*) FROM troca WHERE status = 'CONCLUIDA'";
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
            throw new DbException("Erro ao contar trocas concluídas: " + e.getMessage());
        }
    }

    public int contarEmAnalise() {
        String sql = "SELECT COUNT(*) FROM troca WHERE status = 'EM_ANALISE'";
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
            throw new DbException("Erro ao contar trocas em análise: " + e.getMessage());
        }
    }

    public List<Troca> buscarTrocasRecebidas(int idDono) {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT * FROM troca WHERE id_dono = ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idDono);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Troca troca = new Troca();
                troca.setId(rs.getInt("id"));
                troca.setIdLivro(rs.getInt("id_livro"));
                troca.setIdSolicitante(rs.getInt("id_solicitante"));
                troca.setIdDono(rs.getInt("id_dono"));
                troca.setStatus(rs.getString("status"));
                trocas.add(troca);
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao buscar trocas recebidas: " + e.getMessage());
        }
        return trocas;
    }

    public List<Troca> buscarTrocasEnviadas(int idSolicitante) {
        List<Troca> trocas = new ArrayList<>();
        String sql = "SELECT * FROM troca WHERE id_solicitante = ?";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idSolicitante);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Troca troca = new Troca();
                troca.setId(rs.getInt("id"));
                troca.setIdLivro(rs.getInt("id_livro"));
                troca.setIdSolicitante(rs.getInt("id_solicitante"));
                troca.setIdDono(rs.getInt("id_dono"));
                troca.setStatus(rs.getString("status"));
                trocas.add(troca);
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao buscar trocas enviadas: " + e.getMessage());
        }
        return trocas;
    }
}