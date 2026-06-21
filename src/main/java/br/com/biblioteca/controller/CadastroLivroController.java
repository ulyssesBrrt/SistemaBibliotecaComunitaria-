package br.com.biblioteca.controller;

import br.com.biblioteca.dao.LivroDao;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.sessao.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroLivroController {

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtGenero;

    @FXML
    private Button btnCadastrarLivro;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnAcervo;

    @FXML
    private Button btnTrocas;

    @FXML
    private Button btnUsuario;

    @FXML
    public void cadastrarLivro() {

        try {

            Usuario usuarioLogado =
                    Sessao.getUsuarioLogado();

            if (usuarioLogado == null) {

                System.out.println(
                        "Nenhum usuário logado."
                );

                return;
            }

            Livro livro =
                    new Livro();

            livro.setTitulo(
                    txtTitulo.getText()
            );

            livro.setAutor(
                    txtAutor.getText()
            );

            livro.setGenero(
                    txtGenero.getText()
            );

            livro.setDisponivel(
                    true
            );

            livro.setIdDono(
                    usuarioLogado.getId()
            );

            LivroDao livroDao =
                    new LivroDao();

            livroDao.salvar(
                    livro
            );

            System.out.println(
                    "Livro cadastrado com sucesso!"
            );

            abrirAcervo();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao cadastrar livro:"
            );

            e.printStackTrace();
        }
    }

    @FXML
    public void abrirDashboard() {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/view/dashboard.fxml"
                            )
                    );

            Stage stage =
                    (Stage) btnDashboard
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void abrirAcervo() {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/view/tela-acervo.fxml"
                            )
                    );

            Stage stage =
                    (Stage) btnCadastrarLivro
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void abrirTrocas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/tela-trocas.fxml"));
            Stage stage = (Stage) btnTrocas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro ao abrir Trocas");
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirUsuario() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/tela-usuario.fxml"));
            Stage stage = (Stage) btnUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro ao abrir Usuário");
            e.printStackTrace();
        }
    }

    public Button getBtnTrocas() {
        return btnTrocas;
    }

    public void setBtnTrocas(Button btnTrocas) {
        this.btnTrocas = btnTrocas;
    }

    public Button getBtnUsuario() {
        return btnUsuario;
    }

    public void setBtnUsuario(Button btnUsuario) {
        this.btnUsuario = btnUsuario;
    }
}