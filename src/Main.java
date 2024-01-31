import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static Scanner input=new Scanner(System.in);        //scanner for whole file
    private static ArrayList<ContactClass> arrayList=new ArrayList<>();     //create arraylist 
    public static void main(String[] args) throws IOException{
        //create arraylist
        //ArrayList<ContactClass> arrayList=new ArrayList<>();        


        System.out.println("\nContacts loading. Please wait...\n");
        
        //load file into arraylist
        loadFile();    
        
        //intro messages and menu selection
        System.out.println("Contacts loaded successfully.");
        System.out.println("\n*Welcome to Contact Manager*");
        System.out.println("-----------------------------\n");
        
        do {
            System.out.println("**Main Menu**\n----------");
            System.out.print("1. View contact.\n2. Add contact\n3. Edit Contact\n4. Delete Contact\n5. Search Contact\n0. Exit\nSelect your function: ");

            //user input for menu choice
            String chooseFunction=input.nextLine(); //I used String in order to track unwated string input.

            switch (chooseFunction){    //perorm selected function
                case "0": System.out.println("Bye...Tak Tar...");       //save contact and exit system
                    saveToFile();
                    input.close();
                    System.exit(0);
                case "1": viewContact();
                    System.out.println("Press \"ENTER\" to go back to main menu.");
                    input.nextLine();
                    break;
                case "2": addContact();
                    break;
                case "3": editContact();
                    break;
                case "4": deleteContact();
                    break;
                case "5": searchContact();
                    //input.nextLine();
                    break;
                default:
                    System.out.println("Invalid Input.");
                    break;
            }
            saveToFile();
        }while(true);   //will lead to main menu again and again

        //It worked LoL

    }

    //view contact (printing)
    private static void viewContact(){
        for (int i=0; i<arrayList.size(); i++){
            System.out.println(i+1+"\t"+arrayList.get(i).toString());
        }
    }

    //add contact
    private static void addContact(){
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
    private static void editContact(){
        String choice="";
        do{
            System.out.println("Update Contact");
            viewContact(); //display name and ID first

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
    private static void deleteContact(){ 
        String choice="";
        viewContact(); 
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
    private static void saveToFile()throws IOException{
        //save from arrayList to data file
        FileWriter writeToData=new FileWriter("Datafile.csv");        //write to data file
        for (int i=0; i<arrayList.size();i++){
            writeToData.write(arrayList.get(i).toString()+"\n");
        }
        writeToData.close();
    }

    //load files (and add into arraylist)
    private static void loadFile() throws FileNotFoundException{
        File dataIpt=new File("DataFile.csv");  //file input
        Scanner dataScan=new Scanner(dataIpt);

        while(dataScan.hasNextLine()){      //read line by line from file
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

    private static void searchContact(){
        String choice="";
        do{
            boolean isFound=false;
            System.out.print("Enter the name you want to search: ");
            String nameToSearch=input.nextLine();

            for (int i=0; i<arrayList.size();i++){
                if (nameToSearch.equalsIgnoreCase(arrayList.get(i).getName())){
                    System.out.println("\nLine: "+(i+1)+",\t"+arrayList.get(i).toString()+"\n");
                    isFound=true;
                }
            }
            if (isFound==false){  
                System.out.println("The contact is not found.");
                System.out.println("The best match name: "+bestMatch(nameToSearch));
            }

            choice=continueOrNot("Search again.");
        }while(!choice.equals("0"));
    }

    private static String bestMatch(String name){
        String tempNameToSearch=name.replaceAll("\\s", "").toLowerCase();
        String mostMatch=null;
        int temp1=0;
        int temp2=-1;
        for(int i=0; i<arrayList.size();i++){
            for(int j=0; j<arrayList.get(i).getName().replaceAll("\\s", "").length() && j<tempNameToSearch.length(); j++){
                if(tempNameToSearch.charAt(j)==arrayList.get(i).getName().toLowerCase().charAt(j)){
                    temp1=j;
                    if (temp1>temp2){
                        mostMatch=(i+1)+"\t"+arrayList.get(i).toString();
                    }
                }
                else{
                    break;
                }
            }
            temp2=temp1;
        }
        return mostMatch;
    }

}