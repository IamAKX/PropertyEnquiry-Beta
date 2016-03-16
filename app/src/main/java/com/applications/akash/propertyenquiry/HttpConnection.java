/**Attention! Please modify changes here for this class.
 *
 * Description: This class handles HTTP connection. Can be usefull in making a POST request to the server.
 *
 * Remove resources if not modified. Only insert modified resources name.
 * RESOURCES: NA
 *
 * Remove function if not modified. Only insert modified function name.
 * FUNCTIONS:
 *
 *
 *
 * Increment this no. if this class is modified.
 * VER 0.1
 */

package com.applications.akash.propertyenquiry;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpConnection {

    public static String url;

    /**
     *
     * @param url Url to connect
     */

    static String text=new String();
    HttpConnection(String url)
    {

        this.url=url;


    }
    public void sendPost(String param)
    {
        NetworkManager nw=new NetworkManager();
        nw.execute(param);
    }

     String serverReply()
    {

        return text;
    }

    /**
     *
     */
    private void POST(String param)
    {
        HttpURLConnection urlConnection = null;
        String urlParam=param;
        try{
            URL url = new URL(HttpConnection.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Length", "" +
                    Integer.toString(param.getBytes().length));
            urlConnection.setRequestProperty("Content-Language", "en-US");
            OutputStream os = urlConnection.getOutputStream();
            os.write(urlParam.getBytes());
            Log.i("POST","posted");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            UserInfoManager.setUsername(text);
            if(!text.isEmpty()) {
                Log.i("posti", text);
                setText(text);
            }
            else
                Log.i("post","EMPTY RESPONSE");
        }catch(Exception e){
            Log.d("Exception downloading", e.toString());
        }finally{

            if (urlConnection != null) urlConnection.disconnect();
        }

    }

    private void setText(String s) {
        text=s;
    }

    private class NetworkManager extends AsyncTask<String,Integer,String>{
       // ProgressDialog dialog;




        @Override
        protected String doInBackground(String... strings) {
            POST(strings[0]);
            return null;
        }


    }




}
