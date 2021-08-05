/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Shyam Vyas
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/2
**/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MiniCalendarView extends JPanel {
	private Controller controller;
	private LocalDate date= LocalDate.now();
	private boolean enabled = true;
	int day= date.getDayOfMonth();
	Month mo= date.getMonth();
	int year= date.getYear();
	DailyView daily= new DailyView();
	ArrayList<Event> arr= new ArrayList<Event>();
	public MiniCalendarView() {
		super(new BorderLayout());
		this.date = LocalDate.now();
		updateCalendar();
	}
	public void setDailyView(DailyView daily) {
		this.daily=daily;
	}
	public JPanel getView() {
		return this;
	}
	public void setArrayList(Controller c) {
		if(c==null) {
			return;
		}
		arr=c.getModel().getArrayListOfEvents();
	}
	public ArrayList<Event> getEvents(){
		return arr;
	}
	public DailyView getDailyView() {
		return daily;
	}

	private void updateCalendar() {
		SpinnerNumberModel yearModel = new SpinnerNumberModel(this.date.getYear(), 0, 3000, 1);
		JComboBox<Month> months = new JComboBox<Month>(Month.values());
		
		months.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mo= (Month)months.getSelectedItem();
				day=LocalDate.now().getDayOfMonth();
				LocalDate ld= LocalDate.of(year, mo, day);
				String a= "";
				for(Event ec: getEvents()) {
					if(ec.getDate().equals(ld))
					{a=a+ec;
					a=a+"/n";}
				}
				daily.setText(a);
				daily.repaint();
				//system.out.println(mo);
			}
			
		});
		JSpinner years = new JSpinner(yearModel);
		years.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				year= (int)years.getValue();
				mo=LocalDate.now().getMonth();
				day=LocalDate.now().getDayOfMonth();
				LocalDate ld= LocalDate.of(year, mo, day);
				String a= "";
				for(Event ec: getEvents()) {
					if(ec.getDate().equals(ld))
					{a=a+ec;
					a=a+"/n";}
				}
				daily.setText(a);
				daily.repaint();
				//system.out.println(year);
			}
			
		});
		JButton today = new JButton("today");
		JButton left = new JButton("<");
		JButton right = new JButton(">");
		months.setEnabled(this.enabled);
		years.setEnabled(this.enabled);
		today.setEnabled(this.enabled);
		left.setEnabled(this.enabled);
		right.setEnabled(this.enabled);

		today.setBackground(Color.WHITE);
		left.setBackground(Color.WHITE);
		right.setBackground(Color.WHITE);
		months.setBackground(Color.WHITE);
		months.setSelectedItem(this.date.getMonth());
		years.setBackground(Color.WHITE);
		years.setEditor(new JSpinner.NumberEditor(years, "#"));

		today.addActionListener(event -> {
			mo=LocalDate.now().getMonth();
			year=LocalDate.now().getYear();
			day=LocalDate.now().getDayOfMonth();
			this.controller.setDate(LocalDate.now());
			LocalDate ld= LocalDate.of(year, mo, day);
			String a= "";
			for(Event ec: getEvents()) {
				if(ec.getDate().equals(ld))
				{a=a+ec;
				a=a+"/n";}
			}
			daily.setText(a);
			daily.repaint();
			//system.out.println(mo);
			//system.out.println(year);
			//system.out.println(day);
		});
		left.addActionListener(event -> {
			if(mo.equals(Month.JANUARY)) {
				year--;
				//system.out.println(year);
			}
			day=LocalDate.now().getDayOfMonth();
			
			this.controller.setDate(this.date.minusMonths(1));
			LocalDate ld= LocalDate.of(year, mo, day);
			String a= "";
			for(Event ec: getEvents()) {
				if(ec.getDate().equals(ld))
				{a=a+ec;
				a=a+"/n";}
			}
			daily.setText(a);
			daily.repaint();

		});
		months.addActionListener(event -> {
			this.controller.setDate(this.date.withMonth(months.getItemAt(months.getSelectedIndex()).getValue()));
			LocalDate ld= LocalDate.of(year, mo, day);
			String a= "";
			for(Event ec: getEvents()) {
				if(ec.getDate().equals(ld))
				{a=a+ec;
				a=a+"/n";}
			}
			daily.setText(a);
			daily.repaint();

		});
		right.addActionListener(event -> {
			if(mo.equals(Month.DECEMBER)) {
				year++;
				//system.out.println(year);
			}
			day=LocalDate.now().getDayOfMonth();
			this.controller.setDate(this.date.plusMonths(1));
			LocalDate ld= LocalDate.of(year, mo, day);
			String a= "";
			for(Event ec: getEvents()) {
				if(ec.getDate().equals(ld))
				{a=a+ec;
				a=a+"/n";}
			}
			daily.setText(a);
			daily.repaint();

		});
		years.addChangeListener(event -> {
			year= (int)years.getValue();
			this.controller.setDate(this.date.withYear(yearModel.getNumber().intValue()));
			LocalDate ld= LocalDate.of(year, mo, day);
			String a= "";
			for(Event ec: getEvents()) {
				if(ec.getDate().equals(ld))
				{a=a+ec;
				a=a+"/n";}
			}
			daily.setText(a);
			daily.repaint();

		});

		super.removeAll();
		JPanel panel = new JPanel();
		panel.add(today);
		panel.add(left);
		panel.add(months);
		panel.add(right);
		panel.add(years);

		super.add(panel, BorderLayout.PAGE_START);
		super.add(getChildPanel(), BorderLayout.PAGE_END);
		super.revalidate();
	}

	private JPanel getChildPanel() {
		JPanel panel= new JPanel();
		LocalDate day = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
		LocalDate now = LocalDate.now();
		int maxDay = this.date.lengthOfMonth();

		panel.setLayout(new GridLayout(7,7));
		JLabel label = new JLabel("Su", SwingConstants.CENTER);
		label.setEnabled(this.enabled);
		panel.add(label);
		label = new JLabel("Mo", SwingConstants.CENTER);
		label.setEnabled(this.enabled);
		panel.add(label);
		label = new JLabel("Tu", SwingConstants.CENTER);
		label.setEnabled(this.enabled);
		panel.add(label);
		label = new JLabel("We", SwingConstants.CENTER);
		label.setEnabled(this.enabled);
		panel.add(label);
		label = new JLabel("Th", SwingConstants.CENTER);
		label.setEnabled(this.enabled);
		panel.add(label);
		label = new JLabel("Fr", SwingConstants.CENTER);
		label.setEnabled(this.enabled);
		panel.add(label);
		label = new JLabel("Sa", SwingConstants.CENTER);

		int counter = 0;
		while (counter++ < day.getDayOfWeek().getValue() % 7) {
			panel.add(new JLabel());
		}
		
		for (int dayCounter = 1; dayCounter <= maxDay; ++dayCounter) {
			JButton button= new JButton(Integer.toString(dayCounter));
			button.setEnabled(this.enabled);
			button.addActionListener(event -> {
				MiniCalendarView.this.day= Integer.parseInt(button.getText());
				controller.setDate(LocalDate.of(this.date.getYear(), this.date.getMonth(), Integer.parseInt(event.getActionCommand())));
			});

			if (day.isEqual(now)) {
				button.setBackground(Color.GRAY);
			} else if (day.isEqual(this.date)) {
				button.setBackground(Color.YELLOW);
			} else {
				button.setBackground(Color.WHITE);
			}
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

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.enabled = enabled;
		updateCalendar();
	}
}
