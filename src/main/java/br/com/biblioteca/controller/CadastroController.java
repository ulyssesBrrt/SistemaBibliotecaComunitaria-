package br.com.biblioteca.controller;

import br.com.biblioteca.dao.UsuarioDao;
import br.com.biblioteca.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroController {

    @FXML private TextField txtNome;
    @FXML private TextField txtSobrenome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnCadastrar;

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void cadastrar() {
        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || txtUsuario.getText().isEmpty()) {
            mostrarAlerta("Aviso", "Preencha todos os campos obrigatórios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Usuario usuario = new Usuario();
            usuario.setNome(txtNome.getText());
            usuario.setSobrenome(txtSobrenome.getText());
            usuario.setCpf(txtCpf.getText());
            usuario.setUsuario(txtUsuario.getText());
            usuario.setSenha(txtSenha.getText());

            UsuarioDao dao = new UsuarioDao();
            dao.salvar(usuario);

            mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);
            abrirDashboard();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao cadastrar. Verifique se o CPF ou Usuário já existem.", Alert.AlertType.ERROR);
        }
    }

    public void abrirDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Stage stage = (Stage) btnCadastrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao abrir o sistema.", Alert.AlertType.ERROR);
        }
    }
}