package com.example.qrcodereader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qrcodereader.R;
import com.example.qrcodereader.model.RetrofitProvider;
import com.example.qrcodereader.model.User;
import com.example.qrcodereader.util.SharedPrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefsUtils.getStringPreference(this, "key") != null) {
            gotoScanActivity();
        }

        setContentView(R.layout.activity_scan);

        EditText name = (EditText) findViewById(R.id.et_name);
        EditText family = (EditText) findViewById(R.id.et_family);
        EditText dob = (EditText) findViewById(R.id.et_dob);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_sex);


        findViewById(R.id.btn_register).setOnClickListener(e -> {
            if (!name.getText().toString().isEmpty() &&
                    !family.getText().toString().isEmpty() &&
                    !dob.getText().toString().isEmpty()) {
                registerUser(name.getText().toString(),
                        family.getText().toString(),
                        dob.getText().toString(),
                        radioGroup.getCheckedRadioButtonId() == R.id.female);
            } else
                Toast.makeText(this, "لطفا تمام اطلاعات را وارد کنید", Toast.LENGTH_SHORT).show();
        });
    }

    private void gotoScanActivity() {
        Log.d("onCreate: key = ", SharedPrefsUtils.getStringPreference(this, "key"));
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
        finish();
    }

    private void registerUser(String name, String family, String dob, Boolean sex) {
        User user = new User(name, dob, sex.toString(), null);
        Call<User> registerUser =
                RetrofitProvider.getUidApi().registerUser(user);
        registerUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "ثبت نام شما با موفقیت انجام شد.", Toast.LENGTH_SHORT).show();
                    SharedPrefsUtils.setStringPreference(RegisterActivity.this, "key", response.body().getKey());
                    gotoScanActivity();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("register User onFailure", "  " + t.getCause() + " " + t.getMessage());
            }
        });
    }

}
