package br.im.cursoandroid.util;

import br.im.cursoandroid.entidades.Usuario;
import br.im.cursoandroid.exceptions.UsuarioNaoEstaLogado;

/**
 * Created by root on 8/30/16.
 */
public class UsuarioLogado {

    public static UsuarioLogado instance;
    private Usuario usuario;

    private UsuarioLogado() {}

    public static UsuarioLogado getInstance() {
        if (instance == null) {
            instance = new UsuarioLogado();
        }
        return instance;
    }

    public void setValues(Usuario usuario) {
        try {
            this.usuario = new Usuario(usuario.getNome(), usuario.getSenha(), usuario.getSenha(), usuario.getEmail());
            this.usuario.setEndereco(usuario.getEndereco());
            this.usuario.setDataNascimento(usuario.getDataNascimento());
            this.usuario.setFoto(usuario.getFoto());
        }catch (Exception e) {
            e.printStackTrace();
            this.usuario = null;
        }
    }

    public Usuario getUsuario() throws UsuarioNaoEstaLogado {
        if (usuario == null) throw new UsuarioNaoEstaLogado();
        return usuario;
    }

}
