package br.com.eduardoklitzkee.todolist.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/* MODIFICADOR
 * 
 * public
 * private 
 * protected
 */

@RestController
@RequestMapping("/users")
public class UserController {

    /*
     * String (Texto)
     * Integer (int) números inteiros
     * Double (double) números 0.0000
     * Float (float) número 0.000
     * char (A C)
     * Date (data)
     * void
     */

     @Autowired
     private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            return ResponseEntity.status(400).body("Usuário já existe");
        }

        var passwordHashred =  BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);



        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);

        
     

    }
    
}
