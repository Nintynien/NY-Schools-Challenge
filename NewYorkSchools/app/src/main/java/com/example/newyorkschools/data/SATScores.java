package com.example.newyorkschools.data;

public class SATScores {
    public String dbn;
    public String school_name;
    public String num_of_sat_test_takers;
    public String sat_critical_reading_avg_score;
    public String sat_math_avg_score;
    public String sat_writing_avg_score;

    public SATScores(String dbn,
                     String school_name,
                     String num_of_sat_test_takers,
                     String sat_critical_reading_avg_score,
                     String sat_math_avg_score,
                     String sat_writing_avg_score) {
        this.dbn = dbn;
        this.school_name = school_name;
        this.num_of_sat_test_takers = num_of_sat_test_takers;
        this.sat_critical_reading_avg_score = sat_critical_reading_avg_score;
        this.sat_math_avg_score = sat_math_avg_score;
        this.sat_writing_avg_score = sat_writing_avg_score;
    }
}