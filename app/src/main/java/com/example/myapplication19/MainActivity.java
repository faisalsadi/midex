package com.example.myapplication19;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication19.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<RVitem> RVitems;
    AdapterRV adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();
        Spinner spinner = findViewById(R.id.spinner);

        // we pass our item list and context to our Adapter.
        adapter = new AdapterRV(this, RVitems);
        spinner.setAdapter(adapter);
        Log.d("","");
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id)
                    {

                        // It returns the clicked item.
                        RVitem clickedItem = (RVitem)
                                parent.getItemAtPosition(position);
                        String name = clickedItem.getAlgorithmName();
                        Toast.makeText(MainActivity.this, name + " selected", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {
                    }
                });
    }

    // It is used to set the algorithm names to our array list.
    private void initList()
    {
        RVitems = new ArrayList<>();
        RVitems.add(new RVitem("Quick Sort"));
        RVitems.add(new RVitem("Merge Sort"));
        RVitems.add(new RVitem("Heap Sort"));
        RVitems.add(new RVitem("Prims Algorithm"));
        RVitems.add(new RVitem("Kruskal Algorithm"));
        RVitems.add(new RVitem("Rabin Karp"));
        RVitems.add(new RVitem("Binary Search"));
    }
}