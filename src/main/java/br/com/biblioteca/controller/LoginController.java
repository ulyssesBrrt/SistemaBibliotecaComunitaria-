package br.com.biblioteca.controller;

import br.com.biblioteca.dao.UsuarioDao;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.sessao.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnEntrar;
    @FXML private Button btnCadastrar;

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void entrar() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Aviso", "Preencha usuário e senha.", Alert.AlertType.WARNING);
            return;
        }

        try {
            UsuarioDao dao = new UsuarioDao();
            Usuario usuarioLogado = dao.fazerLogin(usuario, senha);

            if (usuarioLogado != null) {
                Sessao.setUsuarioLogado(usuarioLogado);
                abrirDashboard();
            } else {
                mostrarAlerta("Erro", "Usuário ou senha inválidos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro de Conexão", "Não foi possível conectar ao banco de dados.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void abrirCadastro() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/tela-cadastro.fxml")));
            Stage stage = (Stage) btnCadastrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao abrir tela de cadastro.", Alert.AlertType.ERROR);
        }
    }

    private void abrirDashboard() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml")));
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao abrir o dashboard.", Alert.AlertType.ERROR);
        }
    }
}