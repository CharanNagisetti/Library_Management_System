import java.util.*;
import java.time.LocalDate;
class Books{
    private String bookId;
    private String title;
    private String author;
    private int quantity;
    Books(String bookId, String title, String author, int quantity){
        this.bookId=bookId;
        this.title=title;
        this.author=author;
        this.quantity=quantity;
    }
    public String getBookId(){
        return bookId;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getAuthor(){
        return author;
    }
    public String getBookTitle(){
        return title;
    }
    public boolean isEmpty(){
        if(quantity==0){
            return true;
        }
        return false;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public void borrowedbook(){
        this.quantity-=1;
        System.out.println("Borrowed book "+this.title+". Remaining book quantities: "+this.quantity);
    }
    public String bookDetails() {
        return "Book id: " + this.bookId + "\nBook Title: " + title + "\nBook Author: " + author + "\nBooks quantity: " + quantity;
    }
    
}
class Library{
    private HashMap<String, Books> book;
    private HashMap<User, Books> borrowedbook=new HashMap<User, Books>();
    private HashMap<User, Integer> allocatedDays=new HashMap<>();
    private HashMap<User, Integer> dueDays=new HashMap<>();
    public Library(){
        this.book=new HashMap<String, Books>();
    }
    public void addBook(Books books){
        book.put(books.getBookId(), books);
    }
    public void deleteBook(Books books){
        if(book.containsKey(books.getBookId())){
            book.remove(books.getBookId());
            System.out.println("Book "+books.getBookId()+" deleted sucessfully");
        }
        else{
            System.out.println("Given book is not present");
        }
    }
    public void displayBooks() {
        for (Books b : book.values()) {
            System.out.println(b.bookDetails());
        }
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Books b : book.values()) {
            sb.append(b.bookDetails()).append("\n");
        }
        return sb.toString();
    }
    
    public void searchBookId(String bookId) {
        if (book.containsKey(bookId)) {
            System.out.println(book.get(bookId).bookDetails());
        } else {
            System.out.println("Book with ID " + bookId + " not found.");
        }
    }
    public void borrowBook(User user, String bookTitle) {
        for (Books b : book.values()) { // Iterate over the book collection
            if (b.getBookTitle().equalsIgnoreCase(bookTitle)) { 
                if (b.getQuantity() <= 0) {
                    System.out.println(bookTitle + " is not available right now.");
                    return;
                }
                b.borrowedbook();
                borrowedbook.put(user, b);
                allocatedDays.put(user, 10);
                System.out.println(bookTitle + " has been allocated to you for 10 days.");
                return;
            }
        }
        System.out.println("No book titled '" + bookTitle + "' found in the library.");
    }

    public Books getBookById(String bookId) {
        return book.getOrDefault(bookId, null);
    }
    public void returnBook(User user, String bookTitle, int days) {
        Iterator<Map.Entry<User, Books>> iterator = borrowedbook.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<User, Books> entry = iterator.next();
            if (entry.getKey().equals(user) && entry.getValue().getBookTitle().equals(bookTitle)) {
                Books book = entry.getValue();
                book.setQuantity(book.getQuantity() + 1);
                iterator.remove(); // Remove the book safely
    
                int allocatedDays = this.allocatedDays.getOrDefault(user, 0);
                if (days > allocatedDays) {
                    int fine = (days - allocatedDays) * 10;
                    System.out.println("Returned late. Fine: $" + fine);
                } else {
                    System.out.println("Book returned on time. Thank you!");
                }
                return;
            }
        }
        System.out.println("You haven't borrowed the book: " + bookTitle);
    }

