/**
 *	@(#)WeeklyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.JComponent;
import javax.swing.JTextPane;

public class WeeklyView extends JTextPane implements CalendarView {
	private Controller controller;

	public WeeklyView() {
		super();
		setPreferredSize(new Dimension(430,300));
	}
		
	public String getLabel() {
		return "Week";
	}
		
	public JComponent getView() {
		return this;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
	
	public void setDate(LocalDate date) {
		LocalDate localDate = LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth());

        while (localDate.getDayOfWeek().getValue() != 1){
            localDate = localDate.minusDays(1);
        }

        String text = "";
        HashSet<Event> set = new HashSet<>();

        for (int i = 0;i< 7;i++){
            Event[] list = this.controller.getDayEvents(localDate);
			Collections.addAll(set, list);
            localDate = localDate.plusDays(1);
        }

        for (Event e : set) {
            text += e + "\n";
        }
		
        super.setText(text);
    }
}
