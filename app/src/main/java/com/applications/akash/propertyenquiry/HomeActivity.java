package com.applications.akash.propertyenquiry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v7.app.AlertDialog.*;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv,tv2;
    private static Spinner sBuilder,sLocation,sType;
    CheckBox cBuilder,cLocation,cType;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<PropertyType> datas;
    static Context cntxt;
    private View navHeaderView;
    ImageView userDP;
    private Menu myMenu;
    Uri dp_uri=null;
    private Bitmap photo;
    public static FragmentManager fm;
    static HomeActivity ha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final String[] lBuilder = UserInfoManager.getBuilderList(getBaseContext());

        final String[] lLocation = UserInfoManager.getLocationList(getBaseContext());

        final String[] lType= {"Buy","Resale","Rent","Project"};


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                Snackbar.make(view, "I will add the functionality later", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();



                final ArrayAdapter<String> adBuilder=new ArrayAdapter<String>(HomeActivity.this,
                        android.R.layout.simple_list_item_1,lBuilder);
                adBuilder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



                final ArrayAdapter<String> adLocation=new ArrayAdapter<String>(HomeActivity.this,
                        android.R.layout.simple_list_item_1,lLocation);
                adLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                final ArrayAdapter<String> adType=new ArrayAdapter<String>(HomeActivity.this,
                        android.R.layout.simple_list_item_1,lType);
                adType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.searchlayout, null);

                final EditText et = (EditText) alertLayout.findViewById(R.id.editTextSearch);

                cBuilder = (CheckBox) alertLayout.findViewById(R.id.checkBoxBuilder);
                cLocation = (CheckBox) alertLayout.findViewById(R.id.checkBoxLocation);
                cType = (CheckBox) alertLayout.findViewById(R.id.checkBoxType);


                cBuilder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            sBuilder.setEnabled(true);
                        } else {
                            sBuilder.setEnabled(false);
                        }

                    }
                });

                cLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            sLocation.setEnabled(true);
                        } else {
                            sLocation.setEnabled(false);
                        }

                    }
                });

                cType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            sType.setEnabled(true);
                        } else {
                            sType.setEnabled(false);
                        }

                    }
                });

                sBuilder = (Spinner) alertLayout.findViewById(R.id.spinnerBuilder);
                sLocation = (Spinner) alertLayout.findViewById(R.id.spinnerLoaction);
                sType = (Spinner) alertLayout.findViewById(R.id.spinnerType);

                sBuilder.setAdapter(adBuilder);
                sLocation.setAdapter(adLocation);
                sType.setAdapter(adType);

                sBuilder.setEnabled(false);
                sLocation.setEnabled(false);
                sType.setEnabled(false);
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Search");
                builder.setView(alertLayout);


                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int id) {
                                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                                if(!cd.isConnectingToInternet())
                                    Snackbar.make(view, "Check your Internet Connection", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                                else {
                                    SearchActivity.NAME = et.getText().toString();
                                    if (cBuilder.isChecked())
                                        SearchActivity.BUILDER = sBuilder.getSelectedItem().toString().trim();
                                    if (cLocation.isChecked())
                                        SearchActivity.LOCATION = sLocation.getSelectedItem().toString().trim();
                                    if (cType.isChecked())
                                        SearchActivity.TYPE = sType.getSelectedItem().toString().trim();
                                    if (SearchActivity.NAME.equals("null") || et.getText().toString().equals(null))
                                        Toast.makeText(getBaseContext(), "Enter the Property name first", Toast.LENGTH_SHORT).show();
                                    else
                                        startActivity(new Intent(getBaseContext(), SearchActivity.class));
                                }
                            }
                        }
                );
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                );
                builder.show();


            }
        });




            ha=this;
        getSupportActionBar().setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        cntxt=getBaseContext();
        fm = getSupportFragmentManager();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        tv = (TextView)navHeaderView.findViewById(R.id.username);
        tv2 = (TextView)navHeaderView.findViewById(R.id.userEmail);
        userDP = (ImageView)navHeaderView.findViewById(R.id.imageViewUser);





        myMenu = navigationView.getMenu();


        SharedPreferences sp =cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        if(sp.getString("loggedin","false").equals("true")) {
            tv.setText("Welcome " + sp.getString("uname", "User"));
            tv2.setText(sp.getString("email", null));
            String imgpath=sp.getString("imgpath","nopath");

            if(imgpath.equals("nopath"))
                userDP.setImageResource(R.drawable.user);
            else
            {
                Bitmap img = StringToBitMap(imgpath);
                userDP.setImageBitmap(img);
            }
            myMenu.findItem(R.id.nav_logout).setEnabled(true);myMenu.findItem(R.id.nav_logout).setEnabled(true);

        }
        else {
            tv.setText("Login/Register");
            tv2.setText("");
            userDP.setImageResource(R.drawable.user);
            myMenu.findItem(R.id.nav_logout).setEnabled(false);
        }



        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        datas =new  ArrayList<PropertyType>();
        datas.add(new PropertyType(R.drawable.buyproperty,"Buy Property"));
        datas.add(new PropertyType(R.drawable.rentproperty, "Rent Property"));
        datas.add(new PropertyType(R.drawable.resaleproperty,"Resale Property"));
        datas.add(new PropertyType(R.drawable.hostelproperty, "Hostel"));
        datas.add(new PropertyType(R.drawable.projectproperty, "Project"));
        datas.add(new PropertyType(R.drawable.loanproperty, "Home Loan"));

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PropertyTypeAdapter(datas);
        recyclerView.setAdapter(adapter);



    }

    public void changeDP(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Choose your Profile Image"), 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK)
                {

                        final Bundle extras = data.getExtras();

                        if (extras != null) {
                            photo = extras.getParcelable("data");
                            Log.i("Bitmap",photo+"");
                            SharedPreferences sp =cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor sEdit = sp.edit();
                            sEdit.putString("imgpath",BitMapToString(photo));
                            sEdit.commit();
                            userDP.setImageBitmap(photo);
//                            uploadImage();
                        }



                }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }
    int i =0,count=0,count1=0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Refresh_List) {

            ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            if(!cd.isConnectingToInternet())
                Snackbar.make(getWindow().getDecorView(), "Check your Internet Connection", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else {


                final ProgressDialog pd = new ProgressDialog(HomeActivity.this);
                pd.setMessage("Refreshing your Builder and Location list");
                pd.setCancelable(false);
                pd.show();

                new Thread() {
                    public void run() {
                        while (i++ < 7000) {
                            try {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (i < 4000 && i % 500 == 0) {
                                            count++;
                                            if (count == 1)
                                                pd.setMessage("Refreshing your Builder and Location list .");
                                            else if (count == 2)
                                                pd.setMessage("Refreshing your Builder and Location list . .");
                                            else if (count == 3) {
                                                pd.setMessage("Refreshing your Builder and Location list . . .");
                                                count = 0;
                                            }

                                        }
                                        if (i >= 2500 && i % 500 == 0) {
                                            count1++;
                                            if (count1 == 1)
                                                pd.setMessage("Preparing your list .");
                                            else if (count1 == 2)
                                                pd.setMessage("Preparing your list . .");
                                            else if (count1 == 3) {
                                                pd.setMessage("Preparing your list . . .");
                                                count = 0;
                                            }

                                        }

                                    }
                                });
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        pd.dismiss();
                        i = 0;
                        count = 0;
                        count1 = 0;
                    }
                }.start();
                new Thread() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UserInfoManager.saveLocationList(getBaseContext());

                                UserInfoManager.saveBuilderList(getBaseContext());

                            }
                        });

                    }
                }.start();

            }
            return true;
        }
        else

        if (id == R.id.action_home) {
            startActivity(new Intent(getBaseContext(),HomeActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        
        Fragment myFragment = null;
       switch(id)
       {
           case R.id.nav_buy:

               myFragment = new BuyFragment();
               getSupportActionBar().setTitle("Buy");
               recyclerView.setVisibility(View.INVISIBLE);
               break;
           case R.id.nav_rent:

               myFragment = new RentFragment();
               getSupportActionBar().setTitle("Rent");
               recyclerView.setVisibility(View.INVISIBLE);
               break;
           case R.id.nav_resale:

               myFragment = new ResaleFragment();
               getSupportActionBar().setTitle("Resale");
               recyclerView.setVisibility(View.INVISIBLE);
               break;
           case R.id.nav_hostel:

               myFragment = new HostelFragment();
               getSupportActionBar().setTitle("Hostel");
               recyclerView.setVisibility(View.INVISIBLE);
               break;
           case R.id.nav_project:

               myFragment = new ProjectFragment();
               getSupportActionBar().setTitle("Project");
               recyclerView.setVisibility(View.INVISIBLE);
               break;
           case R.id.nav_homeloan:

               myFragment = new HomeLoanFragment();
               getSupportActionBar().setTitle("Home Loan");
               recyclerView.setVisibility(View.INVISIBLE);
               break;

           case R.id.nav_share:
               Toast.makeText(getBaseContext(), "Coding not done!!", Toast.LENGTH_LONG).show();
              return true;
           case R.id.nav_send:
               Toast.makeText(getBaseContext(),"Coding not done!!",Toast.LENGTH_LONG).show();
               return true;
           case R.id.nav_logout:
               SharedPreferences sp = cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
               SharedPreferences.Editor sEdit = sp.edit();
               if(sp.getString("loggedin","false").equals("true")){
               sEdit.putString("loggedin", "false");
               sEdit.putString("imgpath","nopath");
               sEdit.commit();
               Toast.makeText(getBaseContext(), "Successfully Logged out!!", Toast.LENGTH_LONG).show();
               tv.setText("Login/Register");
               tv2.setText("");
               userDP.setImageResource(R.drawable.user);
               myMenu.findItem(R.id.nav_logout).setEnabled( false);
               }
               else
               {
                   item.setEnabled(false);
                   Toast.makeText(getBaseContext(), "You are already logged out", Toast.LENGTH_LONG).show();
               }

               return true;
           default:
               break;
       }



        fm.beginTransaction()
                .replace(R.id.container,myFragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logintv(View view)
    {
        SharedPreferences sp =cntxt.getSharedPreferences("UserPref", Activity.MODE_PRIVATE);

        if(sp.getString("loggedin","false").equals("false")) {
            startActivity(new Intent(getBaseContext(), LogIn.class));

        }

    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


}
