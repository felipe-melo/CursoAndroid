package br.im.cursoandroid.util;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by felipe on 8/6/16.
 */
public class SingletonRequest {

    public static RequestQueue requestQueue;

    public static RequestQueue initializeQueue(Context context) {
        if (context == null) return null;
        requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }
}
