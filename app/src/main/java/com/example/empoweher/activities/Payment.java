package com.example.empoweher.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.empoweher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    String currentFirebaseUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

//        String  newString= (String) savedInstanceState.getSerializable("eventId");
//
//        Button btn=findViewById(R.id.payButton);
//        btn.setOnClickListener(view -> startPayment());

        startPayment();

        try{
            currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        catch(Exception e){

        }


    }
    public void startPayment() {

        Checkout checkout = new Checkout();

        checkout.setImage(R.mipmap.ic_launcher);

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "EmpowerHer");
            options.put("description", "Payment For An Event");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("send_sms_hash", true);
            options.put("allow rotation", false);


            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill=new JSONObject();
            preFill.put("prefill.email", "2021.rajveer.tolani@ves.ac.in");
            preFill.put("prefill.contact","9324569397");

            options.put("prefill",preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Toast.makeText(activity,"Error in Payment: "+ e.getMessage(),Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        if(currentFirebaseUser!=null && currentFirebaseUser!=""){



        }



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Error"+s,Toast.LENGTH_SHORT).show();
    }
}