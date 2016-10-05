package com.example.murphysl.mvp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.murphysl.mvp.R;
import com.example.murphysl.mvp.SuccessActivity;
import com.example.murphysl.mvp.presenter.LoginPresenterComl;

public class LoginActivity extends AppCompatActivity implements ILoginView , View.OnClickListener{

    private LoginPresenterComl presenter;
    private Button login , clear;
    private EditText name , password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        clear = (Button) findViewById(R.id.clear);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        presenter = new LoginPresenterComl(this);

        login.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClear() {
        name.setText("");
        password.setText("");
    }

    @Override
    public void onLoginResult(Boolean flag) {
        presenter.setProgressBarVisibility(View.INVISIBLE);
        login.setEnabled(true);
        clear.setEnabled(true);
        if (flag){
            startActivity(new Intent(LoginActivity.this , SuccessActivity.class));
            finish();
            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear:
                presenter.clear();
                break;
            case R.id.login:
                name.setEnabled(false);
                password.setEnabled(false);
                presenter.doLogin(name.getText().toString() , password.getText().toString());
                break;
        }
    }
}
