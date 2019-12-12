package id.co.bigtek.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Activity_ListProduk extends AppCompatActivity {
    private DBHelper db;

    private RecyclerView listProduk;
    private ArrayList<model_produk> produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);
        db = new DBHelper(this);
        inisialisasi();
        load_fungsi();
        load_data();

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //membuka halaman awal setelah query di proses
            startActivity(new Intent(Activity_ListProduk.this,HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    private void load_data() {
        //mempersiapkan list
        produk = new ArrayList<>();
        //memastikan list agar kosong
        produk.clear();
        //mengatur layoutmanager RecyclerView
        //agar menggunakan LinearLayout sebagai container
        listProduk.setLayoutManager(new LinearLayoutManager(this));

        String query = "SELECT * FROM produk";
        //mengeksekusi custom query
        Cursor cursor = db.customQuery(query);
        //mengarahkan cursor pada baris pertama hasil query
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            model_produk model = new model_produk();
            model.setKode_produk(cursor.getInt(cursor.getColumnIndex("kode_produk")));
            model.setNama_produk(cursor.getString(cursor.getColumnIndex("nama_produk")));
            model.setHarga_pokok(cursor.getDouble(cursor.getColumnIndex("harga_pokok")));
            model.setHarga_jual(cursor.getDouble(cursor.getColumnIndex("harga_jual")));
            model.setStok(cursor.getInt(cursor.getColumnIndex("stok")));
            produk.add(model);
            //cursor.moveToNext();
        }
        //mempersiapkan adapter untuk recyclerView
        AdapterProduk adapterProduk = new AdapterProduk(this,produk);
        //mengatur adapter untuk RecyclerView
        listProduk.setAdapter(adapterProduk);
        listProduk.getAdapter().notifyDataSetChanged();
    }

    private void inisialisasi() {

        listProduk = findViewById(R.id.listProduk);
    }

    private void load_fungsi(){

    }
}
