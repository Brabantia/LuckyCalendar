/**
 *	@(#)DayOnlyFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/30
**/

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayOnlyFilter implements EventFilter {
	private final LocalDate date;

	public DayOnlyFilter(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return "only events on a particular day";
	}

	public Event[] filter(Event[] events) {
		List<Event> filtered = Arrays.asList(events).stream()
			.filter(e -> e.occursOn(date)).collect(Collectors.toList());
		
		Event[] results = new Event[filtered.size()];
		return filtered.toArray(results);
	}
}
