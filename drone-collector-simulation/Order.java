//for storing the order list in the "Home"
import java.util.*;
import java.io.*;//for interface Serializable

public class Order implements Serializable
{
	private String date;
	private String contact;
	private String address;
	private DSALinkedList productsList;
	public Order(String date,String contact,String address,DSALinkedList productsList)
	{
		
		try
		{
            setDate(date);
            setContact(contact);
            setAddress(address);
            setProductsList(productsList);
		}
		catch(NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	//setter
	public void setDate(String date)
	{
		if(date == null)
		{
            throw new NullPointerException("Date CANNOT be NULL!");
		}
		else
		{
            this.date = date;
		}
	}
	
	public void setContact(String contact)
	{
		if(contact == null)
		{
            throw new NullPointerException("Contact CANNOT be NULL!");
		}
		else
		{
            this.contact = contact;
		}
	}
	
	public void setAddress(String address)
	{
		if(address == null)
		{
            throw new NullPointerException("Address CANNOT be NULL!");
		}
		else
		{
            this.address = address;
		}
	}
	
	public void setProductsList(DSALinkedList productsList)
	{
        Iterator products = productsList.iterator();
		if(products.next() == null)
		{
            throw new NullPointerException("ProductsList CANNOT HAVE NULL ITEM!");
		}
		else
		{
            this.productsList = productsList;
		}
	}
	
	//getter 
	public String getDate()
	{
        return this.date;
	}
	
	public String getContact()
	{
        return this.contact;
	}
	
	public String getAddress()
	{
        return this.address;
	}
	
	public DSALinkedList getProductsList()
	{
        return this.productsList;
	}
	
	//override java.lang.Object class's toString()
	public String toString()
	{
        return "\nDate: " + this.date + " \nContact: " + this.contact + " \nAddress: " + this.address + " \nProducts list: " + this.productsList;
	}
}
