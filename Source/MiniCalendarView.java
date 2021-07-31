/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Yorick van de Water, Shyam Vyas
 *	@version 1.00 2021/7/17
**/

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.Border;

public class MiniCalendarView extends JComponent {
	private final FrameView frame;
	private Controller controller;
	public static JPanel jl;
	LocalDate ld=LocalDate.now();
	String str;
	
	public void setLocalDate(LocalDate l) {
		ld=l;
	}
	public LocalDate getLocalDate() {
		return ld;
	}
	
	public MiniCalendarView(FrameView frame) {
		this.frame = frame;
	}
	public JComponent getView() {
		int counter=0;
		jl=new JPanel();
		String s= "";
		LocalDate now= ld;
		MyCalendar.populateMap();
		String nowString=now.toString();
		int day= Integer.parseInt(nowString.substring(8));
		int year= Integer.parseInt(nowString.substring(0,4));
		String month= MyCalendar.monthMap.get(nowString.substring(5, 7));
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
		int daysInTheMonth=MyCalendar.daysPerMonth.get(nowString.substring(5, 7));
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

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
