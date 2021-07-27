/**
 *	@(#)FrameView.java
 *
 *	@author Yorick van de Water, Shyam Vyas
 *	@version 1.00 2021/7/17
**/
package project151;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;

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
	LocalDate ld= LocalDate.now();
	JPanel overviewPanel;
	public FrameView() {
		this.miniCal = new MiniCalendarView(this);
		this.agenda = new AgendaView(this);
		this.views = new CalendarView[] {
			new DailyView(this), new WeeklyView(this), new MonthlyView(this), this.agenda
		};
		this.currentView = this.views[0];
		this.viewPanel.add(this.currentView.getView());

		JPanel monthButtonPanel = new JPanel();
		JPanel viewButtonPanel = new JPanel();
		JPanel eventButtonPanel = new JPanel();
		overviewPanel = new JPanel();
		JPanel eventViewPanel = new JPanel();
		
		this.leftButton.addActionListener(ActionEvent -> {
			miniCal.setLocalDate(miniCal.getLocalDate().minusMonths(1));
			overviewPanel.remove(MiniCalendarView.jl);
			overviewPanel.revalidate();
			overviewPanel.repaint();
			overviewPanel.add(miniCal.getView(), BorderLayout.SOUTH);
			overviewPanel.repaint();
		});
		leftButton.setBackground(Color.WHITE);
		this.rightButton.addActionListener(ActionEvent -> {
			miniCal.setLocalDate(miniCal.getLocalDate().plusMonths(1));
			overviewPanel.remove(MiniCalendarView.jl);
			overviewPanel.revalidate();
			overviewPanel.repaint();
			overviewPanel.add(miniCal.getView(), BorderLayout.SOUTH);
			overviewPanel.repaint();
		});
		rightButton.setBackground(Color.WHITE);
		this.todayButton.addActionListener(ActionEvent -> {
			miniCal.setLocalDate(LocalDate.now());
			overviewPanel.remove(MiniCalendarView.jl);
			overviewPanel.revalidate();
			overviewPanel.repaint();
			overviewPanel.add(miniCal.getView(), BorderLayout.SOUTH);
			overviewPanel.repaint();
		});
		todayButton.setBackground(Color.white);
		monthButtonPanel.add(this.todayButton);
		monthButtonPanel.add(this.leftButton);
		
		monthButtonPanel.add(this.rightButton);

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

	public void attach(Controller controller) {
		this.controller = controller;
		this.miniCal.attach(controller);
		for (CalendarView view : this.views) {
			view.attach(controller);
		}
	}

	public void display() {
		setVisible(true);
	}

	public void setFilters(String... filters) {
		this.agenda.setFilters(filters);
	}

	public void refreshData() {
	}

	/**
	 *	Set the view to the specified date. Can be called by the other views.
	**/
	public void setDate(LocalDate date) {
	}

	public void setView(String name) {
		if (name == null) return;

		for (CalendarView view : this.views) {
			if (name.equals(view.getLabel())) {
				this.viewPanel.removeAll();
				this.currentView = view;
				this.viewPanel.add(this.currentView.getView());
				this.viewPanel.revalidate();
			}
		}
	}
}
