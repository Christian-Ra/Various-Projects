/* Loops my date class to print all dates between 28 Dec, 2011 to 2 Mar 2012;
@author Christian Razo
@version 11/26/19
*/

public class CalendarLoop {

    public static void main (String [] args) {
        MyDate d1 = new MyDate(12,28,2011);
        while (d1.getDay() != 2 || d1.getMonth() != 3 || d1.getYear() != 2012) { 
            System.out.println(d1.nextDay());
        }
    }
}