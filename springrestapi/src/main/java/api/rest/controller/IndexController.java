package api.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import api.rest.model.User;
import api.rest.repository.UserRepository;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<User> buscar(@PathVariable (value= "id") Long id) {
		
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public List<User> listarTodos(){
		return (List<User>) userRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User adicionar(@Valid @RequestBody User user){
				
		Optional<User> usuarioExistente = userRepository.findByLogin(user.getLogin());
		
		if(usuarioExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já cadastrado. Tente novamente com um novo nome.");
		}
		return userRepository.save(user);
	}
	
	
	@PutMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<User> editar(@PathVariable Long id, @RequestBody User usuarioNovo){
		
		Optional<User> usuarioAntigo = userRepository.findById(id);
		
		if(!usuarioAntigo.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		usuarioNovo.setId(id);
		userRepository.save(usuarioNovo);
		
		return new ResponseEntity<User>(usuarioNovo, HttpStatus.OK);
	}	
	
	
	@DeleteMapping(value = "/{id}")
	public void deletar(@PathVariable Long id){
		userRepository.deleteById(id);
	}
	
	

}
