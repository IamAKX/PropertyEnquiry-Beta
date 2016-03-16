package com.applications.akash.propertyenquiry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;


    ProgressDialog progressDialog;
    View myView;
    private RecyclerView recyclerView;
    private ArrayList<PropertyCard> datas;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    static String NAME = "null";
    static String BUILDER = "null";
    static String LOCATION = "null";
    static String TYPE = "null";
    TextView textView;

    static boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new PropertyCardAdapter(getBaseContext());
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setTitle("Searching...");
        progressDialog.setMessage("Please Wait while we fetch your property");
        progressDialog.setCancelable(false);
        datas =new ArrayList<PropertyCard>();


        progressDialog.show();
        search_Property();
        textView = (TextView)findViewById(R.id.textView4);
        if(datas.size()!=0)
            textView.setVisibility(View.GONE);
        search_Property();

        layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PropertyCardAdapter(datas);
        recyclerView.setAdapter(adapter);
        datas.clear();

    }



    private void search_Property() {
        int id=0;
        String name="",builder="",address="",bhk="",price="",phone="",possession="",detail="",url="";
        HttpConnection http = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/searchproperty.php");
        http.text="";
        String param="";
        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");
            param += "&" + URLEncoder.encode("name", "UTF-8")
                    + "=" + URLEncoder.encode(NAME, "UTF-8");
            param += "&" + URLEncoder.encode("builder", "UTF-8")
                    + "=" + URLEncoder.encode(BUILDER, "UTF-8");
            param += "&" + URLEncoder.encode("location", "UTF-8")
                    + "=" + URLEncoder.encode(LOCATION, "UTF-8");
            param += "&" + URLEncoder.encode("typeee", "UTF-8")
                    + "=" + URLEncoder.encode(TYPE, "UTF-8");
            http.sendPost(param);
            Log.i("aaaaa",param);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("#*#*",NAME+LOCATION+BUILDER+TYPE);
        String rply=http.serverReply();
        int apla=0;
        while(rply.equals(null)||rply.length()==0){
            rply=http.serverReply().trim();
            Log.i("xxxxx"," "+apla++);
        }
        Log.i("xxxx","  "+rply);
        progressDialog.dismiss();

        if(rply.equals("noresult"))
        {
            
            Toast.makeText(getBaseContext(),"No result found!!",Toast.LENGTH_SHORT).show();
            return;
        }

        int i=0,x=0;
        char ch;
        String word = "";

        while(i<rply.length())
        {
            ch=rply.charAt(i);
            if(ch!='#')
            {

                word+=ch;
            }
            else
            {
                if(x==0)
                    id=(Integer.parseInt(word.trim()));
                else
                if(x==1)
                    name=word;
                else
                if(x==2)
                    builder=word;
                else
                if(x==3)
                    address=word;
                else
                if(x==4)
                    bhk=word;
                else
                if(x==5)
                    price=word;
                else
                if(x==6)
                    phone=word;
                else
                if(x==7)
                    possession=word;
                else
                if(x==8)
                    detail=word;

                else
                if(x==9)
                    url=word;

                word="";
                x++;
                if(x>9 )
                {
                    x=0;
                    datas.add(new PropertyCard(url,"Possession : "+possession,name,builder,address,phone,"Rs."+price));


                }

            }
            i++;
        }
        http.text=null;
    }
}
