/*REFERENCES: 
 Obtained from Practical 06*/

import java.util.*;
import java.io.*;//for interface Serializable

public class DSAGraphVertex implements Serializable
{
	private String label;//like the key in binary tree
	private Object value;//Value for vertex is DSALinkedList productsList(lie Product[])(If "Home",will be orderList but datatype are the SAME)
	private DSALinkedList adjacentList;
	private boolean visited;
    
	public DSAGraphVertex(String label,Object value)
	{
        //I dun want to create a vertex that either label is null...
        if(label == null)
        {
            throw new NullPointerException("When creating the vertex: label stored cannot be NULL!!");
        }
        else
        {
            setLabel(label);
            setValue(value);
            //productsList = new DSALinkedList();
            adjacentList = new DSALinkedList();
            visited = false;
        }
	}
	
	//mutator: add the edge(just add the node it links to)
	public void addEdge(DSAGraphVertex vertex)
	{
        adjacentList.insertLast(vertex);
	}
	
	public void setLabel(String label)
	{
        if(label == null)
        {
            throw new NullPointerException("When creating the vertex: label stored cannot be NULL!!");
        }
        else
        {
            this.label = label;
        }
	}
	
	public void setValue(Object value)
	{
        this.value = value;
	}
	
	//getter
	public String getLabel()//like key in binary tree
	{
        return this.label;
	}	
	
	public Object getValue()//like value in binary tree
	{
        return this.value;
	}
	
	public DSALinkedList getAdjacentLinkedList()
	{
        return adjacentList;
	}
	
	//getter: to get all of the adjacent vertex's labels (of this vertex)
	public String getAdjacentListLabel()
	{
        //To get all the labels of the adjacentList
        Iterator adjacent_list = adjacentList.iterator();
        String adjacent = "";
        //get the first vertex
        DSAGraphVertex next = (DSAGraphVertex)adjacent_list.next(); 
        /*
            We cannot have String adjacent = next.getLabel();
            as if we do this, the program will STOP when the graph has NO vertex at all(at the BEGIN) as we cannot have null.getLabel();
        */
        //If we have NO vertex in adjacent list
        if(next == null)
        {
            adjacent = " ";
        }        
        //store the FIRST vertex's label in the adjacent list(of this vertex) into the string
        else
        {
            adjacent += next.getLabel() + ",";
        }
        //to make a string(adjacent) that contains SECOND TO LAST vertex's labels in the adjacent list(used for printing)
        while(adjacent_list.hasNext())//It is fine even if FIRST adjacent vertex is null as null != null return false(u go see hasNext())
        {        
            next = (DSAGraphVertex)adjacent_list.next();//move to the next vertex in the adjacent_list
            //This one MUST put AFTER the next = adjacent_list.next(), as if u don't, you start to store the FIRST vertex again (instead of SECOND vertex) in the adjacent list 
            adjacent += next.getLabel() + ",";
        }
    return adjacent;
	}
	
	public String toString()
	{
        return "\nlabel: " + this.label + " value: " + this.value + " adjacentList:(" + getAdjacentListLabel() + ") visited: " + this.visited + "\n";
	}
	
	//for searching later(skip first)
	public void setVisited()
	{
        visited = true;
	}	
	public void clearVisited()
	{
        visited = false;
	}
	public boolean getVisited()
	{
        return visited;
	}
		
