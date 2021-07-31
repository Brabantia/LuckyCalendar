/**
 *	@(#)EventTest.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/6/19
**/

import java.time.DayOfWeek;

public class EventTest {
	public static final boolean CONFLICT = true;
	public static final boolean NO_CONFLICT = false;
	private static int totalTests = 0, testsPassed = 0;

	private static boolean testDaysOfWeekParsing(String text, DayOfWeek... expected) {
		totalTests++;
		DayOfWeek[] actual;
		try {
			actual = RecurringEvent.daysOfWeek(text);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to parse days of week text: " + text);
			return false;
		}

		int idx;
		for (idx = 0; idx < actual.length && idx < expected.length; ++idx) {
			if (actual[idx] != expected[idx]) {
				System.err.println("For text=" + text + ", expected the day of the week " + expected[idx] +" at idx=" + idx + " but got: " + actual[idx]);
				return false;
			}
		}
		if (idx != actual.length) {
			while (idx < actual.length) {
				System.err.println("For text=" + text + ", extra day of week at idx=" + idx + ": " + actual[idx]);
				++idx;
			}
			return false;
		} else if (idx != expected.length) {
			while (idx < expected.length) {
				System.err.println("For text=" + text + ", missing day of week at idx=" + idx + ": " + expected[idx]);
				++idx;
			}
			return false;
		}

		testsPassed++;
		return true;
	}

	private static boolean testEvent(boolean expected, String date1, String date2, TimeInterval interval1, TimeInterval interval2) {
		totalTests++;
		Event event1 = null, event2 = null;
		try {
			event1= new Event("first", date1, interval1);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create Event for (" + date1 + "," + interval1 + ")");
			return false;
		}
		try {
			event2 = new Event("second", date2, interval2);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create Event for (" + date2 + "," + interval2 + ")");
			return false;
		}
		boolean actual = event1.conflicts(event2);
		if (actual && !expected) {
			System.err.println("Expected no conflict but there was conflcit between " + event1 + " and " + event2);
			return false;
		}
		if (!actual && expected) {
			System.err.println("Expected conflict but there was no conflict between " + event1 + " and " + event2);
			return false;
		}
		testsPassed++;
		return true;
	}

	private static boolean testEventWithRecurring(boolean expected, String date, String start, String end, TimeInterval interval1, TimeInterval interval2, String daysOfWeek) {
		totalTests++;
		Event event1 = null, event2 = null;
		try {
			event1= new Event("first", date, interval1);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create Event for (" + date+ "," + interval1 + ")");
			return false;
		}
		try {
			event2 = new RecurringEvent("second", start, interval2, end, daysOfWeek);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create Event for (" + start + "," + interval2+ "," + end + ")");
			return false;
		}
		boolean actual = event1.conflicts(event2);
		if (actual && !expected) {
			System.err.println("Expected no conflict but there was conflcit between " + event1 + " and " + event2);
			return false;
		}
		if (!actual && expected) {
			System.err.println("Expected conflict but there was no conflict between " + event1 + " and " + event2);
			return false;
		}
		if (expected != event2.conflicts(event1)) {
			System.err.println("Calling conflicts on the recurring event gave a different result than the expected: " + expected);
		}

		testsPassed++;
		return true;
	}

	private static boolean testRecurringEvents(boolean expected, String start1, String end1, String start2, String end2, TimeInterval interval1, TimeInterval interval2, String daysOfWeek1, String daysOfWeek2) {
		totalTests++;
		Event event1 = null, event2 = null;
		try {
			event1 = new RecurringEvent("first", start1, interval1, end1, daysOfWeek1);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create Event for (" + start1 + "," + end1 + "," + interval1 + ")");
			return false;
		}
		try {
			event2 = new RecurringEvent("second", start2, interval2, end2, daysOfWeek2);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create Event for (" + start2 + "," + end2 + "," + interval2 + ")");
			return false;
		}
		boolean actual = event1.conflicts(event2);
		if (actual && !expected) {
			System.err.println("Expected no conflict but there was conflcit between " + event1 + " and " + event2);
			return false;
		}
		if (!actual && expected) {
			System.err.println("Expected conflict but there was no conflict between " + event1 + " and " + event2);
			return false;
		}
		if (expected != event2.conflicts(event1)) {
			System.err.println("Calling conflicts on the other recurring event gave a different result than the expected: " + expected);
		}

		testsPassed++;
		return true;
	}

