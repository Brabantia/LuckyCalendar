/**
 *	@(#)NameOrderFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

public class NameOrderFilter implements EventFilter {
	public String getName() {
		return "order by name";
	}

	public Event[] filter(Event[] events) {
		return null;
	}
}