package com.example.android.bakingapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.android.bakingapp.data.model.Recipe;
import com.example.android.bakingapp.data.network.APIService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeRepo {


    // Initialization
    private final MutableLiveData<List<Recipe>> mutableLiveData = new MutableLiveData<>();
    APIService apiService;
    public CompositeDisposable compositeDisposable;


    // Our constructor
    @Inject
    public HomeRepo(CompositeDisposable compositeDisposable, APIService apiService) {
        this.compositeDisposable = compositeDisposable;
        this.apiService = apiService;
    }


    public void getData() {
        Single<List<Recipe>> observable =
                apiService.getRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        // Wrap observable with composite disposable
        compositeDisposable.add(observable.subscribe(mutableLiveData::setValue));
    }


    // Getter for live data
    public MutableLiveData<List<Recipe>> getMutableLiveData() {
        return mutableLiveData;
    }
}