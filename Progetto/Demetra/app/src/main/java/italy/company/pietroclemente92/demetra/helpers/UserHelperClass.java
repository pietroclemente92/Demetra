package italy.company.pietroclemente92.demetra.helpers;

public class UserHelperClass {

    String name, username, email, password;
    boolean userAccount, privateAccount;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String username, String email, String password, boolean userAccount, boolean privateAccount) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userAccount = userAccount;
        this.privateAccount = privateAccount;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUserAccount() {
        return userAccount;
    }

    public boolean isPrivateAccount() {
        return privateAccount;
    }
}
