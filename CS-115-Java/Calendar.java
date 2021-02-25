/*class used to test MyDate methods and functionality
@author Christian Razo
@version 11/26/19
*/

public class Calendar {

    public static void main (String [] args) {
    
       MyDate calendar = new MyDate(12,28,2012);
       System.out.println(calendar);
       System.out.println(calendar.nextDay());
       System.out.println(calendar.nextDay());
       System.out.println(calendar.nextMonth());
       System.out.println(calendar.nextYear());

       MyDate d2 = new MyDate (1 , 2, 2012);

       System.out.println(d2); // Monday 2 Jan 2012
       System.out.println(d2.previousDay()); // Sunday 1 Jan 2012
       System.out.println(d2.previousDay()); // Saturday 31 Dec 2011
       System.out.println(d2.previousMonth()); // Wednesday 30 Nov 2011
       System.out.println(d2.previousYear()); // Tuesday 30 Nov 2010

       MyDate d3 = new MyDate(2, 29, 2012);

       System.out.println(d3.previousYear()); // Monday 28 Feb 2011

       MyDate d4 = new MyDate (2099 , 11 , 31); // Invalid year , month , or day !
       MyDate d5 = new MyDate (2011 , 2 , 29); // Invalid year , month , or day !
    }
}