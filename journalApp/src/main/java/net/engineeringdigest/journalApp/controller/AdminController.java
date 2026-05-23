package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entry.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all  = userService.getAll();
        if( !all.isEmpty()  ){
            return new  ResponseEntity<>(all ,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-admin")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);

    }
}
