package id.co.bigtek.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private int waktu_loading = 3000;
    //3000=3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke login activity
                Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(login);
                finish();

            }
        }, waktu_loading);
    }
}
