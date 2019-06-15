package com.principal.apptransito.utilidades;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.principal.apptransito.R;

public class DialogoConfirmacion extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "MainFragment";
    private static final String MESSAGE = "MensajeDelDialogo";
    private String tituloDialogo;
    private String contenidoDialogo;

    public interface OnInputSelected {
        void confirmarMensaje();
    }

    public OnInputSelected onInputSelected;

    private TextView dialogoTitulo;
    private TextView dialogoContenido;
    private TextView dialogoAceptar;
    private TextView dialogoCancelar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_confirmacion, container, false);

        dialogoContenido = view.findViewById(R.id.idDialogoContenido);
        dialogoAceptar = view.findViewById(R.id.idDialogoAceptar);
        dialogoCancelar = view.findViewById(R.id.idDialogoCancelar);

        dialogoAceptar.setOnClickListener(this);
        dialogoCancelar.setOnClickListener(this);

        dialogoContenido.setText(contenidoDialogo);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.idDialogoAceptar:
                onInputSelected.confirmarMensaje();
                getDialog().dismiss();
                break;
            case R.id.idDialogoCancelar:
                getDialog().dismiss();
                break;
            default:
                // No hacer nada.
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
             onInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }

        contenidoDialogo = getArguments().getString(MESSAGE, "Algo ocurrió mal");


    }
}
