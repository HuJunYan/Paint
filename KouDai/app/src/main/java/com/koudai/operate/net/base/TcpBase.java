package com.koudai.operate.net.base;

import android.util.Log;

import com.koudai.operate.utils.Base64;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
//
public class TcpBase {
////    public static void startSocket(final String content, final CallBack callBack) {
////        new Thread(new Runnable() {
////
////            @Override
////            public void run() {
////                write(content, callBack);
////            }
////        }).start();
////    }
////
////    public static final String IP_ADDR = "101.201.154.89";// 服务器地址
////    public static final int PORT = 7777;// 服务器端口号
////    public static final String KEY = "keys";// 协议的key
////    public static final int RESERVED = 100;// 预留字段
////    public static Socket getDeviceStatusSocket;
////    public static DataInputStream input = null;
////    public static DataOutputStream out = null;
////
////    private static void write(String content, CallBack callBack) {
////        try {
////            if (getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()) { //符合条件就创建一个
////                System.out.println("write getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()");
////                // 创建一个流套接字并将其连接到指定主机上的指定端口号
////                try {
//////    			getDeviceStatusSocket = new Socket(Utils.TCP_IP_ADDR, Utils.TCP_PORT);
////                    getDeviceStatusSocket = new Socket(IP_ADDR, PORT);
////                    // 读取服务器端数据
////                    input = new DataInputStream(getDeviceStatusSocket.getInputStream());
////                    // 向服务器端发送数据
////                    out = new DataOutputStream(getDeviceStatusSocket.getOutputStream());
////                } catch (UnknownHostException e) {
////                    e.printStackTrace();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////
////            byte[] keyByte = KEY.getBytes();
////            if (keyByte.length != 4) {
////                Log.d("ret", "key is incorrect!");
////                return;
////            }
////
////            byte[] contentByte = content.getBytes();
////            Log.d("ret", "content = " + content);
////            out.write(contentByte, 0, contentByte.length);
////
//////			for (int i = 0 ; i < totalByte.length ; i++) {
//////				System.out.println(Integer.toHexString(totalByte[i]));
//////			}
////            read(content, callBack);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////            Log.e("ret", "write客户端异常:" + e.getMessage());
////        } finally {
////            if (getDeviceStatusSocket != null) {
////                try {
////                    getDeviceStatusSocket.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    getDeviceStatusSocket = null;
////                    Log.e("ret", "write客户端 finally 异常:" + e.getMessage());
////                }
////            }
////        }
////    }
////
////    private static String getBashContent(String content) {
////
////        return content;
////    }
////
////    private static void read(String content, CallBack callBack) {
////        try {
////            if (getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()) { //符合条件就创建一个
////                System.out.println("read getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()");
////                return;
////            }
////
////            byte[] recvbuffer = new byte[2048];
////
////            while (true) {
////                Thread.sleep(100);
////                int ret = input.read(recvbuffer);
////
////                // 如接收到 "1" 则断开连接
////                if (-1 == ret) {
////                    Log.d("ret", "read客户端将关闭连接");
////                    Thread.sleep(500);
////                    stopGetDeviceStatusSocete();
////                    return;
////                }
////
////                String contentRecv = new String(recvbuffer, "UTF-8");
////                Log.d("ret", "reveivedata: content = " + contentRecv);
////
//////                if (GsonUtil.isSuccess(contentRecv)) {
////                callBack.onSuccess(contentRecv, content);
//////                } else {
//////                    callBack.onFailure(contentRecv, NetBase.ErrorType.ERROR, GsonUtil.getErrorCode(content));
//////                }
////            }
////        } catch (Exception e) {
////            callBack.onFailure("style of result data is incorrect", NetBase.ErrorType.ERROR, 1);
////            e.printStackTrace();
////            Log.d("ret", "read客户端异常:" + e.getMessage());
////        } finally {
////            if (getDeviceStatusSocket != null) {
////                try {
////                    getDeviceStatusSocket.close();
////                } catch (IOException e) {
////                    getDeviceStatusSocket = null;
////                    e.printStackTrace();
////                    Log.d("ret", "read客户端 finally 异常:" + e.getMessage());
////                }
////            }
////        }
////    }
//
//    public static void startSocket(final String content, final int cmd, final CallBack callBack){
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                write(content, cmd, callBack);
//            }
//        } ).start();
//    }

