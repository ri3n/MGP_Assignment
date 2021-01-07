package com.example.mgp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.DialogFragment;

public class PauseConfirmDialogFragment extends DialogFragment {
    //Check if dialog is shown
    public static boolean IsShown = false;

    //Dialog is a pop page but it has its written API
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Builder name: builder

        builder.setMessage("Confirm Pause?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                GameSystem.Instance.SetIsPaused(true);
                IsShown = false;
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User cancelled pause
                        GameSystem.Instance.SetIsPaused(false);
                        IsShown = false;
                    }
                });

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        IsShown = false;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        IsShown = false;
    }

}
