/**
 *	@(#)AttendeeListView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttendeeListView extends JPanel {
    private final ArrayList<String> attendees = new ArrayList<>();

    public AttendeeListView() {
        super();
        super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        updateView();
    }

    private void updateView() {
        super.removeAll();


        if (this.attendees.size() == 0) {
            super.add(new JLabel(" "));
        }

        for (final String email : this.attendees) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton button = new JButton("X");
            JLabel label = new JLabel(email);
            panel.add(button);
            panel.add(label);
            super.add(panel);

            button.addActionListener(event -> {
                removeAttendee(email);
            });
        }
        super.revalidate();
    }

    public void clear() {
        this.attendees.clear();
        updateView();
    }

    public boolean removeAttendee(String target) {
        if (target == null || target.length() == 0) {
            return false;
        }

        for (int a = 0; a < this.attendees.size(); ++a) {
            if (target.equals(this.attendees.get(a))) {
                this.attendees.remove(a);
                updateView();
                return true;
            }
        }

        return false;
    }

    public boolean addAttendee(String email) {
        if (email == null || !email.contains("@")){
            return false;
        }
        this.attendees.add(email);
        updateView();

        return true;
    }

    public String[] getAttendees() {
        return this.attendees.toArray(new String[this.attendees.size()]);
    }
}
