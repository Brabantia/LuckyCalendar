/**
 *	@(#)DailyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import javax.swing.JComponent;
import javax.swing.JTextPane;
import java.time.LocalDate;

public class DailyView extends JTextPane implements CalendarView {
	private Controller controller;

	public DailyView() {
		super();
	}

	public String getLabel() {
		return "Day";
	}

	public JComponent getView() {
		return this;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setDate(LocalDate date) {
        String text = "";
        for (Event e : this.controller.getDayEvents(date)) {
            text += e + "\n";
        }
        super.setText(text);
    }
}