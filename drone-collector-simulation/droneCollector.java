import java.util.*;
import java.io.*;

public class droneCollector
{
	public static void main(String[] args)
	{
        //args[0] = mode, args[1] = location file, args[2] = product file, args[3] = inventories file,args[4] = order file
        if(args.length > 0)
        {
            if(args[0].equals("-i"))//iterative mode
            {
                //"stores.csv","catalogue.csv","inventories.csv","order1.csv"
                //iterativeMode(args[1],args[2],args[3],args[4]);
                if(args.length == 1)
                {
                    iterativeMode();
                }
                else 
                {
                    usage();
                }
            }
            else if(args[0].equals("-r"))//report mode
            {
                if(args.length == 5)
                {
                try
                {
                    DSAGraph droneCollector = new DSAGraph(); 
                    //mode("stores.csv","catalogue.csv","inventories.csv","order1.csv");
                    droneCollector = loadLocation(args[1],droneCollector);
                    droneCollector = loadInventories(args[3],droneCollector);
                    droneCollector = loadProductsDetails(args[3],args[2],droneCollector);
                    droneCollector = loadOrder(args[4],args[2],droneCollector);
                    //System.out.println(droneCollector);
                    Order order = (Order)((DSALinkedList)(((DSAGraphVertex)droneCollector.getVertex("Home")).getValue())).peekFirst();
                    System.out.println(order.getDate());
                    System.out.println(order.getContact());
                    System.out.println(order.getAddress());
                    droneCollector.routeDisplay(order.getDate(),order.getContact(),order.getAddress());//directly display option 6
                }
                catch(NullPointerException e)
                {
                    System.out.println("MAKE SURE ALL THE FILE ARE VALID/LOCATED IN SAME FOLDER AS THE PROGRAM");
                }
                }
                else 
                {
                    usage();
                }
            }
            else//Invalid Mode
            {
                System.out.println("Invalid mode.");
            }
        }
        else
        {
            usage();
        }
	}
	
	private static void usage()
	{
        //display the usage of the program
        System.out.println("Usage: java droneCollector m");
        System.out.println("  where if");
        System.out.println("        m is one of");
        System.out.println("           -i - interactive	testing	environment. Enter:java droneCollector -i");
        System.out.println("           -r - report mode. Enter: java droneCollector –r <location_file> <prod_file> <inventory_file> <order_file>");
	}
	
