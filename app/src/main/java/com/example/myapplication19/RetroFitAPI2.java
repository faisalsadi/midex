package com.example.myapplication19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroFitAPI2 {



    //get courses from server
    @GET("getallCourses")
    Call<List<Course>> getAllCourses();


    @GET("getSpacesCourses")
    Call<List<Course>> getSpacesCourses();

    @GET("getAnimalsCourses")
    Call <List<Course>> getAnimalsCourses();
    @GET("getArtsCourses")
    Call <List<Course>> getArtsCourses();
    @DELETE("deleteCourse/{name}")
    Call <List<Course>> deleteCourse(@Path("name") String name);




    @GET("getallCourses1")
    Call<List<Course1>> getAllCourses1();
    @GET("getallKids")
    Call <List<Kid>> getAllKids();
    @GET("getallParents")
    Call <List<Parent>> getAllParents();
    @GET("getallCategories")
    Call<List<Category>> getAllCategories();

    @GET("getallnewCourses/{day}")
    Call <List<Integer>> getAllNewCourses(@Path("day") int day);
    @GET("getallnewKids/{day}")
    Call <List<Integer>> getAllNewKids(@Path("day") int day);
    @GET("getallnewParents/{day}")
    Call <List<Integer>> getAllNewParents(@Path("day") int day);
    @GET("getallCoursesByCategory/{name}")
    Call <List<Course1>> getAllCoursesByCategory(@Path("name") String name);
    @GET("getallCoursesByCategoryper/{day}")
    Call <List<Integer>> getAllCoursesByCategoryper(@Path("day") int day);








    @GET("/GetMeetingsStat/{time}")
    Call <int[]> getCoursesForDate(@Path("time") int time);
    @GET("/GetParentsStat/{time}")
    Call <int[]> getParentsForDate(@Path("time") int time);
    @GET("/GetActiveKidsStat/{time}")
    Call <int[]> getKidsForDate(@Path("time") int time);
    @GET("/GetCategoriesInfo/{time}")
    Call <ArrayList<Integer>> getCategoriesPercentage(@Path("time") int time);
    @GET("/GetCategoriesTrend/{time}")
    Call < HashMap<Integer,HashMap<Integer, Integer>>> getTrend(@Path("time") int time);





}
