import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class OrderManager {

    private static ArrayList <Integer> uniqueIDS = new ArrayList<Integer>();
    private static ArrayList <OneTimeOrder> oneTimeOrders = new ArrayList<OneTimeOrder>();
    private static ArrayList <RepeatingOrder> repeatOrders = new ArrayList<RepeatingOrder>();
    private String inputFile;

    public OrderManager (String inputFile) {
        this.inputFile = inputFile;
    }

    public void readOrderFile() throws FileNotFoundException {

        File file = new File(inputFile);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()) {

        try  {
            
            scanner.useDelimiter(",");
            
                if(scanner.next().equals("O")) { //One time order
                    
                    String customerID = scanner.next();
                    String productID = scanner.next();
                    MyDate orderDate = convertToDate(scanner.next());
                    int productAmount = scanner.nextInt();
                    
                    int uniqueID = generateUniqueID();
                    
                    OneTimeOrder order = new OneTimeOrder(uniqueID, customerID, productID, orderDate, productAmount);

                    oneTimeOrders.add(order);
                    
                     

                }

                else if (scanner.next().equals("R")) { //Repeating order

                    String customerID = scanner.next();
                    String productID = scanner.next();
                    MyDate orderDate = convertToDate(scanner.next());
                    int productAmount = scanner.nextInt();
                    int orderPeriod = scanner.nextInt();
                    MyDate endDate = convertToDate(scanner.next());

                    int uniqueID = generateUniqueID();
                    
                    RepeatingOrder rOrder = new RepeatingOrder(uniqueID, customerID, productID, orderDate, productAmount, orderPeriod, endDate);
                    repeatOrders.add(rOrder);
                }

                else 
                scanner.nextLine(); //invalid format, skip order
            

        }

       /* catch (FileNotFoundException fnfe){

            System.err.println("File not found, exiting");

        } */

        catch (InputMismatchException ime){
            System.err.println("Invalid order format, SKIPPING ORDER");
            scanner.nextLine();
        }

        finally {
            scanner.close();
        }

    }

 }

    public static int generateUniqueID () {
        Random rand = new Random();
        boolean isUnique = true;
        int uniqueID;

        do {

        uniqueID = rand.nextInt(1000000);

            for ( Integer test : uniqueIDS) {
                if (uniqueID == test)
                isUnique = false;

            }

        }
        
        while (!isUnique);
        
        return uniqueID;
        
 }

    public static MyDate convertToDate (String dateText) { //takes date (5/2/2019) and converts to Date object
        String[] dateStrings = new String[3];
        dateStrings = dateText.split("/", 0);

        MyDate orderDate = new MyDate(Integer.parseInt(dateStrings[0]), Integer.parseInt(dateStrings[1]), Integer.parseInt(dateStrings[2]));
        return orderDate;
    }

    public static void addOneTimeOrder (String customerID, String productID, int productAmount, MyDate orderDate){

        int uniqueID = generateUniqueID();
        OneTimeOrder order = new OneTimeOrder(uniqueID, customerID, productID, orderDate, productAmount);
        oneTimeOrders.add(order);

    }

    public static void addRepeatingOrder (String customerID, String productID, int productAmount, MyDate orderDate, int orderPeriod, MyDate endDate) {
        
        int uniqueID = generateUniqueID();
        RepeatingOrder order = new RepeatingOrder(uniqueID, customerID, productID, orderDate, productAmount, orderPeriod, endDate);
        repeatOrders.add(order);
    }
        

    public static void deleteOrder (int inputID, boolean isRepeating) throws InputMismatchException {
        if (isRepeating) {
            for (RepeatingOrder order : repeatOrders) {
                if (order.getOrderID() == inputID)
                repeatOrders.remove(order);
            }
            
        }

        else {
            for (OneTimeOrder order : oneTimeOrders) {
                if (order.getOrderID() == inputID) 
                oneTimeOrders.remove(order);
            }
        }
    }

    public static void customerList (String customerID) { //just prints out list of a customers orders, still need to sort in date order

        for (RepeatingOrder orders: repeatOrders) {
            if (orders.getCustomerID().equals(customerID)) 
            System.out.println(orders.toString());
        }

        for (OneTimeOrder orders : oneTimeOrders) {
            if (orders.getCustomerID().equals(customerID))
            System.out.println(orders.toString());
        }

      
    }

    public void monthlyInventory () {
        
    }

}