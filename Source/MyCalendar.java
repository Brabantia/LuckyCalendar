/**
 *	@(#)MyCalendar.java
 *
 *	@author Shyam Vyas
 *	@version 1.00 2021/7/26
**/
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeSet;

public class MyCalendar {
	/**
	 * 
	 * This class contains the underlying data structure that keeps track of the calendar's events
	 */
	
	static HashMap<Character,DayOfWeek> daysOfWeek= new HashMap<>();
	static HashMap<String, String> monthMap = new HashMap<>();
	static HashMap<String, Integer> daysPerMonth= new HashMap<>();
	static HashMap<Integer, TreeSet<Event>> monthEvents= new HashMap<>();
	static HashMap<LocalDate, TreeSet<Event>> dayEvents= new HashMap<>();
	static TreeSet<Event> allEvents= new TreeSet<>();
	static TreeSet<Event> recurringEvents= new TreeSet<>();
	static TreeSet<Event> oneTimeEvents= new TreeSet<>();
	/**
	 * This method populates all of the maps with appropriate days per month, and each number with corresponding string month name and each letter with corresponding week day name.
	 */
	public static void populateMap() {
		monthMap.put("01", "January");
		monthMap.put("02", "February");
		monthMap.put("03", "March");
		monthMap.put("04", "April");
		monthMap.put("05", "May");
		monthMap.put("06", "June");
		monthMap.put("07", "July");
		monthMap.put("08", "August");
		monthMap.put("09", "September");
		monthMap.put("10", "October");
		monthMap.put("11", "November");
		monthMap.put("12", "December");
		daysPerMonth.put("01", 31);
		daysPerMonth.put("02", 28);
		daysPerMonth.put("03", 31);
		daysPerMonth.put("04", 30);
		daysPerMonth.put("05", 31);
		daysPerMonth.put("06", 30);
		daysPerMonth.put("07", 31);
		daysPerMonth.put("08", 31);
		daysPerMonth.put("09", 30);
		daysPerMonth.put("10", 31);
		daysPerMonth.put("11", 30);
		daysPerMonth.put("12", 31);
		for(int i=1;i<=12;i++) {
			monthEvents.put(i, new TreeSet<Event>());
		}
		daysOfWeek.put('S',DayOfWeek.SUNDAY);
		daysOfWeek.put('M', DayOfWeek.MONDAY);
		daysOfWeek.put('T',DayOfWeek.TUESDAY);
		daysOfWeek.put('W', DayOfWeek.WEDNESDAY);
		daysOfWeek.put('R',DayOfWeek.THURSDAY);
		daysOfWeek.put('F', DayOfWeek.FRIDAY);
		daysOfWeek.put('A',DayOfWeek.SATURDAY);

	}
	/**
	 * This prints out an object "o" in a simple "sop" method instead of "System.out.println()"
	 * @param o The object you want to print. 
	 */
	public static void sop(Object o) {
		System.out.println(o);
	}
}
