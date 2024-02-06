import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ContactMethods {
    //create Scanner, ArrayList, variable
    static Scanner input=new Scanner(System.in);
    static ArrayList<ContactClass> arrayList=new ArrayList<>();
    static String looper="";
 
    //---------------------------------------------------------------//


    //***MAIN FUNCTIONS***

    //***View Contact***
    public static void viewContact(){
        displayNames();     //display names first

        try{
            //if contact list is empty...
            if(arrayList.size()==0){        
                System.out.print("\n>>The Contact is Empty. Press ENTER to go back to main menu.<<\n");
                input.nextLine();
                return;
            }

            //user select to view contact detail
            System.out.println("\nEnter '0' to go back to Main Menu.");
            System.out.print("Enter Contact ID to view detail: ");
            int contactID=input.nextInt()-1;    //subtract 1 because arrayList starts from 0
            input.nextLine();   //to avoid scanner error
            if(contactID==-1){  //if user input 0, go back to main menu.
                return;
            }

            //display selected contact
            System.out.println("\n"+arrayList.get(contactID).toString(contactID)+"\n");
            
            //go back to viewContact
            System.out.print("Press 'ENTER' to go back.");
            input.nextLine();    //user press enter
            viewContact();

        }catch(IndexOutOfBoundsException e){    //for wrong ID input
            System.out.println("\n>> Incorrect Contact ID. Try again. <<\n");
            viewContact();
        }catch(InputMismatchException e){       //for worng data type input (like string)
            System.out.println("\n>> Invalid Input. Try again. <<");
            input.nextLine();
            viewContact();
        }

    }

    //***Add Contact***
    public static void addContact(){
        System.out.println("\n-Add Contact-\n---------\n");    //header
        while(!looper.equals("0")){                     //Add contact will continue until user exit.         
            //name
            System.out.print("Enter name\t\t: ");
            String name=input.nextLine();
            if(name.isBlank()){
                System.out.println("\n>> This field is required! Please enter the name! <<\n");
                continue;
            }

            //number
            System.out.print("Enter Mobile Number\t: ");
            String number=input.nextLine();
            if(number.isBlank()){
                number="-";
            }

            //mail
            System.out.print("Enter Mail\t\t: ");
            String mail=input.nextLine();
            if(mail.isBlank()){
                mail="-";
            }

            //add inputs to arrayList
            arrayList.add(new ContactClass(name, number, mail));

            System.out.println("\n>> Contact Successfully Added! <<");  //alert message
            Collections.sort(arrayList);    //sort contact list by name
            looper=continueOrNot("Add another Contact.");
        }
        looper="";  //reset looper
    }

    //***Edit Contact***
    public static void editContact(){
        try{
            while(!looper.equals("0")){
                System.out.println("\n--Edit Contact--\n-------------");
                displayNames(); //diplay names with ID first

                //select ID to edit
                System.out.print("\nEnter '0' to cancle operation.\nEnter Contact ID to edit: ");
                int contactID=input.nextInt()-1;    //subtract 1 beacuse arrayList index start from 0
                input.nextLine();       //to avoid scanner error
                if(contactID==-1){      //go back to main menu
                    break;
                }

                editToSelectedData(contactID);  //edit
                Collections.sort(arrayList);    //sort contacts by name
                looper=continueOrNot("Edit Another Contact.");      //continue editing or exit
            }

            looper="";  //reset looper

        }catch(IndexOutOfBoundsException e){     //if user input out of bound number
            System.out.println("\n>> Incorrect Contact ID. Try Again. <<\n");
            editContact();
        }catch (InputMismatchException e){       //if user input string
            System.out.println("\n>> Invalid Input. Try Again. <<\n");
            input.nextLine();
            editContact();
        }
    }

    //***Delete Contact***
    public static void deleteContact(){
        try{
            while(!looper.equals("0")){
                displayNames();

                //display and input
                System.out.println("\"Press '0' to cancle operation.\nEnter Contact code you want to delete: ");
                int contactID=input.nextInt()-1;
                input.nextLine();   //to avoid scanner conflict
                if(contactID==-1){  //go back to main menu
                    break;
                }

                arrayList.remove(contactID);    //delete selected contact
                System.out.println("\n>> Contact Deleted! <<");
                Collections.sort(arrayList);    //sort contact by names after deleting
                looper=continueOrNot("Delete Another Contact.");    //conuinue or not
            }
            looper="";      //reset looper

        }catch(IndexOutOfBoundsException e){     //if user input out of bound number
            System.out.println("\n>> Incorrect Contact ID. Try Again. <<\n");
            deleteContact();
        }catch (InputMismatchException e){       //if user input string
            System.out.println("\n>> Invalid Input. Try Again. <<\n");
            input.nextLine();
            deleteContact();
        }
    }

    //***Search Contact***
    public static void searchContact(){
        while(!looper.equals("0")){
            boolean isFound=false;
            System.out.print("\nEnter the name you want to search: ");
            String nameToSearch=input.nextLine();

            for (int i=0; i<arrayList.size();i++){          //search for exact match
                if (nameToSearch.equalsIgnoreCase(arrayList.get(i).getName())){
                    System.out.println("\nLine\t:\t"+(i+1)+"\n"+arrayList.get(i).toString(i)+"\n");
                    isFound=true;
                }
            }
            if (isFound==false){            //if there is no exact match, the system will find best match
                System.out.println("\n>> The contact is NOT found. <<\n");
                System.out.println("The best match Contact:\n"+bestMatch(nameToSearch));
            }

            looper=continueOrNot("Search again.");

        }
        looper="";
    }


   //----------------------------------------------------------------//


    //***SUPPORTING FUNCTIONS***

    //Load .csv file into arrayList
    public static void loadFile() throws FileNotFoundException{
        File dataIpt=new File("DataFile.csv");  //file input
        Scanner dataScan=new Scanner(dataIpt);

        while(dataScan.hasNextLine()){      //read line by line from file
            String dataLine=dataScan.nextLine();
            StringTokenizer token=new StringTokenizer(dataLine,",");    //tokenize each line
            arrayList.add(new ContactClass(token.nextToken().trim(),token.nextToken().trim(),token.nextToken().trim()));       //put into arrayList
        }
        dataScan.close();
        Collections.sort(arrayList);
    }

    //Save updated arrayList to .csv file
    public static void saveToFile()throws IOException{
        Collections.sort(arrayList);
        FileWriter writeToData=new FileWriter("Datafile.csv");        //write to data file
        for (int i=0; i<arrayList.size();i++){
            writeToData.write(arrayList.get(i).toString()+"\n");
        }
        writeToData.close();
    }

    //***Display names from contact***
    private static void displayNames(){
        System.out.println("\n-Contact List-\n---------------");
        for (int i=0; i<arrayList.size(); i++){
            System.out.println(i+1+"\t"+arrayList.get(i).getName());    //display names form arraylist.
        }
    }

    //***Continue or Not choice***
    private static String continueOrNot(String x){
        String choice="";
        while(true){
            System.out.println("1."+x+"\t0. Back to Main Menu.");
            System.out.print(">>> ");
            choice=input.nextLine();
            if(choice.equals("1")){
                break;
            }else if(choice.equals("0")){
                break;
            }else{
                System.out.print("\n>> Invalid input. Try again! <<\n");
                continue;
            }
        }
        return choice;  //return user choice to continue or not
    }

    //***Editing to selected Data***
    private static void  editToSelectedData(int x){
        try{
            //temps for unchanged data
            String tempName=arrayList.get(x).getName();
            String tempNumber=arrayList.get(x).getNumber();
            String tempMail=arrayList.get(x).getMail();

            //Display and input
            System.out.println("\nChoose from the following options.");    //choose the one you want to update (name or number or mail)
            System.out.println("1. Edit Name\t\t: "+tempName);
            System.out.println("2. Edit Mobile Number\t: "+tempNumber);
            System.out.println("3. Edit Mail\t\t: "+tempMail);
            System.out.println("4. Edit all.");
            System.out.println("0. Cancle editing.");
            System.out.print("Which one you want to edit: ");
            int editChoice=input.nextInt();
            input.nextLine();

            switch (editChoice) {                                   //edit and update in arrayList
                case 0: 
                    System.out.println(">> Opration cancelled. <<\n");
                    break;
                case 1:
                    System.out.print("Enter new name\t\t: ");
                    String newName=input.nextLine();
                    if(newName.isBlank()){
                        System.out.println("\n>> Name cannot be blank. Contact is not updated! <<\n");
                        break;
                    }
                    arrayList.set(x, new ContactClass(newName,tempNumber,tempMail));
                    System.out.println("\n>> Contact Successfully Updated. <<\n");           
                    break;
            
                case 2:
                    System.out.print("Enter new number\t: ");
                    String newNumber=input.nextLine();
                    if(newNumber.isBlank()){
                        newNumber="-";
                    }
                    arrayList.set(x,new ContactClass(tempName,newNumber,tempMail));
                    System.out.println("\n>> Contact Successfully Updated. <<\n");           
                    break;

                case 3:
                    System.out.print("Enter new mail\t\t: ");
                    String newMail=input.nextLine();
                    if(newMail.isBlank()){
                        newMail="-";
                    }
                    arrayList.set(x,new ContactClass(tempName,tempNumber,newMail));
                    System.out.println("\n>> Contact Successfully Updated. <<\n");           
                    break;
                case 4:
                    System.out.print("Enter new Name\t\t: ");
                    newName=input.nextLine();
                    System.out.print("Enter new Number\t: ");
                    newNumber=input.nextLine();
                    System.out.print("Enter new Mail\t\t: ");
                    newMail=input.nextLine();
                    if(newName.isBlank()){
                        System.out.println("\n>> Name cannot be blank. Name is not updated. <<\n");
                        newName=tempName;
                    }
                    if(newNumber.isBlank()){
                        newNumber="-";
                    }
                    if(newMail.isBlank()){
                        newMail="-";
                    }
                    arrayList.set(x, new ContactClass(newName, newNumber, newMail));
                    System.out.println("\n>> Contact Successfully Updated. <<\n");           
                    break;

                default:
                    System.out.println("\n>> Invalid Choice. Try again. <<");
                    editToSelectedData(x);
            }
        }catch(InputMismatchException e){
            System.out.println("\n>> Invalid Input. Try Again! <<");
            input.nextLine();
            editToSelectedData(x);
        }

    }

    //**Best Match Contact***
    private static String bestMatch(String name){
        String tempNameToSearch=name.replaceAll("\\s", "").toLowerCase();
        String mostMatch=null;
        int temp1=0;    //current match charLength
        int temp2=-1;   //to store Most match charLength

        //check line by line in arrayList
        for(int i=0; i<arrayList.size();i++){
            //check shortest name.length() times(userInput & name from arrList)    //removed spaces in names while checking 
            for(int j=0; j<arrayList.get(i).getName().replaceAll("\\s", "").length() && j<tempNameToSearch.length(); j++){ 
                
                if(tempNameToSearch.charAt(j)==arrayList.get(i).getName().toLowerCase().charAt(j)){
                    temp1=j;
                    if (temp1>temp2){       //store name with most match character length.
                        mostMatch="Line\t:\t"+(i+1)+"\n"+arrayList.get(i).toString(i);  
                    }
                    if (temp1==temp2){      //to display multiple most-match names
                        if (mostMatch==null){
                            mostMatch="Line\t:\t"+(i+1)+"\n"+arrayList.get(i).toString(i);
                        }else{
                            mostMatch=mostMatch+"\n\nLine\t:\t"+(i+1)+"\n"+arrayList.get(i).toString(i);
                        }
                    }
                }
                else{
                    break;
                }
            }
            temp2=temp1;    //update longest length
        }
        return mostMatch;
    }

}
