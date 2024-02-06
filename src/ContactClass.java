public class ContactClass implements Comparable<ContactClass>{
    private String name;
    private String number;
    private String mail;

    //constructors
    public ContactClass(){

    }
    public ContactClass(String name, String number, String mail){
        this.name=name;
        this.number=number;
        this.mail=mail;
    }

    //setters
    public void setName(String name){
        this.name=name;
    }
    public void setNumber(String number){
        this.number=number;
    }
    public void setMail(String mail){
        this.mail=mail;
    }

    //getter
    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
    public String getMail(){
        return mail;
    }

    //to string
    public String toString(){
        return name+","+number+","+mail;
    }

    public String toString(int i){
        return "Name\t:\t"+name+"\nNumber\t:\t"+number+"\nMail\t:\t"+mail;
    }

    @Override
    public int compareTo(ContactClass o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}