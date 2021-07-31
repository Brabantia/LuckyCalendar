/**
 *	@(#)FrameView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;

public class FrameView extends JFrame {
	private final MiniCalendarView miniCal;
	private final CalendarView[] views;
	private final AgendaView agenda;
	private final JPanel viewPanel = new JPanel();
	private final JButton todayButton = new JButton("Today");
	private final JButton leftButton = new JButton("<");
	private final JButton rightButton = new JButton(">");
	private final JButton createButton = new JButton("Create");
	private final JButton fromFileButton = new JButton("From File");
	private final JButton[] viewButtons;
	private CalendarView currentView;
	private Controller controller;
	private LocalDate date;

	public FrameView() {
		this.date = LocalDate.now();
		this.miniCal = new MiniCalendarView();
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

		monthButtonPanel.add(this.leftButton);
		monthButtonPanel.add(this.todayButton);
		monthButtonPanel.add(this.rightButton);
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextMonth();
			}
		});
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previousMonth();
			}
		});
		todayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDate(LocalDate.now());
			}
		});
		viewButtons = new JButton[this.views.length];
		for (int a = 0; a < this.views.length; ++a) {
			viewButtons[a] = new JButton(this.views[a].getLabel());
			viewButtonPanel.add(viewButtons[a]);
			viewButtons[a].addActionListener(event -> {
				setView(event.getActionCommand());
			});
		}

		eventButtonPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Icon.png"))));
		eventButtonPanel.add(this.createButton);
		eventButtonPanel.add(this.fromFileButton);

		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createEvent();
			}
		});
		fromFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
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

	private void nextMonth() {
		setDate(this.date.plusMonths(1));
	}

	private void previousMonth() {
		setDate(this.date.minusMonths(1));
	}

	private void createEvent() {
		String name = JOptionPane.showInputDialog("Enter the date name");
		if (name == null) {
			return;
		}
		String date = JOptionPane.showInputDialog("Enter the date [mm/dd/yyyy]");
		String startTime = JOptionPane.showInputDialog("Enter the start time [HH:mm]");
		String endTime = JOptionPane.showInputDialog("Enter the end time [HH:mm]");
		TimeInterval timeInterval = new TimeInterval(startTime, endTime);
		Event event = new Event(name, date, timeInterval);

		Event conflict = this.controller.createEvent(event);
		if (conflict == null) {
			JOptionPane.showMessageDialog(null, "Add Success!");
		} else {
			JOptionPane.showMessageDialog(null, "There's a conflicting event: " + conflict);
		}
	}

	private void loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(this);
		File file = fileChooser.getSelectedFile();
		if(file != null) {
			this.controller.addEventsFromFile(file.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "Import file success!");
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
