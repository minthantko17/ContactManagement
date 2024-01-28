import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static Scanner input=new Scanner(System.in);        //scanner for whole file
    public static void main(String[] args) throws IOException{
        //create arraylist
        ArrayList<ContactClass> arrayList=new ArrayList<>();        


        System.out.println("\nContacts loading. Please wait...\n");
        
        //load file into arraylist
        loadFile(arrayList);    
        
        //intro messages and menu selection
        System.out.println("Contacts loaded successfully.");
        System.out.println("\n*Welcome to Contact Manager*");
        System.out.println("-----------------------------\n");
        
        do {
            System.out.println("**Main Menu**\n----------");
            System.out.print("1. View contact.\n2. Add contact\n3. Edit Contact\n4. Delete Contact\n0. Exit\nSelect your function: ");

            //user input for menu choice
            int chooseFunction=input.nextInt();
            input.nextLine();

            switch (chooseFunction){    //perorm selected function
                case 0: System.out.println("Bye...Tak Tar...");       //save contact and exit system
                    saveToFile(arrayList);
                    input.close();
                    System.exit(0);
                case 1: viewContact(arrayList);
                    System.out.println("Press \"ENTER\" to go back to main menu.");
                    input.nextLine();
                    break;
                case 2: addContact(arrayList);
                    break;
                case 3: editContact(arrayList);
                    break;
                case 4: deleteContact(arrayList);
                    break;
                default:
                    System.out.println("Invalid Input.");
                    break;
            }
            saveToFile(arrayList);
        }while(true);   //will lead to main menu again and again

        //It worked LoL

    }

    //view contact (printing)
    private static void viewContact(ArrayList<ContactClass> arrayList){
        for (int i=0; i<arrayList.size(); i++){
            System.out.println(i+1+"\t"+arrayList.get(i).toString());
        }
    }

    //add contact
    private static void addContact(ArrayList<ContactClass> arrayList){
        String choice="1";
        do{
            System.out.print("Enter name: ");
            String name=input.nextLine();
            System.out.print("Enter Mobile Number: ");
            String number=input.nextLine();
            System.out.print("Enter email address: ");
            String mail=input.nextLine();

            arrayList.add(new ContactClass(name, number, mail));    //add to local (arraylist)
            System.out.println("Content added successfully!");
            
            choice=continueOrNot("Add another Contact.");       //continue or back to menu

        }while(!choice.equals("0"));
    }

    //edit contact
    private static void editContact(ArrayList<ContactClass> arrayList){
        String choice="";
        do{
            System.out.println("Update Contact");
            for (int i=0; i<arrayList.size(); i++){     //displaying names and id first
                System.out.println(i+1+"\t"+arrayList.get(i).toString());
            }

            //select contact ID to edit
            System.out.print("Enter contact ID: ");
            int contactId=input.nextInt()-1;

            String tempName=arrayList.get(contactId).getName();        //temps to hold unchanged data
            String tempNumber=arrayList.get(contactId).getNumber();
            String tempMail=arrayList.get(contactId).getMail();

            System.out.println("Choose from the following options to edit.");    //choose the one you want to update (name or number or mail)
            System.out.println("1. Name: "+tempName);
            System.out.println("2. Mobile Number: "+tempNumber);
            System.out.println("3. Mail: "+tempMail);
            System.out.print("Which one you want to edit: ");
            int editChoice=input.nextInt();
            input.nextLine();

            switch (editChoice) {                                   //edit and update in arrayList
                case 1:
                    System.out.print("Enter new name: ");
                    String newName=input.nextLine();
                    arrayList.set(contactId, new ContactClass(newName,tempNumber,tempMail));
                    System.out.println("Contact Updated Successfully.");           
                    break;
            
                case 2:
                    System.out.print("Enter new number: ");
                    String newNumber=input.nextLine();
                    arrayList.set(contactId,new ContactClass(tempName,newNumber,tempMail));
                    System.out.println("Contact Updated Successfully.");
                    break;

                case 3:
                    System.out.print("Enter new mail: ");
                    String newMail=input.nextLine();
                    arrayList.set(contactId,new ContactClass(tempName,tempNumber,newMail));
                    System.out.println("Contact Updated Successfully.");
                    break;

                default:
                    System.out.println("Invalid Input.");
                    break;
            }

            choice=continueOrNot("Edit another Contact.");

        }while(!choice.equals("0"));
    }

    //delete contact
    private static void deleteContact(ArrayList<ContactClass> arrayList){ 
        String choice="";
        viewContact(arrayList); 
        do{
            System.out.print("Enter Contact code you want to delete: ");
            int deleteChoice=input.nextInt()-1;
            input.nextLine();

            arrayList.remove(deleteChoice);
            System.out.println("Deleted");

            choice=continueOrNot("Delete another contact.");

        }while(!choice.equals("0"));
    }

    //save to file
    private static void saveToFile(ArrayList<ContactClass> arrayList)throws IOException{
        //save from arrayList to data file
        FileWriter writeToData=new FileWriter("Datafile.csv");        //write to data file
        for (int i=0; i<arrayList.size();i++){
            writeToData.write(arrayList.get(i).toString()+"\n");
        }
        writeToData.close();
    }

    //load files
    private static void loadFile(ArrayList<ContactClass> arrayList) throws FileNotFoundException{
        File dataIpt=new File("DataFile.csv");  //file input
        Scanner dataScan=new Scanner(dataIpt);

        while(dataScan.hasNext()){      //read line by line from file
            String dataLine=dataScan.nextLine();
            StringTokenizer token=new StringTokenizer(dataLine,",");    //tokenize each line
            arrayList.add(new ContactClass(token.nextToken().trim(),token.nextToken().trim(),token.nextToken().trim()));       //put into arrayList
        }
        dataScan.close();
    }

    private static String continueOrNot(String x){
        String choice="";
        while(true){
            System.out.println("1. "+x+"\t0. Back tok main menu.");
            System.out.print(">>> ");
            choice=input.nextLine();
            if(choice.equals("1")){
                break;
            }
            else if(choice.equals("0")){
                break;
            }
            else{
                System.out.println("Invalid input. Try again.");
                continue;
            }
        }
        return choice;
    }

}