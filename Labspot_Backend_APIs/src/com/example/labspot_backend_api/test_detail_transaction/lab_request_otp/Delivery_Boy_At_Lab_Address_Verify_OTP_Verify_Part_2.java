package com.example.labspot_backend_api.test_detail_transaction.lab_request_otp;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Java_Beans.test_detail_transaction_Beans.lab_request_otp_Beans.Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean;
import Java_DAO.test_detail_transaction_DAO.lab_request_otp_DAO.Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_DAO;
import send_mail.Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Mail;

@Path("test_detail_transaction")
public class Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2
{
	Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean;
	Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_DAO delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_DAO;
	
	public String request_id_temp, customer_name_temp, lab_name_temp, lab_email_temp, test_name_temp, otp_temp;
	
	public String date_temp;
	
	public String result, response;
	
	@Path("lab_delivery_request_person_verify_otp_verify_part_2")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deliveryBoyAtLabAddressverifyOTPVerify(String json)
	{
		System.out.println("\nVERIFY OTP FOR DELIVERY PERSON TO VERIFY AT LAB PART 2 REQUEST FROM USER API");
		System.out.println("\n\nJSON Request from Android app \n" + json);
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(json);
		
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			
			// retrieving data from JSON and storing in local variables
			
			request_id_temp = jsonObject.get("request_id").getAsString();
			customer_name_temp = jsonObject.get("customer_name").getAsString();
			lab_name_temp = jsonObject.get("lab_name").getAsString();
			lab_email_temp = jsonObject.get("lab_email").getAsString();
			test_name_temp = jsonObject.get("test_name").getAsString();
			otp_temp = jsonObject.get("otp_from_delivery").getAsString();
			date_temp = jsonObject.get("date_of_delivery_boy_arrived").getAsString();
			
			delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean = new Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean(
					request_id_temp, customer_name_temp, lab_name_temp, lab_email_temp, test_name_temp, otp_temp, date_temp);
		}
		
		delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_DAO = new Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_DAO();
		
		result = delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_DAO.delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2(delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean);
		
		System.out.println("\nReturn Message from delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2 Method : - "+result);
		
		if(result.equals("Delivery Boy At Lab Part 2 OTP Verified"))
		{	
			response = Delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Mail.deliveryBoyAtLabAddressVerifyOTPVerifyPart2Email(delivery_Boy_At_Lab_Address_Verify_OTP_Verify_Part_2_Bean);
			
			System.out.println("\n\nReturn Message from deliveryBoyAtLabAddressVerifyOTPVerifyPart2Email Method : - "+response);
			
			result = result + " and " + response;
		}
		
		System.out.println("\nAfter Mail Part final Return Response is : - "+result);
		
		return "{'transaction_return_message' : '"+result+"'}";
	}
}