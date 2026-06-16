package br.com.biblioteca.dao;

import br.com.biblioteca.conexao.ConexaoBanco;
import br.com.biblioteca.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDao {

    public void salvar(Usuario usuario) {

        if (usuarioExiste(usuario.getCpf(), usuario.getUsuario())) {
            System.out.println("CPF ou usuário já cadastrados.");
            return;
        }

        String sql =
                "INSERT INTO usuario(nome, sobrenome, cpf, usuario, senha) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSobrenome());
            ps.setString(3, usuario.getCpf());
            ps.setString(4, usuario.getUsuario());
            ps.setString(5, usuario.getSenha());

            ps.executeUpdate();

            ps.close();
            conexao.close();

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (Exception e) {

            System.out.println("Erro ao cadastrar:");
            System.out.println(e.getMessage());

        }
    }

    public boolean usuarioExiste(String cpf, String usuario) {

        String sql = "SELECT * FROM usuario WHERE cpf = ? OR usuario = ?";

        try {
            Connection conexao = ConexaoBanco.conectar();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, cpf);
            ps.setString(2, usuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rs.close();
                ps.close();
                conexao.close();
                return true;
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {
            System.out.println("Erro ao verificar existência de usuário:");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Usuario fazerLogin(String usuario, String senha) {

        String sql =
                "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";

        try {

            Connection conexao = ConexaoBanco.conectar();

            PreparedStatement ps =
                    conexao.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuarioEncontrado = new Usuario();

                usuarioEncontrado.setId(rs.getInt("id"));
                usuarioEncontrado.setNome(rs.getString("nome"));
                usuarioEncontrado.setSobrenome(rs.getString("sobrenome"));
                usuarioEncontrado.setCpf(rs.getString("cpf"));
                usuarioEncontrado.setUsuario(rs.getString("usuario"));
                usuarioEncontrado.setSenha(rs.getString("senha"));

                rs.close();
                ps.close();
                conexao.close();

                return usuarioEncontrado;
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro ao fazer login:");
            System.out.println(e.getMessage());

        }

        return null;
    }
}