package id.co.bigtek.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnExit, btnInput, btnLihatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnExit = findViewById(R.id.btnExit);
        btnInput = findViewById(R.id.btnInput);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //perintah untuk masuk ke Input Activity
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });

        //fungsi untuk membuka atau menjalankan Activity_ListProduk
        // dengan mengklik Button Lihat Data

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


}
