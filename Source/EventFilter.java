/**
 *	@(#)EventFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

public abstract class EventFilter {
	public abstract String getName();
	public abstract Event[] filter(Event[] events);
	
	@Override
	public String toString() {
		return getName();
	}
}
