package com.applications.akash.propertyenquiry;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
public class LoginTabbedFragment extends Fragment {

     EditText inputEmail, inputPassword;
     TextInputLayout inputLayoutEmail, inputLayoutPassword;
     Button btnSignUp;
    private String pass;
    private String uEmail;

    public LoginTabbedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputLayoutEmail = (TextInputLayout)getView(). findViewById(R.id.input_layout_login_email);
        inputLayoutPassword = (TextInputLayout)getView().findViewById(R.id.input_layout_login_password);

        inputEmail = (EditText) getView().findViewById(R.id.login_email);
        inputPassword = (EditText) getView().findViewById(R.id.input_login_password);
        btnSignUp = (Button) getView().findViewById(R.id.buttonLogin);

        inputLayoutEmail.setErrorEnabled(false);
        inputLayoutPassword.setErrorEnabled(false);


        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_login_tabbed, container, false);
    }

    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        authenticateUser();

    }

    private void authenticateUser() {


        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if(cd.isConnectingToInternet())
        {
            login();
        }
        else
        {
            Snackbar.make(getView(), "You are not connected to Internet.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }


    }

    private boolean validatePassword() {
       pass = inputPassword.getText().toString().trim();

        if (pass.isEmpty() ) {
            inputLayoutPassword.setError("Empty Field or invalid entry");
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
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

                case R.id.login_email:
                    validateEmail();
                    break;
                case R.id.input_login_password:
                    validatePassword();
                    break;
            }
        }
    }

    public void login()
    {
        String param = "";
        HttpConnection http = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/login.php");
        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");


            param += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(inputEmail.getText().toString(), "UTF-8");
            param += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(inputPassword.getText().toString(), "UTF-8");

            http.sendPost(param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rply=http.text.trim();

        while(rply.equals(null)){

            rply=http.serverReply().trim();
        }
        Log.i("rply",rply);
        if(rply.equalsIgnoreCase("fail")) {
            inputLayoutEmail.setError("Wrong Email ID");
            inputLayoutPassword.setError("Wrong Password");

        }

        else if(rply.length()==0)
        {
            //Toast.makeText(getActivity(), "Sorry for the inconvenience. Try again later. ", Toast.LENGTH_LONG).show();
        }
        else if(rply.substring(0,7).equalsIgnoreCase("success"))
        {
            String nme= rply.substring(8);
            SharedPreferences sp = getActivity().getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor sEdit = sp.edit();
            sEdit.putString("uname",nme);
            sEdit.putString("email",inputEmail.getText().toString());
            sEdit.putString("loggedin", "true");
            sEdit.commit();

            Toast.makeText(getActivity(), "Welcome "+nme+" !!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(),HomeActivity.class));
            getActivity().finish();

        }

    }

}
