package com.koudai.operate.net.base;

public abstract class CallBack
{
  public abstract void onSuccess(String result, String url);

  public abstract void onFailure(String result, NetBase.ErrorType errorType, int errorCode);

  public void onLogout(){};

  public void onLogoutClick(){};
}
