package com.example.gestionafacil.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gestionafacil.R;

import androidx.appcompat.app.AlertDialog;

public class DespachoDialog extends AlertDialog {
    private int despachosEnviados;

    public DespachoDialog(Context context, int despachosEnviados) {
        super(context);
        this.despachosEnviados = despachosEnviados;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.despachar_dialog, null);
        setContentView(dialogView);

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        TextView countTextView = dialogView.findViewById(R.id.countTextView);
        ImageView okeyGifImageView = dialogView.findViewById(R.id.okeyGifImageView);

        countTextView.setText("Despachos enviados: " + despachosEnviados);

        // Cargar el GIF usando Glide
        Glide.with(getContext()).asGif().load(R.drawable.system_regular_31_check).into(okeyGifImageView);
    }

}
