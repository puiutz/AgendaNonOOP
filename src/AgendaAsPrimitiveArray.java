import java.util.Scanner;



/**
 * Created by condor on 10/02/15.
 * FastTrackIT, 2015
 *
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 */
public class AgendaAsPrimitiveArray {

    private final static int MAX_AGENDA_ITEMS=4;
    private Item[] agenda = new Item[MAX_AGENDA_ITEMS];
    private int currentAgendaIndex;

    public static void main(String[] args) {
        System.out.println("AgendaTa versiunea 1.0");
        AgendaAsPrimitiveArray m = new AgendaAsPrimitiveArray();

        do {
            m.printMenu();
            int option = m.readMenuOption();
            switch (option) {
                //   case "loadFile": loadFile();
                //   case "saveFile": saveFile();
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
                case 9:
                    m.exitOption();
                    break;
                default:
                    m.defaultOption();
                    break;
            }
        } while (true);

    }

    private void createItem() {
        boolean wasInserted = false;
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItem();
        String name = handleKeyboard.getName();
        String phone = handleKeyboard.getPhone();

        Item item = new Item();
        item.setName(name);
        item.setPhoneNumber(phone);

        if(currentAgendaIndex<MAX_AGENDA_ITEMS) {
            agenda[currentAgendaIndex] = item;
            currentAgendaIndex++;
             wasInserted = true;
        }
        else {
            //try to find null slots and add th item in the first null slot
            System.out.println("debug: try to find slots");
            for (int i = 0; i < agenda.length; i++) {
                if (agenda[i] == null) { // found one
                    agenda[i]=item;
                    wasInserted=true;
                    System.out.println("debug: slot found, inserted ok");
                    break;
                }
            }
        }
        if(wasInserted)
            System.out.println("Item was added");
        else
            System.out.println("Memory full! The item cannot be added");
    }


    private void updateItem() {
        //search and if found do an update
        int indexItem = searchAgenda();
        if (indexItem != -1) { //found
            HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItem();
            String name = handleKeyboard.getName(); // so we can change the name as well
            String phone = handleKeyboard.getPhone();

            Item i = new Item();
            i.setName(name);
            i.setPhoneNumber(phone);
            agenda[indexItem] = i;
            System.out.println("Item was updated!");
        } else {
            System.out.println("You cannot update an item that does not exists in agenda!");
        }

    }


    private void deleteItem() {
        //search and if found delete it and null the position
        int indexItem = searchAgenda();
        if (indexItem != -1) { //found
            agenda[indexItem] = null;
            System.out.println("Item was deleted!");
        } else {
            System.out.println("Item not found, so you cannot delete it!");
        }

    }


    /* returns the index where the name was found or -1 if the name is not in the agenda*/
    private int searchAgenda() {
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItemName();
        String name = handleKeyboard.getName();
        int indexWhereItWasFound = -1;

        // for (Item anAgenda : agenda) might not work here , we need the index so I keep the original form of for
        for (int i = 0; i < agenda.length; i++) {
            if (agenda[i] != null) {
                Item item = agenda[i];
                String nameInAgenda = item.getName();
                if (name.equalsIgnoreCase(nameInAgenda)) {
                    indexWhereItWasFound = i;
                    break;
                }
            }
        }
        return indexWhereItWasFound;
    }

    /* returns the index where the name was found or -1 if the name is not in the agenda */
    private void searchAgendaAndDisplay() {
        int index = searchAgenda();
        if (index != -1) { //found
            Item item = agenda[index];
            String name = item.getName();
            String phoneNumber = item.getPhoneNumber();
            System.out.println("Name:" + name);
            System.out.println("Phone Number:" + phoneNumber);
        } else {
            System.out.println("This name does not exists in agenda!");
        }
    }


    private void listAgenda() {

        int emptySpaces = 0;
        //System.out.println("agenda.length = " + agenda.length); //sout tab, or soutv tab, or soutm tab
        System.out.println("Your Agenda:");
        for (Item anAgenda : agenda) {
            if (anAgenda != null) {
                String name = anAgenda.getName();
                String telephone = anAgenda.getPhoneNumber();
                System.out.println("Name: "+name + " ;Phone: " + telephone);
            } else {
                emptySpaces++;
            }
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
        System.out.println("9. Exit");
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

    private class HandleKeyboard {
        private String name;
        private String phone;

        private int option;

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public int getOption() {
            return option;
        }

        public HandleKeyboard invokeItem() {
            Scanner s = new Scanner(System.in);
            System.out.print("Name: ");
            name = s.nextLine();
            System.out.print("Phone Number: ");
            phone = s.nextLine();
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
    }
}