    public static final String IP_ADDR = "101.201.154.89";// 服务器地址
    public static final int PORT = 7777;// 服务器端口号
    public static final String KEY = "keys";// 协议的key
    public static final int RESERVED = 0;// 预留字段
    public static Socket getDeviceStatusSocket;
    public static DataInputStream input = null;
    public static DataOutputStream out = null;
    private static void write(String content, int cmd, CallBack callBack) {

        try {
            if (getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()) { //符合条件就创建一个
                System.out.println("write getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()");
                // 创建一个流套接字并将其连接到指定主机上的指定端口号
                try {
//    			getDeviceStatusSocket = new Socket(Utils.TCP_IP_ADDR, Utils.TCP_PORT);
                    getDeviceStatusSocket = new Socket(IP_ADDR, PORT);
                    // 读取服务器端数据
                    input = new DataInputStream(getDeviceStatusSocket.getInputStream());
                    // 向服务器端发送数据
                    out = new DataOutputStream(getDeviceStatusSocket.getOutputStream());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            byte[] keyByte = KEY.getBytes();
            if (keyByte.length != 4) {
                Log.d("ret", "key is incorrect!");
                return;
            }

            String base64Content = Base64.encode(content);
            byte [] contentByte = base64Content.getBytes();
            int totalLength = contentByte.length + 4*4;
            byte [] totalByte = new byte[totalLength];
            byte [] lengthByte = intToBytes2(totalLength);
            byte [] cmdByte = intToBytes2(cmd);
            byte [] reservedByte = intToBytes2(RESERVED);
            Log.d("ret", "senddata: key = " + KEY + "; length = " + totalLength + "; cmd = " + cmd +"; content = " + content);
            System.arraycopy(keyByte, 0, totalByte, 0 , keyByte.length);
            System.arraycopy(lengthByte, 0, totalByte, 4 , lengthByte.length);
            System.arraycopy(cmdByte, 0, totalByte, 8 , cmdByte.length);
            System.arraycopy(reservedByte, 0, totalByte, 12 , reservedByte.length);
            System.arraycopy(contentByte, 0, totalByte, 16 , contentByte.length);

            out.write(totalByte, 0, totalByte.length);

			for (int i = 0 ; i < totalByte.length ; i++) {
				System.out.println(Integer.toHexString(totalByte[i]));
			}
            read(callBack);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ret", "write客户端异常:" + e.getMessage());
        } finally {
            if (getDeviceStatusSocket != null) {
                try {
                    getDeviceStatusSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    getDeviceStatusSocket = null;
                    Log.e("ret", "write客户端 finally 异常:" + e.getMessage());
                }
            }
        }
    }

    private static String getBashContent(String content) {

        return content;
    }

    private static void read(CallBack callBack) {
        try {
            if (getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()) { //符合条件就创建一个
                System.out.println("read getDeviceStatusSocket == null || getDeviceStatusSocket.isClosed()");
                return;
            }

            byte[] recvbuffer = new byte[2048];

            while (true) {
                Thread.sleep(100);
                int ret = input.read(recvbuffer);

                // 如接收到 "1" 则断开连接
                if (-1 == ret) {
                    Log.d("ret", "read客户端将关闭连接");
                    Thread.sleep(500);
                    stopGetDeviceStatusSocete();
                    return;
                }

                byte[] keyByte = new byte[4];
                byte[] lengthByte = new byte[4];
                byte[] cmdByte = new byte[4];
                byte[] reservedByte = new byte[4];
                int contentLength = recvbuffer.length - 16;
                byte[] contentByte = new byte[contentLength];
                System.arraycopy(recvbuffer, 0, keyByte, 0 , 4);
                System.arraycopy(recvbuffer, 4, lengthByte, 0 , 4);
                System.arraycopy(recvbuffer, 8, cmdByte, 0 , 4);
                System.arraycopy(recvbuffer, 12, reservedByte, 0, 4);
                System.arraycopy(recvbuffer, 16, contentByte, 0, contentLength);
                String key = new String(keyByte, "UTF-8");
                int length = byte2int(lengthByte);
                int cmd = byte2int(cmdByte);
                String content = Base64.decode(new String(contentByte, "UTF-8"));
//                String content = new String(decodeContent, "UTF-8");
                Log.d("ret", "reveivedata: key = " + key + "; length = " + length + "; cmd = " + cmd + "; content = " + content);

                if (GsonUtil.isSuccess(content)) {
                    callBack.onSuccess(content, cmd+"");
                } else {
                    callBack.onFailure(content, NetBase.ErrorType.ERROR, GsonUtil.getErrorCode(content));
                }
            }
        } catch (Exception e) {
            callBack.onFailure("style of result data is incorrect", NetBase.ErrorType.ERROR, 1);
            e.printStackTrace();
            Log.d("ret", "read客户端异常:" + e.getMessage());
        } finally {
            if (getDeviceStatusSocket != null) {
                try {
                    getDeviceStatusSocket.close();
                } catch (IOException e) {
                    getDeviceStatusSocket = null;
                    e.printStackTrace();
                    Log.d("ret", "read客户端 finally 异常:" + e.getMessage());
                }
            }
        }
    }

    public static void stopGetDeviceStatusSocete() {
        if (getDeviceStatusSocket != null) {
            try {
                getDeviceStatusSocket.close();
                getDeviceStatusSocket = null;
            } catch (IOException e) {
                getDeviceStatusSocket = null;
                Log.d("ret", "stopGetDeviceStatusSocete 客户端 finally 异常:" + e.getMessage());
            }
        }
    }

    public static byte[] intToBytes2(int n) {
        byte[] b = new byte[4];

        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (n >> (24 - i * 8));
        }
        return b;
    }

    public static byte[] intToBytes2Antitone(int n) {
        byte[] b = new byte[4];

        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (n >> (i * 8));
        }
        return b;
    }

    public static byte[] shortToByte(short number) {
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            b[i] = (byte) (number >> (8 - i * 8));
        }
        return b;
    }

//    /**
//     * 注释：字节数组到short的转换！
//     *
//     * @param b
//     * @return
//     */
//    public static short getShort(byte[] b, int index) {
//        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
//    }

    /**
     * 将4字节的byte数组转成一个int值
     *
     * @param b
     * @return
     */
    public static short getShort(byte[] b) {
        byte[] a = new byte[2];
        short i = (short) (a.length - 1), j = (short) (b.length - 1);
        for (; i >= 0; i--, j--) {//从b的尾部(即int值的低位)开始copy数据
            if (j >= 0)
                a[i] = b[j];
            else
                a[i] = 0;//如果b.length不足4,则将高位补0
        }
        short v0 = (short) ((a[0] & 0xff) << 8);
        short v1 = (short) ((a[1] & 0xff));
        return (short) (v0 + v1);
    }

    public static int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

}
