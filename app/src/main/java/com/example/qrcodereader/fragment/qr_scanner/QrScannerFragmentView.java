package com.example.qrcodereader.fragment.qr_scanner;


import android.support.annotation.StringRes;
import android.support.v4.util.Pair;

import com.example.qrcodereader.fragment.FragmentViewBase;

import java.util.List;


public interface QrScannerFragmentView extends FragmentViewBase {
    void onQrScanned(String qrCode);

    void setCanScan(boolean canScan);

    void showLoading(boolean show);

    void showToast(String message);

    void showToast(@StringRes int messageRes);

}
