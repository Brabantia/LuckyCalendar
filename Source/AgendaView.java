/**
 *	@(#)AgendaView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import javax.swing.JComponent;
import javax.swing.JTextPane;
import java.time.LocalDate;

public class AgendaView extends JTextPane implements CalendarView {
	private Controller controller;

	public AgendaView() {
		super();
	}

	public String getLabel() {
		return "Agenda";
	}

	public JComponent getView() {
		return this;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setFilters(String... filters) {
	}
	
	public void setDate(LocalDate date) {
        StringBuffer sb = new StringBuffer("");
		for (Event event : this.controller.getDayEvents(date)){
			sb.append(event+"\n");
		}
        super.setText(sb.toString());
    }
}
