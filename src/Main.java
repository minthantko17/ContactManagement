import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{

        System.out.println(">> Loading User data! <<");
        LogInOutMethods.loadUserData(".\\userData\\UserData.csv");  //load userdata into local arraylist
        System.out.println(">> Data loaded! <<");

        System.out.println("\n>>> Welcome to Contact manager! <<<\n");
        chooseAcc();

    }

    private static void chooseAcc() throws FileNotFoundException, IOException{
        while(true){
            System.out.println("\nPlease Select your function: \n1. Create new account\n2. Login\n0. Exit");
            System.out.print(">>");
            String choiceAcc = new LogInOutMethods().ipt.nextLine();

            switch(choiceAcc){
                case "0": System.out.println(">> Bye! Have a nice day! <<");
                    System.exit(0);
                case "1": LogInOutMethods.createAccount();
                    break;
                case "2": String temp=LogInOutMethods.logIn();
                    if (temp==null){
                        System.out.println(">> Please Try again! <<");
                        break;
                    }else{
                        startContact(temp);
                        break;
                    }
                default: System.out.println(">> Invalid input! Please try again. <<");
                
            }

        }     
    }

    private static void startContact(String fileName) throws IOException, FileNotFoundException{
        System.out.println("\nContacts loading. Please wait...\n");
        
        //load file into arraylist
        String path=".\\contactData\\"+fileName+".csv";
        ContactMethods.loadFile(path);
        
        //intro messages and menu selection
        System.out.println("Contacts loaded successfully.");
        System.out.println("\n*WELCOME TO CONTACT MANAGER*");
        System.out.println("-----------------------------");
        
        do {
            System.out.println("\n**MAIN MENU**\n----------");
            System.out.print("1. View contact.\n2. Add contact\n3. Edit Contact\n4. Delete Contact\n5. Search Contact\n6. Logout\n0. Exit\n\nSelect your function: ");

            //user input for menu choice
            String chooseFunction=ContactMethods.input.nextLine(); //I used String in order to track unwated string input.

            switch (chooseFunction){    //perorm selected function
                case "0": System.out.println("Bye...Tak Tar...");       //save contact and exit system
                    ContactMethods.saveToFile(path);
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
                case "6": return;
                default:
                    System.out.println("\n>> Invalid Input. <<\n");
                    break;
            }
            ContactMethods.saveToFile(path);
        }while(true);   //will lead to main menu again and again
    }
}