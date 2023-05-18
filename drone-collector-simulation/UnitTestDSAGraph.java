import java.util.*;

public class UnitTestDSAGraph
{
	public static void main(String[] args)
	{
        DSAGraph graph_loc = new DSAGraph();//graph with location
        DSAGraph graph_loc_inv = new DSAGraph();
        DSAGraph graph_loc_inv_pro = new DSAGraph();
        DSAGraph graph_loc_inv_pro_ord = new DSAGraph();
        FileIO file = new FileIO("stores.csv");
        file.readLocation(graph_loc);
        file.readLocation(graph_loc_inv);
        file.readLocation(graph_loc_inv_pro);
        file.readLocation(graph_loc_inv_pro_ord);
        
        file = new FileIO("inventories.csv");
        file.readInventories(graph_loc_inv);
        file.readInventories(graph_loc_inv_pro);
        file.readInventories(graph_loc_inv_pro_ord);
        
        //read the catalogue file
        file.readProducts(graph_loc_inv_pro,"catalogue.csv");
        file.readProducts(graph_loc_inv_pro_ord,"catalogue.csv");
        
        //load order data
        file = new FileIO("order1.csv");
        file.readOrder(graph_loc_inv_pro_ord,"catalogue.csv");
        
        //System.out.println(graph_loc_inv_pro_ord);
        System.out.println("\n\n...............Test locationOverview.................");
        System.out.println("...............If the location data has NOT been loaded.................");
        DSAGraph newGraph = new DSAGraph();
        locationOverviewTest(newGraph);
        System.out.println("\n...............If the location data has been loaded.................");
        locationOverviewTest(graph_loc);
        
        inventoriesOverviewTest(newGraph,graph_loc,graph_loc_inv);
        
        productSearchTest(newGraph,graph_loc,graph_loc_inv_pro);
        
        findStoreInventoryTest(newGraph,graph_loc,graph_loc_inv_pro);
        
        findEdgeTest(newGraph,graph_loc);
        
        routeDisplayTest(newGraph,graph_loc,graph_loc_inv_pro,graph_loc_inv_pro_ord);
        
        //test basic graph methods
        addVertexTest();
        
        addEdgeTest();
        
        hasVertexTest();
        
        getVertexTest();
        
        getEdgeTest();
        
        isAdjacentTest();
	}
	
