package hexlet.code.component;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        createAdmin();
    }

    public void createAdmin() {
        UserCreateDTO userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        userService.create(userData);
    }
}
