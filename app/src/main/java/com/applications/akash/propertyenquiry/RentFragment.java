package com.applications.akash.propertyenquiry;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Akash on 2/13/2016.
 */
public class RentFragment extends Fragment {


    View myView;
    private RecyclerView recyclerView;
    private ArrayList<PropertyCard> datas;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.rent_fragment,container,false);
        return myView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new PropertyCardAdapter(getContext());
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        datas =new ArrayList<PropertyCard>();
        getPropertyData();

        TextView textView = (TextView)getActivity().findViewById(R.id.textView4);
        if(datas.size()!=0)
            textView.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PropertyCardAdapter(datas);
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.gray);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //handling swipe refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.clear();
                        getPropertyData();
                        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        adapter = new PropertyCardAdapter(datas);
                        recyclerView.setAdapter(adapter);

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


    }



    public void getPropertyData()
    {
        int id=0;
        String name="",builder="",address="",bhk="",price="",phone="",possession="",detail="",url="";
        String param = "";
        HttpConnection http = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/getrentdata.php");

        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");
            http.sendPost(param);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rply=http.serverReply();

        while(rply.equals(null)){
            rply=http.serverReply();
        }
        rply=rply.trim();

        int i=0,x=0;
        char ch;
        String word = "";

        while(i<rply.length())
        {
            ch=rply.charAt(i);
            if(ch!='#')
            {
                Log.i("########## 0", rply);
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

    }

}
