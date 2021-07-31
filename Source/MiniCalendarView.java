/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Shyam Vyas
 *	@version 1.00 2021/7/27
**/

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;

public class MiniCalendarView extends JComponent {
	private static HashMap<String, String> monthMap = new HashMap<>();
	private static HashMap<String, Integer> daysPerMonth= new HashMap<>();
	private final FrameView frame;
	private Controller controller;
	public static JPanel jl;
	LocalDate ld=LocalDate.now();
	String str;
	
	public MiniCalendarView(FrameView frame) {
		this.frame = frame;
	}

	public JComponent getView() {
		int counter=0;
		jl=new JPanel();
		String s= "";
		LocalDate now= ld;
		populateMap();
		String nowString=now.toString();
		int day= Integer.parseInt(nowString.substring(8));
		int year= Integer.parseInt(nowString.substring(0,4));
		String month= monthMap.get(nowString.substring(5, 7));
		s+=(month+" "+year);
		JLabel titleLabel= new JLabel(s);
		titleLabel.setFont(new Font("Courier New", Font.PLAIN, 18));
		jl.setLayout(new BoxLayout(jl, BoxLayout.Y_AXIS));
		jl.add(titleLabel);
		JPanel childPanel= new JPanel();
		childPanel.setLayout(new GridLayout(7,7));
		childPanel.add(new JLabel("Su"));
		childPanel.add(new JLabel("Mo"));
		childPanel.add(new JLabel("Tu"));
		childPanel.add(new JLabel("We"));
		childPanel.add(new JLabel("Th"));
		childPanel.add(new JLabel("Fr"));
		childPanel.add(new JLabel("Sa"));
		counter=counter+7;
		LocalDate first= LocalDate.of(now.getYear(), now.getMonth(), 1);
		int firstDay= first.getDayOfWeek().getValue();
		int dayCounter=1;
		if(firstDay==7) {
			firstDay=0;
		}
		for(int i=1;i<=firstDay;i++) {
			childPanel.add(new JLabel());
			counter++;
		}
		
		for(int i=3*firstDay+1;i<=20;i=i+3) {
			if(day==dayCounter && ld.equals(LocalDate.now())) {
				JButton jb= new JButton(Integer.toString(dayCounter));
				jb.setBackground(Color.YELLOW);
				childPanel.add(jb);
			}
			else {
				JButton jb= new JButton(Integer.toString(dayCounter));
				jb.setBackground(Color.white);
				childPanel.add(jb);
			}
			counter++;
			dayCounter++;
		}
		
		int line2counter=1;
		while(dayCounter<=9) {
			if(day==dayCounter && ld.equals(LocalDate.now())) {
				JButton jb= new JButton(Integer.toString(dayCounter));
				jb.setBackground(Color.YELLOW);
				childPanel.add(jb);
			}
			else {
				JButton jb= new JButton(Integer.toString(dayCounter));
				jb.setBackground(Color.WHITE);
				childPanel.add(jb);
			}
			line2counter=line2counter+1;
			if(dayCounter!=9) {
				line2counter=line2counter+2;
			}
			counter++;
			dayCounter++;
		}
		for(int i=line2counter;i<20;i=i+3) {
			if(day==dayCounter && ld.equals(LocalDate.now())) {
				JButton jb= new JButton(Integer.toString(dayCounter));
				jb.setBackground(Color.YELLOW);
				childPanel.add(jb);
			}
			else {
				JButton jb= new JButton(Integer.toString(dayCounter));
				jb.setBackground(Color.WHITE);
				childPanel.add(jb);
			}
			counter++;
			dayCounter++;
		}
		int daysInTheMonth=daysPerMonth.get(nowString.substring(5, 7));
		if(month.equals("02")&& year%4==0) {
			daysInTheMonth=29;
		}
		
		while(dayCounter<=daysInTheMonth) {
			for(int i=0;i<21 &&dayCounter<=daysInTheMonth;i=i+3) {
				if(day==dayCounter && ld.equals(LocalDate.now())) {
					JButton jb= new JButton(Integer.toString(dayCounter));
					jb.setBackground(Color.YELLOW);
					childPanel.add(jb);
				}
				else {
					JButton jb= new JButton(Integer.toString(dayCounter));
					jb.setBackground(Color.WHITE);
					childPanel.add(jb);
				}
				counter++;
				dayCounter++;
				
			}
		}
		for(Component c: childPanel.getComponents()) {
			c.setFont(new Font("Courier New", Font.PLAIN, 18));
		}
		if(counter<49) {
			while(counter<49) {
				childPanel.add(new JLabel());
				counter++;
			}
		}
		jl.add(childPanel);		
		return jl;
	}

	public void setLocalDate(LocalDate l) {
		ld=l;
	}
	
	public void attach(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * This method populates all of the maps with appropriate days per month, and each number with corresponding string month name and each letter with corresponding week day name.
	 */
	public static void populateMap() {
		monthMap.put("01", "January");
		monthMap.put("02", "February");
		monthMap.put("03", "March");
		monthMap.put("04", "April");
		monthMap.put("05", "May");
		monthMap.put("06", "June");
		monthMap.put("07", "July");
		monthMap.put("08", "August");
		monthMap.put("09", "September");
		monthMap.put("10", "October");
		monthMap.put("11", "November");
		monthMap.put("12", "December");
		daysPerMonth.put("01", 31);
		daysPerMonth.put("02", 28);
		daysPerMonth.put("03", 31);
		daysPerMonth.put("04", 30);
		daysPerMonth.put("05", 31);
		daysPerMonth.put("06", 30);
		daysPerMonth.put("07", 31);
		daysPerMonth.put("08", 31);
		daysPerMonth.put("09", 30);
		daysPerMonth.put("10", 31);
		daysPerMonth.put("11", 30);
		daysPerMonth.put("12", 31);
	}

}
