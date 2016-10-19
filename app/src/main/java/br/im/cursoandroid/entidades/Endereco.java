package br.im.cursoandroid.entidades;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.im.cursoandroid.exceptions.CampoObrigatorioException;
import br.im.cursoandroid.exceptions.FormatoEmailException;
import br.im.cursoandroid.exceptions.SenhaCurtaException;
import br.im.cursoandroid.exceptions.SenhasDiferentesException;

/**
 * Created by felipe on 7/31/16.
 */
public class Endereco implements Entidade {

    private String endereco;
    private String numero;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String complemento;

    public Endereco(String endereco, String numero, String cidade) throws CampoObrigatorioException {
        if (endereco == null || endereco.length() == 0 || numero == null || numero.length() == 0 ||
                cidade == null || cidade.length() == 0)
            throw new CampoObrigatorioException();

        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public static Endereco CreateFromJson(String json) throws FormatoEmailException, SenhaCurtaException, CampoObrigatorioException, SenhasDiferentesException, JSONException, ParseException {
        JSONObject jsonObj = new JSONObject(json);

        Endereco endereco = new Endereco(jsonObj.getString("endereco"), jsonObj.getString("numero"), jsonObj.getString("cidade"));
        endereco.setBairro(jsonObj.getString("bairro"));
        endereco.setCep(jsonObj.getString("cep"));
        endereco.setComplemento(jsonObj.getString("complemento"));
        endereco.setEstado(jsonObj.getString("estado"));
        return endereco;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("endereco", this.endereco);
        json.put("numero", this.numero);
        json.put("cidade", this.cidade);
        json.put("cep", this.cep);
        json.put("estado", this.estado);
        json.put("bairro", this.bairro);
        json.put("complemento", this.complemento);

        return json;
    }
}
