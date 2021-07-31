/**
 *	@(#)CalendarModel.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class CalendarModel {
	private ArrayList<Event> events = new ArrayList<>(), recurringEvents = new ArrayList<>();
	private ArrayList<Controller> listeners = new ArrayList<>();

	public void attach(Controller listener) {
		if (listener == null) {
			throw new NullPointerException("Listener passed in was null.");
		}
		listeners.add(listener);
	}

	public boolean addFromFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		Scanner scan = new Scanner(file);
		while (scan.hasNext()) {
			String line = scan.nextLine();
			try {
				Event e = Event.decode(line);
				if (e instanceof RecurringEvent) {
					this.recurringEvents.add(e);
				} else {
					this.events.add(e);
				}
			} catch(Exception e) {
				System.err.println("Failed to parse: " + line);
				e.printStackTrace();
			}
		}
		scan.close();
		this.notifyChanges();
		return true;
	}

	public void saveToFile(String path) throws IOException {
		PrintWriter out = new PrintWriter(new File(path));
		for (Event e : this.events) {
			out.println(e.encode());
		}
		for (Event e : this.recurringEvents) {
			out.println(e.encode());
		}
		out.close();
	}

	public Event[] getEvents(){
		Event[] eventList = new Event[this.events.size() + this.recurringEvents.size()];
		this.events.toArray(eventList);
		int i = this.events.size();
		for (Event e : this.recurringEvents) {
			eventList[i++] = e;
		}
		return eventList;
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
		this.notifyChanges();
	}

	public void removeEvent(Event event) {
		if (event == null) {
			return;
		}
		for (int i = events.size()-1; i >= 0; --i) {
			if (event.getDate().equals(events.get(i).getDate()) && event.getTime().compareTo(events.get(i).getTime())==0) {
				events.remove(i);
			}
		}
		for (int i = recurringEvents.size()-1; i >= 0; --i) {
			if (event.getDate().equals(recurringEvents.get(i).getDate()) && event.getTime().compareTo(recurringEvents.get(i).getTime())==0) {
				recurringEvents.remove(i);
			}
		}
		this.notifyChanges();
	}

	public boolean conflicts(Event event) {
		if (event == null) {
			throw new NullPointerException("Event passed in is null.");
		}
		for (Event e : this.events) {
			if (event.conflicts(e)) {
				return true;
			}
		}
		for (Event e : this.recurringEvents) {
			if (event.conflicts(e)) {
				return true;
			}
		}
		return false;
	}

	public void notifyChanges() {
		for (Controller listener : this.listeners) {
			listener.calendarUpdated();
		}
		for (Event e : this.events) {
			System.out.println(e);
		}
		for (Event e : this.recurringEvents) {
			System.out.println(e);
		}
	}
}