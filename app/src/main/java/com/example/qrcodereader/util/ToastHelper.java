package com.example.qrcodereader.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class ToastHelper {

    public static Toast makeText(Context context , String message){
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static Toast showText(Context context , String message){
        Toast toast = makeText(context , message);
        toast.show();
        return toast;
    }


}
