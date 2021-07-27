/**
 *	@(#)CalendarView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/
package project151;
import javax.swing.JComponent;

public interface CalendarView {
	public String getLabel();
	public JComponent getView();
	public void attach(Controller listener);
}
