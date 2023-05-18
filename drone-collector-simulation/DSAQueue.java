/*REFERENCES: 
 Obtained from Practical 04*/

//If don't import this library, error cannot find symbol, class Iterator.
import java.util.*;

/*
I dun have implements Iterable now,after Mr.Suresh's lesson as he says just create toString() method in DSALinkedList. Thus, I just use the toString() method in DSALinkedList.

If you implements Iterable, you must implement abstract method, Iterator iterator() (go see java api)
*/
public class DSAQueue //implements Iterable
{
	private DSALinkedList list;
	private int count;
	
	public DSAQueue()
	{
        list = new DSALinkedList();
        count = 0;
    }
	
	public void enqueue(Object value)
	{
        try
        {
            if(validateObject(value))
            {
                count = count + 1;
                list.insertLast(value);
            }
            else
            {
                throw new NullPointerException("Object entered cannot be null!");
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	public Object dequeue()
	{
        Object returnedValue = null;
        try
        {   
        if(isEmpty())//list.peekFirst() == null
        {
            //no use as if really isEmpty(), in DSALinkedList,it will throw exception
            throw new NullPointerException("Queue is empty.Nothing to dequeue.");
        }
        else
        {
            count = count - 1;
            returnedValue = list.removeFirst();
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return returnedValue;
	}
	
	//accessor
	public int getCount()
	{
        return count;
	}
	
	public Object peek()
	{
        Object returned = null;
        try
        {
   
            if(isEmpty())
            {
                throw new NullPointerException("Queue is empty.Nothing to peek.");
            }
            else
            {
                returned = list.peekFirst();
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return returned;
	}
	
	//no isFull() method as LinkedList have no limited size
	
	public boolean isEmpty()
	{
        boolean empty = false;
        if(list.isEmpty())//list.peekFirst() == null
        {
            empty = true;
        }
        return empty;
	}
	
	/*
	public Iterator iterator()
	{
        return list.iterator();
	}*/
	
	public String toString()
	{
        //just call toString in DSALinkedList
        //In toString() of DSALinkedList, it will construct an instance,iter for DSALinkedListIterator(list) which used to iterate through THIS list
        return list.toString();
	}
	
	private boolean validateObject(Object value)
	{
        return value != null;
	}
}
