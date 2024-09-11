package hexlet.code.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class UserCreateDTO {
    @NotNull
    @Email
    private String email;

    private String firstName;

    private String lastName;

    @NotNull
    @Size(min = 3)
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
//@Getter
//@Setter
//public class UserCreateDTO {
//    @NotNull
//    @Email
//    private String email;
//
//    private String firstName;
//
//    private String lastName;
//
//    @NotNull
//    @Size(min = 3)
//    private String password;
//}
