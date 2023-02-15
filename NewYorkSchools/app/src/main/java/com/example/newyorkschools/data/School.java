package com.example.newyorkschools.data;

public class School {
    public String dbn;
    public String school_name;
    public String overview_paragraph;
    public String website;
    public int test_takers;
    public int math_score;
    public int reading_score;
    public int writing_score;

    public School(String dbn,
                  String name,
                  String overview,
                  String website,
                  int test_takers,
                  int math_score,
                  int reading_score,
                  int writing_score) {
        this.dbn = dbn;
        this.school_name = name;
        this.overview_paragraph = overview;
        this.website = website;
        this.test_takers = test_takers;
        this.math_score = math_score;
        this.reading_score = reading_score;
        this.writing_score = writing_score;
    }
}
