package com.example.android.bakingapp.data.di;


import com.example.android.bakingapp.data.network.APIService;
import com.example.android.bakingapp.data.repository.HomeRepo;
import com.example.android.bakingapp.ui.adapter.Constant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {


    @Provides
    @Singleton
    public Retrofit provideClient() {
        return new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    public APIService provideService(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }


    @Provides
    @Singleton
    public HomeRepo providesHomeRepo(Retrofit retrofit) {
        return new HomeRepo(providesDisposable(), provideService(retrofit));
    }


    @Provides
    @Singleton
    public CompositeDisposable providesDisposable() {
        return new CompositeDisposable();
    }
}