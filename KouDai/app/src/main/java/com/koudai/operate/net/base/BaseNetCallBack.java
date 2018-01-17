package com.koudai.operate.net.base;

public abstract class BaseNetCallBack<T> {
    public abstract void onSuccess(T paramT);

    public abstract void onFailure(String url, NetBase.ErrorType errorType, int errorCode);

    public void onLogout() {
    }

    public void onLogoutClick() {
    }
}
