import java.util.*;

public class UnitTestFileIO
{
    public static void main(String[] args)
    {
        DSAGraph graph = new DSAGraph();    
        FileIO invalidFile = new FileIO("haha.txt");//Invalid invalidFile 
        FileIO locationFile = new FileIO("stores.csv");
        FileIO inventoryFile = new FileIO("inventories.csv");
        FileIO orderFile = new FileIO("order1.csv");
        FileIO serialisedFile = new FileIO("UnitTestSerialised.dat");
        
        
        readLocationTest(invalidFile,locationFile,graph);
        
        readInventoriesTest(invalidFile,locationFile,inventoryFile,graph);
        
        readProductsTest(invalidFile,locationFile,inventoryFile,graph);
        
        readOrderTest(invalidFile,locationFile,orderFile,graph);
        
        
        System.out.println("\n\n..................readObject()..................");
        System.out.println("\n............Handle INVALID file: throw exception............"); 
        DSAGraph newGraph = new DSAGraph();
        newGraph = invalidFile.readObject(newGraph);
        System.out.println(newGraph.getGraphVerticesList());
        
        System.out.println("\n..........writeOrder(): write the graph into the object serialisedFile...........");
        serialisedFile.writeObject(graph);
        System.out.println("\n............readObject(): Handle VALID file: read the graph and display the vertices list of readed graph............"); 
        newGraph = serialisedFile.readObject(newGraph);
        System.out.println(newGraph.getGraphVerticesList());
    }
    
    public static void readLocationTest(FileIO invalidFile,FileIO locationFile,DSAGraph graph)
    {  
        System.out.println("\n\n..........readLocation()...........");
        System.out.println("\n............Handle INVALID file: throw exception...........");        
        invalidFile.readLocation(graph);
        System.out.println(graph);
        System.out.println("\n............Handle VALID file: print the locations............");   
        locationFile.readLocation(graph);
        System.out.println(graph.getGraphVerticesList());
    }
    
    public static void readInventoriesTest(FileIO invalidFile,FileIO locationFile,FileIO inventoryFile,DSAGraph graph)
    {  
        System.out.println("\n\n..........readInventories()......");
        System.out.println("\n............Handle INVALID file: throw exception............");  
        invalidFile.readInventories(graph);
        System.out.println("\n.............If location data have NOT been loaded yet: throw exception.........");  
        DSAGraph graph_1 = new DSAGraph();       
        inventoryFile.readInventories(graph_1);
        System.out.println("\n.............If location data have been loaded: print the inventories.........");  
        locationFile.readLocation(graph);
        inventoryFile.readInventories(graph);
        System.out.println(graph.getGraphVerticesList());
    }
    
    public static void readProductsTest(FileIO invalidFile,FileIO locationFile,FileIO inventoryFile,DSAGraph graph)
    {
        System.out.println("\n\n..........readProducts()...........");
        System.out.println("\n............Handle INVALID file: throw exception............");  
        invalidFile.readProducts(graph,"catalogue.csv");
        inventoryFile.readProducts(graph,"keke.csv");
        System.out.println("\n........If location data have NOT been loaded yet: throw exception........."); 
        DSAGraph graph_2 = new DSAGraph();
        inventoryFile.readProducts(graph_2,"catalogue.csv");
        System.out.println("\n........If inventories data have NOT been loaded yet: throw exception.........");        
        locationFile.readLocation(graph_2);
        inventoryFile.readProducts(graph_2,"catalogue.csv");
        System.out.println("\n..............If location and inventories data have been loaded................"); 
        inventoryFile.readProducts(graph,"catalogue.csv");
        System.out.println(graph.getGraphVerticesList());
    }
    
    public static void readOrderTest(FileIO invalidFile,FileIO locationFile,FileIO orderFile,DSAGraph graph)
    {
        System.out.println("\n\n..........readOrder()...........");
        System.out.println("\n............Handle INVALID file name: throw exception............");  
        invalidFile.readOrder(graph,"catalogue.csv");
        System.out.println("\n........If location data have NOT been loaded yet: throw exception.........");
        DSAGraph graph_3 = new DSAGraph();
        orderFile.readOrder(graph_3,"catalogue.csv");
        System.out.println("\n........If location data have been loaded: display the order stored in \"Home\".........");
        locationFile.readLocation(graph_3);
        orderFile.readOrder(graph_3,"catalogue.csv");
        System.out.println(graph_3.getVertex("Home"));
        System.out.println("\n........If duplicate order is loaded: throw exception.........");
        orderFile.readOrder(graph_3,"catalogue.csv");
    }
}
