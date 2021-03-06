/**
 *	@(#)FrameView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrameView extends JFrame {
	private final MiniCalendarView miniCal;
	private final CalendarView[] views;
	private final AgendaView agenda;
	private final CardLayout viewLayout = new CardLayout();
	private final JPanel viewPanel = new JPanel(viewLayout);
	private final JButton createButton = new JButton("Create");
	private final JButton fromFileButton = new JButton("From File");
	private final JButton[] viewButtons;
	private Controller controller;
	private LocalDate date;

	public FrameView() {
		this.date = LocalDate.now();
		this.miniCal = new MiniCalendarView();
		this.agenda = new AgendaView();
		this.views = new CalendarView[] {
			new DailyView(), new WeeklyView(), new MonthlyView(), this.agenda
		};

		JPanel monthButtonPanel = new JPanel();
		JPanel viewButtonPanel = new JPanel();
		JPanel eventButtonPanel = new JPanel();
		JPanel overviewPanel = new JPanel();
		JPanel eventViewPanel = new JPanel();

		viewButtons = new JButton[this.views.length];
		for (int a = 0; a < this.views.length; ++a) {
			this.viewPanel.add(this.views[a].getView(), this.views[a].getLabel());
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
		overviewPanel.add(eventButtonPanel, BorderLayout.PAGE_START);
		overviewPanel.add(monthButtonPanel, BorderLayout.CENTER);
		overviewPanel.add(miniCal.getView(), BorderLayout.PAGE_END);

		eventViewPanel.setLayout(new BorderLayout());
		eventViewPanel.add(viewButtonPanel, BorderLayout.PAGE_START);
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
		validate();
		pack();
		setResizable(false);
	}

	private void exit() {
		this.controller.exit();
	}

	private void createEvent() {
		CreateEventDialogView dialog = new CreateEventDialogView(this);
		Event newEvent = dialog.createEvent(this.date);
		if (newEvent == null) {
			return;
		}

		Event conflict = this.controller.createEvent(newEvent);
		if (conflict == null) {
			return;
		}
		JOptionPane.showMessageDialog(this,
			"New event conflicts with existing event: " + conflict,
			"Conflict event", JOptionPane.ERROR_MESSAGE);
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
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}

	public void setFilters(EventFilter... filters) {
		this.agenda.setFilters(filters);
	}

	public void refreshData() {
		setDate(this.date);
		for (CalendarView view : this.views) {
			view.refreshData();
		}
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

		this.viewLayout.show(this.viewPanel, name);
		refreshData();
		super.revalidate();
		super.repaint();
	}
}
