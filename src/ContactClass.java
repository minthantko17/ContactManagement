public class ContactClass implements Comparable<ContactClass>{
    private String name;
    private String number;
    private String mail;
    private String note;

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
    public void setNote(String note){
        this.note=note;
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
    public String getNote(){
        return note;
    }

    //to string
    public String toString(){
        return name+"\t,"+number+"\t,"+mail;
    }
    @Override
    public int compareTo(ContactClass o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}