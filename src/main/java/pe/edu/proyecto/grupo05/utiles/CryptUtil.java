package pe.edu.proyecto.grupo05.utiles;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptUtil {

    public static void main(String[] args) {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        //ingresar contraseña a encriptar
        String password = "miguel123";
        String hash = bcrypt.encode(password);
        System.out.println(hash);

    }
}
