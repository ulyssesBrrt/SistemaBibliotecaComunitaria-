package br.com.biblioteca.controller;

import br.com.biblioteca.dao.LivroDao;
import br.com.biblioteca.dao.TrocaDao;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Troca;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.sessao.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AcervoController implements Initializable {

    @FXML private FlowPane flowLivros;
    @FXML private TextField txtPesquisa;
    @FXML private Label lblQuantidadeLivros;
    @FXML private Button btnAcervo;
    @FXML private Button btnTrocas;
    @FXML private Button btnUsuario;
    @FXML private Button btnDashboard;

    private final LivroDao livroDao = new LivroDao();

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarLivros();
    }

    public void carregarLivros() {
        try {
            flowLivros.getChildren().clear();
            List<Livro> livros = livroDao.buscarTodosLivros();
            lblQuantidadeLivros.setText(livros.size() + " livros encontrados");

            for (Livro livro : livros) {
                VBox card = criarCard(livro);
                flowLivros.getChildren().add(card);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível carregar o acervo.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void pesquisarLivros() {
        try {
            String pesquisa = txtPesquisa.getText();
            flowLivros.getChildren().clear();
            List<Livro> livros = livroDao.pesquisarLivros(pesquisa);
            lblQuantidadeLivros.setText(livros.size() + " livros encontrados");

            for (Livro livro : livros) {
                VBox card = criarCard(livro);
                flowLivros.getChildren().add(card);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao pesquisar livros.", Alert.AlertType.ERROR);
        }
    }

    private VBox criarCard(Livro livro) {
        VBox card = new VBox();
        card.setPrefWidth(170);
        card.setPrefHeight(220);
        card.setSpacing(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-padding: 15;");

        Label lblStatus = new Label();
        if (livro.isDisponivel()) {
            lblStatus.setText("DISPONÍVEL");
            lblStatus.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 4 8 4 8; -fx-background-radius: 8;");
        } else {
            lblStatus.setText("INDISPONÍVEL");
            lblStatus.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-padding: 4 8 4 8; -fx-background-radius: 8;");
        }

        Label lblTitulo = new Label(livro.getTitulo());
        lblTitulo.setWrapText(true);
        lblTitulo.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: #2D2019;");

        Label lblAutor = new Label("Autor: " + livro.getAutor());
        lblAutor.setStyle("-fx-font-size: 12; -fx-text-fill: #8A8177;");

        Label lblGenero = new Label("Gênero: " + livro.getGenero());
        lblGenero.setStyle("-fx-font-size: 12; -fx-text-fill: #8A8177;");

        Button btnReservar = new Button("Reservar");
        btnReservar.setPrefWidth(130);
        btnReservar.setPrefHeight(35);
        btnReservar.setStyle("-fx-background-color: #D6C8B8; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-cursor: hand;");

        if (!livro.isDisponivel()) {
            btnReservar.setDisable(true);
            btnReservar.setStyle("-fx-background-color: #BDBDBD; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold;");
            btnReservar.setText("Indisponível");
        }

        btnReservar.setOnAction(e -> {
            Usuario usuarioLogado = Sessao.getUsuarioLogado();

            if (usuarioLogado == null) {
                mostrarAlerta("Aviso", "Nenhum usuário logado.", Alert.AlertType.WARNING);
                return;
            }
            if (usuarioLogado.getId() == livro.getIdDono()) {
                mostrarAlerta("Aviso", "Você não pode reservar seu próprio livro.", Alert.AlertType.WARNING);
                return;
            }

            try {
                TrocaDao trocaDao = new TrocaDao();
                boolean existeTroca = trocaDao.existeTrocaPendente(livro.getId(), usuarioLogado.getId());

                if (existeTroca) {
                    mostrarAlerta("Aviso", "Você já solicitou este livro.", Alert.AlertType.WARNING);
                    return;
                }

                Troca troca = new Troca();
                troca.setIdLivro(livro.getId());
                troca.setIdSolicitante(usuarioLogado.getId());
                troca.setIdDono(livro.getIdDono());
                troca.setStatus("EM_ANALISE");

                trocaDao.solicitarTroca(troca);
                mostrarAlerta("Sucesso", "Solicitação enviada com sucesso!", Alert.AlertType.INFORMATION);

            } catch (Exception ex) {
                mostrarAlerta("Erro", "Erro ao processar a solicitação.", Alert.AlertType.ERROR);
            }
        });

        card.getChildren().addAll(lblStatus, lblTitulo, lblAutor, lblGenero, btnReservar);
        return card;
    }

    @FXML public void cadastrarLivros() { trocarTela("/view/cadastrar-livro.fxml", flowLivros); }
    @FXML public void abrirDashboard() { trocarTela("/view/dashboard.fxml", btnDashboard); }
    @FXML public void abrirAcervo() { carregarLivros(); }
    @FXML public void abrirTrocas() { trocarTela("/view/tela-trocas.fxml", btnTrocas); }
    @FXML public void abrirUsuario() { trocarTela("/view/tela-usuario.fxml", btnUsuario); }

    private void trocarTela(String caminho, Object controleFx) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminho));
            Stage stage;
            if (controleFx instanceof Button) {
                stage = (Stage) ((Button) controleFx).getScene().getWindow();
            } else {
                stage = (Stage) ((FlowPane) controleFx).getScene().getWindow();
            }
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao abrir tela.", Alert.AlertType.ERROR);
        }
    }
}