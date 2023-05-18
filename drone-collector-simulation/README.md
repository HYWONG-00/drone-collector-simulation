==Short description of the files==
droneCollector is the main system where it contains the menu system as well as the functions for the user to used. For this class, it needs DSAGraph class to work.

DSAGraph class is the data structure which is used to store the details about the locations,inventories,product details and the order details. It can display the connections between two locations. For this class, it will need DSAGraphVertex class, DSAGraphEdge class and DSALinkedList class to function.

DSAGraphVertex class is used to store the store details. The inventories for each store will be stored in this class as well. While the order details will be stored in the "Home" where the droneCollector starts from. For this class, it needs DSALinkedList class and Product class to work.

DSAGraphEdge class is used to store the connection details between the locations. For this class, it needs DSAGraphVertex class and DSALinkedList class to work.

DSALinkedList class is to store the list of the products,stores and orders. 

Product class is to store the details for each product.

Order class is to store the details for each order.

FileIO class is for loading the data in each csv file into the graph. It can also used to load the object from the object file and write the object into the object file. For this class, it will needs DSALinkedList class to work with. For readOrder function, it will needs Order class to work.

==Information on how to run program==
Firstly, we need to have the location file, inventories file, catalogue file and the order file.If you want to run the program with the report mode,"-r",you should enter the command in this way,"java droneCollector -r <location_file> <product_file> <inventories_file> <order_file>". If you want to run the program with iterative mode,you should enter the command,"java droneCollector -i".


