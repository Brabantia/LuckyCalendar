/**
 *	@(#)CalendarView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.LocalDate;
import javax.swing.JComponent;

public interface CalendarView {
	public String getLabel();
	public JComponent getView();
	public void attach(Controller listener);
	public void setDate(LocalDate date);
}
