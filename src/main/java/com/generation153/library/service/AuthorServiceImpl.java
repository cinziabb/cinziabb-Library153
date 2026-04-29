package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.generation153.library.entity.Author;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService{

	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public List<Author> findAllAuthors() {
//<<<<<<< HEAD
        return authorRepository.findAll();
    }

//=======
//		return authorRepository.findAll();
	//}

	@Override
//>>>>>>> refs/remotes/origin/develop
	public Author findAuthorById(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("Id " + id + " nullo");
		}
		Optional<Author> authors = authorRepository.findById(id);
		return authors.orElseThrow(() -> new NotFoundException("Autore con id " + id + " non trovato"));
	}

	@Override
	public Author saveAuthor(Author author) {

		if(author == null) {
			throw new IllegalArgumentException("Autore nullo");
		}

		List<Author> authors = authorRepository.findAll();
		for(Author a : authors) {
			if(a == author) {
				throw new DuplicatedResourceException("L'autore esiste già");
			}
		}
		return authorRepository.save(author);
	}

	@Override
	public Author replaceAuthorById(Author author, Integer id) {
		if(author == null) {
			throw new IllegalArgumentException("Autore nullo");
		}
		if(id == null) {
			throw new IllegalArgumentException("Id " + id + " nullo");
		}
		Author authorOpt = authorRepository.findById(id).orElseThrow(()-> new NotFoundException("Autore non trovato"));
		authorOpt.setFirstName(author.getFirstName());
		authorOpt.setLastName(author.getLastName());

		return authorRepository.save(authorOpt);
	}

	//METODI DA IMPLEMENTARE

	@Override
	public Author updateAuthorById(Author author, Integer id) {
		if(author == null) {
			throw new IllegalArgumentException("Autore nullo");
		}

		if(id == null) {
			throw new IllegalArgumentException("Id " + id + " nullo");
		}

		Author authorOpt = authorRepository.findById(id).orElseThrow(()-> new NotFoundException("Autore non trovato"));
		if(author.getFirstName() != null && author.getFirstName().isBlank()) {
			authorOpt.setFirstName(author.getFirstName());
		}
		if(author.getLastName() != null && author.getLastName().isBlank()) {
			authorOpt.setLastName(author.getLastName());
		}
		return authorRepository.save(authorOpt);
	}

	@Override
	public void deleteAuthorById(Integer id) {
		
		if(id == null) {
			throw new IllegalArgumentException("Id " + id + " nullo");
		}
		
		Author authorOpt = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Autore non trovato"));
		
		authorRepository.delete(authorOpt);
		
	}

}
