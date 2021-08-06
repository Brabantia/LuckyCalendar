/**
 *	@(#)TimeOrderFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TimeOrderFilter extends EventFilter {
	public String getName() {
		return "order by time";
	}

	public Event[] filter(Event[] events) {
		List<Event> filtered = Arrays.asList(events).stream()
			.sorted(Comparator.comparing(Event::getDate))
			.collect(Collectors.toList());

		return filtered.toArray(new Event[filtered.size()]);
	}
}
