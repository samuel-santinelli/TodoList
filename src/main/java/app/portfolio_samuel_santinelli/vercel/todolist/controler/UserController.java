package app.portfolio_samuel_santinelli.vercel.todolist.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.portfolio_samuel_santinelli.vercel.todolist.repository.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import app.portfolio_samuel_santinelli.vercel.todolist.model.UserModel;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository UserRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.UserRepository.findByUsername(userModel.getUsername());

        if(user != null){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome de usuário já em uso");         
        }
        var passwordHashred = BCrypt.withDefaults().
        hashToString(12, userModel.getPassword().toCharArray());        

        userModel.setPassword(passwordHashred);

        var userCreated = this.UserRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
