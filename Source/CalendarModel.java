/**
 *	@(#)CalendarModel.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarModel {
	private ArrayList<Event> events, recurringEvents;

	public void attach(Controller listener) {
	}

	public void addFromFile(String path) {
	}

	public void saveToFile(String path) {
	}

	public Event[] getEvents(){
		return null;
	}

	public void addEvent(Event newEvent) {
		for (Event event : this.events) {
			if (event.conflicts(newEvent)) {
				System.out.println();
				System.out.println("Conflicts with existing event: " + event);
				System.out.println("Event not added");
				return;
			}
		}
		for (Event event : this.recurringEvents) {
			if (event.conflicts(newEvent)) {
				System.out.println();
				System.out.println("Conflicts with existing event: " + event);
				System.out.println("Event not added");
				return;
			}
		}
		this.events.add(newEvent);
	}

	public void removeEvent(Event event) {
	}

	public boolean conflict(Event event) {
		return false;
	}
}
