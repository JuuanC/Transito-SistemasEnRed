package com.principal.apptransito.utilidades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class DialogoModificacion extends DialogFragment {

     private static final String MESSAGE = "MensajeDialogo";
    private String mensaje;
    private OnDialogListener onDialogListener;

    public interface OnDialogListener {
        void confirmacionModificar();
        void cancelarModificacion();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDialogListener = (OnDialogListener) getActivity();
        mensaje = getArguments().getString(MESSAGE, "Error de mensaje");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle("Información del sistema")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.confirmacionModificar();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.cancelarModificacion();
                    }
                });

        return builder.create();
    }
}
