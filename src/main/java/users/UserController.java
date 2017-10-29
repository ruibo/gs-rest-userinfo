package users;

import java.lang.Iterable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    @Autowired
    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUserNames() {
        return this.repository.findAll();
    }

    @RequestMapping(method   = RequestMethod.GET,
                    value    = "/{username}",
                    produces = "application/json")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        User user = this.repository.findByUserName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user); 
    }

    @RequestMapping(method   = RequestMethod.POST,
                    consumes = "application/json",
                    produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        // The following fields are required: username and password.
        if (user.getUserName() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        this.repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @RequestMapping(method   = RequestMethod.PUT,
                    value    = "/{username}",
                    consumes = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable String username,
                                           @RequestBody User user) {
        // Verify that usernames matches.
        if (user.getUserName() != username) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // PUT is used to create or update.
        // If a user with the given username already exists, then
        // update. Otherwise, add the user as a new user.
        User currentUser = this.repository.findByUserName(username);
        if (currentUser == null) {
            this.repository.save(user);
        } else {
            currentUser = user;
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.DELETE,
                    value  = "/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User user = this.repository.findByUserName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        this.repository.delete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
