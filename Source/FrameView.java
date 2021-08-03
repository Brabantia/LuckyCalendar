/**
 *	@(#)FrameView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/2
**/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameView extends JFrame {
	private final MiniCalendarView miniCal;
	private final CalendarView[] views;
	private final AgendaView agenda;
	private final JPanel viewPanel = new JPanel();
	private final JButton createButton = new JButton("Create");
	private final JButton fromFileButton = new JButton("From File");
	private final JButton[] viewButtons;
	private CalendarView currentView;
	private Controller controller;
	private LocalDate date;
	private LocalDate eventStartDate;
	private LocalDate eventEndDate;

	public FrameView() {
		this.date = LocalDate.now();
		this.miniCal = new MiniCalendarView(this.date);
		this.agenda = new AgendaView();
		this.views = new CalendarView[] {
			new DailyView(), new WeeklyView(), new MonthlyView(), this.agenda
		};

		this.currentView = this.views[0];
		this.viewPanel.add(new JScrollPane(this.currentView.getView()));

		JPanel monthButtonPanel = new JPanel();
		JPanel viewButtonPanel = new JPanel();
		JPanel eventButtonPanel = new JPanel();
		JPanel overviewPanel = new JPanel();
		JPanel eventViewPanel = new JPanel();

		viewButtons = new JButton[this.views.length];
		for (int a = 0; a < this.views.length; ++a) {
			viewButtons[a] = new JButton(this.views[a].getLabel());
			viewButtonPanel.add(viewButtons[a]);
			viewButtons[a].addActionListener(event -> {
				setView(event.getActionCommand());
			});
		}

		eventButtonPanel.add(new JLabel(new ImageIcon(getClass().getResource("Icon.png"))));
		eventButtonPanel.add(this.createButton);
		eventButtonPanel.add(this.fromFileButton);

		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createEvent();
			}
		});
		fromFileButton.addActionListener(event -> {
			loadFile();
		});

		overviewPanel.setLayout(new BorderLayout());
		overviewPanel.add(eventButtonPanel, BorderLayout.NORTH);
		overviewPanel.add(monthButtonPanel, BorderLayout.CENTER);
		overviewPanel.add(miniCal.getView(), BorderLayout.SOUTH);

		eventViewPanel.setLayout(new BorderLayout());
		eventViewPanel.add(viewButtonPanel, BorderLayout.NORTH);
		eventViewPanel.add(viewPanel, BorderLayout.CENTER);

		this.setLayout(new FlowLayout());
		this.add(overviewPanel);
		this.add(eventViewPanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				exit();
			}
		});

		setTitle("Calendar");
		pack();
		setResizable(false);
	}

	private void exit() {
		this.controller.exit();
	}

	private void createEvent() {
		SpinnerModel startHourModel = new SpinnerNumberModel(0, 0, 23, 1);
		SpinnerModel startMinuteModel = new SpinnerNumberModel(0, 0, 45, 15);
		SpinnerModel endHourModel = new SpinnerNumberModel(0, 0, 23, 1);
		SpinnerModel endMinuteModel = new SpinnerNumberModel(0, 0, 45, 15);
		JDialog dialog = new JDialog(this, "Create Event", true);
				
		JButton addAttendee = new JButton("Add");
		JButton cancel = new JButton("Cancel");
		JButton create = new JButton("Create");
		JCheckBox friday = new JCheckBox();
		JCheckBox isAvailable = new JCheckBox();
		JCheckBox isRecurring = new JCheckBox();
		JCheckBox monday = new JCheckBox();
		JCheckBox saturday = new JCheckBox();
		JCheckBox sunday = new JCheckBox();
		JCheckBox thursday = new JCheckBox();
		JCheckBox tuesday = new JCheckBox();
		JCheckBox wednesday = new JCheckBox();
		JLabel startDateLabel = new JLabel("Start Date");
		JSpinner endHourSpinner = new JSpinner(endHourModel);
		JSpinner endMinuteSpinner = new JSpinner(endMinuteModel);
		JSpinner startMinuteSpinner = new JSpinner(startMinuteModel);
		JSpinner startHourSpinner = new JSpinner(startHourModel);
		JTextArea description = new JTextArea(4, 60);
		JTextField attendee = new JTextField(35);
		JTextField eventName = new JTextField(20);
		JTextField location = new JTextField(50);
		MiniCalendarView endDateCal = new MiniCalendarView(this.date);
		MiniCalendarView startDateCal = new MiniCalendarView(this.date);
		
		startDateCal.attach(new Controller(null, null) {
			public void setDate(LocalDate date) {
				eventStartDate = date;
				startDateCal.setDate(date);
			}
		});
		endDateCal.attach(new Controller(null, null) {
			public void setDate(LocalDate date) {
				eventEndDate = date;
				endDateCal.setDate(date);
			}
		});

		dialog.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20,10,1,10);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Event Name", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(eventName, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Recurring", SwingConstants.RIGHT), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(isRecurring, c);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(1,10,1,10);
		dialog.add(new JLabel("Start Hour", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(startHourSpinner, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("End Hour", SwingConstants.RIGHT), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(endHourSpinner, c);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Start Minutes", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(startMinuteSpinner, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("End Minutes", SwingConstants.RIGHT), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(endMinuteSpinner, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		dialog.add(startDateLabel, c);
		c.gridx = 2;
		dialog.add(new JLabel("End Date", SwingConstants.CENTER), c);
		c.gridx = 0;
		c.gridy = 4;
		dialog.add(startDateCal, c);
		c.gridx = 2;
		dialog.add(endDateCal, c);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 4;
		dialog.add(new JLabel("Weekly Recurring Event", SwingConstants.CENTER), c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Monday", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(monday, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Tuesday", SwingConstants.RIGHT), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(tuesday, c);
		c.gridx = 0;
		c.gridy = 7;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Wednesday", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(wednesday, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Thursday", SwingConstants.RIGHT), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(thursday, c);
		c.gridx = 0;
		c.gridy = 8;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Friday", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(friday, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Saturday", SwingConstants.RIGHT), c);
		c.gridx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(saturday, c);
		c.gridx = 0;
		c.gridy = 9;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Sunday", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(sunday, c);
		c.gridx = 0;
		c.gridy = 10;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Location", SwingConstants.RIGHT), c);
		c.gridwidth = 3;
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(location, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 11;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Free", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(isAvailable, c);
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.CENTER;
		dialog.add(new JLabel("Description", SwingConstants.CENTER), c);
		c.gridy = 13;
		dialog.add(description, c);
		c.gridy = 14;
		dialog.add(new JLabel("Attendees", SwingConstants.CENTER), c);
		c.gridy = 15;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_END;
		dialog.add(new JLabel("Email", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.LINE_START;
		dialog.add(attendee, c);
		c.gridwidth = 1;
		c.gridx = 3;
		dialog.add(addAttendee, c);
		c.gridx = 0;
		c.gridy = 16;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(1,10,20,10);
		dialog.add(create, c);
		c.gridx = 2;
		dialog.add(cancel, c);

		dialog.pack();
		dialog.setVisible(true);
	}

	private void loadFile() {
		JFileChooser chooser = new JFileChooser();
		// Set chooser to current working directory.
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = chooser.getSelectedFile();
		if (file == null) {
			JOptionPane.showMessageDialog(this, "No file selected");
			return;
		}
		if (this.controller.addEventsFromFile(file.getAbsolutePath())) {
			JOptionPane.showMessageDialog(this, "Successfully imported events from file");
		} else {
			JOptionPane.showMessageDialog(this, "Failure to load or parse events from file");
		}
	}

	public void attach(Controller controller) {
		this.controller = controller;
		this.miniCal.attach(controller);
		for (CalendarView view : this.views) {
			view.attach(controller);
		}
	}

	public void display() {
		refreshData();
		setVisible(true);
	}

	public void setFilters(String... filters) {
		this.agenda.setFilters(filters);
	}

	public void refreshData() {
		setDate(this.date);
	}

	/**
	 *	Set the view to the specified date. Can be called by the other views.
	**/
	public void setDate(LocalDate date) {
		this.date = date;
		this.miniCal.setDate(date);
		for (CalendarView view : this.views) {
			view.setDate(date);
		}
	}

	public void setView(String name) {
		if (name == null) return;

		for (CalendarView view : this.views) {
			if (name.equals(view.getLabel())) {
				this.viewPanel.removeAll();
				this.currentView = view;
				this.viewPanel.add(new JScrollPane(this.currentView.getView()));
				this.viewPanel.revalidate();
			}
		}
	}
}
