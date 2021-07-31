

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class MyCalendar {

    private ArrayList<Event> events;

    public MyCalendar() {
        loadFile("events.txt");
    }

    public void printToFile(File file) {
        try {
            BufferedWriter br = new BufferedWriter(new PrintWriter(file));
            for (Event event : events) {

                LocalDate date = event.getEventTime().getDate();
                br.write(event.getName());
                br.newLine();

                if (event.getRecurring() == null) {
                    br.write(date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 2000 + " "
                            + event.getEventTime().getBeginTime() + " " + event.getEventTime().getEndTime());
                    br.newLine();
                } else {
                    br.write(event.getRecurring().getWeek() + " "
                            + event.getEventTime().getBeginTime() + " "
                            + event.getEventTime().getEndTime() + " "
                            + date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 2000 + " "
                            + event.getRecurring().getEndTime().getMonth().getValue() + "/" + event.getRecurring().getEndTime().getDayOfMonth() + "/" + event.getRecurring().getEndTime().getYear() % 2000);
                    br.newLine();
                }


            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDayOutput(LocalDate date) {
        date.getDayOfWeek().name();

        String data[] = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat",};
        String res = "";

        List<Event> list = getEvnetHappenInDay(date);
        for (Event e : list) {
            res += e + "\n";
        }
        return res;
    }

    public String getWeekOutput(LocalDate date) {

        LocalDate localDate = LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth());

        while (localDate.getDayOfWeek().getValue() != 1){
            localDate = localDate.minusDays(1);
        }

        String res = "";
        Set<Event> set = new HashSet<>();

        for (int i = 0;i< 7;i++){
            List<Event> list = getEvnetHappenInDay(localDate);
            set.addAll(list);
            localDate = localDate.plusDays(1);
        }

        for (Event e : set) {
            res += e + "\n";
        }
        return res;
    }

    public String getMonthOutputDetail(LocalDate date) {

        LocalDate localDate = LocalDate.of(date.getYear(),date.getMonth(),1);
        String res = "";
        Set<Event> set = new HashSet<>();

        for (int i = 0;i< localDate.getMonth().maxLength();i++){
            List<Event> list = getEvnetHappenInDay(localDate);
            set.addAll(list);
            localDate = localDate.plusDays(1);
        }

        for (Event e : set) {
            res += e + "\n";
        }
        return res;
    }

    public String getNowMonthOutput() {
        LocalDate date = LocalDate.now();
        StringBuffer sb = new StringBuffer("");
        sb.append("\t" + date.getMonth() + " " + date.getYear() + "\n");
        sb.append("  Su  Mo  Tu  We  Th  Fr  Sa" + "\n");

        LocalDate oneDay = LocalDate.of(date.getYear(), date.getMonth(), 1);

        for (int i = 0; i < oneDay.getDayOfWeek().getValue(); i++) {
            sb.append("    ");
        }
        for (int i = 1; i <= date.getMonth().maxLength(); i++) {
            if (i == date.getDayOfMonth()) {
                sb.append(String.format("%4s", "[" + i + "]"));
            } else {
                sb.append(String.format("%4s", i));
            }
            if ((i + oneDay.getDayOfWeek().getValue()) % 7 == 0) {
                sb.append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public String getMonthOutput(LocalDate date) {
        StringBuffer sb = new StringBuffer("");
        sb.append("\t" + date.getMonth() + " " + date.getYear() + "\n");
        sb.append("  Su  Mo  Tu  We  Th  Fr  Sa" + "\n");

        LocalDate oneDay = LocalDate.of(date.getYear(), date.getMonth(), 1);

        for (int i = 0; i < oneDay.getDayOfWeek().getValue(); i++) {
            sb.append("    ");
        }
        LocalDate temp = LocalDate.of(date.getYear(), date.getMonth(), 1 + 0);

        for (int i = 1; i <= date.getMonth().maxLength(); i++) {
            ArrayList<Event> eventList = getEvnetHappenInDay(temp);
            if (eventList.size() > 0) {
                sb.append(String.format("%4s", "[" + i + "]"));
            } else {
                sb.append(String.format("%4s", i));
            }
            if ((i + oneDay.getDayOfWeek().getValue()) % 7 == 0) {
                sb.append("\n");
            }
            temp = temp.plusDays(1);
        }
        sb.append("\n");
        return sb.toString();
    }

    public ArrayList<Event> getEvnetHappenInDay(LocalDate date) {
        ArrayList<Event> arrayList = new ArrayList<>();
        for (Event event : events) {
            if (event.getRecurring() == null) {
                if (event.getEventTime().getDate().equals(date)) {
                    arrayList.add(event);
                }
            } else {
                if (event.recurOccur(date)) {
                    arrayList.add(event);
                }
            }
        }
        return arrayList;
    }

    public boolean addEvent(Event event){
        List<Event> temp = getEvnetHappenInDay(event.getEventTime().getDate());
        for (Event e : temp) {
            if (e.getEventTime().getBeginTime().compareTo(event.getEventTime().getBeginTime()) > 0
                    && e.getEventTime().getBeginTime().compareTo(event.getEventTime().getEndTime()) < 0) {
                System.out.println("a conflict with existing events");
                return false;
            }
            if (e.getEventTime().getEndTime().compareTo(event.getEventTime().getBeginTime()) > 0
                    && e.getEventTime().getEndTime().compareTo(event.getEventTime().getEndTime()) < 0) {
                System.out.println("a conflict with existing events");
                return false;
            }
            if (e.getEventTime().getEndTime().compareTo(event.getEventTime().getBeginTime()) > 0
                    && e.getEventTime().getBeginTime().compareTo(event.getEventTime().getEndTime()) < 0) {
                System.out.println("a conflict with existing events");
                return false;
            }
        }
        getEvents().add(event);
        return true;
    }
    public void loadFile(String filepath) {
        events = new ArrayList<>();
        File file = new File(filepath);
        if (!file.exists()) {
            return;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (true) {
                String name = "";
                String date = "";
                if (scanner.hasNextLine()) {
                    name = scanner.nextLine();
                } else {
                    break;
                }
                if (scanner.hasNextLine()) {
                    date = scanner.nextLine();
                } else {
                    break;
                }

                String datas[] = date.split(" ");
                if (datas.length == 3) {
                    LocalDate date1 = paraseLocalDate(datas[0]);
                    TimeInterval timeInterval = new TimeInterval(date1, datas[1], datas[2]);
                    Event event = new Event(name, timeInterval);
                    events.add(event);
                } else {
                    LocalDate date1 = paraseLocalDate(datas[3]);
                    LocalDate date2 = paraseLocalDate(datas[4]);
                    TimeInterval timeInterval = new TimeInterval(date1, datas[1], datas[2]);
                    Event event = new Event(name, timeInterval);
                    events.add(event);

                    Recurring recurring = new Recurring(date2, datas[0]);
                    event.setRecurring(recurring);
                }
            }
            System.out.println("LoadFile"+events);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // MM/dd/yy
    public LocalDate paraseLocalDate(String dateStr) {
        String strs[] = dateStr.split("/");
        LocalDate localDate = LocalDate.of(Integer.parseInt("20" + strs[2]),
                Integer.parseInt(strs[0]),
                Integer.parseInt(strs[1]));
        return localDate;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void deleteEvent(String name) {
        for (Event event : events) {
            events.remove(event);
            return;
        }
    }

    public void deleteEvent(LocalDate date, String name) {
        ArrayList<Event> arrayList = getEvnetHappenInDay(date);
        for (Event event : arrayList) {
            if (!event.getName().equals(name)
                    || event.getRecurring() != null) {
                continue;
            }
            events.remove(event);
        }
    }

    public void deleteEvent(LocalDate date) {
        ArrayList<Event> arrayList = getEvnetHappenInDay(date);
        for (Event event : arrayList) {
            if (event.getRecurring() != null) {
                continue;
            }
            events.remove(event);
        }
    }
}
//
//class MenuClass {
//    MyCalendar myCalendar;
//    Scanner in = new Scanner(System.in);
//
//    public MenuClass() {
//        myCalendar = new MyCalendar();
//        System.out.println(myCalendar.getNowMonthOutput());
//        printMainMenu();
//
//    }
//
//    private void printMainMenu() {
//        System.out.println("Select one of the following main menu options:\n" +
//                "[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit");
//        switch (getCommandInput(in)) {
//            case "V": {
//                printViewMenu();
//                break;
//            }
//            case "C": {
//                printCreateMenu();
//                break;
//            }
//            case "G": {
//                printGotoMenu();
//                break;
//            }
//            case "E": {
//                printEventMenu();
//                break;
//            }
//            case "D": {
//                printDeleteMenu();
//                break;
//            }
//            case "Q": {
//                File file = new File("output.txt");
//                if (!file.exists()) {
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                myCalendar.printToFile(file);
//                System.out.println("Exit Success!");
//                System.exit(0);
//                break;
//            }
//        }
//        printMainMenu();
//    }
//
//    private void printDeleteMenu() {
//        System.out.println("[S]elected [A]ll [DR]");
//        String c = getCommandInput(in);
//
//        switch (c) {
//            case "S": {
//                System.out.println("Enter the date [mm/dd/yyyy]");
//                String date = in.nextLine();
//                LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//
//                System.out.println("Enter the name of the event to delete:");
//                String name = in.nextLine();
//
//                myCalendar.deleteEvent(date1, name);
//
//                break;
//            }
//            case "A": {
//                System.out.println("Enter the date [mm/dd/yyyy]");
//                String date = in.nextLine();
//                LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//
//                myCalendar.deleteEvent(date1);
//                break;
//            }
//            case "DR": {
//                System.out.println("Enter the name of the event to delete:");
//                String name = in.nextLine();
//
//                myCalendar.deleteEvent(name);
//                break;
//            }
//        }
//    }
//
//    private void printEventMenu() {
//        Collections.sort(myCalendar.getEvents());
//        Map<Integer, Integer> map = new LinkedHashMap<>();
//        for (Event event : myCalendar.getEvents()) {
//            if (event.getRecurring() == null) {
//                map.put(event.getEventTime().getDate().getYear(), 1);
//            }
//        }
//        LinkedList<Integer> list = new LinkedList<>(map.keySet());
//        Collections.sort(list);
//
//        System.out.println("ONE TIME EVENTS");
//        for (Integer i : list) {
//            System.out.println(i);
//            for (Event event : myCalendar.getEvents()) {
//                LocalDate date = event.getEventTime().getDate();
//                if (event.getRecurring() == null && date.getYear() == i) {
//                    System.out.println(event.getEventTime() + " " + event.getName());
//                }
//            }
//        }
//        System.out.println("RECURRING EVENTS");
//        for (Event event : myCalendar.getEvents()) {
//            if (event.getRecurring() != null) {
//                System.out.println(event.getName());
//                System.out.println(event.getRecurring().getWeek() + " "
//                        + event.getEventTime().getBeginTime() + " "
//                        + event.getEventTime().getEndTime() + " "
//                        + event.getEventTime().getDate() + " "
//                        + event.getRecurring().getEndTime());
//
//            }
//            map.put(event.getEventTime().getDate().getYear(), 1);
//        }
//    }
//
//    private void printGotoMenu() {
//        System.out.println("Enter the date [mm/dd/yyyy]");
//        String date = in.nextLine();
//        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//        System.out.println(myCalendar.getDayOutput(date1));
//    }
//
//    /**
//     * Name: a string (doesn't have to be one word)
//     * Date: MM/DD/YYYY
//     * Starting time and ending time: 24 hour clock such as 06:00 for 6 AM and 15:30 for 3:30 PM.
//     */
//    private void printCreateMenu() {
//        System.out.println("Enter the date name");
//        String name = in.nextLine();
//        System.out.println("Enter the date [mm/dd/yyyy]");
//        String date = in.nextLine();
//        System.out.println("Enter the start time [HH,mm]");
//        String startTime = in.nextLine();
//        System.out.println("Enter the end time [HH,mm]");
//        String endTime = in.nextLine();
//        LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//
//        Event event = new Event(name, new TimeInterval(date1, startTime, endTime));
//        List<Event> temp = myCalendar.getEvnetHappenInDay(date1);
//        for (Event e : temp) {
//            if (e.getEventTime().getBeginTime().compareTo(event.getEventTime().getBeginTime()) > 0
//                    && e.getEventTime().getBeginTime().compareTo(event.getEventTime().getEndTime()) < 0) {
//                System.out.println("a conflict with existing events");
//                return;
//            }
//            if (e.getEventTime().getEndTime().compareTo(event.getEventTime().getBeginTime()) > 0
//                    && e.getEventTime().getEndTime().compareTo(event.getEventTime().getEndTime()) < 0) {
//                System.out.println("a conflict with existing events");
//                return;
//            }
//            if (e.getEventTime().getEndTime().compareTo(event.getEventTime().getBeginTime()) > 0
//                    && e.getEventTime().getBeginTime().compareTo(event.getEventTime().getEndTime()) < 0) {
//                System.out.println("a conflict with existing events");
//                return;
//            }
//        }
//
//        myCalendar.getEvents().add(event);
//    }
//
//    private void printViewMenu() {
//        System.out.println("[D]ay view or [M]onth view ?");
//        String c = getCommandInput(in);
//        LocalDate date = LocalDate.now();
//
//        switch (c) {
//            case "D": {
//                printDayMenu(date);
//                break;
//            }
//            case "M": {
//                printMonthMenu(date);
//                break;
//            }
//        }
//    }
//
//    private void printDayMenu(LocalDate date) {
//        System.out.println(myCalendar.getDayOutput(date));
//        System.out.println();
//        System.out.println("[P]revious or [N]ext or [G]o back to the main menu ?");
//        String c = getCommandInput(in);
//
//        switch (c) {
//            case "P": {
//                date = date.plusDays(-1);
//                printDayMenu(date);
//                break;
//            }
//            case "N": {
//                date = date.plusDays(1);
//                printDayMenu(date);
//                break;
//            }
//        }
//    }
//
//    private void printMonthMenu(LocalDate date) {
//        System.out.println(myCalendar.getMonthOutput(date));
//        System.out.println();
//        System.out.println("[P]revious or [N]ext or [G]o back to the main menu ?");
//        String c = getCommandInput(in);
//
//        switch (c) {
//            case "P": {
//                date = date.plusMonths(-1);
//                printMonthMenu(date);
//                break;
//            }
//            case "N": {
//                date = date.plusMonths(1);
//                printMonthMenu(date);
//                break;
//            }
//        }
//    }
//
//    public String getCommandInput(Scanner scanner) {
//        String command = scanner.nextLine();
//        command = command.toUpperCase();
//        return command;
//    }
//
//}
