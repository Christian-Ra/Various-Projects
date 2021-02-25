/*final prject for cs 115 calendar class
@author Christian Razo
@version 11/24/19
*/

public class MyDate {

    private int day;
    private int month;
    private int year;
    //private String date = (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);

        public MyDate (int month, int day, int year) { //class constructor

            this.day = day;
            this.month = month;
            this.year = year;
            if (isDateValid(month, day, year)){
                setDate(month, day, year);
                System.out.println( (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year));
            }
            else {
                System.out.println("Error, invalid date enetered, Constructor failed");
            }
        
    }
    
    //*override for toString object
    public String toString () {
        return + month + "/" + day + "/" + year;
    }

    //^^^^ Dates inputted by users

    final private String [] monthName =  {"January", "February", "March", "April", "May", "June", "July", "August", "September","October", "November", "December"};
    final private String [] daysOfWeek = {"Saturday", "Sunday", "Monday", "Tuesday", "Wendesday", "Thursday", "Friday",}; //structured to fit zeller's rule, 0 = sat, 1 = sun...
   
    //^^ Lists for displaying days of week and month
    final static private int [] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //^^ Used to determine range of days in specific month
    public void setDate (int month, int day, int year){ //returns date user enters as a string EX: Date(11,24,2019) returns "November 24, 2019"
        if (isDateValid(month, day, year)) {
            this.month = month;
            this.day = day;
            this.year = year;
        }
        else 
        System.out.println("Invalid date entered");
    }

    public void setYear (int year){
        if (isYearValid(year))
            this.year = year;
        else 
            System.out.println("Invalid year entered");
    }

    public void setMonth (int month) {

        if (isMonthValid(month) && isDayValid(day, month) ) { //new month must be valid for day such as 31 (Can't go from dec 31, to nov 30)

            this.month = month;
            System.out.println("Month set to " + monthName[this.month - 1]);
            
        }

        else 
            System.out.println("Invalid value entered (Must be integer between 1-12). New Month must be compatible with current day value");

    }

    public void setDay (int day) {

        if (isDayValid(day, this.month))
            this.day = day;
        else
            System.out.println("Invalid day entered");

    }

    public String getDayOfWeek (int month, int day, int year) { //using zeller's rule https://www.careeranna.com/articles/find-day-for-given-date-quickly/

        if (isDateValid(month, day, year)){

            //months are formatted to zeller's rule, so march is the first month, while january and february are
            //considered the last 2 months of the previous year
            if (month == 1) {
                month = 13;
                year--;
            }

            if (month == 2) {
                month = 14;
                year--;
            }

            int yearNumber = year % 100; //year number
            int century = year / 100; //century number

            int f = day + 13*(month+1)/5 + yearNumber + yearNumber/4 + century/4 + 5*century;
            // to get day of week, return f % 7. 0 = saturday, 1 = sunday, etc
            return daysOfWeek[f % 7];

        }
        return "";
    }

    public int getDay () {
        return day;
    }

    public int getMonth () {
        return month;
    }
    
    public int getYear () {
        return year;
    }

   public String nextDay () {
        day++;
        if (!isDayValid(day, month)) {
            day = 1;
            return nextMonth();
        }
        setDate(month, day, year);
        return (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);
    } 

    public String nextMonth () {
        month++;
        if (month > 12) { //next month from december is january of next year
            month = 1;
            return nextYear();
        }
        while (!isDayValid(day, month)){
            day--;
        }
        setDate(month, day, year);
        return  (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);
    }

    public String nextYear () {
        year++;
        if(!isDayValid(day, month)){
            day--;
        }
        setDate(month, day, year);
        return  (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);

    }

    public String previousYear () {
        year--;
        if (!isDayValid(day, month)){
            day--;
        }
        setDate(month, day, year);
        return  (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);
    }

    public String previousMonth () {
        month--;
        if (month < 1) { //previous month from january is december of previous year;
            month = 12;
            return previousYear();           
        }
        while (!isDayValid(day, month)){ //if day = 31 and switching to previous month such as february or novemebr
                                         //decrement day until vlaue is valid
            day--;
        }
        setDate(month, day, year);
        return  (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);
    }

    public String previousDay () {
        day--;

        if (!isDayValid(day, month)) {

            if (month == 1){ //if day would loop back from January, day = maxDays in Dec and year is set back one
                day = 31;
                return previousMonth();

            }

            if (isLeapYear(year) && (month--) == 2){ //if previous month is february on leap year, day loops back to 29
                day = 29;
                return previousMonth();
            }
                day = daysInMonth[month - 2];
                return previousMonth();
            

            
        }

        setDate(month, day, year);
        return (getDayOfWeek(month, day, year) + ", " + month + ", " + day + ", " + year);

    } 


    //^^ Methods that alter date entered by user.

    // Methods to determine the validity of user inputted Date//
    private boolean isDateValid (int month, int day, int year) {

        if (isDayValid(day, month) && isMonthValid(month) && isYearValid(year)) {
            return true;
        }
        System.out.println("Invalid date entered");
        return false;
    }

    private boolean isDayValid (int day, int month) {
        
        int maxDays;

        if (isMonthValid(month)){
            
            if ( month == 2 && isLeapYear(year)) {
                maxDays = 29;   //if february during leap year, max days in month increased to 29
            }
            
            else
                maxDays = daysInMonth[month - 1];

            if (day >= 1 && day <= maxDays) {
                    return true;
            }
            return false;
   
        }
        
        return false; 
    }

    private boolean isMonthValid (int month) {

        if ( month >= 1 && month <= 12  )
            return true;
        
            return false;
    }

    private boolean isYearValid(int year) {

        if ( year >= 1 && year <= 9999)
            return true;

        return false; 
    }

    private boolean isLeapYear(int year) {

        if (year % 400 == 0) {
            return true;
        }
        
        if (year % 100 == 0) {
            return false;
        }

        if (year % 4 == 0) {

            return true;

        }
            return false;

        
    }

}