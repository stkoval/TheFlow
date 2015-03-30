package helpers;

/**
 *
 * @author Stas
 */
public enum UserRoleConstants {

    USER("User"),
    ADMIN("Admin");
//    OBSERVER("Observer"),
//    ACCOUNT("Account");

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

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
