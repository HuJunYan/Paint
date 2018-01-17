package com.koudai.operate.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.koudai.operate.constant.Globparams;
import com.koudai.operate.net.base.NetCheck;
import com.koudai.operate.utils.Base64;
import com.koudai.operate.utils.LogUtil;
import com.koudai.operate.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketService extends Service implements Runnable {
    public final String IP = "101.201.154.89";
    public final int PORT = 9527;
    public Socket mSocketClient;
    public DataInputStream input = null;
    public DataOutputStream out = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new Thread(SocketService.this).start();
        }
    };

    public SocketService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {
        try {
            mSocketClient = new Socket(IP, PORT);
            input = new DataInputStream(mSocketClient.getInputStream());
            out = new DataOutputStream(mSocketClient.getOutputStream());
            byte[] contentByte = getSendData();
            out.write(contentByte, 0, contentByte.length);
            read("select_goods");
        } catch (IOException e) {
            if (mSocketClient != null) {
                LogUtil.d("ret", mSocketClient.isConnected() ? "Socket连接失败" : mSocketClient.isClosed() ? "Socket已关闭" : "Socket异常");
            }
            reStartSocket();
            e.printStackTrace();
        }
    }

    private byte[] getSendData() {
        String data = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", "select_goods");
            jsonObject.put("data", new JSONArray());
            data = Base64.encode(jsonObject.toString() + Utils.md5(jsonObject.toString() + "chengrongshangyangxiaojunlihuangde"));
            LogUtil.d("ret", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.getBytes();
    }

    private void read(String content) {
        try {
            if (mSocketClient == null || mSocketClient.isClosed()) { //符合条件就创建一个
                System.out.println("read getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()");
                reStartSocket();
                return;
            }

            while (true) {
                if (mSocketClient != null && mSocketClient.isConnected() && !mSocketClient.isClosed()) {
                    byte[] recvbuffer = new byte[1024 * 4];
                    int ret = input.read(recvbuffer);
                    if (ret > 0) {
                        String contentRecv = new String(recvbuffer, 0, ret, "UTF-8");
                        Intent intent = new Intent(Globparams.TCP_PRICE_CHANGE_ACTION);
                        intent.putExtra("data", contentRecv);
                        sendBroadcast(intent);
                    } else {
                        reStartSocket();
                        return;
                    }
                } else {
                    reStartSocket();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ret", "read客户端异常:" + e.getMessage());
            reStartSocket();
        }
    }

    public void stopGetDeviceStatusSocete() {
        if (mSocketClient != null) {
            try {
                mSocketClient.close();
                mSocketClient = null;
            } catch (IOException e) {
                mSocketClient = null;
                Log.d("ret", "stopGetDeviceStatusSocete 客户端 finally 异常:" + e.getMessage());
            }
        }
    }

    private void reStartSocket() {
        stopGetDeviceStatusSocete();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NetCheck.isNetConnected(getApplicationContext())) {
                        mHandler.sendEmptyMessage(2);
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        stopGetDeviceStatusSocete();
        super.onDestroy();
    }
}
