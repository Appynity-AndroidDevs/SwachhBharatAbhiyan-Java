package com.appynitty.swachbharatabhiyanlibrary.login.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.swachbharatabhiyanlibrary.login.repository.LoginRepository;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;

public class LoginViewModel extends ViewModel implements LoginRepository.LoginUserCallBacks {


    private LoginRepository loginRepository;
    private MutableLiveData<LoginDetailsPojo> loginDetailsSuccessLiveData;
    private MutableLiveData<Throwable> loginDetailsErrorLiveData;

    public LoginViewModel() {
        loginRepository = new LoginRepository(this);
        loginDetailsSuccessLiveData = new MutableLiveData<>();
        loginDetailsErrorLiveData = new MutableLiveData<>();
    }

    public void loginUser(LoginPojo loginPojo) {
        loginRepository.loginUser(loginPojo);
    }

    public MutableLiveData<LoginDetailsPojo> getLoginDetailsSuccessLiveData() {
        return loginDetailsSuccessLiveData;
    }

    public MutableLiveData<Throwable> getLoginDetailsErrorLiveData() {
        return loginDetailsErrorLiveData;
    }

    @Override
    public void onLoginResponseSuccess(LoginDetailsPojo loginDetailsPojo) {
        loginDetailsSuccessLiveData.setValue(loginDetailsPojo);
    }

    @Override
    public void onLoginResponseFailure(Throwable t) {
        loginDetailsErrorLiveData.setValue(t);
    }
}
