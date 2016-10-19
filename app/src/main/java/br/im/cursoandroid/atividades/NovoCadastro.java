package br.im.cursoandroid.atividades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import br.im.cursoandroid.R;
import br.im.cursoandroid.entidades.Endereco;
import br.im.cursoandroid.entidades.Usuario;
import br.im.cursoandroid.exceptions.CampoObrigatorioException;
import br.im.cursoandroid.exceptions.FormatoEmailException;
import br.im.cursoandroid.exceptions.SemConexaoException;
import br.im.cursoandroid.exceptions.SenhaCurtaException;
import br.im.cursoandroid.exceptions.SenhasDiferentesException;
import br.im.cursoandroid.util.Constants;
import br.im.cursoandroid.util.UsuarioLogado;

/**
 * Created by felipe on 7/24/16.
 */
public class NovoCadastro extends MinhaAtividade {

    private EditText nome;
    private EditText senha;
    private EditText email;
    private EditText confSenha;
    private EditText dataNascimento;
    private EditText edtEndereco;
    private EditText numero;
    private EditText complemento;
    private EditText cep;
    private EditText estado;
    private EditText cidade;
    private EditText bairro;
    private ImageView foto;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cadastrar);

        nome = (EditText) findViewById(R.id.edtNome);
        senha = (EditText) findViewById(R.id.edtSenha);
        confSenha = (EditText) findViewById(R.id.edtConfSenha);
        email = (EditText) findViewById(R.id.edtEmail);
        dataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        numero = (EditText) findViewById(R.id.edtNumero);
        complemento = (EditText) findViewById(R.id.edtComplemento);
        cep = (EditText) findViewById(R.id.edtCep);
        estado = (EditText) findViewById(R.id.edtEstado);
        cidade = (EditText) findViewById(R.id.edtCidade);
        bairro = (EditText) findViewById(R.id.edtBairro);
        foto = (ImageView) findViewById(R.id.imgFoto);
    }

    public void cadastrar(View view) {
        try {

            Endereco endereco = new Endereco(edtEndereco.getText().toString(), numero.getText().toString(), cidade.getText().toString());
            endereco.setBairro(bairro.getText().toString());
            endereco.setComplemento(complemento.getText().toString());
            endereco.setEstado(estado.getText().toString());
            endereco.setCep(cep.getText().toString());

            usuario = new Usuario(nome.getText().toString(), senha.getText().toString(), confSenha.getText().toString(), email.getText().toString());
            usuario.setEndereco(endereco);
            usuario.setDataNascimento(dataNascimento.getText().toString());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            usuario.setFoto(stream.toByteArray());

            this.openDialog("Salvando usuário...");

            usuario.save(this);
        } catch (SenhaCurtaException e) {
            this.exibirMensagemErro("A senha deve ter pelo menos 6 caracteres");
            e.printStackTrace();
        } catch (SenhasDiferentesException e) {
            this.exibirMensagemErro("As senhas não conferem");
            e.printStackTrace();
        } catch (FormatoEmailException e) {
            this.exibirMensagemErro("Formato de Email inválido");
            email.setFocusable(true);
            e.printStackTrace();
        } catch (CampoObrigatorioException e) {
            this.exibirMensagemErro("Algum campo obrigatório não foi preenchido");
            e.printStackTrace();
        } catch (ParseException e) {
            this.exibirMensagemErro("Formato de data de nascimento inválida");
            dataNascimento.setFocusable(true);
            e.printStackTrace();
        } catch (JSONException e) {
            this.closeDialog();
            this.exibirMensagemErro("Erro ao cadastar");
            e.printStackTrace();
        } catch (SemConexaoException e) {
            this.closeDialog();
            this.exibirMensagemErro("Seu telene parece não estar conectado");
            e.printStackTrace();
        }
    }

    public void openCamera(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void sucesso(String mensagem) {
        this.closeDialog();
        UsuarioLogado.getInstance().setValues(usuario);
        Intent it = new Intent(this, Principal.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
    }

    @Override
    public void falha(String mensagem) {
        this.closeDialog();
        this.exibirMensagemErro("Erro ao salvar usuário");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE || requestCode == Constants.CROP) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                foto.setImageBitmap(imageBitmap);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                foto.setImageResource(R.drawable.profile);
            } else {
                foto.setImageResource(R.drawable.profile);
                //exibirMensage("Abrir foto");
            }
        }
    }
}
