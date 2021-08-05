/**
 *	@(#)CreateEventDialogView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class CreateEventDialogView extends JDialog {
	private final SpinnerModel startHourModel = new SpinnerNumberModel(0, 0, 23, 1);
	private final SpinnerModel startMinuteModel = new SpinnerNumberModel(0, 0, 45, 15);
	private final SpinnerModel endHourModel = new SpinnerNumberModel(0, 0, 23, 1);
	private final SpinnerModel endMinuteModel = new SpinnerNumberModel(0, 0, 45, 15);

	private final JButton addAttendee = new JButton("Add");
	private final JButton cancel = new JButton("Cancel");
	private final JButton create = new JButton("Create");
	private final JCheckBox friCheckBox = new JCheckBox();
	private final JCheckBox isAvailable = new JCheckBox();
	private final JCheckBox isRecurring = new JCheckBox();
	private final JCheckBox monCheckBox = new JCheckBox();
	private final JCheckBox satCheckBox = new JCheckBox();
	private final JCheckBox sunCheckBox = new JCheckBox();
	private final JCheckBox thuCheckBox = new JCheckBox();
	private final JCheckBox tueCheckBox = new JCheckBox();
	private final JCheckBox wedCheckBox = new JCheckBox();
	private final JLabel startDateLabel = new JLabel("Start Date");
	private final JLabel endDateLabel = new JLabel("End Date");
	private final JLabel startMinutesLabel = new JLabel("Start Minutes");
	private final JLabel endMinutesLabel = new JLabel("End Minutes");
	private final JLabel startHoursLabel = new JLabel("Start Hours");
	private final JLabel endHoursLabel = new JLabel("End Hours");
	private final JLabel monLabel = new JLabel("Mon");
	private final JLabel tueLabel = new JLabel("Tue");
	private final JLabel wedLabel = new JLabel("Wed");
	private final JLabel thuLabel = new JLabel("Thu");
	private final JLabel friLabel = new JLabel("Fri");
	private final JLabel satLabel = new JLabel("Sat");
	private final JLabel sunLabel = new JLabel("Sun");
	private final JLabel weeklyRecurringLabel = new JLabel("Weekly Recurring Event");
	private final JSpinner endHourSpinner = new JSpinner(endHourModel);
	private final JSpinner endMinuteSpinner = new JSpinner(endMinuteModel);
	private final JSpinner startMinuteSpinner = new JSpinner(startMinuteModel);
	private final JSpinner startHourSpinner = new JSpinner(startHourModel);
	private final JTextArea description = new JTextArea(4, 30);
	private final JTextField attendee = new JTextField(15);
	private final JTextField eventName = new JTextField(10);
	private final JTextField location = new JTextField(20);
	private final MiniCalendarView endDateCal = new MiniCalendarView();
	private final MiniCalendarView startDateCal = new MiniCalendarView();
	private final AttendeeListView attendeeList = new AttendeeListView();

	private LocalDate startDate;
	private LocalDate endDate;
	private Event createdEvent;

    public CreateEventDialogView(Frame parent) {
		super(parent, "Create Event", true);

		startDateCal.attach(new Controller(null, null) {
			public void setDate(LocalDate date) {
				startDate = date;
				startDateCal.setDate(date);
			}
		});
		endDateCal.attach(new Controller(null, null) {
			public void setDate(LocalDate date) {
				endDate = date;
				endDateCal.setDate(date);
			}
		});
		isRecurring.addActionListener(event -> {
			setEnabled(isRecurring.isSelected());
		});
		create.addActionListener(event -> {
			try {
				renderEvent();
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error creating event: " + e.getMessage(), "Cannot create event", JOptionPane.ERROR_MESSAGE);
			}
		});
		cancel.addActionListener(event -> {
			setVisible(false);
		});
		addAttendee.addActionListener(event -> {
			String email = attendee.getText();
			if (email == null || email.length() == 0) {
				return;
			}
			if (email.indexOf("@") < 0) {
				JOptionPane.showMessageDialog(this, "Enter a valid email address for the attendee");
				return;
			}

			attendeeList.addAttendee(email);
			attendee.setText("");
			super.revalidate();
		});

		setup();
		setEnabled(false);
	}

	private void setup() {
		super.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20,10,1,10);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Event Name"), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		super.add(eventName, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Recurring"), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(isRecurring, c);
		c.gridx = 0;
		c.gridy++;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(1,10,1,10);
		super.add(startHoursLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(startHourSpinner, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(endHoursLabel, c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(endHourSpinner, c);
		c.gridx = 0;
		c.gridy++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(startMinutesLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(startMinuteSpinner, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(endMinutesLabel, c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(endMinuteSpinner, c);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		super.add(startDateLabel, c);
		c.gridx = 2;
		super.add(endDateLabel, c);
		c.gridx = 0;
		c.gridy++;
		super.add(startDateCal, c);
		c.gridx = 2;
		super.add(endDateCal, c);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 4;
		super.add(weeklyRecurringLabel, c);
		JPanel recurringDaysPanel = new JPanel(new FlowLayout());
		recurringDaysPanel.add(sunLabel);
		recurringDaysPanel.add(sunCheckBox);
		recurringDaysPanel.add(monLabel);
		recurringDaysPanel.add(monCheckBox);
		recurringDaysPanel.add(tueLabel);
		recurringDaysPanel.add(tueCheckBox);
		recurringDaysPanel.add(wedLabel);
		recurringDaysPanel.add(wedCheckBox);
		recurringDaysPanel.add(thuLabel);
		recurringDaysPanel.add(thuCheckBox);
		recurringDaysPanel.add(friLabel);
		recurringDaysPanel.add(friCheckBox);
		recurringDaysPanel.add(satLabel);
		recurringDaysPanel.add(satCheckBox);
		c.gridy++;
		super.add(recurringDaysPanel, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Location"), c);
		c.gridwidth = 3;
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		super.add(location, c);
		JPanel availabilityPanel = new JPanel(new FlowLayout());
		availabilityPanel.add(new JLabel("Free"));
		availabilityPanel.add(isAvailable);
		availabilityPanel.add(new JLabel("(Others will see you as available at this time)"));
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy++;
		super.add(availabilityPanel, c);
		c.gridy++;
		super.add(new JLabel("Description"), c);
		c.gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		super.add(description, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy++;
		super.add(new JLabel("Attendees"), c);
		c.gridy++;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Email"), c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		super.add(attendee, c);
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridx = 3;
		super.add(addAttendee, c);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		JScrollPane scrollPane = new JScrollPane(
			attendeeList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(0, 100));
		super.add(scrollPane, c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		c.insets = new Insets(1,10,20,10);
		super.add(create, c);
		c.gridx = 2;
		super.add(cancel, c);
    }

	private TimeInterval getTimeInterval() {
		LocalTime start = LocalTime.of((Integer)startHourSpinner.getValue(), (Integer)startMinuteSpinner.getValue());
		LocalTime end = LocalTime.of((Integer)endHourSpinner.getValue(), (Integer)endMinuteSpinner.getValue());

		return new TimeInterval(start, end);
	}

	private DayOfWeek[] getRecurringDays() {
		ArrayList<DayOfWeek> days = new ArrayList<>();
		if (sunCheckBox.isSelected()) {
			days.add(DayOfWeek.SUNDAY);
		}
		if (monCheckBox.isSelected()) {
			days.add(DayOfWeek.MONDAY);
		}
		if (tueCheckBox.isSelected()) {
			days.add(DayOfWeek.TUESDAY);
		}
		if (wedCheckBox.isSelected()) {
			days.add(DayOfWeek.WEDNESDAY);
		}
		if (thuCheckBox.isSelected()) {
			days.add(DayOfWeek.THURSDAY);
		}
		if (friCheckBox.isSelected()) {
			days.add(DayOfWeek.FRIDAY);
		}
		if (satCheckBox.isSelected()) {
			days.add(DayOfWeek.SATURDAY);
		}
		return days.toArray(new DayOfWeek[days.size()]);
	}

	private void renderEvent() {
		if (isRecurring.isSelected()) {
			this.createdEvent = new RecurringEvent(
				eventName.getText(),
				description.getText(),
				location.getText(),
				isAvailable.isSelected(),
				startDate,
				getTimeInterval(),
				endDate,
				getRecurringDays(),
				attendeeList.getAttendees());
		} else {
			this.createdEvent = new Event(eventName.getText(),
				description.getText(),
				location.getText(),
				isAvailable.isSelected(),
				getTimeInterval(),
				startDate,
				attendeeList.getAttendees());
		}

		super.setVisible(false);
	}

	public void setEnabled(boolean enabled) {
		this.endDateCal.setEnabled(enabled);
		this.friCheckBox.setEnabled(enabled);
		this.monCheckBox.setEnabled(enabled);
		this.satCheckBox.setEnabled(enabled);
		this.sunCheckBox.setEnabled(enabled);
		this.thuCheckBox.setEnabled(enabled);
		this.tueCheckBox.setEnabled(enabled);
		this.wedCheckBox.setEnabled(enabled);
		this.endDateLabel.setEnabled(enabled);
		this.monLabel.setEnabled(enabled);
		this.tueLabel.setEnabled(enabled);
		this.wedLabel.setEnabled(enabled);
		this.thuLabel.setEnabled(enabled);
		this.friLabel.setEnabled(enabled);
		this.satLabel.setEnabled(enabled);
		this.sunLabel.setEnabled(enabled);
		this.weeklyRecurringLabel.setEnabled(enabled);

		this.startDateLabel.setText(enabled ? "Start Date" : "Date");

		super.revalidate();
	}

	public Event createEvent(LocalDate date) {
		LocalTime now = LocalTime.now();

		this.attendeeList.clear();
		this.createdEvent = null;
		this.endDate = date;
		this.startDate = date;
		this.endDateCal.setDate(date);
		this.startDateCal.setDate(date);
		endHourSpinner.setValue((now.getHour()+(now.getMinute()+30)/60)%24);
		endMinuteSpinner.setValue(15*((now.getMinute()/15+2)%4));
		startHourSpinner.setValue(now.getHour());
		startMinuteSpinner.setValue(15*(now.getMinute()/15));

		super.pack();
		super.setLocationRelativeTo(null);

		// This blocks until the dialog is closed.
		super.setVisible(true);

		return this.createdEvent;
	}
}
