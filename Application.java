import java.util.*;
class Application{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        HashMap<String, String> users=new HashMap<>();
        boolean flag=false;
        
        while(!flag){
            System.out.println();
            System.out.println("Welcome to the login page: ");
            System.out.println("If you are a new user, click Register \nIf you are an existing user, click Login\nIf you want to exit, press Bye");
            String inp=sc.nextLine();
            if(inp.equals("Login")){
                System.out.print("Enter the Username: ");
                String username=sc.nextLine();
                System.out.println("Enter the Password: ");
                String password=sc.nextLine();
                if(users.containsKey(username)){
                    if(users.containsValue(password)){
                        System.out.println("Login Sucessfull...");
                    }
                }
                else{
                    System.out.println("Invalid username or password");
                }
            }
            else if(inp.equals("Register")){
                System.out.print("Enter your username: ");
                String a=sc.nextLine();
                System.out.print("Enter your password: ");
                String b=sc.nextLine();
                System.out.print("Re-enter your password: ");
                String c=sc.nextLine();
                if(b.equals(c)){
                    users.put(a, b);
                    System.out.println("Registration Sucessful");
                }
                else{
                    System.out.println("Invalid input, please try again");
                }
            }
            else if(inp.equals("Bye")){
                System.out.println("Thank you for using the application ");
                flag=true;
            }
            else{
                System.out.println("Invalid Input, exit the code and try again");
            }
        }
        sc.close();
    }
}