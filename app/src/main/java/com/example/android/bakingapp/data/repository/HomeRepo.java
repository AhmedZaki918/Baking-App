package com.example.android.bakingapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.android.bakingapp.data.model.Recipe;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeRepo extends GlobalRepo {


    // Default constructor
    @Inject
    public HomeRepo(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    // Initialization
    private final MutableLiveData<List<Recipe>> mutableLiveData = new MutableLiveData<>();
    public CompositeDisposable compositeDisposable;


    // Getter for live data
    public MutableLiveData<List<Recipe>> getMutableLiveData() {
        return mutableLiveData;
    }


    // Displays data via Rxjava
    public void getData() {
        Single<List<Recipe>> observable =
                getApiInterface()
                        .get_recipe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        // Wrap observable with composite disposable
        compositeDisposable.add(observable.subscribe(mutableLiveData::setValue));
    }
}