    public int calculateFine(User user, int days) {
        int allocatedDays = this.allocatedDays.getOrDefault(user, 0); // Avoids NullPointerException
        if (days > allocatedDays) {
            return (days - allocatedDays) * 10; // $10 per day fine
        }
        return 0; // No fine if returned on time
    }
    public void displayBorrowedBooks(User user) {
        if (borrowedbook.containsKey(user)) {
            Books book = borrowedbook.get(user);
            System.out.println("Borrowed Book: " + book.getBookTitle());
        } else {
            System.out.println("You have not borrowed any books.");
        }
    }
    
}
class User{
    private String userID;
    private String userName;
    private String password;
    private LocalDate DOB;
    private String mobileNumber;
    private HashMap<String, String> login;
    User(String userId, String userName, String password, LocalDate DOB, String mobileNumber){
        this.userID=userId;
        this.userName=userName;
        this.password=password;
        this.mobileNumber=mobileNumber;
        this.DOB=DOB;
        this.login=new HashMap<>();
        this.login.put(userName, password);
    }
    public String get_userId(){
        return userID;
    }
    public String get_userName(){
        return userName;
    }
    public LocalDate get_DOB(){
        return DOB;
    }
    public String get_mobileNumber(){
        return mobileNumber;
    }
    public String display_userDetails(){
        return "UserID: "+userID+" UserName: "+userName+" DOB: "+ DOB+ " MobileNumber: "+mobileNumber;
    }
    @Override
    public String toString() {
        return display_userDetails();
}

}
class Admin extends User{
    private String adminCode;
    Admin(String userId, String userName, String password, LocalDate DOB, String mobileNumber, String adminCode) {
        super(userId, userName, password, DOB, mobileNumber);
        this.adminCode=adminCode;
    }
    public String get_adminCode(){
        return adminCode;
    }
    public String display_adminDetails(){
        return "UserID: "+super.get_userId()+" UserName: "+super.get_userName()+" DOB: "+ super.get_DOB()+ " MobileNumber: "+super.get_mobileNumber();
    }
    @Override
    public String toString() {
        return display_adminDetails();
}

    
}
public class Library_Management_System{  
    private ArrayList<User> users=new ArrayList<>();
    private ArrayList<Admin> admin_Users=new ArrayList<>();
    private HashMap<String, String> userLogin=new HashMap<>();
    private HashMap<String, String> adminLogin=new HashMap<>();
    private Scanner sc=new Scanner(System.in);
    private Library library=new Library();
    public static void main(String[] args) {
        boolean flag=false;
        Library_Management_System system=new Library_Management_System();
        Scanner sc=new Scanner(System.in);
        while(!flag){
            clearScreen();
            System.out.println();
            System.out.println("---- Welcome to the Charan Library ----- ");
            System.out.println("Please select one of the options to proceed");
            System.out.println("If you are a new user, click Register \nIf you are an existing user, click Login\nIf you are an Admin user, Click Admin\nIf you want to exit, press Bye");
            String inp=sc.nextLine();
            if(inp.equals("Login")){
                system.userLogin();
                pause(sc);
                /*Options:
                 * Search Book
                 * Borrow Book
                 * Return Book
                 * Check fine
                 * Borrowed Books
                 * Logout
                 */
            }
            else if(inp.equals("Register")){
                System.out.println("Which registration you want to go with??");
                System.out.println("1. User Registration \t2. Admin Registration");
                int inp_register=sc.nextInt();
                sc.nextLine();
                switch(inp_register){
                    case 1: 
                        system.userRegister();
                        break;
                    case 2: 
                        system.adminRegister();
                        break;
                }
                pause(sc);
            }
            else if(inp.equals("Bye")){
                System.out.println("Thank you for using the application ");
                flag=true;
                pause(sc);
            }
            else if(inp.equals("Admin")){
                system.adminLogin();
                pause(sc);
                /* Options:
                 * Add Book
                 * Remove Book
                 * Update Book
                 * All Books
                 * Check dues
                 * Logout
                 */
            }
            else{
                System.out.println("Invalid Input, exit the code and try again");
                pause(sc);
            }
        }
        sc.close();
    }
    public void userRegister(){
        System.out.println("Enter your new userID");
        String userId=sc.nextLine();
        System.out.print("Enter your new username: ");
        String newuserName=sc.nextLine();
        if (userLogin.containsKey(newuserName)) {
            System.out.println("Username already taken! Try a different one.");
            return;
        }
        System.out.print("Enter your Birthdate Year: ");
        int birth_year=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your Birthday Month: ");
        int birth_month=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your Birthday Day: ");
        int birth_day=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your Mobile Number: ");
        String mobileNumber=sc.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword=sc.nextLine();
        System.out.print("Re-enter your password: ");
        LocalDate DOB=LocalDate.of(birth_year, birth_month, birth_day);
        String confirm_password=sc.nextLine();
        sc.nextLine();
        if(!newPassword.equals(confirm_password)){
            System.out.println("Passwords Dont Match!!!... Retry again");
            return;
        }
        User newUser=new User(userId, newuserName, newPassword, DOB, mobileNumber);
        users.add(newUser);
        userLogin.put(newuserName, newPassword);
        System.out.println();
        System.out.println("User Registration Sucessfull!!!");
    }
    public void adminRegister(){
        System.out.print("Enter your new userID: ");
        String userId=sc.nextLine();
        System.out.print("Enter your new username: ");
        String newuserName=sc.nextLine();
        if (adminLogin.containsKey(newuserName)) {
            System.out.println("Username already taken! Try a different one.");
            return;
        }
        System.out.print("Enter your Birthdate Year: ");
        int birth_year=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your Birthday Month: ");
        int birth_month=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your Birthday Day: ");
        int birth_day=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your Mobile Number: ");
        String mobileNumber=sc.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword=sc.nextLine();
        System.out.print("Re-enter your password: ");
        LocalDate DOB=LocalDate.of(birth_year, birth_month, birth_day);
        String confirm_password=sc.nextLine();
        if(!newPassword.equals(confirm_password)){
            System.out.println("Passwords Dont Match!!!... Retry again");
            return;
        }
        System.out.print("Enter your new AdminCode: ");
        String adminCode=sc.nextLine();
        sc.nextLine();
        System.out.println("Your adminCode is "+adminCode+". Do not forget this adminCode evenr");
        Admin newAdmin=new Admin(userId, newuserName, newPassword, DOB, mobileNumber, adminCode);
        admin_Users.add(newAdmin);
        adminLogin.put(newuserName, newPassword);
        System.out.println();
        System.out.println("Admin Registration Sucessfull!!");
    }
    public void userLogin(){
        System.out.print("Enter the Username: ");
        String username=sc.nextLine();
        System.out.println("Enter the Password: ");
        String password=sc.nextLine();
        if (userLogin.containsKey(username) && userLogin.get(username).equals(password)) {
            User temp=null;
            for(User user: users){
                if(user.get_userName().equals(username)){
                    temp=user;
                }
            }
            userHomePage(temp);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    public void adminLogin(){
        System.out.print("Enter the Username: ");
        String username=sc.nextLine();
        System.out.println("Enter the Password: ");
        String password=sc.nextLine();
        if(adminLogin.containsKey(username) && adminLogin.get(username).equals(password)){
            System.out.print("Enter the Admin Code: ");
            String adminCode=sc.nextLine();
            Admin temp=null;
            for(Admin admin: admin_Users){
                if(admin.get_userName().equals(username)){
                    temp=admin;
                    break;
                }
            }
            if(temp != null && temp.get_adminCode().equals(adminCode)){
                System.out.println("Admin Code Sucessfully Verified");
                adminHomePage();
            }
            else{
                System.out.println("Wrong Admin Code!! Please try again");
                return;
            }
        }
        else{
            System.out.println("Invalid password please try again");
        }

    }
    public void userHomePage(User user){
        System.out.println();
        System.out.println("Login Sucessfull...");
        System.out.println();
        System.out.println("----- Welcome to Charan Library Homepage ------");
        System.out.println("Select an option: ");
        System.out.println("1. Search a Book");
        System.out.println("2. Display All Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Check Fine");
        System.out.println("6. Display Borrowed Books");
        System.out.println("7. Logout");
        int choice=sc.nextInt();
        sc.nextLine();
        switch(choice){
            case 1:
                System.out.println("Enter the book Id: ");
                String search_bookId=sc.nextLine();
                library.searchBookId(search_bookId);
                break;
            case 2:
                library.displayBooks();
                break;
            case 3:
                System.out.println("Enter which titled book do you want?");
                String bookTitle=sc.nextLine();
                library.borrowBook(user, bookTitle);
                break;
            case 4:
                System.out.println();
                System.out.print("Which book you want to return? ");
                String title=sc.nextLine();
                System.out.print("After how many days are you returning this book? ");
                int days=sc.nextInt();
                sc.nextLine();
                library.returnBook(user, title, days);

                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            }

    }
    static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    static void pause(Scanner sc) {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }
    
    
    public void adminHomePage() {
        System.out.println();
        System.out.println("Login Successful...");
        System.out.println();
        System.out.println("----- Welcome to Charan Library Admin Homepage ------");
        System.out.println("Select an option: ");
        System.out.println("1. Add Book");
        System.out.println("2. Display All Books");
        System.out.println("3. Remove a Book");
        System.out.println("4. Update a Book");
        System.out.println("5. Check Dues");
        System.out.println("6. Display Borrowed Books");
        System.out.println("7. Exit");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                library.displayBooks();
                break;
            case 3:
                System.out.print("Enter the Book ID to remove: ");
                String bookId = sc.nextLine();
                Books bookToRemove = library.getBookById(bookId); // Add this method in Library class
                if (bookToRemove != null) {
                    library.deleteBook(bookToRemove);
                } else {
                    System.out.println("Book not found.");
                }
                break;
            case 4:
                System.out.print("Enter the Book ID to update: ");
                String updateBookId = sc.nextLine();
                Books bookToUpdate = library.getBookById(updateBookId); // Add this method in Library class
                if (bookToUpdate != null) {
                    System.out.print("Enter new quantity: ");
                    int newQuantity = sc.nextInt();
                    sc.nextLine();
                    bookToUpdate.setQuantity(newQuantity);
                    System.out.println("Book quantity updated successfully.");
                } else {
                    System.out.println("Book not found.");
                }
                break;
            case 5:
                System.out.print("Enter the User ID to check dues: ");
                String userId = sc.nextLine();
                User user = findUserById(userId); // Add this method in Library_Management_System class
                if (user != null) {
                    int fine = library.calculateFine(user, 0); // Pass actual days if needed
                    System.out.println("Total fine for user " + user.get_userName() + ": $" + fine);
                } else {
                    System.out.println("User not found.");
                }
                break;
            case 6:
                System.out.print("Enter the User ID to display borrowed books: ");
                String borrowUserId = sc.nextLine();
                User borrowUser = findUserById(borrowUserId); // Add this method in Library_Management_System class
                if (borrowUser != null) {
                    library.displayBorrowedBooks(borrowUser);
                } else {
                    System.out.println("User not found.");
                }
                break;
            case 7:
                System.out.println("Logging out...");
                return; // Return to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    public User findUserById(String userId) {
        for (User user : users) {
            if (user.get_userId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    
    public void addBook(){
        sc.nextLine();
        System.out.print("Enter the Book Id: ");
        String bookId=sc.nextLine();
        System.out.println();
        System.out.print("Enter the Book Title: ");
        String title=sc.nextLine();
        System.out.println();
        System.out.print("Enter the Book Author: ");
        String author=sc.nextLine();
        System.out.println();
        System.out.print("Enter the quantity: ");
        int quantity=sc.nextInt();
        sc.nextLine();
        System.out.println();
        Books newBook=new Books(bookId, title, author, quantity);
        library.addBook(newBook);
    }
}
