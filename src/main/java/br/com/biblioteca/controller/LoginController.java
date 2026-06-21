package br.com.biblioteca.controller;

import br.com.biblioteca.dao.UsuarioDao;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.sessao.Sessao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnCadastrar;

    @FXML
    public void entrar() {

        String usuario =
                txtUsuario.getText();

        String senha =
                txtSenha.getText();

        if (usuario.isEmpty()
                || senha.isEmpty()) {

            System.out.println(
                    "Preencha usuário e senha."
            );

            return;
        }

        UsuarioDao dao =
                new UsuarioDao();

        Usuario usuarioLogado =
                dao.fazerLogin(
                        usuario,
                        senha
                );

        if (usuarioLogado != null) {

            // Salva o usuário logado na sessão
            Sessao.setUsuarioLogado(
                    usuarioLogado
            );

            System.out.println(
                    "Login realizado com sucesso!"
            );

            System.out.println(
                    "Bem-vindo, "
                            + usuarioLogado.getNome()
            );

            try {

                Parent root =
                        FXMLLoader.load(
                                Objects.requireNonNull(
                                        getClass().getResource(
                                                "/view/dashboard.fxml"
                                        )
                                )
                        );

                Stage stage =
                        (Stage) btnEntrar
                                .getScene()
                                .getWindow();

                Scene scene =
                        new Scene(root);

                stage.setScene(scene);

                stage.show();

            } catch (Exception e) {

                System.out.println(
                        "Erro ao abrir dashboard:"
                );

                e.printStackTrace();

            }

        } else {

            System.out.println(
                    "Usuário ou senha inválidos."
            );

        }
    }

    @FXML
    public void abrirCadastro() {

        try {

            Parent root =
                    FXMLLoader.load(
                            Objects.requireNonNull(
                                    getClass().getResource(
                                            "/view/tela-cadastro.fxml"
                                    )
                            )
                    );

            Stage stage =
                    (Stage) btnCadastrar
                            .getScene()
                            .getWindow();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao abrir cadastro:"
            );

            e.printStackTrace();

        }
    }
}