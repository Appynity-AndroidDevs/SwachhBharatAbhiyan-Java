package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.swachbharatabhiyanlibrary.repository.TokenRepo;

public class TokenViewModel extends ViewModel {
    private static final String TAG = "TokenViewModel";
    public MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
    public TokenRepo tokenRepo = TokenRepo.getInstance();

    public void init() {
        tokenRepo.getToken(new TokenRepo.iTokenResponseListener() {
            @Override
            public void onResponse(MutableLiveData<String> tokenResponse) {
                tokenLiveData.setValue(tokenResponse.getValue());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

}
