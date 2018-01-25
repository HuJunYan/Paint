package com.koudai.operate.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static android.text.format.Formatter.formatFileSize;

/**
 * Created by hjy on 2018/1/25.
 */

public class Tools {

    private static final String TAG = Tools.class.getName();
    private static final String MEM_INFO_PATH = "/proc/meminfo";
    public static final String MEMTOTAL = "MemTotal";
    public static final String MEMFREE = "MemFree";

    /**
     * 得到中内存大小
     *
     * @param context
     * @return
     */
    public static String getTotalMemory(Context context) {
        return getMemInfoIype(context, MEMTOTAL);
    }

    /**
     * 得到可用内存大小
     *
     * @param context
     * @param memfree
     * @return
     */
    public static String getMemoryFree(Context context, String memfree) {
        return getMemInfoIype(context, MEMFREE);
    }

    /**
     * 得到type info
     *
     * @param context
     * @param type
     * @return
     */
    public static String getMemInfoIype(Context context, String type) {
        try {
            FileReader fileReader = new FileReader(MEM_INFO_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 4 * 1024);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains(type)) {
                    break;
                }
            }
            bufferedReader.close();
            /* \\s表示   空格,回车,换行等空白符,
            +号表示一个或多个的意思     */
            String[] array = str.split("\\s+");
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            int length = Integer.valueOf(array[1]).intValue() * 1024;
            return formatFileSize(context, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到内置存储空间的总容量
     * @param context
     * @return
     */
    public static String getInternalToatalSpace(Context context) {
        long G2 = (long) (2 * Math.pow(1024, 3));
        long G4 = (long) (4 * Math.pow(1024, 3));
        long G8 = (long) (8 * Math.pow(1024, 3));
        long G16 = (long) (16 * Math.pow(1024, 3));
        long G32 = (long) (32 * Math.pow(1024, 3));
        long G64 = (long) (64 * Math.pow(1024, 3));
        long G128 = (long) (128 * Math.pow(1024, 3));
        long G256 = (long) (256 * Math.pow(1024, 3));
        String path = Environment.getDataDirectory().getPath();
        Log.d(TAG, "root path is " + path);
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        long totalBlocks = statFs.getBlockCount();
        long availableBlocks = statFs.getAvailableBlocks();
        long useBlocks = totalBlocks - availableBlocks;

        long rom_length = totalBlocks * blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (rom_length>1024){
               if (Long.compare(rom_length,G2)!=1){
                   rom_length= G2;
               }else if (Long.compare(rom_length,G4)!=1){
                   rom_length= G4;
               }else if (Long.compare(rom_length,G8)!=1){
                   rom_length= G8;
               }else if (Long.compare(rom_length,G16)!=1){
                   rom_length= G16;
               } else if (Long.compare(rom_length,G32)!=1){
                   rom_length= G32;
               } else if (Long.compare(rom_length,G64)!=1){
                   rom_length= G64;
               } else if (Long.compare(rom_length,G128)!=1){
                   rom_length= G128;
               }
               else if (Long.compare(rom_length,G256)!=1){
                   rom_length= G256;
               }else {
                   rom_length= G256;
               }
            }
        }

        String totalSize = Formatter.formatFileSize(context, rom_length);

        return totalSize;
    }
}
