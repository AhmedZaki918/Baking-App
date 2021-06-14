package com.example.android.bakingapp.data.repository;

import com.example.android.bakingapp.data.network.APIClient;
import com.example.android.bakingapp.data.network.APIInterface;

public class GlobalRepo {

    private final APIInterface apiInterface = APIClient.getInstance().getApi();

    /**
     * Getter for api interface
     *
     * @return The interface
     */
    public APIInterface getApiInterface() {
        return apiInterface;
    }
}
