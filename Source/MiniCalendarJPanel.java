import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MiniCalendarJPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private LocalDateTime localDateTime;
    private final static String week[] = {"S", "M", "T", "W", "T", "F", "S"};
    private JLabel dayLabel[][] = new JLabel[6][7], monthLable;
    private final String monthStrs[] = {"January", "February", "March ", "April ", "May ", " June ", "July ", "August", "September", " October", "November", "December"};

    private LocalDate selectDay;

    public LocalDate getSelectDay() {
        return selectDay;
    }

    public MiniCalendarJPanel(Controller controller) {
        localDateTime = LocalDateTime.now();
        selectDay = LocalDate.of(localDateTime.getYear(),localDateTime.getMonth(),localDateTime.getDayOfMonth());

        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        monthLable = new JLabel();
        northPanel.add(monthLable);

        JLabel preMonth = new JLabel("  <  ");
        preMonth.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                calendarAdd(-1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        northPanel.add(preMonth);
        JLabel nextMonth = new JLabel("  >  ");
        nextMonth.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                calendarAdd(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        northPanel.add(nextMonth);
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(7, 7, 1, 1));
        centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        for (String s : week) {
            JLabel weekButton = new JLabel(s);
            weekButton.setBackground(Color.WHITE);
            weekButton.setHorizontalAlignment(JLabel.CENTER);
            centerPanel.add(weekButton);
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                JPanel tempJpanel = new JPanel();
                dayLabel[i][j] = new JLabel();
                tempJpanel.setBackground(Color.white);
                dayLabel[i][j].setHorizontalAlignment(JLabel.CENTER);

                centerPanel.add(tempJpanel);
                tempJpanel.add(dayLabel[i][j]);

                dayLabel[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 7; j++) {
                                dayLabel[i][j].getParent().setBackground(Color.white);
                            }
                        }
                        ((JLabel)e.getSource()).getParent().setBackground(Color.gray);

                        selectDay = LocalDate.of(localDateTime.getYear(),localDateTime.getMonth().getValue(),Integer.parseInt(((JLabel)e.getSource()).getText()));
                        System.out.println(Integer.parseInt(((JLabel)e.getSource()).getText()));
                        System.out.println(selectDay);
                        controller.refreshFrame(selectDay);

                        setVisible(true);
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }
                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(southPanel, BorderLayout.SOUTH);
        updateCalendar();

        setVisible(true);
    }

    private void updateCalendar() {
        int i = 0, j = (localDateTime.getDayOfWeek().getValue()-1) % 7;
        int maxDay = localDateTime.getMonth().maxLength();
        for (int k = 0; k < j; k++) dayLabel[0][k].setText("");
        for (int k = 1; k <= maxDay; k++) {
            dayLabel[i][j].setText(Integer.toString(k));
            if (j == 6) {
                i++;
                j = 0;
            } else j++;
        }
        while (i < 6) {
            dayLabel[i][j].setText("");
            if (j == 6) {
                i++;
                j = 0;
            } else j++;
        }
        monthLable.setText(monthStrs[localDateTime.getMonth().getValue() - 1] + "  " + localDateTime.getYear());
    }

    public void calendarAdd(int delta) {
        localDateTime = localDateTime.plusMonths(delta);
        updateCalendar();
    }

    public void updateDate(LocalDateTime localDateTime1){
        localDateTime = localDateTime1;
        updateCalendar();
    }

}