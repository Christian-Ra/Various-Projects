//Using abstract class for basic getter methods, will be inherited by repeatingOrder and OneTimeOrder class

public abstract class Orders {

    protected int uniqueID;
    protected String customerID;
    protected String productID; 
    protected MyDate orderDate; //using mydate class to run logic for periodic orders
    protected int productAmount;

    public Orders (int uniqueID, String customerID, String productID, MyDate orderDate, int productAmount) {

        this.uniqueID = uniqueID;
        this.customerID = customerID;
        this.productID = productID;
        this.orderDate = orderDate;
        this.productAmount = productAmount;

    }

    public int getOrderID () {
        return uniqueID;
    } 

    public String getCustomerID () {
        return customerID;
    }

    public String getProductID () {
        return productID;
    }

    public MyDate getOrderDate () {
        return orderDate;
    }

    public int getOrderAmount () {
        return productAmount;
    }


        
    

}