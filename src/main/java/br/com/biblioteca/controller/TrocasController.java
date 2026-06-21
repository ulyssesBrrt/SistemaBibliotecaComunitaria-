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

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TrocasController implements Initializable {

    @FXML
    private FlowPane flowRecebidas;

    @FXML
    private FlowPane flowEnviadas;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnAcervo;

    @FXML
    private Button btnTrocas;

    @FXML
    private Button btnUsuario;

    private final TrocaDao trocaDao = new TrocaDao();
    private final LivroDao livroDao = new LivroDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarTrocasRecebidas();
        carregarTrocasEnviadas();
    }

    private void carregarTrocasRecebidas() {
        flowRecebidas.getChildren().clear();
        Usuario usuario = Sessao.getUsuarioLogado();

        if (usuario == null) return;

        List<Troca> trocas = trocaDao.buscarTrocasRecebidas(usuario.getId());

        for (Troca troca : trocas) {
            Livro livro = livroDao.buscarPorId(troca.getIdLivro());
            if (livro != null) {
                VBox card = criarCardRecebido(livro, troca);
                flowRecebidas.getChildren().add(card);
            }
        }
    }

    private void carregarTrocasEnviadas() {
        flowEnviadas.getChildren().clear();
        Usuario usuario = Sessao.getUsuarioLogado();

        if (usuario == null) return;

        List<Troca> trocas = trocaDao.buscarTrocasEnviadas(usuario.getId());

        for (Troca troca : trocas) {
            Livro livro = livroDao.buscarPorId(troca.getIdLivro());
            if (livro != null) {
                VBox card = criarCardEnviado(livro, troca);
                flowEnviadas.getChildren().add(card);
            }
        }
    }

    private VBox criarCardRecebido(Livro livro, Troca troca) {
        VBox card = new VBox();
        card.setPrefWidth(170);
        card.setPrefHeight(230);
        card.setSpacing(10);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 15;"
        );

        Label lblStatus = new Label(troca.getStatus().toUpperCase());
        estilizarStatus(lblStatus, troca.getStatus());

        Label lblTitulo = new Label(livro.getTitulo());
        lblTitulo.setWrapText(true);
        lblTitulo.setStyle(
                "-fx-font-size: 15;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2D2019;"
        );

        Label lblAutor = new Label("Autor: " + livro.getAutor());
        lblAutor.setStyle(
                "-fx-font-size: 12;" +
                        "-fx-text-fill: #8A8177;"
        );

        Button btnAceitar = new Button("Aceitar");
        btnAceitar.setPrefWidth(140);
        btnAceitar.setPrefHeight(30);
        btnAceitar.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        Button btnRecusar = new Button("Recusar");
        btnRecusar.setPrefWidth(140);
        btnRecusar.setPrefHeight(30);
        btnRecusar.setStyle(
                "-fx-background-color: #E53935;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        btnAceitar.setOnAction(e -> {

            trocaDao.atualizarStatus(
                    troca.getId(),
                    "CONCLUIDA"
            );

            livroDao.alterarStatus(
                    livro.getId(),
                    "INDISPONIVEL"
            );

            carregarTrocasRecebidas();
            carregarTrocasEnviadas();
        });

        btnRecusar.setOnAction(e -> {
            trocaDao.atualizarStatus(troca.getId(), "RECUSADA");
            carregarTrocasRecebidas();
            carregarTrocasEnviadas();
        });

        card.getChildren().addAll(lblStatus, lblTitulo, lblAutor, btnAceitar, btnRecusar);
        return card;
    }

    private VBox criarCardEnviado(Livro livro, Troca troca) {
        VBox card = new VBox();
        card.setPrefWidth(170);
        card.setPrefHeight(180);
        card.setSpacing(10);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 15;"
        );

        Label lblStatus = new Label(troca.getStatus().toUpperCase());
        estilizarStatus(lblStatus, troca.getStatus());

        Label lblTitulo = new Label(livro.getTitulo());
        lblTitulo.setWrapText(true);
        lblTitulo.setStyle(
                "-fx-font-size: 15;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2D2019;"
        );

        Label lblAutor = new Label("Autor: " + livro.getAutor());
        lblAutor.setStyle(
                "-fx-font-size: 12;" +
                        "-fx-text-fill: #8A8177;"
        );

        card.getChildren().addAll(lblStatus, lblTitulo, lblAutor);
        return card;
    }

    private void estilizarStatus(Label lblStatus, String status) {
        if (status.equalsIgnoreCase("PENDENTE") || status.equalsIgnoreCase("EM_ANALISE")) {
            lblStatus.setStyle(
                    "-fx-background-color: #FFB300;" +
                            "-fx-text-fill: white;" +
                            "-fx-padding: 4 8 4 8;" +
                            "-fx-background-radius: 8;"
            );
        } else if (status.equalsIgnoreCase("CONCLUIDA")) {
            lblStatus.setStyle(
                    "-fx-background-color: #4CAF50;" +
                            "-fx-text-fill: white;" +
                            "-fx-padding: 4 8 4 8;" +
                            "-fx-background-radius: 8;"
            );
        } else {
            lblStatus.setStyle(
                    "-fx-background-color: #E53935;" +
                            "-fx-text-fill: white;" +
                            "-fx-padding: 4 8 4 8;" +
                            "-fx-background-radius: 8;"
            );
        }
    }

    @FXML
    public void abrirDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Stage stage = (Stage) btnDashboard.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirAcervo() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/tela-acervo.fxml"));
            Stage stage = (Stage) btnAcervo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirTrocas() {
        carregarTrocasRecebidas();
        carregarTrocasEnviadas();
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

    public Button getBtnUsuario() {
        return btnUsuario;
    }

    public void setBtnUsuario(Button btnUsuario) {
        this.btnUsuario = btnUsuario;
    }

    public Button getBtnTrocas() {
        return btnTrocas;
    }

    public void setBtnTrocas(Button btnTrocas) {
        this.btnTrocas = btnTrocas;
    }
}