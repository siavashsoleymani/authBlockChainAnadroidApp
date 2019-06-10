package com.example.qrcodereader.fragment.qr_scanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.qrcodereader.BasePresenter;
import com.example.qrcodereader.R;
import com.example.qrcodereader.fragment.BaseFragment;
import com.example.qrcodereader.util.DialogHelper;
import com.example.qrcodereader.util.ToastHelper;
import com.example.qrcodereader.view.ViewFinderView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class QrScannerFragment
        extends BaseFragment
        implements
        QrScannerFragmentView
        , QRCodeReaderView.OnQRCodeReadListener {

    QRCodeReaderView scannerView;

    ViewGroup layoutScannerView;

    ViewFinderView finderView;


    Dialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout._test_fragment_qr, container, false
        );
        layoutScannerView = view.findViewById(R.id.layoutScannerView);
        scannerView = getNewScannerView(inflater.getContext());
        layoutScannerView.addView(scannerView, 0);
        finderView = createViewFinderView(inflater.getContext());
        layoutScannerView.addView(finderView, 1);
        updateScannerViewRatio();
        return view;
    }

    private void updateScannerViewRatio() {
        finderView.post(() -> {
            double ratioLeft = (100 - finderView.getWidthRatio()) / 2;
            double ratioTop  = (100 - finderView.getHeightRatio()) / 2;
            scannerView.setFinderRatio(
                    ratioLeft, ratioTop, finderView.getWidthRatio(), finderView.getHeightRatio()
            );
        });
    }

    private QRCodeReaderView getNewScannerView(Context context) {
        QRCodeReaderView scannerView = new QRCodeReaderView(context);
        scannerView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        scannerView.setQRDecodingEnabled(true);
        scannerView.setAutofocusInterval(500L);
        scannerView.setOnQRCodeReadListener(this);
        return scannerView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        if (scannerView != null)
            scannerView.releaseCamera();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeCameraView();
    }


    @Override
    public void onQrScanned(String qrCode) {
    }


    boolean canScan = true;

    @Override
    public void setCanScan(boolean canScan) {
        this.canScan = canScan;
        if (canScan) {
            resumeCameraView();
        } else {
            pauseCameraView();
        }
    }

    @Override
    public void showLoading(boolean show) {
        Context context = getContext();
        if (context == null)
            return;
        if (progressDialog == null && getContext() != null) {
            progressDialog = DialogHelper.getDefaultLoadingProgressDialog(getContext());
        }
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message) {
        Context context = getContext();
        if (context == null)
            return;
        ToastHelper
                .showText(
                        context,
                        message
                );
    }

    @Override
    public void showToast(int messageRes) {
        showToast(getString(messageRes));
    }

    public static QrScannerFragment getInstance() {
        return new QrScannerFragment();
    }


    private final ExecutorService pool = Executors.newFixedThreadPool(2);

    public void resumeCameraView() {
        pool.submit(() -> {
            if (scannerView != null) {
                if (finderView != null)
                    finderView.setLaserEnabled(true);
                scannerView.setShowPreview(true);
                scannerView.startCamera();
            }
        });
    }

    public void onCameraPermissionGranted() {
        if (layoutScannerView != null) {
            layoutScannerView.removeView(scannerView);
            scannerView = getNewScannerView(getContext());
            layoutScannerView.addView(scannerView, 0);
            updateScannerViewRatio();
        }
        resumeCameraView();
    }


    public void pauseCameraView() {
        if (scannerView != null) {
            if (finderView != null)
                finderView.setLaserEnabled(false);
            scannerView.stopCamera();
            scannerView.setShowPreview(false);
        }
//        pool.submit(() -> {
//
//        });
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (canScan) {
            pauseCameraView();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setMessage(text)
                    .setPositiveButton(
                            "ok",
                            (dialog, which) -> dialog.dismiss()
                    )
                    .setOnCancelListener(dialog -> {
                        resumeCameraView();
                    });
            builder.show();
        }
    }

    protected ViewFinderView createViewFinderView(Context context) {
        ViewFinderView viewFinderView = new ViewFinderView(context);
        viewFinderView.setBorderColor(Color.WHITE);
        viewFinderView.setLaserColor(Color.RED);
        viewFinderView.setLaserEnabled(true);

        viewFinderView.setBorderCornerRounded(true);
        viewFinderView.setSquareViewFinder(true);
        return viewFinderView;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }
}
