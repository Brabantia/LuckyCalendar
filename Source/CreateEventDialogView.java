/**
 *	@(#)CreateEventDialogView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/3
**/

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class CreateEventDialogView extends JDialog {
	private final SpinnerModel startHourModel = new SpinnerNumberModel(0, 0, 23, 1);
	private final SpinnerModel startMinuteModel = new SpinnerNumberModel(0, 0, 45, 15);
	private final SpinnerModel endHourModel = new SpinnerNumberModel(0, 0, 23, 1);
	private final SpinnerModel endMinuteModel = new SpinnerNumberModel(0, 0, 45, 15);

	private final JButton addAttendee = new JButton("Add");
	private final JButton cancel = new JButton("Cancel");
	private final JButton create = new JButton("Create");
	private final JCheckBox friday = new JCheckBox();
	private final JCheckBox isAvailable = new JCheckBox();
	private final JCheckBox isRecurring = new JCheckBox();
	private final JCheckBox monday = new JCheckBox();
	private final JCheckBox saturday = new JCheckBox();
	private final JCheckBox sunday = new JCheckBox();
	private final JCheckBox thursday = new JCheckBox();
	private final JCheckBox tuesday = new JCheckBox();
	private final JCheckBox wednesday = new JCheckBox();
	private final JLabel startDateLabel = new JLabel("Start Date");
	private final JLabel endDateLabel = new JLabel("End Date");
	private final JLabel startMinutesLabel = new JLabel("Start Minutes");
	private final JLabel endMinutesLabel = new JLabel("End Minutes");
	private final JLabel startHoursLabel = new JLabel("Start Hours");
	private final JLabel endHoursLabel = new JLabel("End Hours");
	private final JLabel mondayLabel = new JLabel("Monday");
	private final JLabel tuesdayLabel = new JLabel("Tuesday");
	private final JLabel wednesdayLabel = new JLabel("Wednesday");
	private final JLabel thursdayLabel = new JLabel("Thursday");
	private final JLabel fridayLabel = new JLabel("Friday");
	private final JLabel saturdayLabel = new JLabel("Saturday");
	private final JLabel sundayLabel = new JLabel("Sunday");
	private final JLabel weeklyRecurringLabel = new JLabel("Weekly Recurring Event");
	private final JSpinner endHourSpinner = new JSpinner(endHourModel);
	private final JSpinner endMinuteSpinner = new JSpinner(endMinuteModel);
	private final JSpinner startMinuteSpinner = new JSpinner(startMinuteModel);
	private final JSpinner startHourSpinner = new JSpinner(startHourModel);
	private final JTextArea description = new JTextArea(4, 60);
	private final JTextField attendee = new JTextField(35);
	private final JTextField eventName = new JTextField(20);
	private final JTextField location = new JTextField(50);
	private final MiniCalendarView endDateCal = new MiniCalendarView();
	private final MiniCalendarView startDateCal = new MiniCalendarView();
	private final ArrayList<String> attendees = new ArrayList<>();

	private JButton[] removeAttendee;
	private Controller controller;
	private LocalDate startDate;
	private LocalDate endDate;
	private Event createdEvent;

    public CreateEventDialogView(Frame parent, Controller controller) {
		super(parent, "Create Event", true);
		this.controller = controller;

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
			renderEvent();
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

			attendees.add(email);
			attendee.setText("");
			setup();
			revalidate();
		});

		setup();
		setEnabled(false);
	}

	private void setup() {
		super.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int row = 0;
		c.insets = new Insets(20,10,1,10);
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Event Name"), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(eventName, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Recurring"), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(isRecurring, c);
		c.gridx = 0;
		c.gridy = row++;
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
		c.gridy = row++;
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
		c.gridy = row++;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		super.add(startDateLabel, c);
		c.gridx = 2;
		super.add(endDateLabel, c);
		c.gridx = 0;
		c.gridy = row++;
		super.add(startDateCal, c);
		c.gridx = 2;
		super.add(endDateCal, c);
		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 4;
		super.add(weeklyRecurringLabel, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(mondayLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(monday, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(tuesdayLabel, c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(tuesday, c);
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(wednesdayLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(wednesday, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(thursdayLabel, c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(thursday, c);
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(fridayLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(friday, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(saturdayLabel, c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(saturday, c);
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(sundayLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(sunday, c);
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Location"), c);
		c.gridwidth = 3;
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(location, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row++;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Free"), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(isAvailable, c);
		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.CENTER;
		super.add(new JLabel("Description"), c);
		c.gridy = row++;
		super.add(description, c);
		c.gridy = row++;
		super.add(new JLabel("Attendees"), c);
		c.gridy = row++;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_END;
		super.add(new JLabel("Email"), c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.LINE_START;
		super.add(attendee, c);
		c.gridwidth = 1;
		c.gridx = 3;
		super.add(addAttendee, c);

		removeAttendee = new JButton[attendees.size()];
		for (int a = 0; a < attendees.size(); ++a) {
			removeAttendee[a] = new JButton("X");
			final int index = a;
			removeAttendee[a].addActionListener(event ->{
				attendees.remove(index);
				setup();
				revalidate();
			});
			
			c.gridx = 0;
			c.gridy = row++;
			c.gridwidth = 2;
			super.add(new JLabel(attendees.get(a)), c);
			c.gridx = 2;
			c.gridwidth = 1;
			super.add(removeAttendee[a], c);
		}

		c.gridx = 0;
		c.gridy = row++;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(1,10,20,10);
		super.add(create, c);
		c.gridx = 2;
		super.add(cancel, c);

		super.pack();
    }

	private void renderEvent() {
		this.createdEvent = new Event(eventName.getText(), this.startDate, null);
		super.setVisible(false);
	}

	public void setEnabled(boolean enabled) {
		this.endDateCal.setEnabled(enabled);
		this.endHourSpinner.setEnabled(enabled);
		this.endMinuteSpinner.setEnabled(enabled);
		this.friday.setEnabled(enabled);
		this.monday.setEnabled(enabled);
		this.saturday.setEnabled(enabled);
		this.sunday.setEnabled(enabled);
		this.thursday.setEnabled(enabled);
		this.tuesday.setEnabled(enabled);
		this.wednesday.setEnabled(enabled);
		this.endDateLabel.setEnabled(enabled);
		this.endMinutesLabel.setEnabled(enabled);
		this.endHoursLabel.setEnabled(enabled);
		this.mondayLabel.setEnabled(enabled);
		this.tuesdayLabel.setEnabled(enabled);
		this.wednesdayLabel.setEnabled(enabled);
		this.thursdayLabel.setEnabled(enabled);
		this.fridayLabel.setEnabled(enabled);
		this.saturdayLabel.setEnabled(enabled);
		this.sundayLabel.setEnabled(enabled);
		this.weeklyRecurringLabel.setEnabled(enabled);

		this.startDateLabel.setText(enabled ? "Start Date" : "Date");
		for (int a = 0; a < this.removeAttendee.length; ++a) {
			this.removeAttendee[a].setEnabled(enabled);
		}

		super.revalidate();
	}

	public Event createEvent(LocalDate date) {
		this.attendees.clear();
		this.createdEvent = null;
		this.endDate = date;
		this.startDate = date;
		this.endDateCal.setDate(date);
		this.startDateCal.setDate(date);
		
		// This blocks until the dialog is closed.
		super.setVisible(true);

		return this.createdEvent;
	}
}
