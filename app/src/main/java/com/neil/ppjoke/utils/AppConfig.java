package com.neil.ppjoke.utils;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.neil.ppjoke.model.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author zhongnan
 * @time 2021/7/14 20:20
 *
 */
public class AppConfig {
    private static HashMap<String, Destination> sDestConfig;

    public static HashMap<String, Destination> getsDestConfig() {
        if(sDestConfig == null) {
            String content = parseFile("destination.json");
            sDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>(){}.getType());

        }
        return sDestConfig;
    }

    private static String parseFile(String fileName) {
        AssetManager assetManager =
                AppGlobals.getApplication().getResources().getAssets();
        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stream = assetManager.open(fileName);
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if(stream != null) {
                    stream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
