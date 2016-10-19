package br.im.cursoandroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import br.im.cursoandroid.atividades.MinhaAtividade;

/**
 * Created by felipe on 11/06/15.
 */
public class StaticMethods {

    public static void postRequest(String url, final JSONObject json, final MinhaAtividade atividade) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        String mensagem = "";
                        try {
                            JSONObject json = new JSONObject(response);
                            mensagem = json.getString("resposta");
                        }catch (Exception e) {
                            atividade.falha("Erro ao enviar requisição");
                        }finally {
                            atividade.sucesso(mensagem);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String mensagem = "";
                        try {
                            JSONObject json = new JSONObject(error.getMessage());
                            mensagem = json.getString("resposta");
                        }catch (Exception e) {
                            mensagem = "Erro ao enviar requisição";
                        }finally {
                            atividade.sucesso(mensagem);
                        }
                    }
                }
        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-type", "application/json");
                return params;
            }

        };
        SingletonRequest.requestQueue.add(postRequest);
    }

    public static void getRequest(String url, Context context) {

    }

    public static void putRequest(String url, JSONObject json, Context context) {

    }

    public static boolean isEmailValid(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-zA-Z]+");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
