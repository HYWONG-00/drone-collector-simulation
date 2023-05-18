/*REFERENCES: 
 Obtained from Practical 06*/

import java.util.*;
import java.io.*;//for interface Serializable

public class DSAGraph implements Serializable
{
    private DSALinkedList verticesList;//verticesList is all the vertexs u hv in the graph
    private DSALinkedList edgeList;
    
    public DSAGraph()
    {
        verticesList = new DSALinkedList();
        edgeList = new DSALinkedList();
    }
    
    //getter
    //get the verticeslist of the graph
    public DSALinkedList getGraphVerticesList()
    {
        return verticesList;
    }
    //get the edge list of the graph
    public DSALinkedList getGraphEdgeList()
    {
        return edgeList;
    }
    
    public void addVertex(String label,Object value)
    {
        //DSALinkedList productsList = new DSALinkedList();//(If "Home",will be orderList but datatype are the SAME)
        DSAGraphVertex newVertex = new DSAGraphVertex(label,value);//create a graph node first 
        
        //Actually, I just want to get my FIRST vertex by NOT using peekFirst() only (as peekFirst() if vertices list is empty AT THE BEGIN will STOP the program)
        Iterator iter = verticesList.iterator();
        DSAGraphVertex next = (DSAGraphVertex)iter.next();  
        
        //check if there are SAME label exists in the vertices list already.(!!!vertice list is NOT EMPTY oh)
        if(next != null && hasVertex(label))
        {
            //throw new MyException("We cannot assign two vertex with SAME label(" + label + ") in the graph. This label has exist already.");
            System.out.println("addVertex: This vertex with label(" + label + ") is EXIST already thus I will just DO NOTHING at here");
        }
        else//If NOT then,
        {
            verticesList.insertLast(newVertex);//add the new vertex into the verticesList
        }
        //System.out.println("addVertex: " + newVertex);
        //System.out.println("sortList(b4): " + verticesList);
        //sortVertexList(verticesList);
        //System.out.println("sortList(after): " + verticesList);
    }
    
    //add the edge
    public void addEdge(String label_1,String label_2,String edgeLabel,Object value,boolean directed)
    {
        
        //if it is directed then only 1 to 2 or 2 to 1 cannot hv both directed to each other
        try
        {
        //if it is undirected just point both the vertex to each other 
        DSAGraphVertex vertex_1 = getVertex(label_1);
        DSAGraphVertex vertex_2 = getVertex(label_2);
        
        //If edge is undirected,
            
                if(vertex_1 == null)
                {
                    throw new NullPointerException("addEdge(): Edge cannot be created between " + vertex_1.getLabel() + " and " + vertex_2.getLabel() + " as " + vertex_1.getLabel() + " is INVALID.");
                }
                else if(vertex_2 == null)
                {
                    throw new NullPointerException("addEdge(): Edge cannot be created between " + vertex_1.getLabel() + " and " + vertex_2.getLabel() + " as " + vertex_2.getLabel() + " is INVALID.");
                }
                else if(isAdjacent(label_1,label_2))
                {
                    throw new IllegalArgumentException("addEdge(): The edge with " + label_1 + " and " + label_2 + " has been added. No need to add second time.(Will just skip adding this edge into edgeList)" );
                }
                else
                {
                //create a new edge for label_1 to label_2 and label_2 to label_1
                DSAGraphEdge edge_1 = new DSAGraphEdge(vertex_1,vertex_2,edgeLabel,value,directed);
                
                //If edge is undirected. Then add both edges to the edgeList(in DSAGraph)
                edgeList.insertLast(edge_1);
                
                //add vertex_1 as adjacent vertex(in adjacent list of vertex_2) and vice versa
                vertex_1.addEdge(vertex_2);
                //System.out.println("addEdge1: " + vertex_1);
                vertex_2.addEdge(vertex_1);
                //System.out.println("addEdge2: " + vertex_2);
                }
            }
            catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        
    }
    
