package com.makienkovs.creditcalc;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void writeStringAsFile(String fileContents, File file) {
        BufferedWriter out = null;
        try {
            if (file != null) {
                if (file.createNewFile()){
                    out = new BufferedWriter(new FileWriter(file), 1024);
                    out.write(fileContents);
                }
            }
        } catch (IOException e) {
            Log.e("FileExplorer", "Error writing string data to file", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e("FileExplorer", "I/O problem", e);
                }
            }
        }
    }
}
