package poeupdated;

import java.io.*;
import java.util.regex.*;

public class Process {
    ///Declarations of REGEX and file to save details
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{5}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$";
    private static final String PHONE_REGEX = "^0[6-8][0-9]{8}$|^\\+27[6-8][0-9]{8,}$";
    private static final String FILE_PATH = "users.txt";

    public static class Result {
        private boolean success;
        private String message;

        public Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /// Handles login
    public Result loginUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new Result(false, "Fields are empty, Please make sure both fields are filled.");
        }

        if (checkCredentials(username, password)) {
            return new Result(true, "Welcome back, " + username + "! It is great to see you again.");
        } else {
            return new Result(false, "Username or password incorrect. Please try again.");
        }
    }

    /// Handles registration
    public Result registerUser(String username, String phone, String password) {
        if (!username.matches(USERNAME_REGEX)) {
            return new Result(false, "Please ensure that your username contains an underscore and is more than five characters in length.");
        }

        if (!username.contains("_")) {
            return new Result(false, "Make sure your username includes underscore '_'.");
        }

        if (!password.matches(PASSWORD_REGEX)) {
            return new Result(false, "Password must be at least 8 characters long and include a capital letter, a number, and a special character.");
        }

        if (!phone.matches(PHONE_REGEX)) {
            return new Result(false, "Phone number must be local or +27 format. Do not include 0");
        }

        if (userExists(username)) {
            return new Result(false, "Username already exists.");
        }

        if (saveUser(username, phone, password)) {
            return new Result(true, "User registered successfully!");
        } else {
            return new Result(false, "Could not save user. Please try again.");
        }
    }

    /// Save user to file
    private boolean saveUser(String username, String phone, String password) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(username + "," + password + "," + phone);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /// Check if user exists
    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }

    /// Check login credentials
    private boolean checkCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }
}