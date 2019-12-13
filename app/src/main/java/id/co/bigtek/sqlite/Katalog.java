package id.co.bigtek.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Katalog extends Fragment implements View.OnClickListener {

    Button button3;
    Button button4;
    Button  button6;
    Button  button7;
    Button  button8;
    Button   button9;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_katalog, container, false);
        button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = view.findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button6 = view.findViewById(R.id.button6);
        button6.setOnClickListener(this);

        button7 = view.findViewById(R.id.button7);
        button7.setOnClickListener(this);

        button8 = view.findViewById(R.id.button8);
        button8.setOnClickListener(this);

        button9 = view.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        return view;
    }



     @Override
     public void onClick(View v) {
         //do what you want to do when button is clicked
         switch (v.getId()) {
             case R.id.button3:
                 Intent i = new Intent(getContext(), bio.class);
                 startActivity(i);
                 break;
             case R.id.button4:
                 Intent a = new Intent(getContext(), EssayActivity.class);
                 startActivity(a);
                 break;

             case R.id.button6:
                 Intent c = new Intent(getContext(), CinemaActivity.class);
                 startActivity(c);
                 break;
             case R.id.button7:
                 Intent d = new Intent(getContext(), LiteraturActivity.class);
                 startActivity(d);
                 break;
             case R.id.button8:
                 Intent e = new Intent(getContext(), SelfActivity.class);
                 startActivity(e);
                 break;
             case R.id.button9:
                 Intent f = new Intent(getContext(), Essay1Activity.class);
                 startActivity(f);
                 break;

         }

     }

}
