
public class OneTimeOrder extends Orders { 

    public OneTimeOrder(int uniqueID, String customerID, String productID, MyDate orderDate, int productAmount ) {
        super(uniqueID, customerID, productID, orderDate, productAmount);
    }

    @Override 
    public String toString() {
        return ("orderID=" + uniqueID + "customerID=" + customerID + "productID=" + productID + "date=" + orderDate.toString() + "orderAmount=" + productAmount);
    }

}