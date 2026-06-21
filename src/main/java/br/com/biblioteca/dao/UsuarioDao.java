package br.com.biblioteca.dao;

import br.com.biblioteca.db.DB;
import br.com.biblioteca.db.DbException;
import br.com.biblioteca.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDao {

    public void salvar(Usuario usuario) {
        if (usuario.getNome().isEmpty() || usuario.getSobrenome().isEmpty() || usuario.getCpf().isEmpty() || usuario.getUsuario().isEmpty() || usuario.getSenha().isEmpty()) {
            throw new DbException("Todos os campos devem ser preenchidos.");
        }

        if (usuarioExiste(usuario.getCpf(), usuario.getUsuario())) {
            throw new DbException("CPF ou usuário já cadastrados.");
        }

        String sql = "INSERT INTO usuario(nome, sobrenome, cpf, usuario, senha) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSobrenome());
            ps.setString(3, usuario.getCpf());
            ps.setString(4, usuario.getUsuario());
            ps.setString(5, usuario.getSenha());

            ps.executeUpdate();
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public boolean usuarioExiste(String cpf, String usuario) {
        String sql = "SELECT * FROM usuario WHERE cpf = ? OR usuario = ?";
        boolean existe = false;

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, cpf);
            ps.setString(2, usuario);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = true;
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao verificar existência de usuário: " + e.getMessage());
        }
        return existe;
    }

    public Usuario fazerLogin(String usuario, String senha) {
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";
        Usuario usuarioEncontrado = null;

        try {
            Connection conexao = DB.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(rs.getInt("id"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setSobrenome(rs.getString("sobrenome"));
                usuarioEncontrado.setCpf(rs.getString("cpf"));
                usuarioEncontrado.setUsuario(rs.getString("usuario"));
                usuarioEncontrado.setSenha(rs.getString("senha"));
            }

            DB.closeResultSet(rs);
            DB.closeStatement(ps);

        } catch (Exception e) {
            throw new DbException("Erro ao fazer login: " + e.getMessage());
        }
        return usuarioEncontrado;
    }
}