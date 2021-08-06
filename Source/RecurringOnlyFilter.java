/**
 *	@(#)RecurringOnlyFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecurringOnlyFilter extends EventFilter {
	public String getName() {
		return "recurring events only";
	}

	public Event[] filter(Event[] events) {
		List<Event> filtered = Arrays.asList(events).stream()
			.filter(e -> e instanceof RecurringEvent).collect(Collectors.toList());

		return filtered.toArray(new Event[filtered.size()]);
	}
}
