package app.portfolio_samuel_santinelli.vercel.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.portfolio_samuel_santinelli.vercel.todolist.model.UserModel;

/**
 * Modificator
 * public
 * private
 * protected
 */

@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * String (texto)
     * Intenger (int) Número inteiros
     * Double (double) Números 0.0000
     * Float (float) Número 0.0000
     * char (A C) Caracteres
     * Date (data)
     * void (Retorno não tem nenhum retorno)
     */
    /**
     * Body
     */
    @PostMapping("/")
    public void create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getUsername());
    }
}
