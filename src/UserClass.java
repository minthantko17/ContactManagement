public class UserClass {
    private String name;
    private String password;

    public UserClass(){
        
    }

    public UserClass(String name, String password){
        setName(name);
        setPassword(password);
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public String toString(){
        return name+","+password;
    }
}
