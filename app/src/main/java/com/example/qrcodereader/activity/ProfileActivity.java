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

public class ProfileActivity extends Activity {

    EditText nameEt;
    EditText familyEt;
    EditText dobEt;
    EditText emailEt;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        nameEt = findViewById(R.id.et_name);
        familyEt = findViewById(R.id.et_family);
        dobEt = findViewById(R.id.et_dob);
        emailEt = findViewById(R.id.et_email);
        radioGroup = findViewById(R.id.rg_sex);

        nameEt.setText(SharedPrefsUtils.getStringPreference(ProfileActivity.this, "name"));
        familyEt.setText(SharedPrefsUtils.getStringPreference(ProfileActivity.this, "family"));
        emailEt.setText(SharedPrefsUtils.getStringPreference(ProfileActivity.this, "email"));
        dobEt.setText(SharedPrefsUtils.getStringPreference(ProfileActivity.this, "dob"));


        findViewById(R.id.btn_register).setOnClickListener(e -> {
            if (!nameEt.getText().toString().isEmpty() &&
                    !familyEt.getText().toString().isEmpty() &&
                    !dobEt.getText().toString().isEmpty() &&
                    !emailEt.getText().toString().isEmpty()) {
                editUser(nameEt.getText().toString(),
                        familyEt.getText().toString(),
                        emailEt.getText().toString(),
                        dobEt.getText().toString(),
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

    private void editUser(String name, String family, String email, String dob, Boolean sex) {
        User user = new User(name, family, email, dob, sex.toString());
        user.setKey(SharedPrefsUtils.getStringPreference(this, "key"));
        Call<User> editUser =
                RetrofitProvider.getUidApi().editUser(user);
        editUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "تغییرات شما اعمال شدند.", Toast.LENGTH_SHORT).show();

                    SharedPrefsUtils.setStringPreference(ProfileActivity.this, "name", nameEt.getText().toString());
                    SharedPrefsUtils.setStringPreference(ProfileActivity.this, "family", familyEt.getText().toString());
                    SharedPrefsUtils.setStringPreference(ProfileActivity.this, "email", emailEt.getText().toString());
                    SharedPrefsUtils.setStringPreference(ProfileActivity.this, "dob", dobEt.getText().toString());
                    SharedPrefsUtils.setBooleanPreference(ProfileActivity.this, "sex", radioGroup.getCheckedRadioButtonId() == R.id.female);

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
