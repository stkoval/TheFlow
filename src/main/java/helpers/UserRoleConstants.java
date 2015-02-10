/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author Stas
 */
public enum UserRoleConstants {

    User("User"),
    Admin("Admin");

    private final String name;

    private UserRoleConstants(String s) {
        name = s;
    }

    public boolean equalsString(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public static UserRoleConstants getEnum(String value) {
        for (UserRoleConstants v : values()) {
            if ((v.name).equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return name;
    }
}
