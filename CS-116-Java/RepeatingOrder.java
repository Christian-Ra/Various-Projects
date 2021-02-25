
public class RepeatingOrder extends Orders{

    private int orderPeriod;
    private MyDate endDate;

    public RepeatingOrder(int uniqueID, String customerID, String productID, MyDate orderDate, int productAmount, int orderPeriod, MyDate endDate) {

        super(uniqueID, customerID, productID, orderDate, productAmount);
        this.orderPeriod = orderPeriod;
        this.endDate = endDate;

    }

    public int getOrderPeriod () {
        return orderPeriod;
    }

    public MyDate getEndDate () {
        return endDate;
    }

    @Override
    public String toString () {
        return ("orderID=" + uniqueID + "customerID=" + customerID + "productID=" + productID + "date=" + orderDate.toString() + "orderAmount=" + productAmount + "orderPeriod" + orderPeriod + " endDate" + endDate.toString());
    }

   /* public MyDate getNextOrder () {
        for (int i = orderPeriod; i >= 0; i--) {
            orderDate.nextDay();
        }

        MyDate nextOrder = new MyDate(orderDate.getMonth(), orderDate.getDay(), orderDate.getYear());

        return nextOrder;
    } */



}