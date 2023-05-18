/*REFERENCES: 
 Obtained from Practical 04*/

//We need to import util in order to implements interface,Iterable(!CANNOT just use the one in class with main method)
import java.util.*;
import java.io.*;//import io in order to implements interface,Serializable

public class DSALinkedList implements Serializable,Iterable
{
    //must return String instead of void. Otherwise, error: toString() in DSALinkedList cannot override toString() in Object(as toString() in Object return String)    
    //method overriding
    public String toString()
    {
        String allNodesInList = "";
        Iterator iter = this.iterator();//create a DSALinkedListIterator by using the returned default constructor
        while(iter.hasNext())
        {
            //No need typecast to String as java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String
            allNodesInList += (iter.next() + ",");
        }
        
        return allNodesInList;
    }
    
    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }
    
    private class DSALinkedListIterator implements Iterator
    {
        private DSAListNode iterateNext;
        //default constructor
        public DSALinkedListIterator(DSALinkedList list)
        {
            iterateNext = list.head;//!!private inner class can access the private field in the DSALinkedList
        }
        //Iterator interface implementation
        public boolean hasNext()//check if still have next node
        {
            return iterateNext != null;
        }
        
        //return the next element of the Collection(LinkedList)
        public Object next()
        {
            Object value = null;
            if(hasNext())
            {
                value = iterateNext.getValue();//get the value in node
                iterateNext = iterateNext.getNext();//assign the next node to "iterateNext"
            }
        return value;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException("Not support remove() method.");
        }
    }
    
	//private inner class
	private class DSAListNode implements Serializable
	{
		private Object value;
		private DSAListNode prev;
		private DSAListNode next;
		
		//alternate constructor
		public DSAListNode(Object value)
		{
			this.value = value;
			this.prev = null;
			this.next = null;			
		}
		public void setPrev(DSAListNode value)
		{
			this.prev = value;
		}
		public void setNext(DSAListNode value)
		{
			this.next = value;
		}
		public void setValue(Object value)
		{
            this.value = value;
		}
		public DSAListNode getPrev()
		{
			return this.prev;
		}
		public DSAListNode getNext()
		{
			return this.next;
		}
		public Object getValue()
		{
            return this.value;
		}
	}
	
	
	//all class fields and method for DSALinkedList class
	private DSAListNode head,tail;	

	public DSALinkedList()
	{
		head = null;
		tail = null;
	}
	
	public boolean isEmpty()
	{
        boolean empty = false;
        if(head == null)
        {
            empty = true;
        }
        return empty;
	}
	
	//insert as the first node in Linked List
	public void insertFirst(Object inValue)
	{
        //create a node with inValue first
        DSAListNode insert = new DSAListNode(inValue);
        
        //head == null means NO node in LinkedList       
        if(isEmpty())
        {
        //then we should set both head and tail to that node 1st
            head = insert;
            tail = insert;
        }
        //head.next == null means ONLY ONE node in LinkedList
        //but for insertFirst,both only one cases and many node cases are the same 
        else
        {
            //set head.prev = insert
            head.setPrev(insert);
            //set insert.next = head;
            insert.setNext(head);
            //set head = insert
            head = insert;
        }
        //System.out.println("insertFirst successfully!");
	}
	
	public void insertLast(Object inValue)
	{
        DSAListNode insert = new DSAListNode(inValue);

        if(isEmpty())
        {
            //If LinkedList is empty then we should set both head and tail to that node 1st
            head = insert;
            tail = insert;
        }
        //For insertLast,both only one cases and many node cases are the same 
        else
        {
            //connect two nodes together 1st
            tail.setNext(insert);
            insert.setPrev(tail);
            //set the new tail
            tail = insert;
        }        
	}
	
	public Object peekFirst()//same as getHead()
	{
        Object returned = null;
        try
        {
            
            if(isEmpty())
            {
                throw new NullPointerException("peekFirst(): Linked List is empty.Nothing to peek");            
            }
            else
            {
                returned = head.getValue();
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return returned;
	}
	
	public Object peekLast()//same as getTail()
	{
        Object returned = null;
        try
        {
            
            if(isEmpty())
            {
                throw new NullPointerException("peekLast(): Linked List is empty.Nothing to peek");
            }
            else
            {
                returned = tail.getValue();
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return returned;
	}
	
	public Object removeFirst()
	{
        Object remove = null;
        try
        {
            
            if(isEmpty())
            {
                throw new NullPointerException("removeFirst(): Linked List is empty.Nothing to remove");
            }
            //ONLY ONE node in LinkedList
            else if(head.getNext() == null)
            {
                remove = head.getValue();
                head = null;
                tail = null;
            }
            //MANY node in LinkedList
            else 
            {
                remove = head.getValue();
                //Don't touch tail,just re-adjust the head
                head = head.getNext();
                head.setPrev(null);
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return remove;
	}
	
	public Object removeLast()
	{
        Object remove = null;
        try
        {
            
            if(isEmpty())
            {
                throw new NullPointerException("removeLast(): Linked List is empty.Nothing to remove");
            }
            //ONLY ONE node in LinkedList
            else if(head.getNext() == null)
            {
                //same as remove = head.getValue() , only in this case la(as first n last node is the same node)
                remove = tail.getValue();
                head = null;
                tail = null;
            }
            //MANY node in LinkedList
            else 
            {
                remove = tail.getValue();
                //Don't touch head,just re-adjust the tail
                tail = tail.getPrev();
                tail.setNext(null);
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return remove;
	}
}
