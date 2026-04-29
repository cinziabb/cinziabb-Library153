package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Publisher;

public interface PublisherService {
	
	List<Publisher>findAllPublishers();
	Publisher findPublisherById(Integer id); 
	Publisher savePublisher(Publisher publisher);
	Publisher replacePublisherById(Publisher publisher, Integer id);
	Publisher updatePublisherById(Publisher publisher, Integer id);
	void deletePublisherById(Integer id);
	
}
