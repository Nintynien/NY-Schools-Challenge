package com.example.newyorkschools.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.newyorkschools.R;
import com.example.newyorkschools.SchoolsViewModel;
import com.example.newyorkschools.data.SATScores;
import com.example.newyorkschools.data.School;
import com.example.newyorkschools.databinding.SchoolDetailBinding;

public class SchoolFragment extends Fragment {

    private static final String DBN_KEY = "DBN";
    String dbn;

    public static SchoolFragment newInstance(String dbn) {
        SchoolFragment schoolFragment = new SchoolFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DBN_KEY, dbn);
        schoolFragment.setArguments(bundle);
        return schoolFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SchoolDetailBinding binding = SchoolDetailBinding.inflate(inflater, container, false);
        Bundle args = getArguments();
        if (args != null) {
            dbn = args.getString(DBN_KEY);
        }
        binding.scoreMath.setText(getString(R.string.sat_score_math, getString(R.string.sat_score_loading)));
        binding.scoreWriting.setText(getString(R.string.sat_score_writing, getString(R.string.sat_score_loading)));
        binding.scoreReading.setText(getString(R.string.sat_score_reading, getString(R.string.sat_score_loading)));

        ViewModelStoreOwner viewModelStoreOwner = this.getActivity() != null ? this.getActivity() : this;
        SchoolsViewModel schoolsViewModel = new ViewModelProvider(viewModelStoreOwner).get(SchoolsViewModel.class);
        schoolsViewModel.schools.observe(getViewLifecycleOwner(), schools -> {
            String name = "";
            String description = "";
            String website = "";
            for (School school : schools) {
                if (school.dbn.equals(dbn)) {
                    name = school.school_name;
                    description = school.overview_paragraph;
                    website = school.website;
                    binding.name.setText(school.school_name);
                    binding.description.setText(school.overview_paragraph);
                    binding.website.setText(school.website);
                    break;
                }
            }
            binding.name.setText(name);
            binding.description.setText(description);
            binding.website.setText(website);
        });
        schoolsViewModel.scores.observe(getViewLifecycleOwner(), scores -> {
            String mathScore = getString(R.string.sat_score_missing);
            String readingScore = getString(R.string.sat_score_missing);
            String writingScore = getString(R.string.sat_score_missing);
            for (SATScores score : scores) {
                if (score.dbn.equals(dbn)) {
                    mathScore = score.sat_math_avg_score;
                    readingScore = score.sat_critical_reading_avg_score;
                    writingScore = score.sat_writing_avg_score;
                    break;
                }
            }
            binding.scoreMath.setText(getString(R.string.sat_score_math, mathScore));
            binding.scoreWriting.setText(getString(R.string.sat_score_writing, readingScore));
            binding.scoreReading.setText(getString(R.string.sat_score_reading, writingScore));
        });
        schoolsViewModel.loadSatScores();

        return binding.getRoot();
    }
}