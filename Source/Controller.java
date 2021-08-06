/**
 *	@(#)Controller.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.time.LocalDate;

public class Controller {
	public static final String OUTPUT_FILE = "output.txt";
	private final CalendarModel model;
	private final FrameView frame;
	private final EventFilter[] filters;

	public Controller(CalendarModel model, FrameView frame) {
		this.model = model;
		this.frame = frame;
		this.filters = new EventFilter[] {
			new TimeOrderFilter(), new NameOrderFilter(), new OneTimeOnlyFilter(), new RecurringOnlyFilter()
		};

		if (frame != null) {
			frame.setFilters(this.filters);
		}
	}

	public void exit() {
		try{
			this.model.saveToFile(OUTPUT_FILE);
		} catch(Exception e) {
			System.err.println("Failed to save file: " + OUTPUT_FILE);
			e.printStackTrace();
		}
		System.exit(0);
	}

	public boolean addEventsFromFile(String file) {
		return model.addFromFile(file);
	}

	/**
	 *	Adds the event to the calendar if it doesn't conflict with an existing event.
	 *	@param event the event to be added to the calendar.
	 *	@return one of the conflicting events, if there is one, null otherwise.
	**/
	public Event createEvent(Event event) {
		Event conflict = this.model.conflicts(event);
		if (conflict != null) {
			return conflict;
		}

		this.model.addEvent(event);
		calendarUpdated();

		return null;
	}

	public Event[] getDayEvents(LocalDate date) {
		return (new DayOnlyFilter(date)).filter(this.model.getEvents());
	}

	public Event[] getWeekEvents(LocalDate date) {
		return (new WeekOnlyFilter(date)).filter(this.model.getEvents());
	}

	public Event[] getAllEvents(String filter) {
		return this.model.getEvents();
	}

	/**
	 *	Set the view to the specified date. Can be called by the other views.
	**/
	public void setDate(LocalDate date) {
		this.frame.setDate(date);
	}

	/**
	 *	Called by the model to indicate that the underlying data has been updated
	 *	indicating that all views should be refreshed.
	**/
	public void calendarUpdated() {
		this.frame.refreshData();
	}
}
