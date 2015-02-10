import java.util.Scanner;



/**
 * Created by condor on 10/02/15.
 * FastTrackIT, 2015
 *
 * DEMO ONLY PURPOSES, MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 */
public class AgendaAsPrimitiveArray {

    private Item[] agenda = new Item[500]; // our agenda has 500 items
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
                //  case "delete": deleteItem();
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
        HandleKeyboard handleKeyboard = new HandleKeyboard().invokeItem();
        String name = handleKeyboard.getName();
        String phone = handleKeyboard.getPhone();

        Item i = new Item();
        i.setName(name);
        i.setPhoneNumber(phone);
        agenda[currentAgendaIndex] = i;
        currentAgendaIndex++;
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
        System.out.println("agenda.length = " + agenda.length); //sout tab, or soutv tab, or soutm tab
        for (Item anAgenda : agenda) {
            if (anAgenda != null) {
                String name = anAgenda.getName();
                String telephone = anAgenda.getPhoneNumber();
                System.out.println(name + " " + telephone);
            } else {
                emptySpaces++;
            }
        }
        System.out.println("empty spaces:" + emptySpaces);
    }


    private void printMenu() {
        System.out.println("1. List");
        System.out.println("2. Search");
        System.out.println("3. Create");
        System.out.println("4. Update");
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
