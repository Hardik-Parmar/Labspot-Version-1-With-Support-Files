package Java_Beans.lab_Beans.lab_after_login_Beans;

public class Lab_Add_Test_Details_Bean 
{
	private String lab_name, lab_email, lab_test_name, lab_test_description, lab_test_price;

	public Lab_Add_Test_Details_Bean(String lab_name, String lab_email, String lab_test_name, String lab_test_description, String lab_test_price)
	{
		this.setLab_name(lab_name);
		this.setLab_email(lab_email);
		this.setLab_test_name(lab_test_name);
		this.setLab_test_description(lab_test_description);
		this.setLab_test_price(lab_test_price);
	}
	
	public void setLab_name(String lab_name) 
	{
		this.lab_name = lab_name;
	}
	
	public String getLab_name() 
	{
		return lab_name;
	}

	public void setLab_email(String lab_email) 
	{
		this.lab_email = lab_email;
	}

	public String getLab_email() 
	{
		return lab_email;
	}

	public void setLab_test_name(String lab_test_name) 
	{
		this.lab_test_name = lab_test_name;
	}
	
	public String getLab_test_name() 
	{
		return lab_test_name;
	}

	public void setLab_test_description(String lab_test_description) 
	{
		this.lab_test_description = lab_test_description;
	}
	
	public String getLab_test_description() 
	{
		return lab_test_description;
	}

	public void setLab_test_price(String lab_test_price) 
	{
		this.lab_test_price = lab_test_price;
	}

	public String getLab_test_price() 
	{
		return lab_test_price;
	}
}