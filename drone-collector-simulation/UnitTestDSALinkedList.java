import java.util.*;

public class UnitTestDSALinkedList
{
	public static void main(String[] args)
	{
		DSALinkedList list = new DSALinkedList();
		
		System.out.println("..........Test isEmpty().............");
		System.out.println("Answer:" + list.isEmpty());
		
		System.out.println("..........Test insertFirst().............");
		list.insertFirst("hi");
		System.out.println(list.toString());
		list.insertFirst(1);
		System.out.println(list);
		list.insertFirst('c');
		System.out.println(list);
		
		System.out.println("..........Test peekFirst().............");
		System.out.println(list.peekFirst());
		
		System.out.println("..........Test removeFirst().............");
		System.out.println(list.removeFirst());
		System.out.println(list.removeFirst());
		System.out.println(list.removeFirst());
		System.out.println(list.removeFirst());
		
		System.out.println("peekFirst()" + list.peekFirst());
		
		System.out.println("..........Test insertLast().............");
		list.insertLast("hi");
		System.out.println(list.toString());
		list.insertLast(1);
		System.out.println(list);
		list.insertLast('c');
		System.out.println(list);
		
		System.out.println("..........Test isEmpty().............");
		System.out.println("Answer:" + list.isEmpty());
		
		System.out.println("..........Test peekLast().............");
		System.out.println(list.peekLast());
		
		System.out.println("..........Test removeLast().............");
		System.out.println(list.removeLast());
		System.out.println(list.removeLast());
		System.out.println(list.removeLast());
		System.out.println(list.removeLast());
		
	}
}
