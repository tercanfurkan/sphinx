package models.user;

import play.data.validation.*;

public class User {
    
    public User(String username, String password) {
    	this.username = username;
    	this.password = password;
    }

    @Constraints.Required
    public String username;
    
    @Constraints.Required
    public String password;
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String username, String password) {
    	System.out.println(username + ' ' + password);
    	if (username == null || password == null)
    		return null;
    	else if (username.equals("admin") && password.equals("EfeSus2012"))
    		return new User("admin", "EfeSus2012");
    	else 
    		return null;
    }
    
    public String toString() {
        return "User(" + username + ")";
    }

}

