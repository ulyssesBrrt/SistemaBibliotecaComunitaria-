package br.com.biblioteca.controller;

import br.com.biblioteca.dao.LivroDao;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.sessao.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroLivroController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtGenero;
    @FXML private Button btnCadastrarLivro;
    @FXML private Button btnDashboard;
    @FXML private Button btnAcervo;
    @FXML private Button btnTrocas;
    @FXML private Button btnUsuario;

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void cadastrarLivro() {
        Usuario usuarioLogado = Sessao.getUsuarioLogado();

        if (usuarioLogado == null) {
            mostrarAlerta("Aviso", "Nenhum usuário logado.", Alert.AlertType.WARNING);
            return;
        }

        if (txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty()) {
            mostrarAlerta("Aviso", "Preencha o título e o autor.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Livro livro = new Livro();
            livro.setTitulo(txtTitulo.getText());
            livro.setAutor(txtAutor.getText());
            livro.setGenero(txtGenero.getText());
            livro.setDisponivel(true);
            livro.setIdDono(usuarioLogado.getId());

            LivroDao livroDao = new LivroDao();
            livroDao.salvar(livro);

            mostrarAlerta("Sucesso", "Livro cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            abrirAcervo();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao cadastrar livro no banco de dados.", Alert.AlertType.ERROR);
        }
    }

    // --- Navegação ---
    @FXML public void abrirDashboard() { trocarTela("/view/dashboard.fxml", btnDashboard); }
    @FXML public void abrirAcervo() { trocarTela("/view/tela-acervo.fxml", btnCadastrarLivro); }
    @FXML public void abrirTrocas() { trocarTela("/view/tela-trocas.fxml", btnTrocas); }
    @FXML public void abrirUsuario() { trocarTela("/view/tela-usuario.fxml", btnUsuario); }

    private void trocarTela(String caminho, Button botao) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminho));
            Stage stage = (Stage) botao.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao trocar de tela.", Alert.AlertType.ERROR);
        }
    }
}