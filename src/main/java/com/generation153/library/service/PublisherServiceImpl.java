package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Publisher;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.PublisherRepository;

public class PublisherServiceImpl implements PublisherService{
	
	private final PublisherRepository publisherRepository; 
	
	public PublisherServiceImpl(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	@Override
	public List<Publisher> findAllPublishers() {
		return publisherRepository.findAll();
	}

	@Override
	public Publisher findPublisherById(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
		Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new NotFoundException("Publisher non trovato"));
		
		return publisher;
	}

	@Override
	public Publisher savePublisher(Publisher publisher) {
		if(publisher == null) {
			throw new IllegalArgumentException("publisher nullo"); 
		}
		List<Publisher> publisherList = publisherRepository.findAll(); 
		for(Publisher p : publisherList) {
			if(p == publisher) {
				throw new DuplicatedResourceException("Publisher " + p + " già presente"); 
			}
		}
		
		return publisherRepository.save(publisher);
	}

	@Override
	public Publisher replacePublisherById(Publisher publisher, Integer id) {
		if(publisher == null) {
			throw new IllegalArgumentException("publisher nullo"); 
		}
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
		Publisher publisherList = publisherRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Publisher " + publisher + " con id " + id + " non trovato"));
		
			publisherList.setName(publisher.getName());
			
		return publisherRepository.save(publisherList);
	}

	@Override
	public Publisher updatePublisherById(Publisher publisher, Integer id) {
		if(publisher == null) {
			throw new IllegalArgumentException("publisher nullo"); 
		}
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
		Publisher publisherList = publisherRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Publisher " + publisher + " con id " + id + " non trovato"));
		
		if(publisher.getName() != null && !publisher.getName().isBlank()) {
			publisherList.setName(publisher.getName());
		}
		return publisherRepository.save(publisherList);
	}

	@Override
	public void deletePublisherById(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
		Publisher publisher = publisherRepository.findById(id).
				orElseThrow(() -> new NotFoundException("Publisher con id " + id + " non trovato")); 
		
		publisherRepository.delete(publisher);
	}

}
