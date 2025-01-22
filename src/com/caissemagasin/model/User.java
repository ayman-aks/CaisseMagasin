package com.caissemagasin.model;

/**
 * Represents a User in the system.
 * A user has an ID, name, surname, login credentials, password, and flags for admin status and activity status.
 */
public class User {
    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Boolean isAdmin;
    private Boolean isActive;

    /**
     * Constructs a User with the specified ID, name, surname, login, password, admin status, and activity status.
     * 
     * @param id the unique identifier for the user
     * @param name the name of the user
     * @param surname the surname of the user
     * @param login the login credentials of the user
     * @param password the password of the user
     * @param isAdmin flag indicating whether the user is an admin
     * @param isActive flag indicating whether the user is active
     */
    public User(Integer id, String name, String surname, String login, String password, Boolean isAdmin, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    /**
     * Gets the admin status of the user.
     * 
     * @return true if the user is an admin, false otherwise
     */
    public Boolean getAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin status of the user.
     * 
     * @param admin the admin status to set
     */
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    /**
     * Gets the activity status of the user.
     * 
     * @return true if the user is active, false otherwise
     */
    public Boolean getActive() {
        return isActive;
    }

    /**
     * Sets the activity status of the user.
     * 
     * @param active the activity status to set
     */
    public void setActive(Boolean active) {
        isActive = active;
    }

    /**
     * Gets the unique identifier of the user.
     * 
     * @return the user ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     * 
     * @param id the user ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     * 
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the user.
     * 
     * @return the user's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the login credentials of the user.
     * 
     * @return the user's login credentials
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login credentials of the user.
     * 
     * @param login the login credentials to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
