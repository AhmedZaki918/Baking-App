package com.example.android.bakingapp.data.di;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.bakingapp.data.repository.HomeRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
@InstallIn(SingletonComponent.class)
public class ClassModule {


    @Provides
    @Singleton
    public LinearLayoutManager provideLinearLayoutManager(@ApplicationContext Context context) {
        return new LinearLayoutManager(context);
    }


    @Provides
    @Singleton
    public HomeRepo providesHomeRepo() {
        return new HomeRepo(providesDisposable());
    }


    @Provides
    @Singleton
    public CompositeDisposable providesDisposable() {
        return new CompositeDisposable();
    }
}