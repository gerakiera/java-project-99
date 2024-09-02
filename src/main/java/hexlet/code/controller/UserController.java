package hexlet.code.controller;

import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserController {
    @Autowired
    private UserRepository userRepository;
}






//@RestController
//@RequestMapping(path = "/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private static final String ONLY_OWNER_BY_ID
//            = "@userRepository.findById(#id).get().getEmail() == authentication.getName()";
//
//    private final UserService userService;
//
//    @GetMapping(path = "")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<UserDTO>> index() {
//        var result = userService.getAll();
//        return ResponseEntity.ok()
//                .header("X-Total-Count", String.valueOf(result.size()))
//                .body(result);
//    }
//
//    @PostMapping(path = "")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
//        return userService.create(userData);
//    }
//
//    @PutMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize(ONLY_OWNER_BY_ID)
//    public UserDTO update(@Valid @RequestBody UserUpdateDTO userData, @PathVariable Long id) {
//        return userService.update(userData, id);
//    }
//
//    @GetMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO show(@PathVariable Long id) {
//        return userService.findById(id);
//    }
//
//    @DeleteMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize(ONLY_OWNER_BY_ID)
//    public void destroy(@PathVariable Long id) {
//        userService.delete(id);
//    }
//}
