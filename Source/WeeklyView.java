/**
 *	@(#)WeeklyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import javax.swing.JComponent;
import javax.swing.JTextPane;
import java.time.LocalDate;

public class WeeklyView extends JTextPane implements CalendarView {
	private Controller controller;

	public WeeklyView() {
		super();
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
		String text = "";
        for (Event e : this.controller.getWeekEvents(date)) {
            text += e + "\n";
        }
        super.setText(text);
    }
}
