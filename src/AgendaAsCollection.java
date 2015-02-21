import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by condor on 10/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 */
public class AgendaAsCollection {

    //private final static int MAX_AGENDA_ITEMS=4;
    private List<Item> agenda = new ArrayList<Item>();

    public static void main(String[] args) {

        System.out.println("AgendaTa versiunea 2.0");
        AgendaAsCollection m = new AgendaAsCollection();
     m.readFromFile();
        do {
            m.printMenu();
            int option = m.readMenuOption();
            switch (option) {
                case 1:
                    m.listAgenda();
                    break;
                case 2:
                    m.searchAgendaAndDisplay();
                    break;
                case 3:
                    m.createItem();
                    break;
                case 4:
                    m.updateItem();
                    break;
                case 5:
                    m.deleteItem();
                    break;
                case 6:
                    m.readFromFile();
                    break;
                case 7:
                    m.writeToFile();
                    break;
                case 9:
                    m.exitOption();
                    break;
                case 10:
                    m.sortare();
                    break;
                default:
                    m.defaultOption();
                    break;
            }
        } while (true);

    }

    private void createItem() {
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItem();
        String name = handleKeyboard.getName();
        String phone = handleKeyboard.getPhone();

        Item item = new Item();
        item.setName(name);
        item.setPhoneNumber(phone);

        agenda.add(item);
        System.out.println("Added.");


        writeToFile();
    }


    private void updateItem() {
        //search and if found do an update
        int indexItem = searchAgenda();
        if (indexItem != -1) { //found
            HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItem();
            String name = handleKeyboard.getName(); // so we can change the name as well
            String phone = handleKeyboard.getPhone();

            Item item = new Item();
            item.setName(name);
            item.setPhoneNumber(phone);
            agenda.remove(indexItem);
            agenda.add(item);
            System.out.println("Item was updated!");
        } else {
            System.out.println("You cannot update an item that does not exists in agenda!");

                 writeToFile();
        }

    }


    private void deleteItem() {
        //search and if found delete it and null the position
        int indexItem = searchAgenda();
        if (indexItem != -1) { //found
            agenda.remove(indexItem);
            System.out.println("Item was deleted!");
        } else {
            System.out.println("Item not found, so you cannot delete it!");


            writeToFile();
        }

    }

    private void sortare(){

    }

    /* returns the index where the name was found or -1 if the name is not in the agenda*/
    private int searchAgenda() {
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItemName();
        String name = handleKeyboard.getName();
        int indexWhereItWasFound = -1;


        // for (Item anAgenda : agenda) might not work here , we need the index so I keep the original form of for
        for (int i = 0; i < agenda.size(); i++) {
            Item item = agenda.get(i);
            String nameInAgenda = item.getName();
            if (name.equalsIgnoreCase(nameInAgenda)) {
                indexWhereItWasFound = i;
                break;
            }

        }
        return indexWhereItWasFound;
    }

    /* returns the index where the name was found or -1 if the name is not in the agenda */
    private void searchAgendaAndDisplay() {
        int index = searchAgenda();
        if (index != -1) { //found
            Item item = agenda.get(index);
            String name = item.getName();
            String phoneNumber = item.getPhoneNumber();
            System.out.println("Name:" + name);
            System.out.println("Phone Number:" + phoneNumber);
        } else {
            System.out.println("This name does not exists in agenda!");
        }
    }


    private void listAgenda() {

        System.out.println("Your Agenda:");
        for (Item anAgenda : agenda) {
            String name = anAgenda.getName();
            String telephone = anAgenda.getPhoneNumber();
            System.out.println("Name: " + name + " ;Phone: " + telephone);
        }
        // System.out.println("empty spaces:" + emptySpaces);
        System.out.println("---------------");
    }


    private void printMenu() {
        System.out.println("1. List");
        System.out.println("2. Search");
        System.out.println("3. Create");
        System.out.println("4. Update");
        System.out.println("5. Delete");
        System.out.println("6. Read From File");
        System.out.println("7. Write to File");
        System.out.println("9. Exit");
        System.out.println("10. Sortare");
    }

    private void exitOption() {
        System.out.println("Bye, bye...the content not saved will now be erased");
        System.exit(0);
    }

    private void defaultOption() {
        System.out.println("This option does not exist. Pls take another option");
    }

    private int readMenuOption() {
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeOption();
        return handleKeyboard.getOption();
    }


    private void readFromFile() {

        //warning, it is going to overwrite
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeYesNo();
        String yesNo = handleKeyboard.getYesNo();
        if (yesNo.equalsIgnoreCase("Y")) {
            FileInputStream fis = null;
            ByteArrayOutputStream out = null;
            try {
                File f = new File("agendalist.txt");
                fis = new FileInputStream(f);
                out = new ByteArrayOutputStream();
                IOUtils.copy(fis, out);
                byte[] data = out.toByteArray();
                agenda = SerializationUtils.deserialize(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(fis);
            }
            System.out.println("Read from file done!");
        }

    }


    private void writeToFile() {

        FileOutputStream fwr = null;
        try {
            byte[] data = SerializationUtils.serialize((ArrayList) agenda);
            File f = new File("agendalist.txt");
            fwr = new FileOutputStream(f);
            fwr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fwr);
        }
        System.out.println("Write to file done!");


    }


    private class HandleKeyboard {
        private String name;
        private String phone;
        private String firstname;

        private int option;

        private String yesNo;


        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public int getOption() {
            return option;
        }

        public String getYesNo() {
            return yesNo;
        }

        public HandleKeyboard invokeItem() {
            Scanner s = new Scanner(System.in);
            System.out.print("Name: ");
            name = s.nextLine();
            System.out.print("Phone Number: ");
            phone = s.nextLine();
            System.out.println("Firstname: ");
            firstname =s.nextLine();
            return this;

        }

        public HandleKeyboard invokeItemName() {
            Scanner s = new Scanner(System.in);
            System.out.print("Name: ");
            name = s.nextLine();
            return this;
        }

        public HandleKeyboard invokeOption() {
            Scanner s = new Scanner(System.in);
            System.out.print("Option: ");
            option = s.nextInt();
            return this;
        }

        public HandleKeyboard invokeYesNo() {
            Scanner s = new Scanner(System.in);
            System.out.print("Are you sure you want to overwrite your current content in memory ? (Y,N): ");
            yesNo = s.nextLine();
            return this;
        }
    }
}
