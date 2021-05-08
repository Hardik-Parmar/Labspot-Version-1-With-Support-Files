package com.example.laspost10h.delivery.after_login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.example.laspost10h.R;
import com.example.laspost10h.SupportClass.Utility;
import com.example.laspost10h.Confidential_Details.Java_API_URLs;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Delivery_Forgot_Password_otp_Submit_frag extends Fragment
{
    View view;

    String delivery_received_email, delivery_forgot_password_otp_verify_temp;
    TextInputEditText delivery_forgot_password_otp_verify;
    Button delivery_forgot_password_otp_submit_2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delivery_forgot_password_otp_submit_frag, container, false);

        Bundle bundle = getArguments();
        delivery_received_email = bundle.getString("delivery_sent_mail");

        // EditText
        delivery_forgot_password_otp_verify = (TextInputEditText) view.findViewById(R.id.delivery_forgot_password_otp_verify);

        // Button
        delivery_forgot_password_otp_submit_2 = (Button) view.findViewById(R.id.delivery_forgot_password_otp_submit_2);

        delivery_forgot_password_otp_submit_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delivery_forgot_password_otp_verify_temp = delivery_forgot_password_otp_verify.getText().toString();

                if (delivery_forgot_password_otp_verify_temp.equals(""))
                {
                    Toast.makeText(getActivity(), "Please fill out the OTP Field. \n\nCheck Mail for OTP.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (Utility.isNetworkAvailable(getActivity()))
                    {
                        getDeliveryForgotPasswordOTPVerify(delivery_received_email, delivery_forgot_password_otp_verify_temp);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Internet is not Connected. Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return  view;
    }

    private void getDeliveryForgotPasswordOTPVerify(String email, String otp)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Map<String, String> jsonParameters = new HashMap<String, String>();
        jsonParameters.put("delivery_email", email);
        jsonParameters.put("delivery_forgot_password_otp", otp);

        // Progress Dialog till Process Completes
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait OTP Verification process is in Progress");
        progressDialog.setTitle("Delivery Staff OTP Verification");
        progressDialog.show();
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.mipmap.logo1);

        //Alert Dialog for handling responses
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        JsonObjectRequest delivery_Forgot_Password_OTP_Verification_POST_Request = new JsonObjectRequest(Request.Method.POST, Java_API_URLs.DELIVERY_FORGOT_PASSWORD_OTP_VERIFY, new JSONObject(jsonParameters), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                String delivery_return_message = null;

                try
                {
                    delivery_return_message = (String) response.get("delivery_return_message");
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // 1st Possible Response
                if (delivery_return_message.equals("Something went wrong in Delivery Forgot Password OTP Verification"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Unexpected Error. Please Try Again.", Toast.LENGTH_SHORT).show();
                }

                // 2nd Possible Response
                else if (delivery_return_message.equals("Delivery Forgot Password OTP Not Verified Wrong OTP"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Wrong OTP. Please Try Again.", Toast.LENGTH_LONG).show();
                }

                //3rd Possible Response
                else if (delivery_return_message.equals("You Does Not Exist as Delivery Staff please register yourself first"))
                {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "Your Account Did not Found in App, Please register your self First.", Toast.LENGTH_SHORT).show();
                }

                //4th Possible Response
                else if (delivery_return_message.equals("Delivery Forgot Password OTP Verified"))
                {
                    progressDialog.dismiss();

                    builder.setIcon(R.mipmap.logo1);
                    builder.setTitle("OTP for Reset Password Verified");
                    builder.setMessage("Woo-hoo Your OTP is Verified.\n\nPlease click on 'Reset Password' to Reset Your Password.");

                    builder.setPositiveButton("Reset Password", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString("delivery_sent_mail", email);

                            Delivery_Reset_Password_frag delivery_reset_password_frag = new Delivery_Reset_Password_frag();
                            delivery_reset_password_frag.setArguments(bundle);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.delivery_fragment_container, delivery_reset_password_frag).commit();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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

        delivery_Forgot_Password_OTP_Verification_POST_Request.setRetryPolicy(new RetryPolicy()
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

        requestQueue.add(delivery_Forgot_Password_OTP_Verification_POST_Request);
    }
}