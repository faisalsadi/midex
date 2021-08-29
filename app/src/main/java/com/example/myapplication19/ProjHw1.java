package com.example.myapplication19;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjHw1 extends AppCompatActivity {

    private int[] yData={70,30};
    private String[] xData={"x","y"};
    private int[] yDataTotal={210,140,70,80};
    private String[] xDataTotal={"sport","space","art","math"};
    private static final String[] WEEKS = {"WEEK1", "WEEK2", "WEEK3", "WEEK4", "WEEK5"};
    private static final String[] MONTH = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};


    Category1 c1=new Category1 ("Animals", "]");
    Category1 c2=new Category1 ("Spaces", "]");
    Category1 c3=new Category1 ("School", "]");
    Category1 c4=new Category1 ("Plants", "]");
    Category1 c5=new Category1 ("Math", "]");

    private TextView txt_Course1,txt_Course2,txt_parent,txt_children;
    private PieChart pieChart,pieChart1,pieChart2,pieChart3;
    private com.github.mikephil.charting.charts.PieChart pieChartHours;

    private BarDataSet set1, set2, set3, set4, set5;

    private BarChart barChart;
    private ArrayList<Integer> colors;

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



    float groupSpace = 0.5f; // space width between groups - each group is 5 categories
    float barSpace = 0.048f; //space between all the cols in 1 group
    float barWidth = 0.035f; //width of one col

    //parameter for how many group we have according to week,month,year int the BatChart
    Integer numberOfGroups2 = MONTH.length;
    Integer numberOfGroups1 = DAYS.length;
    Integer numberOfGroups = WEEKS.length;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8097/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.

    RetroFitAPI2 retrofitAPI2 = retrofit.create(RetroFitAPI2.class);



    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8095/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.

    RetroFitAPI2 retrofitAPI3 = retrofit2.create(RetroFitAPI2.class);


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

        // initializing variable for bar chart.
        barChart = findViewById(R.id.barchart);



        c1.setId("id1");
        c2.setId("id0");
        c3.setId("id3");
        c4.setId("id2");
        c5.setId("id4");

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


        colors=new ArrayList<>();

        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.BLACK);

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pieChart.clearChart();
                pieChart1.clearChart();
                pieChart2.clearChart();
                pieChart3.clearChart();
                txt_Course1.setText("Weekly Activities in hour");
                Call<int []> call= retrofitAPI3.getCoursesForDate(1);
                call.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();

                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));

                        pieChart.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Courses Added (+"+responseFromAPI[2]+")%";
                        txt_Course2.setText(f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });
                pieChart.startAnimation();


                Call<int []> call1= retrofitAPI3.getParentsForDate(1);
                call1.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart1.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart1.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Parents Added (+"+responseFromAPI[2]+")%";
                        txt_parent.setText( f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });

                // To animate the pie chart
                pieChart1.startAnimation();


                Call<int []> call2= retrofitAPI3.getKidsForDate(1);
                call2.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart2.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart2.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Kids Added (+"+responseFromAPI[2]+")%";
                        txt_children.setText( f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart2.startAnimation();

                pieChart3.setInnerPaddingColor(0);

                Call<ArrayList<Integer>> call3= retrofitAPI3.getCategoriesPercentage(1);
                call3.enqueue(new Callback<ArrayList<Integer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Integer>> call2, Response<ArrayList<Integer>> response) {
                        ArrayList<Integer> responseFromAPI = response.body();



                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(0))),
                                        Color.BLUE));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(1))),
                                        Color.YELLOW));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(2))),
                                        Color.GREEN));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(3))),
                                        Color.RED));
                        //pieChart3.addPieSlice(
                          //      new PieModel(
                            //            "Python",
                              //          Integer.parseInt(Integer.toString(responseFromAPI.get(4))),
                                //        Color.BLACK));

                    }
                    @Override
                    public void onFailure(Call<ArrayList<Integer>> call2, Throwable t) {
                    }
                });

                // To animate the pie chart
                pieChart3.startAnimation();

                BarData data = createCharData(7);
                configureChartAppearance(DAYS);
                prepareCharData(data);
                data.setBarWidth(barWidth);
                barChart.groupBars(0,groupSpace,barSpace);


                Call<HashMap<Integer,HashMap<Integer, Integer>>> call4= retrofitAPI3.getTrend(1);
                call4.enqueue(new Callback<HashMap<Integer, HashMap<Integer, Integer>>>() {
                    @Override
                    public void onResponse(Call<HashMap<Integer, HashMap<Integer, Integer>>> call, Response<HashMap<Integer, HashMap<Integer, Integer>>> response) {
                        HashMap<Integer, HashMap<Integer, Integer>> responseFromAPI=response.body();

                        /*
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
                                    value_1.add(new BarEntry(i, responseFromAPI.get(i).get(i)));//determine the number of children in each col (the second pos)
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


                        /*BarData data=createCharData(5,responseFromAPI);


                        configureChartAppearance(WEEKS);
                        prepareCharData(data);
                        data.setBarWidth(barWidth);
                        barChart.groupBars(0,groupSpace,barSpace);
                         */

                    }

                    @Override
                    public void onFailure(Call<HashMap<Integer, HashMap<Integer, Integer>>> call, Throwable t) {

                    }
                });



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
                Call<int []> call= retrofitAPI3.getCoursesForDate(2);
                call.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Courses Added (+"+responseFromAPI[2]+")%";
                        txt_Course2.setText(f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });
                pieChart.startAnimation();


                Call<int []> call1= retrofitAPI3.getParentsForDate(2);
                call1.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart1.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart1.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Parents Added (+"+responseFromAPI[2]+")%";
                        txt_parent.setText( f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });

                // To animate the pie chart
                pieChart1.startAnimation();

                Call<int []> call2= retrofitAPI3.getKidsForDate(2);
                call2.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart2.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart2.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Kids Added (+"+responseFromAPI[2]+")%";
                        txt_children.setText( f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart2.startAnimation();

                pieChart3.setInnerPaddingColor(0);

                Call<ArrayList<Integer>> call3= retrofitAPI3.getCategoriesPercentage(2);
                call3.enqueue(new Callback<ArrayList<Integer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Integer>> call2, Response<ArrayList<Integer>> response) {
                        ArrayList<Integer> responseFromAPI = response.body();



                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(0))),
                                        Color.BLUE));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(1))),
                                        Color.YELLOW));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(2))),
                                        Color.GREEN));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(3))),
                                        Color.RED));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(4))),
                                        Color.BLACK));


                    }
                    @Override
                    public void onFailure(Call<ArrayList<Integer>> call2, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart3.startAnimation();




                BarData data = createCharData(5);
                configureChartAppearance(WEEKS);
                prepareCharData(data);
                data.setBarWidth(barWidth);
                barChart.groupBars(0,groupSpace,barSpace);

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
                Call<int []> call= retrofitAPI3.getCoursesForDate(3);
                call.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Courses Added (+"+responseFromAPI[2]+")%";
                        txt_Course2.setText(f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });
                pieChart.startAnimation();

                Call<int []> call1= retrofitAPI3.getParentsForDate(3);
                call1.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart1.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart1.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Parents Added (+"+responseFromAPI[2]+")%";
                        txt_parent.setText( f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });

                // To animate the pie chart
                pieChart1.startAnimation();
                Call<int []> call2= retrofitAPI3.getKidsForDate(3);
                call2.enqueue(new Callback<int []>() {
                    @Override
                    public void onResponse(Call<int []> call, Response<int []> response) {
                        int [] responseFromAPI = response.body();


                        int s=responseFromAPI[0];
                        int s1=responseFromAPI[1];
                        int perc= s* (100/s1);
                        pieChart2.addPieSlice(
                                new PieModel(
                                        "R",
                                        Integer.parseInt(Integer.toString(s)),
                                        Color.BLACK));

                        // Color.BLACK));


                        pieChart2.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(s1)),
                                        Color.parseColor("#66BB6A")));
                        String f=s+" Kids Added (+"+responseFromAPI[2]+")%";
                        txt_children.setText( f);
                    }
                    @Override
                    public void onFailure(Call<int []> call, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart2.startAnimation();

                pieChart3.setInnerPaddingColor(0);

                Call<ArrayList<Integer>> call3= retrofitAPI3.getCategoriesPercentage(3);
                call3.enqueue(new Callback<ArrayList<Integer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Integer>> call2, Response<ArrayList<Integer>> response) {
                        ArrayList<Integer> responseFromAPI = response.body();

                        /*
                        colors.add(Color.BLUE);
                        colors.add(Color.YELLOW);
                        colors.add(Color.GREEN);
                        colors.add(Color.RED);
                        colors.add(Color.BLACK);

                         */


                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(0))),
                                        Color.BLUE));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(1))),
                                        Color.YELLOW));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(2))),
                                        Color.GREEN));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(3))),
                                        Color.RED));
                        pieChart3.addPieSlice(
                                new PieModel(
                                        "Python",
                                        Integer.parseInt(Integer.toString(responseFromAPI.get(4))),
                                        Color.BLACK));

                    }
                    @Override
                    public void onFailure(Call<ArrayList<Integer>> call2, Throwable t) {
                    }
                });
                // To animate the pie chart
                pieChart3.startAnimation();


                BarData data = createCharData(12);
                configureChartAppearance(MONTH);
                prepareCharData(data);
                data.setBarWidth(barWidth);
                barChart.groupBars(0,groupSpace,barSpace);

            }
        });

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
                        fragment = new HomeFragment();
                        //intent=new Intent(ProjHw1.this,Home.class);
                        //startActivity(intent);
                        break;
                    case R.id.leaders:
                        fragment = new LeadersFragment();
                        //intent=new Intent(ProjHw1.this,Leaders.class);
                        //startActivity(intent);
                        break;
                    case R.id.users:
                        fragment = new UsersFragment();
                        //intent=new Intent(ProjHw1.this,Users.class);
                        //startActivity(intent);
                        break;
                    case R.id.courses:
                        fragment = new CoursesFragment();
                        //intent=new Intent(ProjHw1.this,Courses.class);
                        //startActivity(intent);
                        break;
                    case R.id.more:
                        fragment = new MoreFragment();
                        //intent=new Intent(ProjHw1.this,More.class);
                        //startActivity(intent);
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });



/*

        BarData data = createCharData((Integer) numberOfGroups);

        configureChartAppearance(WEEKS);
        prepareCharData(data);
        data.setBarWidth(barWidth);
        barChart.groupBars(0,groupSpace,barSpace);


 */
    }

    private void prepareCharData(BarData data) {
        data.setValueTextSize(0f);
        barChart.setData(data);
        barChart.invalidate();
    }

    private void configureChartAppearance(String[] n) {
        barChart.getDescription().setEnabled(true);
        barChart.setDrawValueAboveBar(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(n)); //for week number labels
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



    private BarData createCharData(Integer numberOfGroups)//,HashMap<Integer,HashMap<Integer, Integer>> hsh) {

    {

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

