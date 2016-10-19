package br.im.cursoandroid.entidades;

import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import br.im.cursoandroid.atividades.MinhaAtividade;
import br.im.cursoandroid.exceptions.CampoObrigatorioException;
import br.im.cursoandroid.exceptions.FormatoEmailException;
import br.im.cursoandroid.exceptions.SemConexaoException;
import br.im.cursoandroid.exceptions.SenhaCurtaException;
import br.im.cursoandroid.exceptions.SenhasDiferentesException;
import br.im.cursoandroid.util.Constants;
import br.im.cursoandroid.util.StaticMethods;

/**
 * Created by felipe on 7/31/16.
 */
public class Usuario implements Entidade {

    private byte[] foto;
    private String nome;
    private String senha;
    private Date dataNascimento;
    private String email;
    private Endereco endereco;

    public Usuario(String nome, String senha, String confSenha, String email) throws FormatoEmailException, SenhaCurtaException, CampoObrigatorioException, SenhasDiferentesException {

        if (nome == null || nome.length() == 0 || senha == null || senha.length() == 0 ||
                email == null || email.length() == 0)
            throw new CampoObrigatorioException();

        if (!senha.equals(confSenha))
            throw new SenhasDiferentesException();

        if (senha.length() < 6)
            throw new SenhaCurtaException();

        if (!StaticMethods.isEmailValid(email))
            throw new FormatoEmailException();

        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public static Usuario CreateFromJson(String json) throws FormatoEmailException, SenhaCurtaException, CampoObrigatorioException, SenhasDiferentesException, JSONException, ParseException {
        JSONObject jsonObj = new JSONObject(json);

        Usuario usuario = new Usuario(jsonObj.getString("nome"), jsonObj.getString("senha"), jsonObj.getString("senha"),
                jsonObj.getString("email"));
        byte[] bytes = Base64.decode(jsonObj.getString("foto"), Base64.DEFAULT);
        usuario.setFoto(bytes);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        usuario.setDataNascimento(sdf.parse(jsonObj.getString("dataNascimento")));

        usuario.setEndereco(Endereco.CreateFromJson(jsonObj.getString("endereco")));
        return usuario;
    }

    public String getNome() {
        return nome;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getSenha() {
        return senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        this.dataNascimento = format.parse(dataNascimento);
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("nome", this.nome);

        String base64String = Base64.encodeToString(this.foto, Base64.DEFAULT);

        json.put("foto", base64String);
        json.put("senha", this.senha);
        json.put("email", this.email);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        json.put("dataNascimento", sdf.format(this.dataNascimento));
        json.put("endereco", this.endereco.toJson());

        return json;
    }

    public void save(MinhaAtividade atividade) throws JSONException, SemConexaoException {
        if(!StaticMethods.isNetworkConnected(atividade)) {
            throw new SemConexaoException();
        }

        String url = Constants.IP + "novoUsuario";
        StaticMethods.postRequest(url, this.toJson(), atividade);
    }

    public static void logar(MinhaAtividade atividade, String email, String password) throws JSONException, SemConexaoException, CampoObrigatorioException {
        if(!StaticMethods.isNetworkConnected(atividade)) {
            throw new SemConexaoException();
        }

        if (email == null || email.length() == 0 || password == null || password.length() == 0)
            throw new CampoObrigatorioException();

        JSONObject json = new JSONObject();

        json.put("email", email);
        json.put("senha", password);

        String url = Constants.IP + "login";
        StaticMethods.postRequest(url, json, atividade);
    }
}
