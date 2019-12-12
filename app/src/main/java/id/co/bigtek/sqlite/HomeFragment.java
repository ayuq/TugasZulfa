package id.co.bigtek.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private DBHelper db;
    Button btnSimpan, btnBersihkan;
    EditText editNamaProduk;
    EditText edithargaPokok;
    EditText editHargaJual;
    EditText editStok;
    EditText editKodeProduk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);
        db = new DBHelper(getContext());
        btnBersihkan = view.findViewById(R.id.btnBersihkan);
        btnSimpan = view.findViewById(R.id.btnSimpan);

        editKodeProduk = view.findViewById(R.id.editKodeProduk);
        editNamaProduk = view.findViewById(R.id.editNamaProduk);
        edithargaPokok = view.findViewById(R.id.editHargaPokok);
        editHargaJual = view.findViewById(R.id.editHargaJual);
        editStok = view.findViewById(R.id.editStokProduk);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method prosesSimpan()
                prosesSimpan();

            }
        });


        return view;
    }

    private void prosesSimpan() {
        //mengecek apakah form masih ada yang kosong
        if(editKodeProduk.getText().toString().isEmpty() ||
                editNamaProduk.getText().toString().isEmpty() ||
                edithargaPokok.getText().toString().isEmpty() ||
                editHargaJual.getText().toString().isEmpty() ||
                editStok.getText().toString().isEmpty()){
            //Beritahukan jika ada form yang kosong dengan TOASTER
            Toast.makeText(getContext(), "Data masih belum lengkap", Toast.LENGTH_SHORT).show();
        }else{
            //periksa apakah sudah ada data dengan kode yang sama
            String queryCek = "SELECT * FROM produk WHERE kode_produk = "+editKodeProduk.getText();
            if(db.customQuery(queryCek).getCount() > 0){
                //jika sudah ada
                Toast.makeText(getContext(), "Kode Produk sudah digunakan.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Data berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), HomeActivity.class);
                    startActivity(i);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
