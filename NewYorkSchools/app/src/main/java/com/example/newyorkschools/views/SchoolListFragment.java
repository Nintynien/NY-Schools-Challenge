package com.example.newyorkschools.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newyorkschools.R;
import com.example.newyorkschools.viewmodels.SchoolsViewModel;
import com.example.newyorkschools.data.School;
import com.example.newyorkschools.databinding.SchoolListBinding;

import java.util.Collections;

public class SchoolListFragment extends Fragment implements RecyclerAdapter.SchoolClickHandler {

    ProgressBar loading;
    RecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new RecyclerAdapter(Collections.emptyList(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SchoolListBinding binding = SchoolListBinding.inflate(inflater, container, false);
        loading = binding.loading;
        RecyclerView recyclerView = binding.recyclerList;
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ViewModelStoreOwner viewModelStoreOwner = this.getActivity() != null ? this.getActivity() : this;
        SchoolsViewModel schoolsViewModel = new ViewModelProvider(viewModelStoreOwner).get(SchoolsViewModel.class);
        setLoading(schoolsViewModel.isLoadingSchools());
        schoolsViewModel.schools.observe(getViewLifecycleOwner(), schools -> {
            recyclerAdapter.updateSchools(schools);
            setLoading(schoolsViewModel.isLoadingSchools());
        });

        return binding.getRoot();
    }

    private void setLoading(boolean loading) {
        this.loading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSchoolClicked(School school) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, SchoolFragment.newInstance(school.dbn))
                    .addToBackStack(null)
                    .commit();
        }
    }
}