	private static void iterativeMode()//(String locationFile,String productFile,String inventoriesFile,String orderFile)
	{
        try
        {
        Scanner sc = new Scanner(System.in);
		int userInput;
		DSAGraph droneCollector = new DSAGraph();
		
		do
		{
		System.out.println("Pick one option: ");
		System.out.println("  0. Load data");
		System.out.println("  1. Location overview");
		System.out.println("  2. Inventory overview");
		System.out.println("  3. Product search");
		System.out.println("  4. Find and display the inventory in A STORE");
		System.out.println("  5. Find and display DISTANCE between TWO LOCATIONS");
		System.out.println("  6. Find and display ROUTE for collecting order");
		System.out.println("  7. Save data");
		System.out.println("  8. Exit");
		userInput = sc.nextInt();
		FileIO file;
		DSAGraphVertex store;//used by case 3,4 n 5
		String date,contact,address;
		try
		{
		switch(userInput)
		{
            case 0:
                System.out.println("Load which data: ");
                System.out.println("  1. Location data");//read the store details(name,distance...)
                System.out.println("  2. Inventory data");//read each of the product's inventories(inventories.csv 3rd column) 
                System.out.println("  3. Product data");//read the product's code(inventories.csv 2nd column)                               
                System.out.println("  4. Order data");
                System.out.println("  5. Serialised	data");
                int dataOption = sc.nextInt();
                
              
                switch(dataOption)
                {
                    case 1:
                        System.out.println("Please enter the location file name:");
                        String locationFile = sc.next();
                        droneCollector = loadLocation(locationFile,droneCollector);
                        System.out.println(droneCollector);
                    break;
                  
                    case 2:    
                        System.out.println("Please enter the inventories file name:");
                        String inventoriesFile = sc.next();
                        droneCollector = loadInventories(inventoriesFile,droneCollector);
                        System.out.println(droneCollector);
                    break;
                    
                    case 3:
                        System.out.println("Please enter the inventories file name:");
                        inventoriesFile = sc.next();
                        System.out.println("Please enter the product file name:");
                        String productFile = sc.next();
                        droneCollector = loadProductsDetails(inventoriesFile,productFile,droneCollector);
                        System.out.println(droneCollector.getGraphVerticesList());
                       
                    break;
                    
                    case 4:
                        
                        System.out.println("Please enter the product file name:");
                        productFile = sc.next();
                        int stop = 1;
                        do
                        {
                            System.out.println("Please enter the order file name:");
                            String orderFile = sc.next();
                            //insert into the orderList(in "Home") ONE BY ONE
                            droneCollector = loadOrder(orderFile,productFile,droneCollector);
                            
                            System.out.println("Do you want to continue loading the order files?");
                            System.out.println("    0. No");
                            System.out.println("    1. Yes");
                            stop = sc.nextInt();
                        }while(stop != 0);
                        
                        if(!droneCollector.getGraphVerticesList().isEmpty())
                        {
                            //store = droneCollector.getVertex("Home");
                            //DSALinkedList list = (DSALinkedList)store.getValue();
                            System.out.println(droneCollector.getVertex("Home"));
                        }
                        
                    break;
                    
                    case 5:
                        file = new FileIO("serialisedData.dat");
                        //everything including display the newGraph read from the file have been done in the method
                        droneCollector = file.readObject(droneCollector);
                        System.out.println(droneCollector);
                    break;                    
                }
            break;
            
            case 1:
            try
            {
                if(droneCollector.getGraphVerticesList().isEmpty())
                {
                    throw new NullPointerException("PLEASE LOAD THE LOCATION DATAS FIRST");
                }
                else
                {
                    Iterator edge_list = (droneCollector.getGraphEdgeList()).iterator();
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
            break;
            
            case 2:
                droneCollector.inventoryOverview();
            break;
            
            //Display the product(+ its all details) with MATCHED product code in EACH STORE
            case 3:
             //1. ask fot the product code to search
            System.out.println("Please enter the product code you want to search for: ");
            String userProductCode = sc.next();
            
            droneCollector.productSearch(userProductCode);
            break;
            
            case 4:
            //1. get the store name to search 
            System.out.println("Please enter the store(name in String) you want to search for: ");
            String findStore = sc.next();
            droneCollector.findStoreInventory(findStore);
            break;
            
            case 5:
            try
            {
                    System.out.println("Please enter the two locations u want to search with: ");
                    String location_1 = sc.next();
                    String location_2 = sc.next();
                    
                    DSAGraphEdge edge = droneCollector.findEdge(location_1,location_2);
                    System.out.println("TWO LOCATIONS          DISTANCE");
                    System.out.println(edge.getLabel() + "         " + edge.getValue());
            }
            catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
                
            break;
            
            case 6:
            
                System.out.println("Please enter the order's details: ");
                System.out.println("Date: ");
                date = sc.next();
                sc.nextLine();//just to absorb the remaining line?
                System.out.println("Contact: ");
                contact = sc.nextLine();
                System.out.println("Address: ");
                address = sc.nextLine();
                droneCollector.routeDisplay(date,contact,address);
            break;
            
            case 7:
                try
                {
                    //create a new object file called "serialisedData.dat"
                    File newFile = new File("serialisedData.dat");
                    newFile.createNewFile();
                    
                    file = new FileIO("serialisedData.dat");
                    file.writeObject(droneCollector);
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            break;
            
            case 8:
                System.out.println("THANK YOU!");
            break;
            
            default:
                throw new IllegalArgumentException("Please enter ONLY correct option");
  
		}
		
		}
		catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        
		}while(userInput != 8);
		}		
		catch(InputMismatchException e)
		{
            System.out.println(e.getMessage());
		}
	}
	//option 0: load location data
	private static DSAGraph loadLocation(String locationFile,DSAGraph droneCollector)
	{
        FileIO file = new FileIO(locationFile);//"stores.csv"
        file.readLocation(droneCollector);
        return droneCollector;
	}
	
	private static DSAGraph loadInventories(String inventoriesFile,DSAGraph droneCollector)
	{
        FileIO file = new FileIO(inventoriesFile);//"inventories.csv"
        file.readInventories(droneCollector);
        return droneCollector;
	}
	
	private static DSAGraph loadProductsDetails(String inventoriesFile,String productFile,DSAGraph droneCollector)
	{
        FileIO file = new FileIO(inventoriesFile);//"inventories.csv"
        file.readProducts(droneCollector,productFile);//"catalogue.csv"
        return droneCollector;
	}
	
	private static DSAGraph loadOrder(String orderFile,String productFile,DSAGraph droneCollector)
	{
        FileIO file = new FileIO(orderFile);//"order66.csv"
        file.readOrder(droneCollector,productFile);//"catalogue.csv"
        return droneCollector;
	}
	
	
	

}	
