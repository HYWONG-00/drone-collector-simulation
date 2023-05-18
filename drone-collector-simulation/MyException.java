import java.util.*;

public class MyException extends Exception 
{
	private String error;
	//constructor methods for throwing exception.
	public MyException(String message)
	{
		super(message);
		this.error="Something bad Happened";
	}
	public MyException(String message, String error)
	{
		super(message);
		this.error=error;
	}
	public String getError()
	{
		return this.error;
	}
}