package com.example.myapplication19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import com.github.mikephil.charting.charts.BarChart;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjHw extends AppCompatActivity {


    private int[] yData={70,30};
    private String[] xData={"x","y"};
    private int[] yDataTotal={210,140,70,80};
    private String[] xDataTotal={"sport","space","art","math"};
    private static final String[] WEEKS = {"WEEK1", "WEEK2", "WEEK3", "WEEK4", "WEEK5"};
    private static final String[] MONTH = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};


    private TextView txt_Course1,txt_Course2,txt_parent,txt_children;
    private PieChart pieChart,pieChart1,pieChart2,pieChart3;
    private com.github.mikephil.charting.charts.PieChart pieChartHours;

    private BarDataSet set1, set2, set3, set4, set5;

    private BarChart barChart;
    private ArrayList<String> colors;

    // variable for our bar data.
    private BarData barData;

    // variable for our bar data set.
    private BarDataSet barDataSet;

    // array list for storing entries.
    private ArrayList barEntriesArrayList;

    static  int pos=0;

    private BottomNavigationView bottomNavigationView;
    private RadioGroup radioGroup;
    private RadioButton week,month,year;



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8097/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.

    RetroFitAPI2 retrofitAPI2 = retrofit.create(RetroFitAPI2.class);



    /*
    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8095/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.

    RetroFitAPI2 retrofitAPI2 = retrofit2.create(RetroFitAPI2.class);
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_hw);
       /* pieChartHours=findViewById(R.id.idPieChartHours);
        pieChartHours.setRotationEnabled(false);
        pieChartHours.setUsePercentValues(false);
        pieChartHours.setHoleRadius(80f);
        pieChartHours.setTransparentCircleAlpha(0);
        pieChartHours.setCenterText("250");
        pieChartHours.setCenterTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieChartHours.setCenterTextSize(20);
        pieChartHours.getDescription().setEnabled(false);
        pieChartHours.setHighlightPerTapEnabled(false);

        */


       // addDataSetHours();

        getSupportActionBar().hide();

        txt_children=findViewById(R.id.children_perc);
        txt_Course1=findViewById(R.id.month);
        txt_Course2=findViewById(R.id.course_perc);
        txt_parent=findViewById(R.id.parent_perc);

        ///////////////////////////////////////////////
        pieChart = findViewById(R.id.piechart);
        pieChart1 = findViewById(R.id.piechart1);
        pieChart2 = findViewById(R.id.piechart2);
        pieChart3 = findViewById(R.id.piechart3);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        week=(RadioButton)findViewById(R.id.weekradioButton);
        month=(RadioButton)findViewById(R.id.monthradioButton);
        year=(RadioButton)findViewById(R.id.yearradioButton);

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pieChart.clearChart();
                pieChart1.clearChart();
                pieChart2.clearChart();
                pieChart3.clearChart();
                txt_Course1.setText("Weekly Activities in hour");
                Call<List<Integer>> call= retrofitAPI2.getAllNewCourses(7);
                call.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.size()-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Courses Added (+"+perc+")%";
                        txt_Course2.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                pieChart.startAnimation();


                Call<List<Integer>> call1= retrofitAPI2.getAllNewParents(7);
                call1.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart1.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart1.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.size()-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Parents Added (+"+perc+")%";
                        txt_parent.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart1.startAnimation();


                Call<List<Integer>> call2= retrofitAPI2.getAllNewKids(7);
                call2.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart2.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart2.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.size()-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Kids Added (+"+perc+")%";
                        txt_children.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart2.startAnimation();

                pieChart3.setInnerPaddingColor(0);

                Call<List<Integer>> call3= retrofitAPI2.getAllCoursesByCategoryper(7);
                call3.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call2, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(0))),
                                        Color.parseColor( "#FFFF90")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(1))),
                                        Color.parseColor( "#66BB6A")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(2))),
                                        Color.parseColor( "#EF5350")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(3))),
                                        Color.parseColor( "#29B6F6")));

                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call2, Throwable t) {
                    }
                });

                // To animate the pie chart
                pieChart3.startAnimation();
            }
        });
        week.performClick();
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.clearChart();
                pieChart1.clearChart();
                pieChart2.clearChart();
                pieChart3.clearChart();
                txt_Course1.setText("Monthly Activities in hour");
                Call<List<Integer>> call= retrofitAPI2.getAllNewCourses(35);
                call.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Courses Added (+"+perc+")%";
                        txt_Course2.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                pieChart.startAnimation();


                Call<List<Integer>> call1= retrofitAPI2.getAllNewParents(35);
                call1.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart1.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart1.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Parents Added (+"+perc+")%";
                        txt_parent.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart1.startAnimation();


                Call<List<Integer>> call2= retrofitAPI2.getAllNewKids(35);
                call2.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart2.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart2.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Kids Added (+"+perc+")%";
                        txt_children.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart2.startAnimation();
                pieChart3.setInnerPaddingColor(0);

                Call<List<Integer>> call3= retrofitAPI2.getAllCoursesByCategoryper(35);
                call3.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call2, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(0))),
                                        Color.parseColor( "#FFFF90")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(1))),
                                        Color.parseColor( "#66BB6A")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(2))),
                                        Color.parseColor( "#EF5350")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(3))),
                                        Color.parseColor( "#29B6F6")));

                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call2, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart3.startAnimation();

            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.clearChart();
                pieChart1.clearChart();
                pieChart2.clearChart();
                pieChart3.clearChart();
                txt_Course1.setText("Yearly Activities in hour");
                Call<List<Integer>> call= retrofitAPI2.getAllNewCourses(365);
                call.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc=s*(100/s1);
                        pieChart.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Courses Added (+"+perc+")%";
                        txt_Course2.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                pieChart.startAnimation();
                    Call<List<Integer>> call1= retrofitAPI2.getAllNewParents(365);
                call1.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart1.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart1.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Parents Added (+"+perc+")%";
                        txt_parent.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });

                // To animate the pie chart
                pieChart1.startAnimation();
                Call<List<Integer>> call2= retrofitAPI2.getAllNewKids(365);
                call2.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();


                        int s=responseFromAPI.get(0);
                        int s1=responseFromAPI.get(1);
                        int perc= s* (100/s1);
                        pieChart2.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.parseColor("#FFA726")));



                        pieChart2.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1-s)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Kids Added (+"+perc+")%";
                        txt_children.setText( f);
                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart2.startAnimation();
                pieChart3.setInnerPaddingColor(0);

                Call<List<Integer>> call3= retrofitAPI2.getAllCoursesByCategoryper(365);
                call3.enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call2, Response<List<Integer>> response) {
                        List<Integer> responseFromAPI = response.body();
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(0))),
                                        Color.parseColor( "#FFFF90")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(1))),
                                        Color.parseColor( "#66BB6A")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(2))),
                                        Color.parseColor( "#EF5350")));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(3))),
                                        Color.parseColor( "#29B6F6")));

                    }
                    @Override
                    public void onFailure(Call<List<Integer>> call2, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart3.startAnimation();

            }
        });
        colors=new ArrayList<>();
        colors.add("#FFFF90");
        colors.add("#66BB6A");
        colors.add("#EF5350");
        colors.add("#29B6F6");
        colors.add("#66BB6A");
        colors.add("#EF5350");




        BottomNavigationView bnv = findViewById(R.id.bottomNav);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();


        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment = null;
                Intent intent;
                switch(id){
                    case R.id.home:
          //              fragment = new HomeFragment();
                        intent=new Intent(ProjHw.this,Home.class);
                        startActivity(intent);
                        break;
                    case R.id.leaders:
            //            fragment = new LeadersFragment();
                        intent=new Intent(ProjHw.this,Leaders.class);
                        startActivity(intent);
                        break;
                    case R.id.users:
              //          fragment = new UsersFragment();
                        intent=new Intent(ProjHw.this,Users.class);
                        startActivity(intent);
                        break;
                    case R.id.courses:
                //        fragment = new CoursesFragment();
                        intent=new Intent(ProjHw.this,Courses.class);
                        startActivity(intent);
                        break;
                    case R.id.more:
                  //      fragment = new MoreFragment();
                        intent=new Intent(ProjHw.this,More.class);
                        startActivity(intent);
                        break;

                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });



        // initializing variable for bar chart.
        barChart = findViewById(R.id.barchart);

        float groupSpace = 0.5f; // space width between groups - each group is 5 categories
        float barSpace = 0.048f; //space between all the cols in 1 group
        float barWidth = 0.035f; //width of one col

        //parameter for how many group we have according to week,month,year int the BatChart
