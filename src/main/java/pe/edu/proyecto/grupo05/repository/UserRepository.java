package pe.edu.proyecto.grupo05.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.proyecto.grupo05.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
