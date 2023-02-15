package com.example.newyorkschools;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newyorkschools.data.SATScores;
import com.example.newyorkschools.data.School;
import com.example.newyorkschools.util.NycSchoolApi;

import java.util.List;

public class SchoolsViewModel extends ViewModel {

    public final MutableLiveData<List<School>> schools = new MutableLiveData<>();
    public final MutableLiveData<List<SATScores>> scores = new MutableLiveData<>();

    public SchoolsViewModel() {
        // On creation (app start), start loading the school list
        loadSchools();
    }

    // Helper for determining if the school list is loading
    public boolean isLoadingSchools() {
        return schools.getValue() == null || schools.getValue().isEmpty();
    }

    private void loadSchools() {
        // Only load school list once
        if (schools.getValue() != null && !schools.getValue().isEmpty()) return;
        NycSchoolApi.getSchools(schools);
    }

    public void loadSatScores() {
        // Only load SAT scores once
        if (scores.getValue() != null && !scores.getValue().isEmpty()) return;
        NycSchoolApi.getSatScores(scores);
    }
}
