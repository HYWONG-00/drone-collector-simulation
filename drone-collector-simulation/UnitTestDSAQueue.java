public class UnitTestDSAQueue
{
	public static void main(String[] args)
	{
		DSAQueue queue = new DSAQueue();
		System.out.println(".....Test isEmpty() method......");
		System.out.println("isEmpty = true:" + queue.isEmpty());
		
		System.out.println(".....Test enqueue() method......");
		System.out.println(".....If the object enqueue is null......");
		queue.enqueue(null);
		System.out.println(".....If the object enqueue is not null......");
		queue.enqueue("hi");
		System.out.println(queue);
		
		System.out.println(".....Test isEmpty() method......");
		System.out.println("isEmpty = false:" + queue.isEmpty());
		
		System.out.println(".....Test peek() method......");	
		System.out.println("....If the queue is not empty.....");	
		System.out.println(queue.peek());
		queue.dequeue();
		System.out.println("....If the queue is empty.....");	
		System.out.println(queue.peek());
		queue.enqueue("hi");
		
		System.out.println(".....Test dequeue() method......");	
		System.out.println("....If the queue is not empty.....");	
		System.out.println(queue.dequeue());
		System.out.println("....If the queue is empty.....");	
		System.out.println(queue.dequeue());
	}
}
