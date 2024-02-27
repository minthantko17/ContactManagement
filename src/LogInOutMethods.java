import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LogInOutMethods {
    static Scanner ipt=new Scanner(System.in);
    static ArrayList<UserClass> arrayListUser=new ArrayList<>();

    //load user data into local arrayList
    public static void loadUserData(String fileName) throws IOException, FileNotFoundException{
        File dataIpt=new File(fileName);
        Scanner userScanner=new Scanner(dataIpt);

        while(userScanner.hasNextLine()){      //read line by line from file
            String dataLine=userScanner.nextLine();
            StringTokenizer token=new StringTokenizer(dataLine,",");    //tokenize each line
            arrayListUser.add(new UserClass(token.nextToken().trim(), token.nextToken().trim()) {
            });       //put into arrayListContact
        }
        userScanner.close();
    }

    //save user data to .csv file
    public static void saveUserData(String fileName)throws IOException, FileNotFoundException{
        FileWriter writeToUserData=new FileWriter(".\\userData\\"+fileName+".csv");        //write to data file
        for (int i=0; i<arrayListUser.size();i++){
            writeToUserData.write(arrayListUser.get(i).toString()+"\n");
        }
        writeToUserData.close();
    }
    
    //create data file for each user
    private static void createSpecUserFile(String username) throws IOException{
        File file=new File(".\\contactData\\"+username+".csv");
        file.createNewFile();
    }

    //create account
    public static void createAccount() throws FileNotFoundException, IOException{
        topLoop: while(true){
            System.out.print("Set username: ");
            String name=ipt.nextLine();

            if(name.contains(" ")||name.contains(",")){
                System.out.println(">> Username cannot contain whitespace or comma. Try again. <<");
                continue;
            }

            for (int i=0; i<arrayListUser.size();i++){      //check whether username already exists
                if(arrayListUser.get(i).getName().equals(name)){
                    System.out.println(">> Username already exists. Please try other username. <<");
                    continue topLoop;   //go back to "Set user name: "
                }
            }

            boolean match=true;
            String password=null;  
            while(match){
                System.out.print("Set password: "); //set password
                String pw=ipt.nextLine();

                if(pw.isEmpty() || pw.contains(" ")|| pw.contains(",")){
                    System.out.println(">> Invalid format! Password cannot be empty or cannot contain whitespace or comma. <<");    
                    continue;
                }

                System.out.print("Confirm password: "); //confirm password
                String confirmPw=ipt.nextLine();

                if(pw.equals(confirmPw)){
                    password=pw;    //if confirmed is match, save password
                    match=false;
                }
            }

            arrayListUser.add(new UserClass(name, password));   //add new acc to arraylist
            saveUserData("UserData");       //save updated arraylist to User datafile
            createSpecUserFile(name.trim());        //create new data file with new username
            System.out.println(">> Account created successfully! <<");
            System.out.println("(You will be redirected to LogIn page again!)");  
            break;
        }
    }

    //login account
    public static String logIn(){
        String name=null;
        int attempt=3;

        while(attempt>0){
            attempt--;

            System.out.print("Enter username: ");
            name=ipt.nextLine();
            
            System.out.print("Enter password: ");
            String password=ipt.nextLine();

            int target=-1;
            for(int i=0; i<arrayListUser.size();i++){   //check existance of username.
                if (arrayListUser.get(i).getName().equals(name)){
                    target=i;
                    break;
                }
            }
            if (target==-1){
                System.out.println(">> Incorrect Username. Try again! <<");
                System.out.println(">> Remaining attempt: "+attempt);
                continue;
            }

            if(arrayListUser.get(target).getPassword().equals(password)){   //check password, and return name
                    return name;
            }else{
                System.out.println(">> Incorrect Password. Try Again! <<");
                System.out.println(">> Remaining attempt: "+attempt);
            }

        }

        return null;    //return null
    }

}
