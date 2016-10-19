package br.im.cursoandroid.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.JSONException;

import java.text.ParseException;

import br.im.cursoandroid.R;
import br.im.cursoandroid.entidades.Usuario;
import br.im.cursoandroid.exceptions.CampoObrigatorioException;
import br.im.cursoandroid.exceptions.FormatoEmailException;
import br.im.cursoandroid.exceptions.SemConexaoException;
import br.im.cursoandroid.exceptions.SenhaCurtaException;
import br.im.cursoandroid.exceptions.SenhasDiferentesException;
import br.im.cursoandroid.util.UsuarioLogado;

/**
 * Created by felipe on 7/24/16.
 */
public class Login extends MinhaAtividade {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
    }

    public void logar(View view) {
        try {
            this.openDialog("login...");
            Usuario.logar(this, email.getText().toString(), password.getText().toString());
        } catch (SemConexaoException e) {
            exibirMensagemErro("Falha na conexão.");
            this.closeDialog();
        } catch (JSONException e) {
            exibirMensagemErro("Erro ao enviar requisição.");
            this.closeDialog();
        } catch (CampoObrigatorioException e) {
            exibirMensagemErro("Usuário ou senha inválidos.");
            this.closeDialog();
        }
    }

    public void cadastrar(View view) {
        Intent it = new Intent(this, NovoCadastro.class);
        startActivity(it);
    }

    @Override
    public void sucesso(String resposta) {
        this.closeDialog();
        try {
            Usuario usuario = Usuario.CreateFromJson(resposta);

            UsuarioLogado.getInstance().setValues(usuario);

            Intent it = new Intent(this, Principal.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
        } catch (JSONException | ParseException | SenhaCurtaException | SenhasDiferentesException | CampoObrigatorioException | FormatoEmailException  e) {
            e.printStackTrace();
            exibirMensagemErro("Falha ao carregar usuário.");
        }
    }

    @Override
    public void falha(String resposta) {
        this.closeDialog();
        exibirMensagemErro(resposta);
    }
}
