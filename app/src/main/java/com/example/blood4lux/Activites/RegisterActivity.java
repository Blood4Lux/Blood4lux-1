package com.example.blood4lux.Activites;

import android.content.Intent;
import android.net.sip.SipSession;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood4lux.R;
import com.example.blood4lux.Utils.Endpoints;
import com.example.blood4lux.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.blood4lux.Utils.Endpoints.register_url;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEt, placeEt, bloodGroupEt, passwordEt, contactEt;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEt = findViewById(R.id.name);
        placeEt = findViewById(R.id.place);
        bloodGroupEt = findViewById(R.id.blood_group);
        passwordEt = findViewById(R.id.password);
        contactEt = findViewById(R.id.number);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, city, blood_group, passward, number;
                name = nameEt.getText().toString();
                city = placeEt.getText().toString();
                blood_group = bloodGroupEt.getText().toString();
                passward = passwordEt.getText().toString();
                number = contactEt.getText().toString();

                if(isValid(name, city, blood_group, passward, number)){
                    register(name, city, blood_group, passward, number);

                }
                //showMessage(  name+"/n"+city+"/n"+blood_group+"/n"+passward+"/n"+number);

            }
        });

    }


    private void register(final String name, final String city, final String blood_group, final String passward,
                          final String number){

        StringRequest stringRequest = new StringRequest(Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success")){
                    Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                } else{
                    Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.d("error",error.toString());
                Toast.makeText(RegisterActivity.this, "Something went worng", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        })
        /** stringRequest = new StringRequest(Method.POST, Endpoints.register_url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success")){
                    Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                } else{
                    Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new ErrorListener(){


            @Override
            public void warning(TransformerException exception) throws TransformerException {

            }

            @Override
            public void error(TransformerException exception) throws TransformerException {

            }

            @Override
            public void fatalError(TransformerException exception) throws TransformerException {

            }

            public void onErrorResponse(VolleyError error){
                Toast.makeText(RegisterActivity.this, "Something went worng", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }


        })**/{
            protected Map<String, String> getParams()throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("city",city);
                params.put("blood_group",blood_group);
                params.put("passward",passward);
                params.put("number",number);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    private boolean isValid(String name, String city, String blood_group, String passward, String number){
        List<String>valid_blood_groups = new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");

        if(name.isEmpty()){
            showMessage("Name Field is empty");
            return false;
        }else if (city.isEmpty()){
            showMessage("City name is required");
        }else if(!valid_blood_groups.contains(blood_group)){
            showMessage("Blood group invalid choose from "+valid_blood_groups);
            return false;
        }else if(passward.length() <=4){
            showMessage("Password length is to short");
            return false;
        }

        else if(number.length() !=9){
            showMessage("Invalid mobile number, number should be of 10 digits");
            return false;
        }
        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
