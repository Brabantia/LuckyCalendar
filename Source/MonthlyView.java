/**
 *	@(#)MonthlyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import javax.swing.JComponent;
import javax.swing.JTextPane;
import java.time.LocalDate;

public class MonthlyView extends JTextPane implements CalendarView {
	private Controller controller;

	public MonthlyView() {
		super();
	}

	public String getLabel() {
		return "Month";
	}

	public JComponent getView() {
		return this;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setDate(LocalDate date) {
        StringBuffer sb = new StringBuffer("");
        sb.append("\t" + date.getMonth() + " " + date.getYear() + "\n");
        sb.append("  Su  Mo  Tu  We  Th  Fr  Sa" + "\n");

        LocalDate oneDay = LocalDate.of(date.getYear(), date.getMonth(), 1);

        for (int i = 0; i < oneDay.getDayOfWeek().getValue(); i++) {
            sb.append("    ");
        }

        LocalDate temp = LocalDate.of(date.getYear(), date.getMonth(), 1 + 0);
        for (int i = 1; i <= date.getMonth().maxLength(); i++) {
            if (this.controller.getDayEvents(temp).length > 0) {
                sb.append(String.format("%4s", "[" + i + "]"));
            } else {
                sb.append(String.format("%4s", i));
            }
            if ((i + oneDay.getDayOfWeek().getValue()) % 7 == 0) {
                sb.append("\n");
            }
            temp = temp.plusDays(1);
        }
        sb.append("\n");
        super.setText(sb.toString());
    }
}
