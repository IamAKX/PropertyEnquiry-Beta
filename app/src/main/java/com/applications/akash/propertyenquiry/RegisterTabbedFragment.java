package com.applications.akash.propertyenquiry;


import android.content.Intent;
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
public class RegisterTabbedFragment extends Fragment {


    EditText inputEmail, inputPassword, inputCnfPassword, inputName;
    TextInputLayout inputLayoutEmail, inputLayoutName, inputLayoutCnfPassword, inputLayoutPassword;
    Button btnSignUp;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputLayoutName = (TextInputLayout) getView().findViewById(R.id.input_layout_reg_name);
        inputLayoutEmail = (TextInputLayout) getView().findViewById(R.id.input_layout_reg_email);
        inputLayoutCnfPassword = (TextInputLayout) getView().findViewById(R.id.input_layout_reg_cnfpassword);
        inputLayoutPassword = (TextInputLayout) getView().findViewById(R.id.input_layout_reg_password);

        inputName = (EditText) getView().findViewById(R.id.reg_name);
        inputEmail = (EditText) getView().findViewById(R.id.reg_email);
        inputCnfPassword = (EditText) getView().findViewById(R.id.input_reg_cnfpassword);
        inputPassword = (EditText) getView().findViewById(R.id.input_reg_password);
        btnSignUp = (Button) getView().findViewById(R.id.buttonRegister);

        inputLayoutPassword.setErrorEnabled(false);
        inputLayoutEmail.setErrorEnabled(false);
        inputLayoutCnfPassword.setErrorEnabled(false);
        inputLayoutName.setErrorEnabled(false);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputCnfPassword.addTextChangedListener(new MyTextWatcher(inputCnfPassword));



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }


    public RegisterTabbedFragment() {
        // Required empty public constructor
    }


    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePass()) {
            return;
        }
        if (!validateCnfPass()) {
            return;
        }


        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if(cd.isConnectingToInternet())
        {
            registerTheUser();
            new UserInfoManager().saveDetail(getActivity(), inputName.getText().toString(), inputEmail.getText().toString());
        }
        else
        {
            Snackbar.make(getView(), "You are not connected to Internet.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private boolean validateCnfPass() {
        if (!inputCnfPassword.getText().toString().trim().equals(inputPassword.getText().toString().trim())) {

            inputLayoutCnfPassword.setError("Password did not matched");

            requestFocus(inputCnfPassword);
            return false;
        } else {
            inputLayoutCnfPassword.setErrorEnabled(false);

        }

        return true;
    }

    private boolean validatePass() {
        if (inputPassword.getText().toString().isEmpty()) {

            inputLayoutPassword.setError("Empty Field");

            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);

        }

        return true;
    }

    private boolean validateEmail() {

        if (inputEmail.getText().toString().trim().isEmpty()) {

            inputLayoutEmail.setError("Empty Field or invalid entry");

            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);

        }

        return true;
    }

    private boolean validateName() {


        if (inputName.getText().toString().trim().isEmpty()) {

            inputLayoutName.setError("Empty Field or invalid entry");

            requestFocus(inputName);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_register_tabbed, container, false);
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

                case R.id.reg_email:
                    validateEmail();
                    break;
                case R.id.input_reg_password:
                    validatePass();
                    break;
                case R.id.input_reg_cnfpassword:
                    validateCnfPass();
                    break;
                case R.id.reg_name:
                    validateName();
                    break;
            }
        }
    }


    public void registerTheUser() {
        String param = "";
        try {
            param = URLEncoder.encode("type", "UTF-8")
                    + "=" + URLEncoder.encode("new", "UTF-8");

            param += "&" + URLEncoder.encode("name", "UTF-8")
                    + "=" + URLEncoder.encode(inputName.getText().toString(), "UTF-8");
            param += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(inputEmail.getText().toString(), "UTF-8");
            param += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(inputPassword.getText().toString(), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpConnection http = new HttpConnection("http://anamexamplecafe.esy.es/propertyenquiry/register.php");
        http.sendPost(param);

        String rply=http.text.trim();

        while(rply.equals(null)){
            Log.i("rply", rply);
            rply=http.serverReply().trim();
        }
        if(rply.equalsIgnoreCase("duplicate"))
        {
            inputLayoutEmail.setError("e-Mail already registered. Recover password or use another e-Mail.");
            requestFocus(inputEmail);

        }
        else if(rply.equalsIgnoreCase("success")) {
            Toast.makeText(getActivity(), "You are registered successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        }


    }
}