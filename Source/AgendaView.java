/**
 *	@(#)AgendaView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/8/5
**/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class AgendaView extends JPanel implements CalendarView {
	private Controller controller;
	private JButton button;
	private JTextField textField1,textField2;
	private JTextPane textPane;

	public AgendaView() {
		super();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(430,300));
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Begin date"));
		topPanel.add(textField1 = new JTextField(10));
		topPanel.add(new JLabel("End date"));
		topPanel.add(textField2 = new JTextField(10));
		add(topPanel,"North");

		topPanel.add(button = new JButton("Check"));

		textField1.setText(LocalDate.now().format(Event.FORMATTER));
		textField2.setText(LocalDate.now().format(Event.FORMATTER));

		add(new JScrollPane(textPane = new JTextPane()),"Center");

		button.addActionListener(actionEvent -> {
			setDate(LocalDate.now());
		});
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

	public void setFilters(String... filters) {
	}

	public void setDate(LocalDate date) {
		if (textField1.getText().equals("")){
			return;
		}
		try {
			LocalDate localDate1 = LocalDate.parse(textField1.getText().trim(),Event.FORMATTER);
			LocalDate localDate2 = LocalDate.parse(textField2.getText().trim(),Event.FORMATTER);
			StringBuffer sb = new StringBuffer("");
			while (localDate1.compareTo(localDate2) <=0){
				System.out.println(localDate1);
				for (Event event : this.controller.getDayEvents(localDate1)){
					sb.append(event+"\n");
				}
				localDate1 = localDate1.plusDays(1);
			}
			textPane.setText(sb.toString());
			textPane.setCaretPosition(0);
		}catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please input formatted date [MM/dd/yyyy]");
		}
    }
}
