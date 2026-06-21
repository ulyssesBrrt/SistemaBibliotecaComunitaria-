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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {

    @FXML private Label lblNome;
    @FXML private Label lblSobrenome;
    @FXML private Label lblCpf;
    @FXML private Label lblUsuario;
    @FXML private Label lblTotalLivros;
    @FXML private Label lblDisponiveis;
    @FXML private Label lblTrocados;
    @FXML private Label lblEmAnalise;
    @FXML private FlowPane flowMeusLivros;

    @FXML private Button btnDashboard;
    @FXML private Button btnAcervo;
    @FXML private Button btnTrocas;
    @FXML private Button btnUsuario;

    private final LivroDao livroDao = new LivroDao();
    private final TrocaDao trocaDao = new TrocaDao();

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarPerfil();
        carregarEstatisticas();
        carregarMeusLivros();
    }

    private void carregarPerfil() {
        Usuario usuario = Sessao.getUsuarioLogado();
        if (usuario != null) {
            lblNome.setText(usuario.getNome());
            lblSobrenome.setText(usuario.getSobrenome());
            lblCpf.setText(usuario.getCpf());
            lblUsuario.setText(usuario.getUsuario());
        }
    }

    private void carregarEstatisticas() {
        Usuario usuario = Sessao.getUsuarioLogado();
        if (usuario == null) return;

        try {
            List<Livro> meusLivros = livroDao.buscarLivrosDoUsuario(usuario.getId());
            lblTotalLivros.setText(String.valueOf(meusLivros.size()));

            int disponiveis = 0;
            for (Livro livro : meusLivros) {
                if (livro.isDisponivel()) disponiveis++;
            }
            lblDisponiveis.setText(String.valueOf(disponiveis));

            List<Troca> trocasRecebidas = trocaDao.buscarTrocasRecebidas(usuario.getId());
            List<Troca> trocasEnviadas = trocaDao.buscarTrocasEnviadas(usuario.getId());

            List<Troca> todasAsTrocas = new ArrayList<>();
            if (trocasRecebidas != null) todasAsTrocas.addAll(trocasRecebidas);
            if (trocasEnviadas != null) todasAsTrocas.addAll(trocasEnviadas);

            int emAnalise = 0, concluidas = 0;
            for (Troca troca : todasAsTrocas) {
                if (troca.getStatus().equalsIgnoreCase("EM_ANALISE") || troca.getStatus().equalsIgnoreCase("PENDENTE")) {
                    emAnalise++;
                } else if (troca.getStatus().equalsIgnoreCase("CONCLUIDA")) {
                    concluidas++;
                }
            }

            lblEmAnalise.setText(String.valueOf(emAnalise));
            lblTrocados.setText(String.valueOf(concluidas));
        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível carregar as estatísticas.", Alert.AlertType.ERROR);
        }
    }

    private void carregarMeusLivros() {
        Usuario usuario = Sessao.getUsuarioLogado();
        if (usuario == null) return;

        flowMeusLivros.getChildren().clear();

        try {
            List<Livro> livros = livroDao.buscarLivrosDoUsuario(usuario.getId());
            for (Livro livro : livros) {
                VBox card = criarCardLivroUsuario(livro);
                flowMeusLivros.getChildren().add(card);
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao carregar seus livros.", Alert.AlertType.ERROR);
        }
    }

    private VBox criarCardLivroUsuario(Livro livro) {
        VBox card = new VBox();
        card.setPrefWidth(160);
        card.setPrefHeight(120);
        card.setSpacing(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-padding: 15;");

        Label lblTitulo = new Label(livro.getTitulo());
        lblTitulo.setWrapText(true);
        lblTitulo.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: #2D2019;");

        Label lblAutor = new Label(livro.getAutor());
        lblAutor.setStyle("-fx-font-size: 12; -fx-text-fill: #8A8177;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-background-radius: 8;");

        btnExcluir.setOnAction(e -> {
            try {

                livroDao.deletar(livro.getId());
                mostrarAlerta("Sucesso", "Livro excluído com sucesso!", Alert.AlertType.INFORMATION);
                carregarEstatisticas();
                carregarMeusLivros();
            } catch (Exception ex) {
                mostrarAlerta("Erro", "Não foi possível excluir o livro. Ele pode estar em uma troca ativa.", Alert.AlertType.ERROR);
            }
        });

        card.getChildren().addAll(lblTitulo, lblAutor, btnExcluir);
        return card;
    }

    // --- Métodos de Navegação ---
    @FXML public void abrirDashboard() { trocarTela("/view/dashboard.fxml", btnDashboard); }
    @FXML public void abrirAcervo() { trocarTela("/view/tela-acervo.fxml", btnAcervo); }
    @FXML public void abrirTrocas() { trocarTela("/view/tela-trocas.fxml", btnTrocas); }
    @FXML public void abrirUsuarios() { carregarPerfil(); carregarEstatisticas(); carregarMeusLivros(); }

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