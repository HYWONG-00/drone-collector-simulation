import java.io.*;//for interface Serializable

public class Product implements Serializable
{
    private String productCode;
    private int stockOnHand;
    private String description;
    private String qty_1;//price for buying 1-9(1++) of this product
    private String qty_10;//price for buying 10-24(10++) of this product
    private String qty_25;//price for buying 25++ of this product
  
    public Product(String productCode,int stockOnHand,String description,String qty_1,String qty_10,String qty_25)
    {
        setProductCode(productCode);
        setStockOnHand(stockOnHand);
        setDescription(description);
        setQty_1(qty_1);
        setQty_10(qty_10);
        setQty_25(qty_25);    
    }
        
    //setter
    public void setProductCode(String productCode)
    {  
        if(productCode == null || productCode.isEmpty())
        {
            throw new NullPointerException("Product code cannot be null or empty.");
        }
        else
        {
            this.productCode = productCode;
        }
    }
        
    public void setStockOnHand(int stockOnHand)
    {
        this.stockOnHand = stockOnHand;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void setQty_1(String qty_1)
    {
        this.qty_1 = qty_1;
    }
    
    public void setQty_10(String qty_10)
    {
        this.qty_10 = qty_10;
    }
    
    public void setQty_25(String qty_25)
    {
        this.qty_25 = qty_25;
    }
        
    //getter
    public String getProductCode()
    {
        return this.productCode;
    }
        
    public int getStockOnHand()
    {
        return this.stockOnHand;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public String getQty_1()
    {
        return this.qty_1;
    }
    
    public String getQty_10()
    {
        return this.qty_10;
    }
    public String getQty_25()
    {
        return this.qty_25;
    }
        
    public String toString()
    {
        return "\nProduct code: " + this.productCode + ", Stock: " + this.stockOnHand + ", Description: " + this.description + ", Price for quantity1++: " + this.qty_1 + ", 10++: " + this.qty_10 + ", 25++: " + this.qty_25; 
    }

}
