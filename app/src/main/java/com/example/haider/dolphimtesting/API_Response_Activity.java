package com.example.haider.dolphimtesting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class API_Response_Activity extends AppCompatActivity {
    ListView lstResponse;
    EditText edtUsername;
    EditText edtPassword;
    Button btnSubmit;
    TextView txtStatus;
    String QRCode;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_response);
        lstResponse=findViewById(R.id.lstResponse);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnSubmit=findViewById(R.id.btnSubmit);
        txtStatus=findViewById(R.id.txtStatus);
        //-------------------//
        Intent intent = getIntent();
        QRCode=intent.getStringExtra("QR");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResponse(QRCode);
            }
        });


    }

    private void getResponse(String QR) {
        ProgressDialog progressDialog=new ProgressDialog(API_Response_Activity.this);
        //Getting the username and password
        String USERNAME=edtUsername.getText().toString();
        String PASSWORD=edtPassword.getText().toString();
        //calling python code
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python=Python.getInstance();
        try {
            List<String> responseDataList = new ArrayList<>();
            PyObject module = python.getModule("testing");
            PyObject func = module.get("fetchAPIData");
            String response=func.call(QR,USERNAME,PASSWORD).toJava(String.class);
            if(response.isEmpty()){
                txtStatus.setText("Unauthorized");
                responseDataList.clear();
            }else {
                txtStatus.setText("Status : 200");
                //-----------Dummy data----------------//
                // Dummy Data
                {

                    // Add key-value pairs to the list
                    responseDataList.add("Material Code : Material");
                    responseDataList.add("Material Description : MatDesc");
                    responseDataList.add("Size : Size");
                    responseDataList.add("Packet No : Packet No");
                    responseDataList.add("Mill : MillCode");
                    responseDataList.add("Wagon No : WagonNum");
                    responseDataList.add("TC No : TCNumber");
                    responseDataList.add("Inv No : BillingNo");
                    responseDataList.add("Inv Date : BillingDate");
                    responseDataList.add("Inv Qty : BillingQty");
                    responseDataList.add("Sold To : SoldToPartyCode");
                    responseDataList.add("Sold To Name : SoldToParty");
                    responseDataList.add("Ship To : ShipToPartyCode");
                    responseDataList.add("Ship To Name : ShipToParty");
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, responseDataList);
            lstResponse.setAdapter(adapter);

        }catch (Exception e){
            Toast.makeText(this, "Something Went Wrong :-(", Toast.LENGTH_SHORT).show();
        }
    }
}