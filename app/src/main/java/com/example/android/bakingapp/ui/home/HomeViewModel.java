package com.example.android.bakingapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.data.repository.HomeRepo;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends AndroidViewModel {

    HomeRepo repo;

    // Constructor for our class
    @Inject
    public HomeViewModel(@NonNull Application application, HomeRepo repo) {
        super(application);
        this.repo = repo;
    }

    // @return mutable live data of recipes
    public MutableLiveData<List<Recipe>> getResponse() {
        return repo.getMutableLiveData();
    }

    // Init get request of recipes
    public void initRequest() {
        repo.getData();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repo.compositeDisposable.clear();
    }
}
