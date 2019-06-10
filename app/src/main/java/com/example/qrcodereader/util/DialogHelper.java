package com.example.qrcodereader.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.qrcodereader.R;


public class DialogHelper {

    public static AlertDialog.Builder getCustomDialogBuilder(Context context, View rootView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(null)
                .setMessage(null)
                .setView(
                        rootView
                );
        return builder;
    }


    @NonNull
    public static Dialog getDefaultLoadingProgressDialog(@NonNull Context context) {

        Dialog      dialog      = new Dialog(context, R.style.CustomDialog);
        ProgressBar progressBar = new ProgressBar(context);
        dialog.setContentView(progressBar);
        dialog.setCancelable(false);
        return dialog;
    }

    @NonNull
    public static Dialog getCustomFullScreenDialog(@NonNull Context context, View contentView) {

        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(
                contentView,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return dialog;
    }


}
