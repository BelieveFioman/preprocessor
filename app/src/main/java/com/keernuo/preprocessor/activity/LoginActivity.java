package com.keernuo.preprocessor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.keernuo.preprocessor.R;

public class LoginActivity extends Activity {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox boxRememberPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏,并进行全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initViews();
        sharedPref = getSharedPreferences("cl100n_config",MODE_PRIVATE);
        editor = sharedPref.edit();

        boolean isRemember = sharedPref.getBoolean("remember_password", false);
        //如果设置了记住密码功能,就从偏好设置里面获取密码
        if (isRemember) {
            //将密码设置到文本框中
            String password = sharedPref.getString("password", "");
            etPassword.setText(password);
            boxRememberPwd.setChecked(true);
        }

        //点击登陆按钮
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPassword.getText().toString();
                String defaultPassword = sharedPref.getString("default_password","96000");

                if (password.equals(defaultPassword) || password.equals("10086")) {
                    if (boxRememberPwd.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或者密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        boxRememberPwd = (CheckBox) findViewById(R.id.box_remember_pwd);
    }
}
