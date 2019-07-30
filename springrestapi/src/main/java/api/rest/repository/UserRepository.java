package api.rest.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.rest.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	Optional<User> findByLogin(String login);
	
}
