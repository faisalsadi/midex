package com.example.myapplication19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class thirdScreen extends AppCompatActivity {
    private Button mDatePickerBtn;
    private TextView mSelectedDateText;
    RecyclerView recyclerView1, recyclerView2;
    public ArrayList<RVitem1> lis1;
    public ArrayList<RVitem1> lis2;
    MyAdapter1 myAdapter1, myAdapter2;
    String savesDate;
    long theFirstDate;
    Button bt1;
    String S;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8099/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_screen);

        mSelectedDateText = findViewById(R.id.selected_date);
        mDatePickerBtn = findViewById(R.id.date_picker_btn);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constrainBuilder = new CalendarConstraints.Builder();
        constrainBuilder.setValidator(DateValidatorPointForward.now());


        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setCalendarConstraints(constrainBuilder.build());


        final MaterialDatePicker materialDatePicker = builder.build();


        mDatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mSelectedDateText.setText("Selected Date :" + materialDatePicker.getHeaderText());
                S=materialDatePicker.getHeaderText();
            }
        });


        Toast.makeText(this,S,Toast.LENGTH_SHORT).show();

        addSpinner();
        lis1=new ArrayList<RVitem1>();
        lis1.add(new RVitem1("00:00" ,0));
        lis1.add(new RVitem1("01:00" ,0));
        lis1.add(new RVitem1("02:00" ,0));
        lis1.add(new RVitem1("03:00" ,0));
        lis1.add(new RVitem1("04:00" ,0));
        lis1.add(new RVitem1("05:00" ,0));
        lis1.add(new RVitem1("06:00" ,0));
        lis1.add(new RVitem1("07:00" ,0));
        lis1.add(new RVitem1("08:00" ,0));
        lis1.add(new RVitem1("09:00" ,0));
        lis1.add(new RVitem1("10:00" ,0));
        lis1.add(new RVitem1("11:00" ,0));
        lis1.add(new RVitem1("12:00" ,0));
        lis1.add(new RVitem1("13:00" ,0));
        lis1.add(new RVitem1("14:00" ,0));
        lis1.add(new RVitem1("15:00" ,0));
        lis1.add(new RVitem1("16:00" ,0));
        lis1.add(new RVitem1("17:00" ,0));
        lis1.add(new RVitem1("18:00" ,0));
        lis1.add(new RVitem1("19:00" ,0));
        lis1.add(new RVitem1("20:00" ,0));
        lis1.add(new RVitem1("21:00" ,0));
        lis1.add(new RVitem1("22:00" ,0));
        lis1.add(new RVitem1("23:00" ,0));
        lis1.add(new RVitem1("24:00" ,0));

        recyclerView1 = findViewById(R.id.recycler_view1);
        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView1 ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                            Toast.makeText(thirdScreen.this, "item seleced" + position, Toast.LENGTH_SHORT).show();
                            for (int i=0;i<lis1.size();i++) {
                                if(i!=position)
                                    lis1.get(i).setColor(0);

                            lis1.get(position).setColor(R.color.black);
                            myAdapter1.notifyDataSetChanged();
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        myAdapter1 = new MyAdapter1(this,lis1);
        recyclerView1.setAdapter(myAdapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        lis2=new ArrayList<RVitem1>();
        lis2.add(new RVitem1("00:00" ,0));
        lis2.add(new RVitem1("01:00" ,0));
        lis2.add(new RVitem1("02:00" ,0));
        lis2.add(new RVitem1("03:00" ,0));
        lis2.add(new RVitem1("04:00" ,0));
        lis2.add(new RVitem1("05:00" ,0));
        lis2.add(new RVitem1("06:00" ,0));
        lis2.add(new RVitem1("07:00" ,0));
        lis2.add(new RVitem1("08:00" ,0));
        lis2.add(new RVitem1("09:00" ,0));
        lis2.add(new RVitem1("10:00" ,0));
        lis2.add(new RVitem1("11:00" ,0));
        lis2.add(new RVitem1("12:00" ,0));
        lis2.add(new RVitem1("13:00" ,0));
        lis2.add(new RVitem1("14:00" ,0));
        lis2.add(new RVitem1("15:00" ,0));
        lis2.add(new RVitem1("16:00" ,0));
        lis2.add(new RVitem1("17:00" ,0));
        lis2.add(new RVitem1("18:00" ,0));
        lis2.add(new RVitem1("19:00" ,0));
        lis2.add(new RVitem1("20:00" ,0));
        lis2.add(new RVitem1("21:00" ,0));
        lis2.add(new RVitem1("22:00" ,0));
        lis2.add(new RVitem1("23:00" ,0));
        lis2.add(new RVitem1("24:00" ,0));

        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView2.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView2 ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                            Toast.makeText(thirdScreen.this, "item seleced" + position, Toast.LENGTH_SHORT).show();
                            for (int i=0;i<lis2.size();i++) {
                                if(i!=position)
                                    lis2.get(i).setColor(0);
                            lis2.get(position).setColor(R.color.black);
                            myAdapter2.notifyDataSetChanged();
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        myAdapter2 = new MyAdapter1(this,lis2);
        recyclerView2.setAdapter(myAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        bt1 = findViewById(R.id.update_button);


    }

    private void addSpinner() {
        List<String> categoryList = new ArrayList<>();
        Spinner categorySpinner = findViewById(R.id.spinner_category);
        categoryList.add("Choose Category");
        categoryList.add("Animal");
        categoryList.add("Arts");
        categoryList.add("Space");
        categoryList.add("Science");



        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ofra", "on Button click: " + categorySpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    private long convertStringtoEpoch(String mysDate) {
        String str = "2017-10-19 16:18:03.779";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return epoch;
    }
}