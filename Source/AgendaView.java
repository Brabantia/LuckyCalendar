/**
 *	@(#)AgendaView.java
 *
 *	@author Bingzhen Chen
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;

public class AgendaView extends JPanel implements CalendarView {
	private SpinnerDateModel beginDateModel = new SpinnerDateModel(Date.from(Instant.now()), null, null, Calendar.DAY_OF_YEAR);
	private SpinnerDateModel endDateModel = new SpinnerDateModel(Date.from(Instant.now()), null, null, Calendar.DAY_OF_YEAR);
	private JSpinner beginDate = new JSpinner(beginDateModel);
	private JSpinner endDate = new JSpinner(endDateModel);
	private JTextPane textPane;
	private JComboBox<EventFilter> filterList = new JComboBox<>();
	private Controller controller;

	public AgendaView() {
		super();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(430, 300));

		// The dates must be rendered in this format if the spinner is to increment by a day.
		beginDate.setEditor(new JSpinner.DateEditor(beginDate, "dd MMM yyyy"));
		endDate.setEditor(new JSpinner.DateEditor(endDate, "dd MMM yyyy"));

		filterList.addActionListener(event -> {
			if (this.controller != null) {
				refreshData();
			}
		});
		beginDate.addChangeListener(event -> {
			if (beginDateModel.getDate().after(endDateModel.getDate())) {
				beginDate.setValue(endDateModel.getDate());
				return;
			}
			refreshData();
		});
		endDate.addChangeListener(event -> {
			if (endDateModel.getDate().before(beginDateModel.getDate())) {
				endDate.setValue(beginDateModel.getDate());
				return;
			}
			refreshData();
		});

		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Begin"));
		topPanel.add(beginDate);
		topPanel.add(new JLabel("End"));
		topPanel.add(endDate);
		topPanel.add(filterList);
		add(topPanel, BorderLayout.PAGE_START);

		add(new JScrollPane(textPane = new JTextPane()), BorderLayout.CENTER);
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

	public void setFilters(EventFilter... filters) {
		filterList.removeAllItems();
		for (EventFilter filter : filters) {
			filterList.addItem(filter);
		}
		filterList.setSelectedIndex(0);
	}

	public void setDate(LocalDate date) {
		beginDate.setValue(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		endDate.setValue(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		// The beginDate might have failed because it was after the endDate.
		beginDate.setValue(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		
		refreshData();
	}

	public void refreshData() {
		LocalDate begin = beginDateModel.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate end = endDateModel.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String text = "";
		while (begin.compareTo(end) <= 0) {
			EventFilter filter = this.filterList.getItemAt(this.filterList.getSelectedIndex());
			Event[] daysEvents = filter.filter(this.controller.getDayEvents(begin));
			String date = Event.FORMATTER.format(begin);
			begin = begin.plusDays(1);
			if (daysEvents == null || daysEvents.length == 0) {
				continue;
			}

			text += "====== " + date + " ======\n";
			for (Event event : daysEvents) {
				text += "\n" + event;
			}
			text += "\n";
		}
		while (text.endsWith("\n")) {
			text = text.substring(0, text.length()-1);
		}
		textPane.setText(text);
		textPane.setCaretPosition(0);
    }
}
