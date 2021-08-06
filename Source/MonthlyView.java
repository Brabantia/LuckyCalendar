/**
 *	@(#)MonthlyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class MonthlyView extends JPanel implements CalendarView {
	private Controller controller;
    private LocalDate date;


    public MonthlyView() {
		super();
		setPreferredSize(new Dimension(430,300));
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
	}

    private void updateCalendar() {
        super.removeAll();
        setLayout(new BorderLayout());
        super.add(new JLabel(this.date.getMonth() + " " + this.date.getYear(), SwingConstants.CENTER), BorderLayout.NORTH);
        super.add(getChildPanel(), BorderLayout.CENTER);
        super.revalidate();
    }

    private JPanel getChildPanel() {
        JPanel panel= new JPanel();
        LocalDate day = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
        LocalDate now = LocalDate.now();
        int maxDay = this.date.lengthOfMonth();

        panel.setLayout(new GridLayout(7,7));
        panel.add(new JLabel("Su"));
        panel.add(new JLabel("Mo"));
        panel.add(new JLabel("Tu"));
        panel.add(new JLabel("We"));
        panel.add(new JLabel("Th"));
        panel.add(new JLabel("Fr"));
        panel.add(new JLabel("Sa"));

        int counter = 0;
        while (counter++ < day.getDayOfWeek().getValue() % 7) {
            panel.add(new JLabel());
        }

        for (int dayCounter = 1; dayCounter <= maxDay; ++dayCounter) {
            JButton button= new JButton(Integer.toString(dayCounter));
//            button.addActionListener(event -> {
//                controller.setDate(LocalDate.of(this.date.getYear(), this.date.getMonth(), Integer.parseInt(event.getActionCommand())));
//
//            });

            Event[] events = controller.getDayEvents(day);
            if (events != null && events.length > 0) {
                button.setBackground(Color.gray);
//                String s = "";
//                for (int i = 0;i< events.length;i++){
//                    s+= events[i].getName()+"\n";
//                }
//                button.setText(button.getText()+"\n"+s);

                  button.setText(button.getText()+"["+events.length+"]");



            }else {
                button.setBackground(Color.WHITE);

            }
//            if (day.isEqual(now)) {
//                button.setBackground(Color.GRAY);
//            } else if (day.isEqual(this.date)) {
//                button.setBackground(Color.YELLOW);
//            } else {
//                button.setBackground(Color.WHITE);
//            }
            day = day.plusDays(1);

            panel.add(button);
        }

        counter += maxDay;
        while(counter++ <= 42) {
            panel.add(new JLabel());
        }
        return panel;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        updateCalendar();
    }

    public void attach(Controller controller) {
        this.controller = controller;
    }

	public String getLabel() {
		return "Month";
	}

	public JComponent getView() {
		return this;
	}

//	public void attach(Controller controller) {
//		this.controller = controller;
//	}
//
//	public void setDate(LocalDate date) {
//        StringBuffer sb = new StringBuffer("");
//        sb.append("\t" + date.getMonth() + " " + date.getYear() + "\n");
//        sb.append("  Su  Mo  Tu  We  Th  Fr  Sa" + "\n");
//
//        LocalDate oneDay = LocalDate.of(date.getYear(), date.getMonth(), 1);
//
//        for (int i = 0; i < oneDay.getDayOfWeek().getValue() % 7; i++) {
//            sb.append("    ");
//        }
//
//        LocalDate temp = LocalDate.of(date.getYear(), date.getMonth(), 1 + 0);
//        for (int i = 1; i <= date.getMonth().maxLength(); i++) {
//            if (this.controller.getDayEvents(temp).length > 0) {
//                sb.append(String.format("%4s", "[" + i + "]"));
//            } else {
//                sb.append(String.format("%4s", i));
//            }
//            if ((i + oneDay.getDayOfWeek().getValue()) % 7 == 0) {
//                sb.append("\n");
//            }
//            temp = temp.plusDays(1);
//        }
//        sb.append("\n");
//        super.setText(sb.toString());
//    }
}
