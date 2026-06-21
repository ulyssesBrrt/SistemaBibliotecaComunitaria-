package br.com.biblioteca.sessao;

import br.com.biblioteca.model.Usuario;

public class Sessao {

    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        Sessao.usuarioLogado = usuarioLogado;
    }
}