/**
 *	@(#)TimeIntervalTest.java
 *	Tests whether TimeInterval is working as expected.
 *	@author Yorick van de Water
 *	@version 1.00 2021/6/19
**/

public final class TimeIntervalTest {
	public static final boolean CONFLICT = true;
	public static final boolean NO_CONFLICT = false;
	private static int totalTests = 0, testsPassed = 0;

	private static boolean test(String start1, String end1, String start2, String end2, boolean expected) {
		totalTests++;
		TimeInterval interval1 = null, interval2 = null;
		try {
			interval1 = new TimeInterval(start1, end1);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create interval for ("+start1+","+end1+")");
			return false;
		}
		try {
			interval2 = new TimeInterval(start2, end2);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Unable to create interval for ("+start2+","+end2+")");
			return false;
		}
		boolean actual = interval1.conflicts(interval2);
		if (actual && !expected) {
			System.err.println("Expected no conflict but there was conflcit between " + interval1 + " and " + interval2);
			return false;
		}
		if (!actual && expected) {
			System.err.println("Expected conflict but there was no conflict between " + interval1 + " and " + interval2);
			return false;
		}
		testsPassed++;
		return true;
	}

	/**
	 *	@param args the command line arguments
	**/
	public static void main(String[] args) {
		boolean passed = true;

		passed &= test("11:00", "12:00", "13:00", "14:00", NO_CONFLICT);
		passed &= test("13:00", "14:00", "11:00", "12:00", NO_CONFLICT);
		// Partial overlap.
		passed &= test("11:00", "13:00", "12:00", "14:00", CONFLICT);
		passed &= test("12:00", "14:00", "11:00", "13:00", CONFLICT);
		// Entire overlap.
		passed &= test("11:00", "14:00", "12:00", "13:00", CONFLICT);
		passed &= test("12:00", "13:00", "11:00", "14:00", CONFLICT);
		// Sharing start and end times.
		passed &= test("12:00", "13:00", "11:00", "13:00", CONFLICT);
		passed &= test("12:00", "13:00", "12:00", "14:00", CONFLICT);
		// Starting right when the other ends is not a conflict.
		passed &= test("12:00", "13:00", "13:00", "14:00", NO_CONFLICT);
		passed &= test("12:00", "13:00", "11:00", "12:00", NO_CONFLICT);
		// Overlap of just 1 minute is a conflict.
		passed &= test("12:00", "13:01", "13:00", "14:00", CONFLICT);
		passed &= test("11:59", "13:00", "11:00", "12:00", CONFLICT);

		System.out.println(testsPassed +" tests passed out of " + totalTests);
		if (passed) {
			System.out.println("ALL TESTS PASSED");
		} else {
			System.out.println("TEST FAILURE");
		}
	}
}
