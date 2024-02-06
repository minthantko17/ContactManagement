import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        System.out.println("\nContacts loading. Please wait...\n");
        
        //load file into arraylist
        ContactMethods.loadFile();
        
        //intro messages and menu selection
        System.out.println("Contacts loaded successfully.");
        System.out.println("\n*WELCOME TO CONTACT MANAGER*");
        System.out.println("-----------------------------");
        
        do {
            System.out.println("\n**MAIN MENU**\n----------");
            System.out.print("1. View contact.\n2. Add contact\n3. Edit Contact\n4. Delete Contact\n5. Search Contact\n0. Exit\n\nSelect your function: ");

            //user input for menu choice
            String chooseFunction=ContactMethods.input.nextLine(); //I used String in order to track unwated string input.

            switch (chooseFunction){    //perorm selected function
                case "0": System.out.println("Bye...Tak Tar...");       //save contact and exit system
                    ContactMethods.saveToFile();
                    ContactMethods.input.close();
                    System.exit(0);
                case "1": ContactMethods.viewContact();
                    break;
                case "2": ContactMethods.addContact();
                    break;
                case "3": ContactMethods.editContact();
                    break;
                case "4": ContactMethods.deleteContact();
                    break;
                case "5": ContactMethods.searchContact();
                    //input.nextLine();
                    break;
                default:
                    System.out.println("\n>> Invalid Input. <<\n");
                    break;
            }
            ContactMethods.saveToFile();
        }while(true);   //will lead to main menu again and again

        //It worked LoL

    }
}