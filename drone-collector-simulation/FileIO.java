import java.io.*;
import java.util.*;

public class FileIO 
{
    String filename;
	public FileIO(String filename)
	{
		this.filename = filename;
	}
	
	public void readLocation(DSAGraph graph)
	{
        readFile(graph,filename,1,null);//null as we dun need this in the readFile funciton
    
	}
	
	public void readInventories(DSAGraph graph)
	{    
        //int count = countElementCSV(filename);
        readFile(graph,filename,2,null);
   
	}
	
	public void readProducts(DSAGraph graph,String catalogueFile)
	{    
        //int count = countElementCSV(filename);
        readFile(graph,filename,3,catalogueFile);
   
	}
	
	public void readOrder(DSAGraph graph,String catalogueFile)
	{
        readFile(graph,filename,4,catalogueFile);
	}
	
	public DSAGraph readObject(DSAGraph graph)
    {
        FileInputStream fileStream;
        ObjectInputStream objStream;
        try
        {
            fileStream = new FileInputStream(filename);
            objStream = new ObjectInputStream(fileStream);
            graph = (DSAGraph)objStream.readObject();
            System.out.println("GRAPH IN THE OBJECT FILE HAS BEEN SUCCESSFULLY READ NOW");
            System.out.println("...........THIS IS THE GRAPH IN THE OBJECT FILE...........");
            //System.out.println(graph);
            
            objStream.close();
            fileStream.close();
        }
        //If not catch this exception, you will have error: cannot find symbol: class Serializable
        catch(NotSerializableException e)
		{
            //attempting to serialize or deserialize an object that does not implement the java.io.Serializable interface. 
            System.out.println(e.getMessage());
		}
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		catch(ClassNotFoundException e)//throw when we try to load a class, if the class loader is not able to find the class at the specified path
		{
            //The class names you pass to these methods should be accurate
            //The specified class (along with the package) should be either in the current directory or, its path should be listed in the environment variable classpath.
			System.out.println("Class DSAGraph not found" + e.getMessage());
		}
		catch (Exception e) 
		{
 			throw new IllegalArgumentException("Unable to load object from file"); 
 		}
    return graph;
    }
	
	public void writeObject(DSAGraph graph)
    {
        try
        {
            //open the file to write
            FileOutputStream fileStream = new FileOutputStream(filename);
            ObjectOutputStream objStream = new ObjectOutputStream(fileStream);
            objStream.writeObject(graph);
            
            fileStream.close();
            
        }
        catch(IOException e)
		{	
			System.out.println(e.getMessage()); 
		}
		catch(Exception e)
		{	
			throw new IllegalArgumentException("Unable to save object to file"); 
		}
    }
	
