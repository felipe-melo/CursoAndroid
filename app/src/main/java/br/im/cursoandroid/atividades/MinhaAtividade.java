package br.im.cursoandroid.atividades;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.im.cursoandroid.R;

/**
 * Created by felipe on 7/24/16.
 */
public abstract class MinhaAtividade extends Activity {

    private ProgressDialog dialog;

    public void exibirMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void exibirMensagemErro(String mensagem) {
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.field_error, null);

        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        ImageView cancel = (ImageView) view.findViewById(R.id.imgCancel);
        tvMessage.setText(mensagem);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        final Dialog dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    protected void openDialog(String mensagem) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(mensagem);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    protected void closeDialog() {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    public abstract void sucesso(String resposta);

    public abstract void falha(String resposta);
}