//        Integer numberOfGroups = MONTH.length;
//       Integer numberOfGroups = DAYS.length;
        Integer numberOfGroups = WEEKS.length;

        BarData data = createCharData((Integer) numberOfGroups);
        configureChartAppearance();
        prepareCharData(data);
        data.setBarWidth(barWidth);

        barChart.groupBars(0,groupSpace,barSpace);

    }

    private void prepareCharData(BarData data) {
        data.setValueTextSize(0f);
        barChart.setData(data);
        barChart.invalidate();
    }

    private void configureChartAppearance() {
        barChart.getDescription().setEnabled(true);
        barChart.setDrawValueAboveBar(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(WEEKS)); //for week number labels
        //       xAxis.setValueFormatter(new IndexAxisValueFormatter(DAYS)); //for days number labels
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(MONTH)); //for days number labels


        xAxis.setCenterAxisLabels(true); //put te "WEEK" under the cols of this specific WEEK
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //location the "WEEK" under the barChart
        YAxis axisLeft = barChart.getAxisLeft();


        YAxis axisRight = barChart.getAxisRight();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(-20);
    }



    private BarData createCharData(Integer numberOfGroups) {
        // int j =5;


        //arrayList for keeping the number of children
        ArrayList<BarEntry> value_1 = new ArrayList<>();
        ArrayList<BarEntry> value_2 = new ArrayList<>();
        ArrayList<BarEntry> value_3 = new ArrayList<>();
        ArrayList<BarEntry> value_4 = new ArrayList<>();
        ArrayList<BarEntry> value_5 = new ArrayList<>();

        if(numberOfGroups >= 5) {

            for (int i = 0; i < numberOfGroups; i++) { //determine the number of groups
                if (i == 0) {
                    //instead of static number y: put the function from the repository which return all the kids in WEEK1
                    //week1 = 35 - 28 days
                    value_1.add(new BarEntry(i, 10));//determine the number of children in each col (the second pos)
                    value_2.add(new BarEntry(i, 10));
                    value_3.add(new BarEntry(i, 10));
                    value_4.add(new BarEntry(i, 10));
                    value_5.add(new BarEntry(i, 10));
                }
                if (i == 1) {
                    //instead of static number y: put the function from the repository which return all the kids in WEEK2
                    //week1 = 28 - 21 days

                    value_1.add(new BarEntry(i, 20));//determine the number of children in each col (the second pos)
                    value_2.add(new BarEntry(i, 21));
                    value_3.add(new BarEntry(i, 22));
                    value_4.add(new BarEntry(i, 23));
                    value_5.add(new BarEntry(i, 30));
                }

                if (i == 2) {
                    //instead of static number y: put the function from the repository which return all the kids in WEEK3
                    //week1 = 21 - 14 days

                    value_1.add(new BarEntry(i, 30));//determine the number of children in each col (the second pos)
                    value_2.add(new BarEntry(i, 32));
                    value_3.add(new BarEntry(i, 32));
                    value_4.add(new BarEntry(i, 34));
                    value_5.add(new BarEntry(i, 35));
                }

                if (i == 3) {
                    //instead of static number y: put the function from the repository which return all the kids in WEEK4
                    //week1 = 14 - 7 days

                    value_1.add(new BarEntry(i, 40));//determine the number of children in each col (the second pos)
                    value_2.add(new BarEntry(i, 42));
                    value_3.add(new BarEntry(i, 44));
                    value_4.add(new BarEntry(i, 46));
                    value_5.add(new BarEntry(i, 48));
                }

                if (i == 4) { //for month = 5 cols
                    //instead of static number y: put the function from the repository which return all the kids in WEEK5
                    //week1 = 7 - 0 days

                    value_1.add(new BarEntry(i, 50));//determine the number of children in each col (the second pos)
                    value_2.add(new BarEntry(i, 50));
                    value_3.add(new BarEntry(i, 56));
                    value_4.add(new BarEntry(i, 58));
                    value_5.add(new BarEntry(i, 50));
                }
                if(numberOfGroups >= 7){ //for week = 7 cols
                    if (i == 5) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK4
                        //week1 = 14 - 7 days

                        value_1.add(new BarEntry(i, 10));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 42));
                        value_3.add(new BarEntry(i, 44));
                        value_4.add(new BarEntry(i, 46));
                        value_5.add(new BarEntry(i, 60));
                    }

                    if (i == 6) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK5
                        //week1 = 7 - 0 days

                        value_1.add(new BarEntry(i, 50));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 50));
                        value_3.add(new BarEntry(i, 56));
                        value_4.add(new BarEntry(i, 58));
                        value_5.add(new BarEntry(i, 50));
                    }

                }
                if(numberOfGroups == 12) { //for year = 12 cols
                    if (i == 7) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK4
                        //week1 = 14 - 7 days

                        value_1.add(new BarEntry(i, 10));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 42));
                        value_3.add(new BarEntry(i, 44));
                        value_4.add(new BarEntry(i, 46));
                        value_5.add(new BarEntry(i, 60));
                    }

                    if (i == 8) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK5
                        //week1 = 7 - 0 days

                        value_1.add(new BarEntry(i, 50));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 50));
                        value_3.add(new BarEntry(i, 56));
                        value_4.add(new BarEntry(i, 58));
                        value_5.add(new BarEntry(i, 50));
                    }
                    if (i == 9) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK4
                        //week1 = 14 - 7 days

                        value_1.add(new BarEntry(i, 10));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 42));
                        value_3.add(new BarEntry(i, 44));
                        value_4.add(new BarEntry(i, 46));
                        value_5.add(new BarEntry(i, 60));
                    }

                    if (i == 10) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK5
                        //week1 = 7 - 0 days

                        value_1.add(new BarEntry(i, 50));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 50));
                        value_3.add(new BarEntry(i, 56));
                        value_4.add(new BarEntry(i, 58));
                        value_5.add(new BarEntry(i, 50));
                    }
                    if (i == 11) {
                        //instead of static number y: put the function from the repository which return all the kids in WEEK5
                        //week1 = 7 - 0 days

                        value_1.add(new BarEntry(i, 50));//determine the number of children in each col (the second pos)
                        value_2.add(new BarEntry(i, 50));
                        value_3.add(new BarEntry(i, 56));
                        value_4.add(new BarEntry(i, 58));
                        value_5.add(new BarEntry(i, 50));
                    }
                }

            }
        }

        //fill the number of children in each col (the second pos) and set the color
        set1 = new BarDataSet(value_1, "spaces");
        set1.setColor(Color.BLUE);
        set2 = new BarDataSet(value_2, "Animal");
        set2.setColor(Color.YELLOW);
        set3 = new BarDataSet(value_3, "Art");
        set3.setColor(Color.GREEN);
        set4 = new BarDataSet(value_4, "Sience");
        set4.setColor(Color.RED);
        set5 = new BarDataSet(value_5, "Music");
        set5.setColor(Color.BLACK);

        ArrayList<IBarDataSet> dataSets_1 = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets_2 = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets_3 = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets_4 = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets_5 = new ArrayList<>();

        dataSets_1.add(set1);
        dataSets_2.add(set2);
        dataSets_3.add(set3);
        dataSets_4.add(set4);
        dataSets_5.add(set5);

        BarData data = new BarData(set1,set2,set3,set4, set5);
        return data;

    }

}

     /*
        Call<List<Category>> call3=retrofitAPI.getAllCategories();
        call3.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call2, Response<List<Category>> response) {
                List<Category> responseFromAPI = response.body();
                List<String> names=new ArrayList<String>();
                int s=responseFromAPI.size();
                for (int i=0 ;i<s;i++)
                    names.add(responseFromAPI.get(i).getName());

                Call<List<Course1>> call4;
                for (int j=0;j<s;j++) {
                    call4 = retrofitAPI.getAllCoursesByCategory(names.get(j));

                    call4.enqueue(new Callback<List<Course1>>() {

                        @Override
                        public void onResponse(Call<List<Course1>> call3, Response<List<Course1>> response) {
                            List<Course1> responseFromAPI = response.body();

                            pos++;
                            pieChart3.addPieSlice(
                                    new PieModel(
                                            "Python",
                                            Integer.parseInt(Integer.toString(responseFromAPI.size())),
                                            Color.parseColor( colors.get(pos))));
                        }

                        @Override
                        public void onFailure(Call<List<Course1>> call3, Throwable t) {
                        }
                    });

                }

            }
            @Override
            public void onFailure(Call<List<Category>> call2, Throwable t) {
            }
        });

         */