    //getter: check if we have this vertex in the vertices list or not
    public boolean hasVertex(String label)
    {
        
        boolean hasVertex = false;
        try
        {
        Iterator vertex_list = verticesList.iterator();
        //get the first vertex
        DSAGraphVertex next = (DSAGraphVertex)vertex_list.next(); 
        
        //!!!Be aware: we MUST check this before we use peekFirst()/peekLast() as if u check after peekFirst()/peekLast() yr program will immediately STOP if your vertices list is EMPTY
        //PART 1: check if NO vertex in the vertices list
        if(next == null)
        {
            throw new NullPointerException("hasVertex: No vertex in the vertices list(graph is empty) at this moment.It is impossible to have a vertex with matched label...");
        }
        
        DSAGraphVertex lastVertex = (DSAGraphVertex)verticesList.peekLast();//for PART 3 condition
        
        //PART 2: check the FIRST vertex in the vertices list
        if(label.equals(next.getLabel()))
        {
            hasVertex = true;
        }
        //PART 3: check the LAST vertex in the vertices list
        else if(label.equals(lastVertex.getLabel()))
        {
            hasVertex = true;
        }
        //PART 4: check the SECOND vertex to LAST SECOND vertex
        else
        {
         
            //If we have next vertex in the vertices list...(only 3 possible condition to fail this condition,one is we have NO/ONE/LAST vertex).          
            while(vertex_list.hasNext())
            {
                if(label.equals(next.getLabel()))
                {      
                    hasVertex = true;
                }
                next = (DSAGraphVertex)vertex_list.next();//move to the next vertex in the vertices list
            }
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return hasVertex;
    }
    
    //PROBLEM: when graph have no vertex, it does not throw NullPointerException.BUT the program will END but idk why
    public DSAGraphVertex getVertex(String label)
    {
        DSAGraphVertex vertex = null;
        
        Iterator vertex_list = verticesList.iterator();
        //to get the FIRST graph vertex
        DSAGraphVertex next = (DSAGraphVertex)vertex_list.next();   
        
        try
        {
        //PART 1: if there is NO vertex found in the list
        if(next == null)
        {
            throw new NullPointerException("getVertex: There is no vertex in the vertices list at the moment.Nothing to get...");
        }
        //PART 2: check the FIRST vertex in the graph has same label as the parameter label, then...
        else if(label.equals(next.getLabel()))//Actually we need to check this condition as if vertices list only have one vertex it will fail the condition of  while(vertex_list.hasNext())
        {
            //we just return the first vertex
            vertex = (DSAGraphVertex)verticesList.peekFirst();
        }
        else //If the first vertex has NO same label as the parameter label, then search thru the following vertices list n see whether we can found a vertex with matched label.
        {
            //PART 3: check from the SECOND node to the LAST SECOND node
            while(vertex_list.hasNext())
            {
                //Once you vertex_list.next() it will immediately move to the next vertex in the vertices list which is bad as u haven't check with its label.
                //System.out.println("next: " + vertex_list.next());
                //System.out.println("next: " + vertex_list.next());
                
                //System.out.println("next's label: " + next.getLabel());
                if(label.equals(next.getLabel()))
                {
                    //System.out.println("hehe");
                    vertex = (DSAGraphVertex)next;//If you vertex = next,then you get the current graph node. If you vertex = vertex_list.next(),then you get the node that is next to the current node.
                    //System.out.println(vertex);
                }
                next = (DSAGraphVertex)vertex_list.next();//move to the next vertex in the vertices list
            } 
            
            //PART 4: check the LAST vertex's label in the vertices list matches this lebel or not 
            DSAGraphVertex lastVertex = (DSAGraphVertex)verticesList.peekLast();
            if(label.equals(lastVertex.getLabel()))
            {
                //System.out.println("haha");
                vertex = (DSAGraphVertex)verticesList.peekLast();
            }
        }
        
        //If vertex returned is null, this means no vertex with matched label is found in the vertices list(doesn't mean that we have no vertex ya. Just does not have one that got the SAME label as this only)
        if(vertex == null)
        {
            throw new NullPointerException("getVertex: No vertex with matched label(" + label + ") is found in the vertice lists");
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    return vertex;
    }
    
    /*If like this u better think about how to make edgeLabel unique*/
    /*BUT RIGHT we still got 2 same edgeLabel if the edge is undirected KL-US,US-KL*/
    public DSAGraphEdge getEdge(String edgeLabel)
    {
        
        Iterator edge_list = edgeList.iterator();
        DSAGraphEdge next = (DSAGraphEdge)edge_list.next();//get the FIRST edge pair
        DSAGraphEdge edge = null;
        
        try
        {
        //CASE 1: edgeList is EMPTY
        if(next == null)
        {   
            throw new NullPointerException("getEdge: edgeList is EMPTY!(NO edge pair in the edgeList now!)");
        }
        //edgeList is NOT EMPTY
        else
        {
            //CASE 2: We found the edge pairs with matched label
            //PART 1: check the FIRST edge pair's edgeLabel are the same as entered edgeLabel or not
            if(edgeLabel.equals(next.getLabel()))
            {
                edge = next;//If yes,then we just return this edge nia
            }
            //PART 2: check the SECOND TO LAST edge pair's edgeLabel are the same as entered edgeLabel or not
            while(edge_list.hasNext())
            {
                next = (DSAGraphEdge)edge_list.next();//move to the next edge pair
                if(edgeLabel.equals(next.getLabel()))
                {
                    edge = next;
                }
            }
            
            //CASE 2: NO edge pairs with matched label found
            if(edge == null)
            {
                throw new NoSuchElementException("getEdge: NO edge pair with matched label(" + edgeLabel + ") found in the edgeList...");
            }
           
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
        return edge;
    }
    
    //SECOND WAY: !!!!!THIS IS FOR UNDIRECTED EDGE ONLY(if you want to make for directed, you can ONLY check ----- next.getFromVertex().getLabel().equals(label_1) && next.getToVertex().getLabel().equals(label_2))
    public boolean isAdjacent(String label_1,String label_2)
    {
        boolean isAdjacent = false;
        
        Iterator edge_list = edgeList.iterator();
        DSAGraphEdge next = (DSAGraphEdge)edge_list.next();//get the FIRST edge in the edgeList
        
        //CASE 1: NO edge found in the edgeList(of the graph) 
        if(next == null)
        {
            System.out.println("isAdjacent(): NO edge found in the edgeList(of the graph)!");
        }
        else
        {
            //CASE 2: we have edge in graph but does not hv the one that label_1 is adjacent with label_2
            //NO need to check as isAdjacent = false;
            
            //CASE 3: we have this edge in graph
            //PART 1: check the FIRST edge pair in the edgeList
            if((next.getFromVertex().getLabel().equals(label_1) && next.getToVertex().getLabel().equals(label_2)) || (next.getFromVertex().getLabel().equals(label_2) && next.getToVertex().getLabel().equals(label_1)))
            {
                isAdjacent = true;
            }
            //PART 2: check the SECOND TO LAST edge pair in the edgeList
            while(edge_list.hasNext() && isAdjacent == false)
            {
                next = (DSAGraphEdge)edge_list.next();//move to the NEXT edge pair in the edgeList
                if((next.getFromVertex().getLabel().equals(label_1) && next.getToVertex().getLabel().equals(label_2)) || (next.getFromVertex().getLabel().equals(label_2) && next.getToVertex().getLabel().equals(label_1)))
                {
                    isAdjacent = true;
                }
            }
            
        }
    return isAdjacent;
    }
    
    public String toString()
    {
        return "Vertices list of the graph: " + verticesList.toString() + "\nEdge list of the graph" + edgeList.toString();
    }
  
    
    public void printEdgePairLabel()
    {
        Iterator edge_list = edgeList.iterator();
        DSAGraphEdge edge = (DSAGraphEdge)edge_list.next();//get the FIRST edge pair
        
        if(edge != null)
        {
            System.out.println("printEdgePairLabel(): ");
            System.out.println("[" + edge.getFromVertex().getLabel() + "," + edge.getToVertex().getLabel() + "]");
        }
        while(edge_list.hasNext())
        {
            edge = (DSAGraphEdge)edge_list.next();//move to the NEXT edge pair
            if(edge != null)
            {
                System.out.println("[" + edge.getFromVertex().getLabel() + "," + edge.getToVertex().getLabel() + "]");
            }
        }
    }
    
    /***************************************************
    IMPLEMENTATION CODE FOR DRONE COLLECTOR
    *****************************************************/
    /***************************************************
    IMPLEMENTATION FOR OPTION 2: INVENTORY OVERVIEW
    *****************************************************/
    public void inventoryOverview()
    {
        try
        {
                
            //get ALL of the STORES stored in the graph.
            DSALinkedList storeList = this.getGraphVerticesList();
            Iterator store_list = storeList.iterator();
            DSAGraphVertex store = (DSAGraphVertex)store_list.next();//get the FIRST store in verticesList
                    
            //CASE 1: check that is that location datas have been loaded already?
            if(this.getGraphVerticesList().isEmpty())//store == null
            {
                throw new NullPointerException("PLEASE LOAD THE LOCATION DATAS FIRST");
            }
            else
            {
                //display the products from FIRST TO LAST SECOND store
                while(store_list.hasNext())
                {
                    //!!!Home is not the STORE, so if u got home then just skip it first
                    if(!store.getLabel().equals("Home"))
                    {
                        this.displayProducts(store);
                    }
                store = (DSAGraphVertex)store_list.next();//move to the NEXT STORE in verticesList
                }
                        
                this.displayProducts(store);
                        
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());//"PLAESE LOADING THE (LOCATION + PRODUCT + INVENTORY)DATAS FIRST"
        }
    }
    /***************************************************
    IMPLEMENTATION FOR OPTION 3: PRODUCT SEARCH
    *****************************************************/
    public void productSearch(String userProductCode)
    {
            try
            {            
                //2. loop thru ALL THE STORE and check if we have this product in THAT STORE OR NOT
                DSALinkedList verticesList = (DSALinkedList)this.getGraphVerticesList();
                Iterator vertex_list = verticesList.iterator();
                DSAGraphVertex store = (DSAGraphVertex)vertex_list.next();//get the FIRST store 
                //CASE 1: check that is that location datas have been loaded already?
                if(verticesList.isEmpty())//store == null
                {
                    throw new NullPointerException("PLEASE LOAD THE LOCATION DATAS FIRST");
                }
                else
                {
                    //PART 1: check the FIRST store(Does it has this product? If yes,print it out.)
                    Product product = searchProduct(userProductCode,store);
                    System.out.println("Store (" + store.getLabel() + "): " + product); 
                    //PART 2: check the SECOND TO LAST store(Does it has this product)
                    while(vertex_list.hasNext())
                    {
                        store = (DSAGraphVertex)vertex_list.next();//move to the NEXT store
                        product = searchProduct(userProductCode,store);
                        System.out.println("Store (" + store.getLabel() + "): " + product); 
                    }
                }
            }
            catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
    }
    
    //HELPER FUNCTION FOR OPTION 3
    //return the product details in THE STORE(if this product is exists in that store la)
	private Product searchProduct(String productCode,DSAGraphVertex store)
	{

        Product returnedProduct = null;
        boolean found = false;
        try
        {
        //CASE 1: the store entered is HOME (which does not store product at all. How can it has product inside)
        if(store.getLabel().equals("Home"))
        {
            throw new NullPointerException("Home does not have any product inside");
        }
        else
        {
            DSALinkedList productsList = (DSALinkedList)store.getValue();//get the product list of THE STORE  
            Iterator product_list = productsList.iterator();
            Product product = (Product)product_list.next();//get the FIRST product in the list
            
            //CASE 2: We found the product with that product code in THAT STORE
            //loop thru the product list in THE STORE and find the product you want to find
            //PART 1: check if the FIRST product is the one we want to find
            if(productCode.equals(product.getProductCode()))
            {
                returnedProduct = product;
                found = true;
            }
            //PART 2: check if the SECOND TO LAST product got the one we want to find
            while(product_list.hasNext() && !found)//loop until u find the item
            {
                product = (Product)product_list.next();//move to the NEXT product in the list
                if(productCode.equals(product.getProductCode()))
                {
                    returnedProduct = product;
                    found = true;
                }
            }
            
        }

        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    return returnedProduct;
	}
	
	/***************************************************
    IMPLEMENTATION FOR OPTION 4: FIND AND DISPLAY THE INVENTORY OF A STORE
    *****************************************************/	
    public void findStoreInventory(String findStore)
    {
        try
        {                  
            //2. find the store 
            DSAGraphVertex store = getVertex(findStore);
                    
            //CASE 1: This store does not exists(meaning that haven't load the store's data or the store name to find is INVALID)
            //I throw this because of null.getValue() => gives NullPointerException             
            if(store == null)
            {
                throw new NullPointerException("Store does not exists at the moment.");
            }
            //CASE 2: This store is (exists) but search for "Home". Since home does not have products at all. Just display a message
            else if(findStore.equals("Home"))
            {
                System.out.println("Home does not have any inventories");
            }
            //CASE 3: This store is exists => then we display the products with their inventories/stocks
            else
            {             
                //3. display the store
                //DSALinkedList productsList = (DSALinkedList)store.getValue();                        
                //System.out.println("Store (" + store.getLabel() + "): " + productsList);
                displayProducts(store);
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }
	//HELPER FUNCTION FOR OPTION 2: INVERNTORY OVERVIEW AND OPTION 4
	//To display the products in THE STORE
	private void displayProducts(DSAGraphVertex store)
	{
        DSALinkedList productsList = (DSALinkedList)store.getValue();
        Iterator product_list = productsList.iterator();
        Product product = (Product)product_list.next();//get the FIRST product in the productsList of THE STORE
        try
        {
            //CASE 1: productsList is EMPTY FOR THAT STORE
            if(product == null)
            {
                throw new NullPointerException("displayProducts(): No product exists in the store(" + store.getLabel() + "). TRY TO LOAD THE PRODUCT DATAS FIRST.");
            }
            else
            {
                //PART 1: display FIRST product in the STORE
                System.out.println("..................Inventories in store(" + store.getLabel() + ")..................");
                System.out.println("Product code: " + product.getProductCode() + ", Stock: " + product.getStockOnHand());
                //PART 2: display SECOND TO LAST product in the STORE
                while(product_list.hasNext())
                {
                    product = (Product)product_list.next();//move to the NEXT product in the productsList of THE STORE
                    System.out.println("Product code: " + product.getProductCode() + ", Stock: " + product.getStockOnHand());
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	/***************************************************
    IMPLEMENTATION FOR OPTION 5: FIND AND DISPLAY THE DISTANCE BETWEEN TWO LOCATIONS
    *****************************************************/	
	//HELPER FUNCTION FOR Option 5 : Find and display the distance between two locations
	public DSAGraphEdge findEdge(String location_1,String location_2)
    {
    DSAGraphEdge returned = null;
    try
    {          
        //1. get the edgeList of the droneCollector n make iterator
        DSALinkedList edgeList = (DSALinkedList)getGraphEdgeList();
        Iterator edge_list = edgeList.iterator();
        DSAGraphEdge edge = (DSAGraphEdge)edge_list.next();
                
        //CASE 1: locations/edges are null
        if(edgeList.isEmpty())//edge == null
        {
            throw new NullPointerException("Edge(two locations) does not exists at the moment = IMPOSSIBLE to find the distance.(PLEASE LOAD THE STORE DATA FIRST)");
        }
        //CASE 2: Invalid store name entered by the user
        else if(getVertex(location_1) == null)
        {
            throw new NullPointerException("FIRST LOCATION ENTERED JUST NOW IS INVALID!");
        }
        else if(getVertex(location_2) == null)
        {
            throw new NullPointerException("SECOND LOCATION ENTERED JUST NOW IS INVALID!");
        }
        //CASE 3: This edge is exists + 2 stores name exists => then we look for the distance between two location
        else
        {
            boolean found = false;
            //1. find the two stores in the edgeList
            //PART 1: CHECK if the vertexs in the FIRST edge pair are the SAME with these 2 stores
            //Home-CablesRUs || CablesRUs-Home are the SAME SINCE edge pair is UNDIRECTED
            if(location_1.equals(edge.getFromVertex().getLabel()) && location_2.equals(edge.getToVertex().getLabel()) || location_2.equals(edge.getFromVertex().getLabel()) && location_1.equals(edge.getToVertex().getLabel()))
            {
                //stop looping since we have found it
                found = true;
                returned = edge;
            }
            //PART 2: CHECK if the vertexs in the SECOND TO LAST edge pair are the SAME with these 2 stores
            while(edge_list.hasNext() && found == false)
            {
                edge = (DSAGraphEdge)edge_list.next();//move to the NEXT edge pair in the "droneCollector" graph
                if(location_1.equals(edge.getFromVertex().getLabel()) && location_2.equals(edge.getToVertex().getLabel()) || location_2.equals(edge.getFromVertex().getLabel()) && location_1.equals(edge.getToVertex().getLabel()))
                {
                    //stop looping since we have found it
                    found = true;
                    returned = edge;
                }
            }                    
        }
                
    }
    catch(NullPointerException e)
    {
        System.out.println(e.getMessage());
    }
    return returned;
    }
	
	/***************************************************
    IMPLEMENTATION FOR OPTION 6: FIND AND DISPLAY THE ROUTE FOR COLLECTING THE ORDER
    *****************************************************/	
	public void routeDisplay(String orderDate,String orderContact,String orderAddress)
	{
            try
            {
                 if(this.getGraphVerticesList().isEmpty())
                 {
                    throw new NullPointerException("PLEASE LOAD THE LOCATION DATA FIRST.");
                 }
                 else
                 {
                 DSAGraphVertex vertex = (DSAGraphVertex)((DSALinkedList)this.getGraphVerticesList()).peekLast();//just random get a vertex n
                 //look into its product list
                 DSALinkedList productsList = (DSALinkedList)vertex.getValue();
                 if(productsList.isEmpty())//peekFirst() == null
                 {  
                    throw new NullPointerException("PLEASE LOAD THE INVENTORY DATA FIRST.");
                 }
                 else
                 {
                    DSAGraph BFStree = this.breathFirstSearch();
                    BFStree.printEdgePairLabel();
                    DSALinkedList orderList = (DSALinkedList)(this.getVertex("Home")).getValue();
                    if(orderList.isEmpty())//.peekFirst() == null
                    {
                        throw new NullPointerException("PLEASE LOAD THE ORDER DATA FIRST.");
                    }
                    else
                    {
                        //find the order at here instead
                        Iterator order_list = orderList.iterator();
                        Order order = (Order)order_list.next();//GET THE FIRST ORDER FIRST
                        boolean findOrder = false;
                        //System.out.println(order.getDate().equals(orderDate));
                        //System.out.println(order.getContact().equals(orderContact));
                        //System.out.println(order.getAddress().equals(orderAddress));
                        if(order.getDate().equals(orderDate) && order.getContact().equals(orderContact) && order.getAddress().equals(orderAddress))
                        {
                            findOrder = true;
                            //System.out.println("found: " + order);
                        }
                        while(order_list.hasNext() && !findOrder)
                        {
                            order = (Order)order_list.next();
                            if(order.getDate().equals(orderDate) && order.getContact().equals(orderContact) && order.getAddress().equals(orderAddress))
                            {
                                findOrder = true;
                                //System.out.println("found: " + order);
                            }
                        }
                        //HOWEVER IF AT THE END STILL NOT FIND THE ORDER, THEN U NEED TO THROW EXCEPTION LIAO
                        if(!findOrder)
                        {
                            throw new NullPointerException("displayRoute(): Invalid order to search for.");
                        }
                        else
                        {
                            BFStree.displayRoute(order);
                        }
                    }
                 }
                 }
            }
            catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
	}
	
	//HELPER FUNCTION NEED BY OPTION 6: to find the path to go thru ALL stores
	private DSAGraph breathFirstSearch()
    {
    DSAGraph BFStree = null;

        Iterator vertex_list = verticesList.iterator();
        DSAGraphVertex next = (DSAGraphVertex)vertex_list.next();//get the FIRST vertex stored in the verticeList
        DSAQueue queue = new DSAQueue();
        
        //1. mark all vertices new and set T = { }
        next.clearVisited();//set FIRST vertex's visited as false
        //set SECOND TO LAST vertex's visited as false
        while(vertex_list.hasNext())
        {
            next = (DSAGraphVertex)vertex_list.next();
            next.clearVisited();
        }
        BFStree = new DSAGraph();//set T = { }
        
        //2. mark any one vertex v(next at here/nodeAtFrontQueue) to old
        next = (DSAGraphVertex)verticesList.peekFirst();//reset the next value(back to FIRST vertex)
        DSAGraphVertex nodeAtFrontQueue = next;//set the FIRST vertex as the start vertex
        nodeAtFrontQueue.setVisited();//mark it as visited vertex
        
        //3. insert (Q, v) //enqueue v(nodeAtFrontQueue) into Queue Q(queue)
        queue.enqueue(nodeAtFrontQueue);
        
        //4. while Q(queue) is nonempty do
        DSAGraphVertex newVertex = null;
        while(!queue.isEmpty())//same as queue.peek() != null
        {
            
            //5. v(nodeAtFrontQueue) = dequeue (Q(queue))
            nodeAtFrontQueue = (DSAGraphVertex)queue.dequeue();
            //6. for loop: vertex w in L[v] marked new do
            while(nodeAtFrontQueue.hasNewVertex())//I just check whether we still have "new" adjacent vertex or not(If yes, then we loop again).
            {
             
                //System.out.println(nodeAtFrontQueue+"/"+nodeAtFrontQueue.hasNewVertex());
                //get the adjacent vertex that haven't visited yet
                newVertex = nodeAtFrontQueue.getNewAdjacentVertex();
                
                //7. T = T ∪ {v(nodeAtFrontQueue),w(newVertex)}
                BFStree.addVertex(nodeAtFrontQueue.getLabel(),nodeAtFrontQueue.getValue());//I just simply add the Object value at here as I dk what to add in
                BFStree.addVertex(newVertex.getLabel(),newVertex.getValue());
                String edgeLabel = nodeAtFrontQueue.getLabel() + "-" + newVertex.getLabel();
                DSAGraphEdge found = getEdge(edgeLabel);
                if(found == null)
                {
                    edgeLabel = newVertex.getLabel() + "-" + nodeAtFrontQueue.getLabel();
                    found = getEdge(edgeLabel);
                }
                BFStree.addEdge(found.getFromVertex().getLabel(),found.getToVertex().getLabel(),found.getLabel(),found.getValue(),found.isDirected());//I only want from nodeAtFrontQueue to newVertex,(NOT newVertex to nodeAtFrontQueue?, IDK is this correct or wrong)
                
                //8. mark w(newVertex) = old // set visited flag to true
                newVertex.setVisited();
                
                //9. insert (Q,w) //enqueue w(newVertex) into Q(queue)
                queue.enqueue(newVertex);
            }
        }

    return BFStree;
    }
    
    //HELPER FUNCTION FOR Option 6: to display route for collecting order + display what product collected in each store + display unfilled order
    private void displayRoute(Order order)
    {
        try
        {
        Iterator edge_list = this.getGraphEdgeList().iterator();
        DSAGraphEdge edge = (DSAGraphEdge)edge_list.next();
        DSALinkedList forDisplay = new DSALinkedList();//create for display the route later on
        
        //getting the productsList(in order) from Home 
        DSAGraphVertex home = this.getVertex("Home");
        DSALinkedList productsList = order.getProductsList();
        boolean done = false;
        
        //start collecting the item
        if(!doneCollecting(order.getProductsList()))
        {
            //put inside here as I want to reset it everytime I loop
            Iterator product_list = productsList.iterator();
            //get the FISRT product in the ORDER
            Product productInOrder = (Product)product_list.next();
        
            //found the product from THAT STORE with matched product code
            Product found = searchProduct(productInOrder.getProductCode(),edge.getToVertex());
            
            //If this product in order have not been filled yet
            if(productInOrder.getStockOnHand() != 0)
            {
                //If we found the product in the store AND it still got stock at there
                if(found != null && found.getStockOnHand() != 0)
                {            
                    updateStock(found,productInOrder,edge,forDisplay);
                }
            }
            while(product_list.hasNext())
            {
                productInOrder = (Product)product_list.next();//get the FIRST product in the order
                found = searchProduct(productInOrder.getProductCode(),edge.getToVertex());
                //If this product in order have not been filled yet
                if(productInOrder.getStockOnHand() != 0)
                {
                    //If we found the product in the store AND it still got stock at there
                    if(found != null && found.getStockOnHand() != 0)
                    {
                        updateStock(found,productInOrder,edge,forDisplay);
                    }
                }
            }       
        }
        while(!doneCollecting(order.getProductsList()) && edge_list.hasNext())//stop loop when all products have been collected OR no more store to go(meaning that the remaining items in order are NOT filled as all store has out of stock for that product odi)
        {
            edge = (DSAGraphEdge)edge_list.next();//move to the NEXT store first. So that, if we haven't complete the orde, we can directly 
            //put inside here as I want to reset it everytime I loop
            Iterator product_list = productsList.iterator();
            //get the FISRT product in the ORDER
            Product productInOrder = (Product)product_list.next();
        
            //found the product from THAT STORE with matched product code
            Product found = searchProduct(productInOrder.getProductCode(),edge.getToVertex());
            
            //If this product in order have not been filled yet
            if(productInOrder.getStockOnHand() != 0)
            {
                //If we found the product in the store AND it still got stock at there
                if(found != null && found.getStockOnHand() != 0)
                {            
                    updateStock(found,productInOrder,edge,forDisplay);
                }
            }
            while(product_list.hasNext())
            {
                productInOrder = (Product)product_list.next();//get the FIRST product in the order
                found = searchProduct(productInOrder.getProductCode(),edge.getToVertex());
                //If this product in order have not been filled yet
                if(productInOrder.getStockOnHand() != 0)
                {
                    //If we found the product in the store AND it still got stock at there
                    if(found != null && found.getStockOnHand() != 0)
                    {
                        updateStock(found,productInOrder,edge,forDisplay);
                    }
                }
            }       
        }
        
        /******************************
        DISPLAY THE linkedlist(forDisplay),containing what drone collector collects from the store when passing thru the stores
        ******************************/
        if(forDisplay.isEmpty())
        {
            throw new NullPointerException("Nothing is collected from store.(Could be because of this order have been filled before...)");
        }
        else
        {
        Iterator iter = forDisplay.iterator();
        DSAGraphEdge collect = (DSAGraphEdge)iter.next();
        String prevFromStore = collect.getFromVertex().getLabel();
        String prevToStore = collect.getToVertex().getLabel();//just used for display(if toVertex store same as previous store then no need display again n again)
        
        DSAGraphVertex productCollected = (DSAGraphVertex)collect.getValue();
        System.out.println("...Go from " + collect.getFromVertex().getLabel() + " to " + collect.getToVertex().getLabel() + ".....");
        System.out.println("    Get " + productCollected.getLabel() + "with amount: " + productCollected.getValue());
        while(iter.hasNext())
        {
            collect = (DSAGraphEdge)iter.next();
            productCollected = (DSAGraphVertex)collect.getValue();
            if(!prevToStore.equals(collect.getToVertex().getLabel()))
            {   
                System.out.println("...From " + prevToStore + " go back to " + prevFromStore + ".....");
                System.out.println("...Go from " + collect.getFromVertex().getLabel() + " to " + collect.getToVertex().getLabel() + ".....");
            }
            System.out.println("    Get " + productCollected.getLabel() + "with amount: " + productCollected.getValue());
            prevFromStore = collect.getFromVertex().getLabel();
            prevToStore = collect.getToVertex().getLabel();//update the prev(for display)
            //System.out.println("prevFromStore:"  + prevFromStore + "prevToStore: " + prevToStore);
        }
        System.out.println("....Send these products to " + order.getAddress() + "......");
        //display the UNFILLED order list (if we have)
        if(!doneCollecting(order.getProductsList()))
        {
            int collecting = 0;
            //order = (Order)orderList.peekFirst();
            productsList = order.getProductsList();
            iter = productsList.iterator();
            Product product = (Product)iter.next();   
            System.out.println("........LIST OF UNFILLED ITEMS...........");
            if(product.getStockOnHand() != 0)
            {
                System.out.println(product.getProductCode() + ":" + product.getStockOnHand());
            }
            while(iter.hasNext())
            {   
                product = (Product)iter.next();
                if(product.getStockOnHand() != 0)
                {
                    System.out.println(product.getProductCode() + ":" + product.getStockOnHand());
                }
            }
        }
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }  
    
    //HELPER FUNCTION FOR option 6's helper function:displayRoute():check if the order have been filled completely or not
    private boolean doneCollecting(DSALinkedList orderList)
    {
        boolean done = false;
        int collecting = 0;
        Iterator iter = orderList.iterator();
        Product product = (Product)iter.next();        
        collecting = collecting + product.getStockOnHand();//add the FIRST product's stock(order)
        while(iter.hasNext())
        {
            product = (Product)iter.next();  
            collecting = collecting + product.getStockOnHand();//add SECOND TO LAST products' stocks(order)
        }
        
        //If collecting != 0 => we have NOT done complete the order yet. Otherwise, we are done with it
        if(collecting == 0)
        {
            done = true;
        }
    return done;
    } 
    
    //HELPER FUNCTION FOR option 6's helper function:displayRoute():update the stock number in both order n the store
    private void updateStock(Product found,Product productInOrder,DSAGraphEdge edge,DSALinkedList forDisplay)
    {
        DSAGraphVertex home = this.getVertex("Home");
        //update the stock details for the product in that store & order details in home
        //System.out.println("B4 CHANGE: " + found.getProductCode() + " in " + edge.getToVertex().getLabel() + "= " + found.getStockOnHand());
        int changeStock;
        if(productInOrder.getStockOnHand() > found.getStockOnHand())//means we need to take all 
        {
                
            //insert for later display first(I create edge not aims to add into graph, just want to save the thing i collect from the store)
            DSAGraphVertex vertex = new DSAGraphVertex(found.getProductCode(),found.getStockOnHand());//This represent the product code that is taken from the store
            DSAGraphEdge edgeForDisplay = new DSAGraphEdge(edge.getFromVertex(),edge.getToVertex(),edge.getLabel(),vertex,edge.isDirected());
            forDisplay.insertLast(edgeForDisplay);
            //System.out.println("edgeForDisplay" + edgeForDisplay);
                
            //update the stock details for both store and the order
            changeStock = productInOrder.getStockOnHand() - found.getStockOnHand();  
            edge.getToVertex().changeStock(found.getProductCode(),0);//update the product's stock in the store
            productInOrder.setStockOnHand(changeStock);//update the order details in home    
            //System.out.println(changeStock); 
        }
        else if(productInOrder.getStockOnHand() == found.getStockOnHand())
        {
            //insert for later display first(I create edge not aims to add into graph, just want to save the thing i collect from the store)
            DSAGraphVertex vertex = new DSAGraphVertex(found.getProductCode(),found.getStockOnHand());//This represent the product code that is taken from the store
            DSAGraphEdge edgeForDisplay = new DSAGraphEdge(edge.getFromVertex(),edge.getToVertex(),edge.getLabel(),vertex,edge.isDirected());
            forDisplay.insertLast(edgeForDisplay);
                    
            //update the stock details for both store and the order
            edge.getToVertex().changeStock(found.getProductCode(),0);//update the product's stock in the store
            productInOrder.setStockOnHand(0);//update the order details in home    
        }
        else//productInOrder.getStockOnHand() < found.getStockOnHand()
        {
            //insert for later display first(I create edge not aims to add into graph, just want to save the thing i collect from the store)
            DSAGraphVertex vertex = new DSAGraphVertex(found.getProductCode(),productInOrder.getStockOnHand());//This represent the product code that is taken from the store
            DSAGraphEdge edgeForDisplay = new DSAGraphEdge(edge.getFromVertex(),edge.getToVertex(),edge.getLabel(),vertex,edge.isDirected());
            forDisplay.insertLast(edgeForDisplay);
                    
            changeStock = found.getStockOnHand() - productInOrder.getStockOnHand();
            edge.getToVertex().changeStock(found.getProductCode(),changeStock);   
            productInOrder.setStockOnHand(0);//update the order details in home   
            //System.out.println(changeStock); 
        }                    
        //System.out.println(home);    
        //System.out.println("AFTER CHANGE: " + found.getProductCode() + " in " + edge.getToVertex().getLabel() + "= " + found.getStockOnHand());
        
    }
}