	/**
	 *	@param args the command line arguments
	**/
	public static void main(String[] args) {
		TimeInterval nonconflicting1 = new TimeInterval("11:00", "12:00");
		TimeInterval nonconflicting2 = new TimeInterval("13:00", "14:00");
		TimeInterval conflicting1 = new TimeInterval("11:00", "13:00");
		TimeInterval conflicting2 = new TimeInterval("12:00", "14:00");

		boolean passed = true;

		// Days of Week
		passed &= testDaysOfWeekParsing("");
		passed &= testDaysOfWeekParsing("m", DayOfWeek.MONDAY);
		passed &= testDaysOfWeekParsing("tr", DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);
		passed &= testDaysOfWeekParsing("mwf", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
		passed &= testDaysOfWeekParsing("as", DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		passed &= testDaysOfWeekParsing("SMTWRFA", DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

		// Event
		// Different days.
		passed &= testEvent(NO_CONFLICT, "06/19/2021", "06/18/2021", nonconflicting1, nonconflicting2);
		passed &= testEvent(NO_CONFLICT, "06/19/2021", "06/18/2021", conflicting1, conflicting2);
		// Same day but non-conflicting time.
		passed &= testEvent(NO_CONFLICT, "06/19/2021", "06/19/2021", nonconflicting1, nonconflicting2);
		// Same day, conflicting time.
		passed &= testEvent(CONFLICT, "06/19/2021", "06/19/2021", conflicting1, conflicting2);

		// Event with Recurring Event
		// Recurring stops before date or starts after.
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/18/2020", "06/18/2021", nonconflicting1, nonconflicting2, "SMTWRFA");
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/18/2020", "06/18/2021", conflicting1, conflicting2, "SMTWRFA");
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/20/2021", "06/20/2022", nonconflicting1, nonconflicting2, "SMTWRFA");
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/20/2021", "06/20/2022", conflicting1, conflicting2, "SMTWRFA");
		// Dates miss by day of the week: i.e. no Saturday.
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/18/2020", "06/20/2022", nonconflicting1, nonconflicting2, "SMTWRF");
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/18/2020", "06/20/2022", conflicting1, conflicting2, "SMTWRF");
		// Dates match but the times don't.
		passed &= testEventWithRecurring(NO_CONFLICT, "06/19/2021", "06/18/2020", "06/20/2022", nonconflicting1, nonconflicting2, "SMTWRFA");
		// Recurring event is the same day.
		passed &= testEventWithRecurring(CONFLICT, "06/19/2021", "06/19/2021", "06/19/2021", conflicting1, conflicting2, "A");
		// Recurring event includes the event in the future.
		passed &= testEventWithRecurring(CONFLICT, "06/30/2021", "06/19/2021", "06/19/2022", conflicting1, conflicting2, "W");

		// Both are recurring events.
		// No overlap in dates.
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/19/2021", "06/20/2021", "06/20/2022", nonconflicting1, nonconflicting2, "SMTWRFA", "SMTWRFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/19/2021", "06/20/2021", "06/20/2022", conflicting1, conflicting2, "SMTWRFA", "SMTWRFA");
		// No overlap in days of week.
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/19/2022", "06/19/2020", "06/19/2022", nonconflicting1, nonconflicting2, "STRA", "MWF");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/19/2022", "06/19/2020", "06/19/2022", conflicting1, conflicting2, "MWF", "STRA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/19/2022", "06/19/2020", "06/19/2022", nonconflicting1, nonconflicting2, "MWF", "STRA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/19/2022", "06/19/2020", "06/19/2022", conflicting1, conflicting2, "STRA", "MWF");
		// Overlapping days are wrong days of the week.
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", nonconflicting1, nonconflicting2, "SMFA", "SMFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", conflicting1, conflicting2, "SMFA", "SMFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", nonconflicting1, nonconflicting2, "SMTWRFA", "SMFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", conflicting1, conflicting2, "SMTWRFA", "SMFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", nonconflicting1, nonconflicting2, "SMFA", "SMTWRFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", conflicting1, conflicting2, "SMFA", "SMTWRFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", nonconflicting1, nonconflicting2, "SMWFA", "SMTRFA");
		passed &= testRecurringEvents(NO_CONFLICT, "06/19/2020", "06/17/2021", "06/15/2021", "06/19/2022", conflicting1, conflicting2, "SMTRFA", "SMWFA");
		// Only 1 day overlaps and it conflicts.
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/19/2021", "06/19/2022", conflicting1, conflicting2, "A", "A");
		// A week overlaps but only one day matches.
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "S", "S");
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "M", "M");
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "T", "T");
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "W", "W");
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "R", "R");
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "F", "F");
		passed &= testRecurringEvents(CONFLICT, "06/19/2020", "06/19/2021", "06/13/2021", "06/19/2022", conflicting1, conflicting2, "A", "A");

		System.out.println(testsPassed +" tests passed out of " + totalTests);
		if (passed) {
			System.out.println("ALL TESTS PASSED");
		} else {
			System.out.println("TEST FAILURE");
		}
	}
}
