package com.example.hexa_neekuankok.mylocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShellActivity extends AppCompatActivity {
    @Bind(R.id.launch)
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.launch)
    public void launchClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("parentActivity", this.getLocalClassName());
        startActivity(intent);

        finish();

    }
}
