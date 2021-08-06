/**
 *	@(#)WeeklyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.*;

public class WeeklyView extends JPanel implements CalendarView {
	private Controller controller;

	public WeeklyView() {
		super();
		setPreferredSize(new Dimension(430,300));
		setLayout(new GridLayout(7,1));
		add(new JLabel());
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

		if (localDate.getDayOfWeek().getValue() != 7){
			while (localDate.getDayOfWeek().getValue() != 1){
				localDate = localDate.minusDays(1);
			}
			localDate = localDate.minusDays(1);
		}


        removeAll();
        JPanel panels[] = new JPanel[7];
		String strs[] = {"Su","Mo","Tu","We","Th","Fr","Sa"};

		for (int i = 0;i < 7;i++){
			panels[i] = new JPanel();
			panels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			add(panels[i]);
			JLabel label = new JLabel(strs[i]);
			label.setPreferredSize(new Dimension(20,40));
			panels[i].add(label);
		}


        String text = "";
        HashSet<Event> set = new HashSet<>();

        for (int i = 0;i< 7;i++){
            Event[] list = this.controller.getDayEvents(localDate);
			String s = "";
			for (int j = 0;j< list.length;j++){
				s+=list[j].getName()+"\n";
			}
			JTextPane textPane = new JTextPane();
			textPane.setPreferredSize(new Dimension(380,40));
			textPane.setText(s);

			panels[i].add(new JScrollPane(textPane));
            localDate = localDate.plusDays(1);
        }

        for (Event e : set) {
            text += e + "\n";
        }

    }
}
