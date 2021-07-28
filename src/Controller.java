/**
 *	@(#)Controller.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.LocalDate;

public class Controller {
//	private final CalendarModel model;
	private final FrameView frame;
//	private final EventFilter[] filters;

	public Controller( FrameView frame) {
//		this.model = model;
		this.frame = frame;
//		this.filters = new EventFilter[] {
//			new NameOrderFilter(), new OneTimeOnlyFilter(), new RecurringOnlyFilter()
//		};

//		String[] filterNames = new String[this.filters.length];
//		for (int a = 0; a < this.filters.length; ++a) {
//			filterNames[a] = this.filters[a].getName();
//		}
//		frame.setFilters(filterNames);
	}

	public void refreshFrame(LocalDate localDate){
		frame.refresh(localDate);
	}

//	public void addMouse

//	public void exit() {
//		this.model.saveToFile("");
//		System.exit(0);
//	}

	public void addEventsFromFile(String path) {
	}

	/**
	 *	Adds the event to the calendar if it doesn't conflict with an existing event.
	 *	@param event the event to be added to the calendar.
	 *	@return one of the conflicting events, if there is one, null otherwise.
	**/
//	public model.Event createEvent(model.Event event) {
//		return null;
//	}
//
//	public model.Event[] getDayEvents(LocalDate date) {
//		return null;
//	}
//
//	public model.Event[] getWeekEvents(LocalDate date) {
//		return null;
//	}
//
//	public model.Event[] getMonthEvents(LocalDate date) {
//		return null;
//	}
//
//	public model.Event[] getAllEvents(String filter) {
//		return null;
//	}

	/**
	 *	Called by the model to indicate that the underlying data has been updated
	 *	indicating that all views should be refreshed.
	**/
	public void calendarUpdated() {
		this.frame.refreshData();
	}
}
