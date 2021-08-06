/**
 *	@(#)DailyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class DailyView extends JTextPane implements CalendarView {
	private Controller controller;
	private LocalDate date = LocalDate.now();

	public DailyView() {
		super();
		super.setText("");
		setPreferredSize(new Dimension(430, 300));
	}

	public String getLabel() {
		return "Day";
	}

	public JComponent getView() {
		return new JScrollPane(this,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setDate(LocalDate date) {
		this.date = date;
		refreshData();
	}

	public void refreshData() {
        String text = "";
        for (Event e : this.controller.getDayEvents(this.date)) {
            text += e + "\n";
        }
        super.setText(text);
		super.setCaretPosition(0);
    }
}
