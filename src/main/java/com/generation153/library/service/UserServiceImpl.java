package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.generation153.library.entity.User;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	/*
	*Il sistema deve consentire di:

	cercare un utente per nome, cognome → creazione di query method in UserRepository

	modificare i dati di un utente → se ne occuperà lo UserService 

	eventualmente disattivare un utente → se ne occuperà lo UserService

	Per ogni utente devono essere memorizzati almeno i dati principali identificativi e di contatto.
	*/
	
	//Dependency Injection
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User findUserById(Integer id) {
		
		//controlla che l'id non sia nullo
		if (id == null) {
			throw new IllegalArgumentException("l'id è nullo!");
		}
		
		//cerca l'utente in base all'id
		Optional<User> optUser = userRepository.findById(id);
		
		//se l'utente esiste, restituiscilo, altrimenti lancia un'eccezione
		return optUser.orElseThrow(() -> new NotFoundException("utente non trovato con id: " + id));
		
	}
	
	@Override
	public List<User> findUserByName(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Nome nullo o vuoto");
		}
		return userRepository.findByFirstName(name)
				.orElseThrow(() -> new NotFoundException("nessun utente trovato con nome " + name));
	}
	
	@Override
	public User saveUser(User user) {
		
		//controlla che l'utente non sia nullo
		if (user == null) {
			throw new IllegalArgumentException("utente nullo!");
		}
		
		//controlla che non esista già un utente con l'id specificato (il quale non è nullo)
		if (userRepository.existsById(user.getId())) {
			throw new DuplicatedResourceException("utente già esistente con id: " + user.getId());
		}
		
		//salva il nuovo utente
		return userRepository.save(user);
		
	}
	
	@Override
	public User updateUserById(User user, Integer id) {
		if (user == null) {
			throw new IllegalArgumentException("Utente nullo!");
		}
		
		//controlla che la l'id passato come parametro non sia nullo
		if (id == null) {
			throw new IllegalArgumentException("Id nullo!");
		}
		
		//cerca l'utente da modificare
		User updateUser = userRepository.findById(id)
						  .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));
		
		// Modifica i campi solo se non sono nulli
		/*firstName
		  lastName
		  email
		  role
		  blocked*/
		
		if (user.getFirstName() != null && !user.getFirstName().isBlank()) {
			updateUser.setFirstName(user.getFirstName());
			}
		if (user.getLastName() != null && !user.getLastName().isBlank()) {
			updateUser.setLastName(user.getLastName());
			}
		if (user.getEmail() != null && !user.getEmail().isBlank()) {
			 if (userRepository.existsByEmailAndIdNot(user.getEmail(), id)) {
			        throw new DuplicatedResourceException("Email già in uso");
			    }
		}
			updateUser.setEmail(user.getEmail());
		
		if (user.getRole() != null) {
			updateUser.setRole(user.getRole());
		}
		if (user.isBlocked()) {
			updateUser.setBlocked(user.isBlocked());
		}	
		
		//salva l'utente con i dati aggiornati
		return userRepository.save(updateUser);
	}
	
	
	@Override
	public User replaceUserById(User user, Integer id) {
		return null;
	}
	
	@Override
	public void deleteUserById(Integer id) {
		if(id == null){
			throw new IllegalArgumentException("Id nullo");
		}
		userRepository.deleteById(id);
	}

}
