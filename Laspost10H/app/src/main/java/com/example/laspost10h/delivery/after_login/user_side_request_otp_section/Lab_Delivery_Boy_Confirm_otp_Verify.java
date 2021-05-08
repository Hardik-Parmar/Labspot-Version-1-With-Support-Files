package com.example.laspost10h.delivery.after_login.user_side_request_otp_section;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Lab_Delivery_Boy_Confirm_otp_Verify extends Fragment
{
    View view;

    String delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_address;
    String delivery_received_applicant_phone, delivery_received_applicant_email, delivery_received_lab_name;
    String delivery_received_lab_address, delivery_received_lab_phone, delivery_received_lab_email, delivery_received_test_name;
    String delivery_received_test_price, delivery_received_test_50_price;

    TextInputEditText user_lab_delivery_boy_confirm_otp_verify_otp_enter;

    Button user_lab_delivery_boy_confirm_otp_verify_otp_submit;

    String verify_at_lab_delivery_boy_otp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lab_delivery_boy_confirm_otp_verify, container, false);

        //Receiving Data from Previous Fragments
        Bundle bundle = getArguments();
        delivery_received_request_id = bundle.getString("request_id");

        delivery_received_applicant_name = bundle.getString("applicant_name");
        delivery_received_applicant_address = bundle.getString("applicant_address");
        delivery_received_applicant_phone = bundle.getString("applicant_phone");
        delivery_received_applicant_email = bundle.getString("applicant_mail");

        delivery_received_lab_name = bundle.getString("lab_name");
        delivery_received_lab_address = bundle.getString("lab_address");
        delivery_received_lab_phone = bundle.getString("lab_phone");
        delivery_received_lab_email = bundle.getString("lab_mail");

        delivery_received_test_name = bundle.getString("test_name");
        delivery_received_test_price = bundle.getString("test_price");
        delivery_received_test_50_price = bundle.getString("50_price");

        //TextInputEditText
        user_lab_delivery_boy_confirm_otp_verify_otp_enter = (TextInputEditText) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_verify_otp_enter);

        //Button
        user_lab_delivery_boy_confirm_otp_verify_otp_submit = (Button) view.findViewById(R.id.user_lab_delivery_boy_confirm_otp_verify_otp_submit);

        user_lab_delivery_boy_confirm_otp_verify_otp_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                verify_at_lab_delivery_boy_otp = user_lab_delivery_boy_confirm_otp_verify_otp_enter.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy HH : mm : ss", Locale.getDefault());
                String currentDateAndTime = simpleDateFormat.format(new Date());

                if(verify_at_lab_delivery_boy_otp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the OTP Field.\n\nAsk Laboratory to their Check Mail for OTP.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(Utility.isNetworkAvailable(getActivity()))
                    {
                        getDeliveryBoyAtLabOTPVerify(delivery_received_request_id, delivery_received_applicant_name, delivery_received_applicant_email, delivery_received_lab_name, delivery_received_lab_email, delivery_received_test_name, verify_at_lab_delivery_boy_otp, currentDateAndTime);
                    }

                    else
                    {
                        Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void getDeliveryBoyAtLabOTPVerify(String request_id, String applicant_name, String applicant_email, String lab_name, String lab_email, String test_name, String otp_from_delivery, String date)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();

        jsonParameters.put("request_id", request_id);

        jsonParameters.put("customer_name", applicant_name);
        jsonParameters.put("customer_email", applicant_email);

        jsonParameters.put("lab_name", lab_name);
        jsonParameters.put("lab_email", lab_email);

        jsonParameters.put("test_name", test_name);

        jsonParameters.put("otp_from_delivery", otp_from_delivery);

        jsonParameters.put("date_of_delivery_boy_arrived", date);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait OTP is being Verified to Authenticate You");
        progressDialog.setTitle("Verifying OTP");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        JsonObjectRequest delivery_Person_At_Lab_Verify_OTP_To_Verify_Himself_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_BOY_AT_LAB_OTP_VERIFICATION_TO_VERIFY_DELIVERY_BOY, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String transaction_return_message = null;

                try
                {
                    transaction_return_message = (String) response.get("transaction_return_message");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st possible response
                if (transaction_return_message.equals("Something went wrong in Delivery Boy At Lab Address Verify OTP Verify"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd possible response
                else if (transaction_return_message.equals("OTP to Verify Delivery Person has not been generated yet, please generate OTP first then verify the OTP"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Some How OTP to Verify you has not been Generated yet. Please Generate OTP then Verify.", Toast.LENGTH_SHORT).show();
                }

                // 3rd possible response
                else if (transaction_return_message.equals("Delivery Boy At Lab OTP is not Verified, Wrong OTP"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Wrong OTP. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 4th possible response
                else if (transaction_return_message.equals("Delivery Boy At Lab OTP Verified and Delivery Boy Verified At Lab Email Sent and Delivery Boy At Lab Email to User Sent"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "OTP Verified Successfully.", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });


        // User Forgot Password API CALL
        delivery_Person_At_Lab_Verify_OTP_To_Verify_Himself_POST_Request.setRetryPolicy(new RetryPolicy()
        {
            @Override
            public int getCurrentTimeout()
            {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount()
            {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError
            {

            }
        });

        requestQueue.add(delivery_Person_At_Lab_Verify_OTP_To_Verify_Himself_POST_Request);
    }
}