    //need these two methods for breadthFirstSearch()
    public boolean hasNewVertex()
    {
    
        boolean hasNewVertex = false;
        try
        {
        Iterator adjacent_list = adjacentList.iterator();
        DSAGraphVertex next = (DSAGraphVertex)adjacent_list.next(); //get the FIRST adjacent vertex
        if(adjacentList.isEmpty())
        {
            throw new NullPointerException("Adjacent list is EMTPY.");
        }
        else
        {
        //PART 1: check if the FIRST adjacent vertex is NOT YET VISITED
        if(!next.getVisited())
        {
            hasNewVertex = true;
        }
        
            //PART 2: check if the SECOND TO LAST adjacent vertex are NOT VISITED yet
            while(adjacent_list.hasNext())
            {        
                next = (DSAGraphVertex)adjacent_list.next();//move to the next vertex in the adjacent_list                
                if(!next.getVisited())
                {
                    hasNewVertex = true;
                }
            }
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        return hasNewVertex;
    }
    
    public DSAGraphVertex getNewAdjacentVertex()
    {
        Iterator adjacent_list = adjacentList.iterator();
        DSAGraphVertex next = (DSAGraphVertex)adjacent_list.next(); //get the FIRST adjacent vertex
        DSAGraphVertex newVertex = null;
        boolean findVertex = false;

        //PART 1: check if the FIRST adjacent vertex is NOT YET VISITED
        if(!next.getVisited())
        {
            newVertex = next;
            findVertex = true;
        }
        else
        {
            //PART 2: loop thru SECOND TO LAST adjacent vertex and get the vertex with visited = false
            while(adjacent_list.hasNext() && findVertex == false)
            {   
            
                next = (DSAGraphVertex)adjacent_list.next();//move to the next vertex in the adjacent_list
                
                if(!next.getVisited())
                {
                    newVertex = next;
                    findVertex = true;
                }
                
            }
        }
    return newVertex;
    }
	
	/**********************************************
	IMPLEMENTATION FOR DRONE COLLECTOR
	*********************************************/
	//setter
	//add new product into the productsList(FOR ALL THE STORE EXCEPT "Home")
	public void addProduct(String productCode,int stockOnHand,String description,String qty_1,String qty_10,String qty_25)//Product newProduct
	{

        try
        {
            //create the product only when we have product code!
            if(productCode == null)// newProduct.getProductCode() == null
            {
                throw new NullPointerException("productCode stored cannot be null!");
            }   
            //If duplicate product code is entered, then SKIP it
            else if(isDuplicate(productCode))//isDuplicate(newProduct.getProductCode())
            {
                throw new IllegalArgumentException("Cannot store duplicate productCode!Product Code (" + productCode + ")");
            }
            else
            {
                //1. create ONE new product
                Product newProduct = new Product(productCode,stockOnHand,description,qty_1,qty_10,qty_25);
                
                //2. then insert it into the value(productsList)
                DSALinkedList productsList = (DSALinkedList)this.getValue();
                productsList.insertLast(newProduct);
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
	
	//setter 
	//change the stock number for THE DESIRED productCode at the DESIRED store(when u call the function, the vertex is the DESIRED store, "DESIRED store".changeStock(which product code,what stock number))
	public void changeStock(String productCode,int changeStock)
	{
        try
        {
            //create the product only when we have product code!
            //CASE 1 & 2 CHECK USER INPUT
            //CASE 1: productCode(for THE product in THIS store) we want to look for is null
            if(productCode == null)
            {
                throw new NullPointerException("productCode stored cannot be null!");
            }
            //CASE 2: stock number that user want to change to is -ve number
            else if(changeStock < 0)
            {
                throw new IllegalArgumentException("stockOnHand(/changeStock) cannot be negative!");        
            }  
           //CASE 3: No product(with THIS productCode) found in the product list of THE store
            else if(getProduct(productCode) == null)
            {
               throw new NullPointerException("No product with THIS productCode(" + productCode + ") is found in the store(" + this.label + ")");
            }            
            //CASE 4: change the product's stock
            else
            {
                //1. find the product with THIS productCode in THIS STORE
                Product product = getProduct(productCode);
                //2. change the product's stock number
                product.setStockOnHand(changeStock);
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
	
	//get THE ENTIRE PRODUCT with this product code in THIS STORE
	private Product getProduct(String productCode)
	{
        Product returnedProduct = null;
        DSALinkedList productsList = (DSALinkedList)this.getValue();
        Iterator products_list = productsList.iterator();
        Product next = (Product)products_list.next();//get the FIRST product in the value(productsList)
        boolean found = false;
        
        try
        {
            //CASE 1: the productList is EMPTY(NO product exists in this store)
            if(next == null)
            {
                throw new NullPointerException("getProduct(): value(productsList) is EMPTY at the moment = NO product exists in THIS STORE now");
            }
            //CASE 2: start find the product
            else
            {
            
                //PART 1: check if the FIRST product in the productList has the SAME productCode
                if(productCode.equals(next.getProductCode()))
                {
                    returnedProduct = next;
                    found = true;
                }
                else
                {
                    while(products_list.hasNext() && !found)
                    {
                        next = (Product)products_list.next();
                        if(productCode.equals(next.getProductCode()))
                        {
                            returnedProduct = next;
                            found = true;
                        }
                    }
                }
            }
            
            //CASE 3: CANNOT find THIS product in THIS STORE
            if(returnedProduct == null)
            {
                throw new NullPointerException("getProduct(): Cannot find this product with productCode(" + productCode + ") in the store...");
            }                
        }
        catch(NullPointerException e)
        {   
            System.out.println(e.getMessage());
        }
        return returnedProduct;
	}
	
	//check if we have duplicate key
	private boolean isDuplicate(String productCode)
	{
        boolean duplicate = false;
        DSALinkedList productsList = (DSALinkedList)getValue();
        Iterator product_list = productsList.iterator();
        Product product = (Product)product_list.next();//get the FIRST product in the product list
        
        //CASE 1: productList is EMPTY
        if(productsList.isEmpty())// product == null
        {
            duplicate = false;
        }
        //CASE 2: productList is NOT EMPTY(then check for duplicateness)
        else
        {
            //PART 1: check the FIRST product in THE STORE(Does it has same product code as the FIRST product)
            if(productCode.equals(product.getProductCode()))
            {
                duplicate = true;
            }
            else
            {
                //PART 2: check the SECOND TO LAST product in THE STORE(Does it has same product code as the SECOND TO LAST product)
                while(product_list.hasNext())
                { 
                    product = (Product)product_list.next();
                    if(productCode.equals(product.getProductCode()))
                    {
                        duplicate = true;
                    }
                }
            }
        }
        
        return duplicate;
	}
	
	
}
