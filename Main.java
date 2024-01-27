import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException{
        
        //file input
        File dataIpt=new File("DataFile.csv");
        Scanner dataScan=new Scanner(dataIpt);


        //create arraylist
        ArrayList<ContactClass> arrayList=new ArrayList<>();


        //load file into arraylist
        while(dataScan.hasNext()){      //read line by line
            String dataLine=dataScan.nextLine();
            StringTokenizer token=new StringTokenizer(dataLine,",");    //tokenize each line
            arrayList.add(new ContactClass(token.nextToken().trim(),token.nextToken().trim(),token.nextToken().trim()));       //put into arrayList
        }
        dataScan.close();
        //---------------------------------------------------------

        //1.view contact
        for (int i=0; i<arrayList.size(); i++){
            System.out.println(i+1+"\t"+arrayList.get(i).toString());
        }
        //---------------------------------------------------------

        //2.add contact
        Scanner input=new Scanner(System.in);
        System.out.print("Enter name: ");
        String name=input.nextLine();
        System.out.print("Enter Mobile Number: ");
        String number=input.nextLine();
        System.out.print("Enter email address: ");
        String mail=input.nextLine();
        //input.close();

        arrayList.add(new ContactClass(name, number, mail));    //add to local (arraylist)

        // FileWriter writeToData=new FileWriter(dataIpt,true);        //write to data file
        // writeToData.write("\n"+arrayList.get(arrayList.size()-1).toString());
        //writeToData.close();

        System.out.println("Content added successfully!");
        //---------------------------------------------------------

        //3.update contact
        System.out.println("Update Contact");
        for (int i=0; i<arrayList.size(); i++){     //displaying names and id first
            System.out.println(i+1+"\t"+arrayList.get(i).toString());
        }

        Scanner input2=new Scanner(System.in);      //select contact ID to edit
        System.out.print("Enter contact ID: ");
        int contactId=input2.nextInt()-1;
        
        String tempName=arrayList.get(contactId).getName();        //temps to hold unchanged data
        String tempNumber=arrayList.get(contactId).getNumber();
        String tempMail=arrayList.get(contactId).getMail();

        System.out.println("Choose the following options.");    //choose the one you want to update (name or number or mail)
        System.out.println("1. Name: "+tempName);
        System.out.println("2. Mobile Number: "+tempNumber);
        System.out.println("3. Mail: "+tempMail);

        System.out.print("Which one you want to edit: ");
        int editChoice=input2.nextInt();
        input2.nextLine();


        switch (editChoice) {                                   //edit and update in arrayList
            case 1:
                System.out.print("Enter new name: ");
                String newName=input2.nextLine();
                arrayList.set(contactId, new ContactClass(newName,tempNumber,tempMail));           
                break;
        
            case 2:
                System.out.print("Enter new number: ");
                String newNumber=input2.nextLine();
                arrayList.set(contactId,new ContactClass(tempName,newNumber,tempMail));
                break;

            case 3:
                System.out.print("Enter new mail: ");
                String newMail=input2.nextLine();
                arrayList.set(contactId,new ContactClass(tempName,tempNumber,newMail));
                break;

            default:
                System.out.println("Invalid Input");
                break;
        }
        System.out.println("Contact Updated Successfully.");
        //------------------------------------------------------


        //4. delete contact
        System.out.print("Enter Contact code you want to delete: ");
        int deleteChoice=input2.nextInt()-1;

        arrayList.remove(deleteChoice);
        System.out.println("Deleted");
        for (int i=0; i<arrayList.size(); i++){
            System.out.println(i+1+"\t"+arrayList.get(i).toString());
        }


        //save from arrayList to data file
        FileWriter writeToData=new FileWriter(dataIpt);        //write to data file
        for (int i=0; i<arrayList.size();i++){
            writeToData.write(arrayList.get(i).toString()+"\n");
        }
        writeToData.close();
        System.out.println("done");

        input.close();
        input2.close();
        //It worked LoL

    }
}
