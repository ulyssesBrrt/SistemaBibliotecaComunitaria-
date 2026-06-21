package br.com.biblioteca.controller;

import br.com.biblioteca.dao.LivroDao;
import br.com.biblioteca.dao.TrocaDao;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.sessao.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label lblTotalLivros;

    @FXML
    private Label lblTrocados;

    @FXML
    private Label lblAnalise;

    @FXML
    private Label lblDisponiveis;

    @FXML
    private Label lblTitulo1;

    @FXML
    private Label lblAutor1;

    @FXML
    private Label lblTitulo2;

    @FXML
    private Label lblAutor2;

    @FXML
    private Label lblTitulo3;

    @FXML
    private Label lblAutor3;

    @FXML
    private Label lblTitulo4;

    @FXML
    private Label lblAutor4;

    @FXML
    private Button btnUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Sessao.getUsuarioLogado() != null) {

            System.out.println(
                    "Usuário logado: "
                            + Sessao.getUsuarioLogado().getNome()
            );
        }

        carregarCards();
        carregarLivros();
    }

    private void carregarCards() {

        LivroDao livroDao =
                new LivroDao();

        TrocaDao trocaDao =
                new TrocaDao();

        lblTotalLivros.setText(
                String.valueOf(
                        livroDao.contarLivros()
                )
        );

        lblDisponiveis.setText(
                String.valueOf(
                        livroDao.contarDisponiveis()
                )
        );

        lblTrocados.setText(
                String.valueOf(
                        trocaDao.contarTrocados()
                )
        );

        lblAnalise.setText(
                String.valueOf(
                        trocaDao.contarEmAnalise()
                )
        );
    }

    private void carregarLivros() {

        LivroDao livroDao =
                new LivroDao();

        List<Livro> livros =
                livroDao.buscarUltimosLivros();

        if (livros.size() > 0) {

            lblTitulo1.setText(
                    livros.get(0).getTitulo()
            );

            lblAutor1.setText(
                    livros.get(0).getAutor()
            );
        }

        if (livros.size() > 1) {

            lblTitulo2.setText(
                    livros.get(1).getTitulo()
            );

            lblAutor2.setText(
                    livros.get(1).getAutor()
            );
        }

        if (livros.size() > 2) {

            lblTitulo3.setText(
                    livros.get(2).getTitulo()
            );

            lblAutor3.setText(
                    livros.get(2).getAutor()
            );
        }

        if (livros.size() > 3) {

            lblTitulo4.setText(
                    livros.get(3).getTitulo()
            );

            lblAutor4.setText(
                    livros.get(3).getAutor()
            );
        }
    }

    @FXML
    private void abrirDashboard() {

        // Já está na Dashboard

    }

    @FXML
    private void abrirAcervo() {

        try {

            Parent root =
                    FXMLLoader.load(
                            Objects.requireNonNull(
                                    getClass().getResource(
                                            "/view/tela-acervo.fxml"
                                    )
                            )
                    );

            Stage stage =
                    (Stage) lblTotalLivros
                            .getScene()
                            .getWindow();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao abrir Acervo:"
            );

            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTrocas() {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/view/tela-trocas.fxml"
                            )
                    );

            Stage stage =
                    (Stage) lblTotalLivros
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao abrir Trocas"
            );

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
}