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

	eventualmente disattivare un utente → se ne occuperà lo UserService (PER ORA L'ABBIAMO FATTO 
	DIRETTAMENTE NELLA UPDATE E NELLA REPLACE)

	Per ogni utente devono essere memorizzati almeno i dati principali identificativi e di contatto.
	*/
	
	/*
	*	👤 Vincoli sugli utenti: 
	
		1) Ogni utente deve essere identificato in modo univoco ---> OK
		2) Un utente può avere più prestiti attivi (se non diversamente specificato) 
		3) Non è definito a priori un limite massimo di prestiti (da chiarire o decidere)
		
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
	public List<User> findUserByFirstName(String firstName) {
		
		if (firstName == null || firstName.isBlank()) {
			throw new IllegalArgumentException("Nome nullo o vuoto");
		}
		
		return userRepository.findByFirstName(firstName)
				.orElseThrow(() -> new NotFoundException("nessun utente trovato con nome " + firstName));
	}
	
	@Override
	public List<User> findUserByLastName(String lastName) {
		
		if (lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("Nome nullo o vuoto");
		}
		
		return userRepository.findByLastName(lastName)
				.orElseThrow(() -> new NotFoundException("nessun utente trovato con cognome " + lastName));
	}
	
	@Override
	public List<User> findUserByFullName(String firstName, String lastName) {
		
		if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
			throw new IllegalArgumentException("Nome / congome nullo o vuoto");
		}
		
		return userRepository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new NotFoundException("nessun utente trovato con nome " + firstName + "e cognome " + lastName));
	}
	
	@Override
	public User saveUser(User user) {
		
		//controlla che l'utente non sia nullo
		if (user == null) {
			throw new IllegalArgumentException("utente nullo!");
		}
		
		//controlla che non esista già un utente con l'email specificata
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicatedResourceException("utente già esistente con email: " + user.getEmail());
		}
		
		//salva il nuovo utente
		return userRepository.save(user);
		
	}
	
	@Override
	public User updateUserById(User user, Integer id) {
		
		/*
		*	l'utente può avere campi nulli
		*/
		
		if (user == null) {
			throw new IllegalArgumentException("Utente nullo!");
		}
		
		//controlla che l'id passato come parametro non sia nullo
		if (id == null) {
			throw new IllegalArgumentException("Id nullo!");
		}
		
		//cerca l'utente da modificare
		User updateUser = userRepository.findById(id)
						  .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));
		
		// Modifica i campi solo se non sono nulli
		
		if (user.getEmail() != null && !user.getEmail().isBlank()) {
			//Verifica che non esista un altro utente con la stessa email
			if (userRepository.existsByEmailAndIdNot(user.getEmail(), id)) {
			 	throw new DuplicatedResourceException("Email già in uso");
			}
			updateUser.setEmail(user.getEmail());
		}
		if (user.getFirstName() != null && !user.getFirstName().isBlank()) {
			updateUser.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null && !user.getLastName().isBlank()) {
			updateUser.setLastName(user.getLastName());
		}
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
		
		/*
		*	l'utente non ha campi nulli, ad eccezione dell'id
		*/
		
		//controlla che l'utente non sia nullo
		if (user == null) {
			throw new IllegalArgumentException("utente nullo!");
		}
		
		//controlla che l'id non sia nullo
		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}
		
		//Cerca l'utente da modificare, altrimenti lancia un'eccezione
		User replacedUser = userRepository.findById(id)
							.orElseThrow(() -> new NotFoundException("utente non trovato con id: " + id));
		
		
		if (user.getEmail() != null && !user.getEmail().isBlank()) {
			//Verifica che non esista un altro utente con la stessa email
			if (userRepository.existsByEmailAndIdNot(user.getEmail(), id)) {
				throw new DuplicatedResourceException("email duplicata");
			}
		}
		
		//aggiorna tutti i dati dell'utente
		replacedUser.setFirstName(user.getFirstName());
		replacedUser.setLastName(user.getLastName());
		replacedUser.setEmail(user.getEmail());
		replacedUser.setRole(user.getRole());
		replacedUser.setBlocked(user.isBlocked());
		
		//salva l'utente con i dati aggiornati
		return userRepository.save(user);
		
	}

	@Override
	public boolean deleteUserById(Integer id) {
		
		if(id == null){
			throw new IllegalArgumentException("Id nullo");
		}
		
		userRepository.deleteById(id);
		return true;
		
	}
	//bloccare e sbloccare utenti con restituzioni in ritardo
	
	@Override
	public User blockUser(Integer id) {
		if(id == null){
			throw new IllegalArgumentException("Id nullo");
		}
		User user = userRepository.findById(id)
				.orElseThrow (() -> new NotFoundException("Utente non trovato con id: " + id));
		
		user.setBlocked(true);
		return userRepository.save(user);
	}

	@Override
	public User unblockUser(Integer id) {
		if(id == null){
			throw new IllegalArgumentException("Id nullo");
		}
		User user = userRepository.findById(id)
				.orElseThrow (() -> new NotFoundException("Utente non trovato con id: " + id));
	
		user.setBlocked(false);
		return userRepository.save(user);
	}
		
	}


