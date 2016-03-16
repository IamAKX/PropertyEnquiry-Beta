package com.applications.akash.propertyenquiry;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Akash on 2/13/2016.
 */
public class UserInfoManager {
    static String uName="User Not Logged In...",uEmail,upass;
    static String username="",email="";

    public static void setUsername(String username) {
        UserInfoManager.username = username;
    }

    public static void setEmail(String email) {
        UserInfoManager.email = email;
    }

    public void getDetail(Context cntxt)
    {
    SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
    SharedPreferences.Editor sEdit = sp.edit();
        uName=sp.getString("uname","User Not Logged In...");
        uEmail=sp.getString("email",null);
        upass=sp.getString("pass",null);
    }

    public void saveDetail(Context cntxt,String nme,String email)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        sEdit.putString("uname",nme);
        sEdit.putString("email",email);
        sEdit.putString("loggedin","true");
        sEdit.commit();

    }
    public static void saveLocationList(Context cntx)
    {
        SharedPreferences sp = cntx.getSharedPreferences("LocationList", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        int c=0;
        String param = "";
        HttpConnection http = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/getAllLocation.php");
        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");
            http.sendPost(param);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rply=http.text.trim();

        while(rply.equals(null)||rply.length()==0){
            rply=http.serverReply().trim();
        }

        Log.i("###", "location : " + rply);
        char ch;
        int noOfRows = sp.getInt("NoOfLoc", 0);
        int fetchedNoOfRows = Integer.parseInt(rply.substring(0,rply.indexOf("#")));
        if(fetchedNoOfRows<noOfRows)
            return;;
        int i=rply.indexOf("#")+1;
        String word="";
        while(i<rply.length())
        {
            ch=rply.charAt(i);
            if(ch!='#')
            {
                word+=ch;
            }
            else
            {
                sEdit.putString("Location"+c,word);
                word="";
                c++;
            }
            i++;
        }
        sEdit.putInt("NoOfLoc", c);
        sEdit.commit();


    }

    public static void saveBuilderList(Context cntx)
    {
        SharedPreferences sp = cntx.getSharedPreferences("BuilderList", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        int c=0;
        String param = "";
        HttpConnection http1 = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/getAllBuilder.php");
        http1.text="";
        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");
            http1.sendPost(param);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rply=http1.text;

        int alpha=0;
        while(rply.equals(null)||rply.length()==0){
            rply=http1.serverReply();
            Log.i("###",""+(alpha++));
        }
        rply=rply.trim();

        char ch;
        int noOfRows = sp.getInt("NoOfBuild",0);
        int fetchedNoOfRows = Integer.parseInt(rply.substring(0,rply.indexOf("#")));
        if(fetchedNoOfRows<noOfRows)
            return;
        int i=rply.indexOf("#")+1;
        String word="";
        while(i<rply.length())
        {
            ch=rply.charAt(i);
            if(ch!='#')
            {
                word+=ch;
            }
            else
            {
                sEdit.putString("Builder"+c,word);
                word="";
                c++;
            }
            i++;
        }
        sEdit.putInt("NoOfBuild", c);
        sEdit.commit();
        http1.text="";
    }
    public static String[] getLocationList(Context cntxt)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("LocationList", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();

       int l = sp.getInt("NoOfLoc",0);
        String[] ar = new String[l];
        for(int i = 0;i<l;i++) {
            ar[i] = sp.getString("Location" + i, "No Location");

        }
        return ar;
    }
    public static String[] getBuilderList(Context cntxt)
    {
        SharedPreferences sp = cntxt.getSharedPreferences("BuilderList", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();

        int l = sp.getInt("NoOfBuild",0);
        String[] ar = new String[l];
        for(int i = 0;i<l;i++)
            ar[i] = sp.getString("Builder"+i,"No Builder");
        return ar;
    }
}