	private static void readFile(DSAGraph graph,String filename,int action,String catalogueFile)
	{
		FileInputStream fileStream;
        InputStreamReader reader;
        BufferedReader bufReader;
        String[] csvLine;
        String line;

        try
        {
            fileStream = new FileInputStream(filename);//open the file you want to read
            reader = new InputStreamReader(fileStream);
            bufReader = new BufferedReader(reader);
            
            
                switch(action)
                {
                    case 1:
                    line = bufReader.readLine();//read first line
                    while(line != null)
                    {
                        
                        csvLine = line.split(",");
                        processLocation(csvLine,graph);
                        line = bufReader.readLine();//read next line
                    }   
                    break;
                    
                    case 2:
                        line = bufReader.readLine();//read first line
                        line = bufReader.readLine();//read second line
                    
                        //CASE 1: CHECK IS THAT STORE DATA HAVE BEEN ENTERED ALREADY?
                        if(graph.getGraphVerticesList().isEmpty())//graph.getVertex("Home") == null
                        {
                            System.out.println("PLEASE LOAD THE LOCATION DATA FIRST");
                            
                        }
                        //START TO LOAD INVENTORY DATA(inventories.csv SECOND COLUMN,productCode + THIRD COLUMN,inventory)
                        else
                        {
                            while(line != null)
                            {
                                csvLine = line.split(",");            
            
                                //find the suitable vertex
                                DSAGraphVertex store = graph.getVertex(csvLine[0]);
                                
                                //then add THE inventory details into the CORRESPONDING product
                                store.addProduct(csvLine[1],Integer.parseInt(csvLine[2]),null,null,null,null);
                    
                                line = bufReader.readLine();//read next line
                            }
                        }
                    break;
                    
                    case 3:             
                        
                        //1. loop thru ALL THE STORE and update EACH PRODUCTS' details
                        DSALinkedList verticesList = (DSALinkedList)graph.getGraphVerticesList();
                        Iterator vertex_list = verticesList.iterator();
                        DSAGraphVertex store = (DSAGraphVertex)vertex_list.next();//get the FIRST "store",Home
                        //CASE 1: CHECK IS THAT STORE DATA HAVE BEEN ENTERED ALREADY?
                        if(graph.getGraphVerticesList().isEmpty())
                        {
                                throw new NullPointerException("PLEASE LOAD THE LOCATION DATAS FIRST");
                        }
                        //need to check whether product data load odi or not: check inside the udpateProductDetails odi
                        else
                        {
                            
                            //2. save the details for the catalogueFile FIRST
                            DSALinkedList catalogue = processProductDetails(catalogueFile);
                            
                            //3. START loop thru the STORE and update EACH OF THE PRODUCTS in THE STORE
                            if(!store.getLabel().equals("Home"))
                            {
                                updateProductDetails(store,catalogue);
                            }
                            while(vertex_list.hasNext())
                            {
                                store = (DSAGraphVertex)vertex_list.next();//move to the NEXT store
                                if(!store.getLabel().equals("Home"))
                                {
                                    updateProductDetails(store,catalogue);
                                }
                            }
                        }           
                       
                    break;
                    
                    
                    
                    case 4:
                    try
                    {
                        //CASE 1: CHECK IS THAT STORE DATA HAVE BEEN ENTERED ALREADY?(So that, we have "Home")
                        if(graph.getGraphVerticesList().isEmpty())//graph.getVertex("Home") == null
                        {
                            throw new NullPointerException("PLEASE LOAD THE LOCATION DATA FIRST");
                            
                        }
                        //If we have "Home", then start load the order details
                        else
                        {
                            //create few variable for temporary storing the getValue
                            String date,contact,address;
                            DSALinkedList productsList = new DSALinkedList();
                            
                            //1. get the "Home" vertex FIRST
                            store = graph.getVertex("Home");
                            
                            //2. process the productDetails FIRST
                            //save the details for the catalogueFile(will be used later)
                            DSALinkedList catalogue = processProductDetails(catalogueFile);
                                                       
                            //3. process the order(csv file)
                            line = bufReader.readLine();//read the FIRST line
                            csvLine = line.split(":");
                            date = csvLine[1];
                            line = bufReader.readLine();//read the SECOND line
                            csvLine = line.split(":");
                            contact = csvLine[1];
                            line = bufReader.readLine();//read the THIRD line
                            csvLine = line.split(":");
                            address = csvLine[1];
                            line = bufReader.readLine();//read the FOURTH line
                            while(line != null)
                            {
                                csvLine = line.split(",");
                                
                                //4. find the relevant product details for THIS PRODUCT
                                Product relevantProduct = findProduct(csvLine[0],catalogue);
                                
                                //5.add product into productsList(for THIS ORDER)
                                Product newProduct = new Product(csvLine[0],Integer.parseInt(csvLine[1]),relevantProduct.getDescription(),relevantProduct.getQty_1(),relevantProduct.getQty_10(),relevantProduct.getQty_25());
                                productsList.insertLast(newProduct);
                                line = bufReader.readLine();//read the NEXT line
                            }
                            
                            //check if this order is exist already in the order list(at "Home")
                            if(duplicateOrder(date,contact,address,graph))
                            {
                                throw new IllegalArgumentException("Load DUPLICATED order is NOT ALLOWED!");
                            }
                            else
                            {
                            //6.make the new order
                            Order newOrder = new Order(date,contact,address,productsList);
                            
                            //7.add it into OrderList for the at the location,"Home"
                            DSALinkedList orderList = (DSALinkedList)store.getValue();
                            orderList.insertLast(newOrder);
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("Array Index out of bound. Could be because of file is empty, no line to read. Or other reason.");
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                
        bufReader.close();
        fileStream.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
	}
	
	private static void processLocation(String[] csvLine,DSAGraph graph)
	{
        String edgeLabel = csvLine[0] + "-" + csvLine[1]; 
        DSALinkedList productsList_0 = new DSALinkedList();
        DSALinkedList productsList_1 = new DSALinkedList();
        graph.addVertex(csvLine[0],productsList_0);
        graph.addVertex(csvLine[1],productsList_1);
        graph.addEdge(csvLine[0],csvLine[1],edgeLabel,csvLine[2],false);//just make it undirected
	}
	
	//"Helper" function for loading PRODUCTS DATA(process catalogue.csv)
	//save the product details for each products into linked list(later use this for searching)
    private static DSALinkedList processProductDetails(String catalogueFile)
    {
        DSALinkedList returned = new DSALinkedList();//return finalized answer
        FileInputStream fileStream;
        InputStreamReader reader;
        BufferedReader bufReader;
        String[] csvLine;
        String line;
        String[] splitPrice;//for temporary storing the price(!!!We can have either 1/2/3 prices in the file)
        
        //actually using the DSAQueue is better, but the concept is the SAME so should be fine.
        DSALinkedList temp = new DSALinkedList();//temporary storing the price
        
        try
        {
            fileStream = new FileInputStream(catalogueFile);//open the file you want to read
            reader = new InputStreamReader(fileStream);
            bufReader = new BufferedReader(reader);
            
            line = bufReader.readLine();//read first line
            line = bufReader.readLine();//read second line
            while(line != null)
            {                        
                csvLine = line.split(":");
                splitPrice = csvLine[2].split(" ");
                
                //!!!We can have either 1/2/3 prices in the file, so this is just HAHAHAHA a dumb method to save the data(idk...as if directly using array will have ArrayIndexOutOfBound for those product with no more than 3 prices listed in the file)
                for(int i = 0 ; i < splitPrice.length ; i++)
                {
                    temp.insertLast(splitPrice[i]);
                }
                /*System.out.println(csvLine);
                System.out.println("csvLine[0]" + csvLine[0]);
                System.out.println("csvLine[1]" + csvLine[1]);
                System.out.println("csvLine[2]" + csvLine[2]);
                System.out.println("splitPrice" + splitPrice);
                System.out.println("splitPrice[0]" + splitPrice[0]);
                System.out.println("splitPrice[1]" + splitPrice[1]);
                System.out.println("splitPrice[2]" + splitPrice[2]);*/
                Product newProduct = new Product(csvLine[0],-1,csvLine[1],(String)temp.removeFirst(),(String)temp.removeFirst(),(String)temp.removeFirst());
                returned.insertLast(newProduct);
                line = bufReader.readLine();//read next line
               
            } 
        bufReader.close();
        fileStream.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        
    return returned;
    }
    
    //update ALL PRODUCTS' with the relevant product details(in catalogue) IN THAT STORE
    private static void updateProductDetails(DSAGraphVertex store,DSALinkedList catalogue)
    {
        try
        {
        DSALinkedList productsList = (DSALinkedList)store.getValue();
 
        Iterator product_list = productsList.iterator();
        Product product = (Product)product_list.next();//get the FIRST product 
        
        if(product == null)
        {
            throw new NullPointerException("This store(" + store.getLabel() + ") has NO products inside.TRY LOAD THE PRODUCT DATAS FIRST");
        }
        else
        {
            //PART 1: update the details for FIRST product
            Product update = findProduct(product.getProductCode(),catalogue);
            if(update != null)//means we SUCCESSFULLY FOUND the product details for THIS PRODUCT
            {
                product.setDescription(update.getDescription());
                product.setQty_1(update.getQty_1());
                product.setQty_10(update.getQty_10());
                product.setQty_25(update.getQty_25());
                       
            }
            //PART 2: update the details for SECOND TO LAST product
            while(product_list.hasNext())
            {
                product = (Product)product_list.next();//move to the NEXT product 
                
                //update the products' details
                update = findProduct(product.getProductCode(),catalogue);
                if(update != null)//means we SUCCESSFULLY FOUND the product details for THIS PRODUCT
                {                    
                    product.setDescription(update.getDescription());
                    product.setQty_1(update.getQty_1());
                    product.setQty_10(update.getQty_10());
                    product.setQty_25(update.getQty_25());
                }
            }
        }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    //"Helper" function for loading PRODUCTS DATAS(option 3) AND ORDER DATAS(option 4)
    //find the relevant product details for THE PRODUCT
    private static Product findProduct(String productCode,DSALinkedList productsList)
    {
        Product returnedProduct = null;
        Iterator product_list = productsList.iterator();
        Product next = (Product)product_list.next();//get the FIRST product in the productsList(catalogueFile)
        boolean found = false;
        //PART 1: check whether the FIRST product in the productsList(in catalogueFile) has the SAME product code as this product or not
        if(next.getProductCode().equals(productCode))
        {
            returnedProduct = next;
            found = true;
        }
        //PART 2: check whether the SECOND TO LAST product in the productsList(in catalogueFile) has the SAME product code as this product or not
        while(product_list.hasNext() || found == false)
        {
            next = (Product)product_list.next();//move to the NEXT product in the productsList(catalogueFile)
                                
            if(next.getProductCode().equals(productCode))
            {
                returnedProduct = next;
                found = true;
            }
        }
    return returnedProduct;
    }
 
    private static boolean duplicateOrder(String date,String contact,String address,DSAGraph graph)
    {
        boolean duplicate = false;
        Iterator order_list = ((DSALinkedList)(graph.getVertex("Home")).getValue()).iterator();
        Order order = (Order)order_list.next();//GET FIRST ORDER
        //ENSURE THAT THIS ORDER LIST IS NOT EMOTY FIRST BEFORE CHECK
        if(order != null)
        {
            //CHECK FIRST ORDER
            if(order.getDate().equals(date) && order.getContact().equals(contact) && order.getAddress().equals(address))
            {
                duplicate = true;
            }
            while(order_list.hasNext() && !duplicate)//return the boolean once we found that there is an order duplicated with this inserted order
            {   
                order = (Order)order_list.next();//GET NEXT ORDER
                if(order.getDate().equals(date) && order.getContact().equals(contact) && order.getAddress().equals(address))
                {
                    duplicate = true;
                }
            }
        }
    return duplicate;
    }
}
