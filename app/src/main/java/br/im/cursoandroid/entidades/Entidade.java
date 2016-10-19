package br.im.cursoandroid.entidades;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by felipe on 7/31/16.
 */
public interface Entidade {

    JSONObject toJson() throws JSONException;
}
