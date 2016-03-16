package com.applications.akash.propertyenquiry;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordTabbedFragment extends Fragment {

    EditText inputEmail;
    TextInputLayout inputLayoutEmail;
    Button getPass;
    private String uEmail;

    public PasswordTabbedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputLayoutEmail = (TextInputLayout)getView(). findViewById(R.id.input_layout_email);

        inputEmail = (EditText) getView().findViewById(R.id.inputemail);
        getPass = (Button) getView().findViewById(R.id.buttonPassword);

        inputLayoutEmail.setErrorEnabled(false);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));




        getPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionDetector cd = new ConnectionDetector(getActivity());
                if(cd.isConnectingToInternet())
                {
                    validateEmail();
                    fetchPassword();
                }
                else
                {
                    Snackbar.make(getView(), "You are not connected to Internet.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void fetchPassword() {

        String param = "";
        HttpConnection http = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/fetchPassword.php");
        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");


            param += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(inputEmail.getText().toString(), "UTF-8");

            http.sendPost(param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rply=http.text.trim();

        while(rply.equals(null)){

            rply=http.serverReply().trim();
        }
        Log.i("rply", rply);
        if(rply.equalsIgnoreCase("fail")) {
            inputLayoutEmail.setError("e-Mail ID are not registered.");


        }

        else if(rply.length()==0)
        {
            //55Toast.makeText(getActivity(), "Sorry for the inconvenience. Try again later. ", Toast.LENGTH_LONG).show();
        }
        else if(rply.equalsIgnoreCase("success"))
        {


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Password recovered successfully!!");
            builder.setMessage("You will receive your password through e-Mail shortly.\nDo you want to check your e-Mail now?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    getActivity().startActivity(intent);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.show();


        }

    }

    private boolean validateEmail() {
        uEmail = inputEmail.getText().toString().trim();

        if (uEmail.isEmpty() ) {
            inputLayoutEmail.setError("Empty Field or invalid entry");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_password_tabbed, container, false);

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.inputemail:
                    validateEmail();
                    break;
               
            }
        }
    }

}
