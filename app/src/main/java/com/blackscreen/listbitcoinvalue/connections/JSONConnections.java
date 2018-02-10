package com.blackscreen.listbitcoinvalue.connections;

import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hamon on 08/02/2018.
 */

public class JSONConnections {

    public static String getJsonAsString (String stringUrl) {

        BufferedReader reader = null;
        HttpURLConnection httpConnection = null;

        try {

            //Instance URL and open connection
            URL url = new URL(stringUrl);
            httpConnection = (HttpURLConnection) url.openConnection();

            //Read all json lines
            reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(httpConnection.getInputStream())));
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            while((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }

            return stringBuffer.toString();


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {

            //Close all open connections
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpConnection != null) {
                httpConnection.disconnect();
            }

        }

    }

}
