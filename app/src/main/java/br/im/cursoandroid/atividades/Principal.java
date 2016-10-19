package br.im.cursoandroid.atividades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.im.cursoandroid.R;
import br.im.cursoandroid.exceptions.UsuarioNaoEstaLogado;
import br.im.cursoandroid.util.UsuarioLogado;

/**
 * Created by felipe on 7/24/16.
 */
public class Principal extends Activity {

    private TextView nome;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.principal);

        nome = (TextView) findViewById(R.id.tvNome);
        foto = (ImageView) findViewById(R.id.imgFoto);

        setValores();
    }

    private void setValores() {
        try {
            byte[] bytes = UsuarioLogado.getInstance().getUsuario().getFoto();
            Log.v("PRINCIPAL", bytes.toString());
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            foto.setImageBitmap(bitmap);

            String ola = getResources().getString(R.string.prin_ola) + " " + UsuarioLogado.getInstance().getUsuario().getNome();
            nome.setText(ola);
        } catch (UsuarioNaoEstaLogado usuarioNaoEstaLogado) {
            usuarioNaoEstaLogado.printStackTrace();
        }
    }

    public void editar(View view) {
        Intent it = new Intent(this, EditarPerfil.class);
        startActivity(it);
    }

    public void sair(View view) {

    }
}
