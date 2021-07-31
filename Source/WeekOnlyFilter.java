/**
 *	@(#)WeekOnlyFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WeekOnlyFilter implements EventFilter {
	private final Predicate<Event> su, mo, tu, we, th, fr, sa;

	public WeekOnlyFilter(LocalDate date) {
		LocalDate sunday = date.minusDays(date.getDayOfWeek().getValue());
		this.su = e -> e.occursOn(sunday);
		this.mo = e -> e.occursOn(sunday.plusDays(1));
		this.tu = e -> e.occursOn(sunday.plusDays(2));
		this.we = e -> e.occursOn(sunday.plusDays(3));
		this.th = e -> e.occursOn(sunday.plusDays(4));
		this.fr = e -> e.occursOn(sunday.plusDays(5));
		this.sa = e -> e.occursOn(sunday.plusDays(6));
	}

	public String getName() {
		return "only events in a particular week";
	}

	public Event[] filter(Event[] events) {
		List<Event> filtered = Arrays.asList(events).stream()
			.filter(su.or(mo).or(tu).or(we).or(th).or(fr).or(sa)).collect(Collectors.toList());
		
		Event[] results = new Event[filtered.size()];
		return filtered.toArray(results);
	}
}