	public static void locationOverviewTest(DSAGraph graph)
	{
        
        try
        {
            if(graph.getGraphVerticesList().isEmpty())
            {
                throw new NullPointerException("PLEASE LOAD THE LOCATION DATAS FIRST");
            }
            else
            {
                Iterator edge_list = (graph.getGraphEdgeList()).iterator();
                DSAGraphEdge edge = (DSAGraphEdge)edge_list.next();
                System.out.println("TWO LOCATIONS          DISTANCE");
                System.out.println(edge.getLabel() + "         " + edge.getValue());
                while(edge_list.hasNext())
                {
                    edge = (DSAGraphEdge)edge_list.next();
                    System.out.println(edge.getLabel() + "         " + edge.getValue());
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());//"PLAESE LOADING THE (LOCATION + PRODUCT + INVENTORY)DATAS FIRST"
        }
	}
	
	public static void inventoriesOverviewTest(DSAGraph newGraph,DSAGraph graph_loc,DSAGraph graph_loc_inv)
	{    
        System.out.println("\n\n...............Test inventories overview.................");
        System.out.println("...............If the location data has NOT been loaded.................");
        newGraph.inventoryOverview();
        System.out.println("\n...............If the inventories data has NOT been loaded.................");
        graph_loc.inventoryOverview();
        System.out.println("\n...............If the location data and inventories data has been loaded.................");
        graph_loc_inv.inventoryOverview();
	}
	
	public static void productSearchTest(DSAGraph newGraph,DSAGraph graph_loc,DSAGraph graph_loc_inv_pro)
	{        
        System.out.println("\n\n...............Test Product Search.................");
        System.out.println("...............If the location data has NOT been loaded.................");
        newGraph.productSearch("TIP122");
        System.out.println("\n...............If the inventories data has NOT been loaded.................");
        graph_loc.productSearch("TIP122");
        System.out.println("\n...............If product code cannot be found in any store.................");
        graph_loc_inv_pro.productSearch("KEKEKKEKE");
        System.out.println("\n...............If product code can be found in the store(s).................");
        graph_loc_inv_pro.productSearch("TIP122");
	}
	
	public static void findStoreInventoryTest(DSAGraph newGraph,DSAGraph graph_loc,DSAGraph graph_loc_inv_pro)
	{
        System.out.println("\n\n...............Test findStoreInventory() methods.................");
        System.out.println("...............If the location data has NOT been loaded.................");
        newGraph.findStoreInventory("gadgetsRUs");
        System.out.println("\n...............If the inventories data has NOT been loaded.................");
        graph_loc.findStoreInventory("gadgetsRUs");
        System.out.println("\n...............If search for INVALID store name.................");
        graph_loc_inv_pro.findStoreInventory("GGGGGGG");
        System.out.println("\n...............If search for VALID store name.................");
        graph_loc_inv_pro.findStoreInventory("gadgetsRUs");
	}
	
	public static void findEdgeTest(DSAGraph newGraph,DSAGraph graph_loc)
	{
        System.out.println("\n\n...............Test findEdge() methods.................");
        System.out.println("...............If the location data has NOT been loaded.................");
        newGraph.findEdge("Home","CablesRUs");
        System.out.println("\n...............If one of the location entered is invalid.................");
        graph_loc.findEdge("HAHAHHAHAA","Home");
        graph_loc.findEdge("Home","HAHAHHAHAA");      
        System.out.println("\n...............If both locations entered are valid.................");
        DSAGraphEdge edge = graph_loc.findEdge("Home","CablesRUs");
        System.out.println("TWO LOCATIONS          DISTANCE");
        System.out.println(edge.getLabel() + "         " + edge.getValue());
	}
	
	public static void routeDisplayTest(DSAGraph newGraph,DSAGraph graph_loc,DSAGraph graph_loc_inv_pro,DSAGraph graph_loc_inv_pro_ord)
	{
        System.out.println("\n\n...............Test routeDisplay() methods.................");
        System.out.println("...............If the location data has NOT been loaded.................");
        newGraph.routeDisplay("12/2/21","Newton Crosby","NOVA Laboratory");
        System.out.println("\n...............If the inventories data has NOT been loaded.................");
        graph_loc.routeDisplay("12/2/21","Newton Crosby","NOVA Laboratory");    
        System.out.println("\n...............If the order data has NOT been loaded.................");
        graph_loc_inv_pro.routeDisplay("12/2/21","Newton Crosby","NOVA Laboratory");
        System.out.println("\n...............If no order has matched date,contact and address.................");
        graph_loc_inv_pro_ord.routeDisplay("12/2/21","nooooo","NOVA Laboratory");
        System.out.println("\n...............If all datas have been loaded.................");
        graph_loc_inv_pro_ord.routeDisplay("12/2/21","Newton Crosby","NOVA Laboratory");
        
	}
	
	public static void addVertexTest()
	{
        DSAGraph graph = new DSAGraph();
        System.out.println("\n\n...............Test addVertex() method.................");
        System.out.println("......Add an vertex with label: \"hi\",value: \"no\"..........");
        graph.addVertex("hi","no");
        System.out.println(".............If the vertex added is duplicated.............");      
        graph.addVertex("hi","no");
        System.out.println(graph);
        System.out.println(".............If the vertex added is NOT duplicated.............");
        graph.addVertex("not","gg");
        System.out.println(graph);
	}
	
	public static void addEdgeTest()
	{
        System.out.println("\n\n...............Test addEdge() method.................");
        DSAGraph graph = new DSAGraph();
        graph.addVertex("hi","no");
        graph.addVertex("not","gg");
        System.out.println(".............If one of the vertex does not exist.............");
        graph.addEdge("hi","invalid","hi-no",null,false);
        System.out.println(".............If the edge added is NOT duplicated.............");
        graph.addEdge("hi","not","hi-no",null,false);
        System.out.println(graph);
        System.out.println(".............If the edge added is DUPLICATED.............");
        graph.addEdge("hi","not","hi-no",null,false);
	}
	
	public static void hasVertexTest()
	{
        System.out.println("\n\n...............Test hasVertex() method.................");
        DSAGraph graph = new DSAGraph();
        System.out.println(".............If the graph is EMPTY.............");
        graph.hasVertex("hi");
        System.out.println(".............Add the vertex with label:\"hi\".............");
        graph.addVertex("hi","no");
        System.out.println(".............If the vertex is exist.............");
        System.out.println("(hasVertex(\"hi\")) = True:" + graph.hasVertex("hi"));
        
        System.out.println(".............If the vertex is NOT exist.............");
        System.out.println("(hasVertex(\"no\")) = false:" + graph.hasVertex("no"));
   
	}
	
	public static void getVertexTest()
	{
        System.out.println("\n\n...............Test getVertex() method.................");
        DSAGraph graph = new DSAGraph();
        System.out.println(".............If the graph is EMPTY.............");
        graph.getVertex("hi");
        System.out.println(".............Add the vertex with label:\"hi\".............");
        graph.addVertex("hi","no");
        System.out.println(".............If the vertex is exist.............");
        System.out.println(graph.getVertex("hi"));
        
        System.out.println(".............If the vertex is NOT exist.............");
        System.out.println(graph.getVertex("no"));
   
	}
	
	public static void getEdgeTest()
	{
        System.out.println("\n\n...............Test getEdge() method.................");
        DSAGraph graph = new DSAGraph();
        System.out.println(".............If the graph is EMPTY.............");
        graph.getEdge("hi");
        System.out.println(".............Add 2 vertex,one with label:\"hi\" and one with label: \"gg\" and add edge between them.............");
        graph.addVertex("hi","no");
        graph.addVertex("gg","kk");
        graph.addEdge("hi","gg","hi-gg",null,false);
        System.out.println(".............If the edge is exist.............");
        System.out.println(graph.getEdge("hi-gg"));
        
        System.out.println(".............If the edge is NOT exist.............");
        System.out.println(graph.getEdge("notExist"));   
	}
	
	public static void isAdjacentTest()
	{
        System.out.println("\n\n...............Test isAdjacent() method.................");
        DSAGraph graph = new DSAGraph();
        System.out.println(".............If the graph is EMPTY.............");
        graph.isAdjacent("hi","gg");
        
        System.out.println(".............Add 2 vertex,one with label:\"hi\" and one with label: \"gg\" and add edge between them.............");
        graph.addVertex("hi","no");
        graph.addVertex("gg","kk");
        graph.addEdge("hi","gg","hi-gg",null,false);
        
        System.out.println(".............If two vertexs are adjacent.............");
        System.out.println("isAdjacent(\"hi\",\"gg\") = true: " + graph.isAdjacent("hi","gg"));
        
        System.out.println(".............If two vertexs are NOT adjacent.............");
        System.out.println("isAdjacent(\"hi\",\"ntoExists\") = false: " + graph.isAdjacent("hi","notExists"));
	}
}
