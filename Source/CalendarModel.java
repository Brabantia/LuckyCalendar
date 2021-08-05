/**
 *	@(#)CalendarModel.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

	public boolean addFromFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.err.println("File doesn't exist: " + path);
			return false;
		}
		try (Scanner scan = new Scanner(file)) {
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
					return false;
				}
			}
			this.notifyChanges();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

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

	public Event[] getEvents() {
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
				return;
			}
		}
		for (Event event : this.recurringEvents) {
			if (event.conflicts(newEvent)) {
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

	public Event conflicts(Event event) {
		if (event == null) {
			throw new NullPointerException("Event passed in is null.");
		}
		for (Event e : this.events) {
			if (event.conflicts(e)) {
				return e;
			}
		}
		for (Event e : this.recurringEvents) {
			if (event.conflicts(e)) {
				return e;
			}
		}
		return null;
	}

	public void notifyChanges() {
		for (Controller listener : this.listeners) {
			listener.calendarUpdated();
		}
	}
}
