package id.co.bigtek.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;


    private DBHelper db;

    private Button btnBersihkan;
    private Button btnSimpan;
    private EditText editNamaProduk;
    private EditText edithargaPokok;
    private EditText editHargaJual;
    private EditText editStok;
    private EditText editKodeProduk;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id == R.id.home_menu){
            Intent i = new Intent(MainActivity.this, HomeFragment.class);
            startActivity(i);
            finish();
        }
        if(id == R.id.tentang_kami){
            Intent i = new Intent(MainActivity.this, ListFragment.class);
            startActivity(i);
            finish();
        }
        if(id == R.id.logout_menu){
            mGoogleSignInClient.signOut();
            FacebookSdk.sdkInitialize(getApplicationContext());
            LoginManager.getInstance().logOut();
            AccessToken.setCurrentAccessToken(null);
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        if(id == R.id.search_menu){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottomnaviagtion_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else {
            super.onBackPressed();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        mGoogleApiClient.connect();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        DrawerLayout drawer =  findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open_navigation, R.string.close_navigation);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // inisialisasi BottomNavigaionView
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        // beri listener pada saat item/menu bottomnavigation terpilih
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


    }



    private void load_function() {
        //fungsi untuk memberishkan isian EditText dengan Button Bersihkan
        btnBersihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editKodeProduk.getText().clear();
                editNamaProduk.getText().clear();
                edithargaPokok.getText().clear();
                editHargaJual.getText().clear();
                editStok.getText().clear();
            }
        });

        //fungsi untuk memproses untuk menyimpan data
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method prosesSimpan()
                prosesSimpan();
            }
        });
    }

    private void prosesSimpan() {
        //mengecek apakah form masih ada yang kosong
        if(editKodeProduk.getText().toString().isEmpty() ||
            editNamaProduk.getText().toString().isEmpty() ||
            edithargaPokok.getText().toString().isEmpty() ||
            editHargaJual.getText().toString().isEmpty() ||
            editStok.getText().toString().isEmpty()){
            //Beritahukan jika ada form yang kosong dengan TOASTER
            Toast.makeText(MainActivity.this,"Data masih belum lengkap.",Toast.LENGTH_SHORT).show();
        }else{
            //periksa apakah sudah ada data dengan kode yang sama
            String queryCek = "SELECT * FROM produk WHERE kode_produk = "+editKodeProduk.getText();
            if(db.customQuery(queryCek).getCount() > 0){
                //jika sudah ada
                Toast.makeText(MainActivity.this,"Kode Produk sudah digunakan.",Toast.LENGTH_SHORT).show();
            }else{
                //jika belum ada
                //lakukan proses insert jika data sudah lengkap
                if(db.insertProduk(
                        //konversi nilai dari text ke tipe data sesuai dengan
                        //yang ada di parameter Method insertProduk pada class DBHelper
                        Integer.valueOf(editKodeProduk.getText().toString()), //parameter 1
                        editNamaProduk.getText().toString(), //parameter 2
                        Double.valueOf(editHargaJual.getText().toString()), //parameter 3
                        Double.valueOf(edithargaPokok.getText().toString()), //parameter 4
                        Integer.valueOf(editStok.getText().toString()) //parameter 5
                )){
                    //beritahukan jika input berhasil dengan TOASTER
                    Toast.makeText(MainActivity.this,"Data berhasil ditambahkan.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void inisialisasi() {
        btnBersihkan = findViewById(R.id.btnBersihkan);
        btnSimpan = findViewById(R.id.btnSimpan);

        editKodeProduk = findViewById(R.id.editKodeProduk);
        editNamaProduk = findViewById(R.id.editNamaProduk);
        edithargaPokok = findViewById(R.id.editHargaPokok);
        editHargaJual = findViewById(R.id.editHargaJual);
        editStok = findViewById(R.id.editStokProduk);
    }



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.home_menu:
                    fragment = new HomeFragment();
                    break;
                case R.id.tentang_kami:
                    fragment = new ListFragment();
                    break;
                case R.id.search_menu:
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                case R.id.logout_menu:
                    mGoogleSignInClient.signOut();
                    FacebookSdk.sdkInitialize(getApplicationContext());
                    LoginManager.getInstance().logOut();
                    AccessToken.setCurrentAccessToken(null);
                    Intent a = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(a);
                    finish();

            }
            return loadFragment(fragment);
        }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            DrawerLayout drawer = findViewById(R.id.drawer);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        return false;
    }

}
