public class UnitTestDSAGraphVertex
{
	public static void main(String[] args)
	{
	
		System.out.println("......Test alternative constructor......");
		System.out.println("......If the label is NOT null......");
		DSAGraph graph = new DSAGraph();
		DSALinkedList productsList_0 = new DSALinkedList();
        DSALinkedList productsList_1 = new DSALinkedList();
		graph.addVertex("hi",productsList_0);
		graph.addVertex("not",productsList_1);
		graph.addEdge("hi","not","hi-not",null,false);
		
		DSAGraphVertex vertex_1 = graph.getVertex("hi");
		DSAGraphVertex vertex_2 = graph.getVertex("not");
			
		hasNewVertexTest(vertex_1,vertex_2);
        
        vertex_2.clearVisited();
        
        getNewAdjacentVertexTest(vertex_1,vertex_2);
        
        addProductTest(graph);
        
        changeStockTest(graph);
	}
	
	public static void hasNewVertexTest(DSAGraphVertex vertex_1,DSAGraphVertex vertex_2)
	{
        System.out.println("......Test hasNewVertex() method......");
		System.out.println("......If the vertex has \"new\" adjacent vertex......");
		System.out.println("...vertex with label \"hi\" has new vertex = true: " + vertex_1.hasNewVertex());
		System.out.println("......If the vertex has NO \"new\" adjacent vertex......");
		vertex_2.setVisited();
		System.out.println("...vertex with label \"hi\" has new vertex = false: " + vertex_1.hasNewVertex());
	}
	
	public 	static void getNewAdjacentVertexTest(DSAGraphVertex vertex_1,DSAGraphVertex vertex_2)
	{
        System.out.println("......Test getNewAdjacentVertex() method......");
		System.out.println("......If the vertex has \"new\" adjacent vertex......");
		System.out.println(vertex_1.getNewAdjacentVertex());
		System.out.println("......If the vertex has NO \"new\" adjacent vertex......");
		vertex_2.setVisited();
		System.out.println(vertex_1.getNewAdjacentVertex());
	}
	
	public static void addProductTest(DSAGraph graph)
	{
        DSAGraphVertex vertex_1 = graph.getVertex("hi");
        System.out.println(vertex_1);
        System.out.println("......Test addProduct() method......");
		System.out.println("......If the product code is null......");
        vertex_1.addProduct(null,0,null,null,null,null);
        System.out.println("......If the product code is NOT null......");
        vertex_1.addProduct("WV7349",10,null,null,null,null);
        System.out.println(vertex_1);
        System.out.println("......If the product code is duplicated with the existing product in the vertex......");
        vertex_1.addProduct("WV7349",10,null,null,null,null);
        System.out.println(vertex_1);
	}
	
	public static void changeStockTest(DSAGraph graph)
	{
        DSAGraphVertex vertex_1 = graph.getVertex("hi");
        System.out.println("......Test changeStock() method......");
		System.out.println("......If the product code is null......");
        vertex_1.changeStock(null,500);
        System.out.println("......If the stock number to change is negative value......");
        vertex_1.changeStock("WV7349",-1);
        System.out.println("......If NO product with the same product code......");
        vertex_1.changeStock("hiiii",500);
        System.out.println(vertex_1);
        System.out.println("......If product with the same product code is found and the stock number to change is positive value......");
        vertex_1.changeStock("WV7349",500);
        System.out.println(vertex_1);
	}
	
}
