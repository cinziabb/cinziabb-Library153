package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import com.generation153.library.entity.Book;
import com.generation153.library.entity.Copy;
import com.generation153.library.entity.EnumCopyStatus;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.BookRepository;
import com.generation153.library.repository.CopyRepository;

public class CopyServiceImpl implements CopyService {
	private final CopyRepository copyRepository;
	private final BookRepository bookRepository;

	public CopyServiceImpl(CopyRepository copyRepository, BookRepository bookRepository) {
		this.copyRepository = copyRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Copy> findAllCopies() {
		return copyRepository.findAll();
	}

	@Override
	public Copy findCopyById(Integer id) {
		if (id == null)
			new NotFoundException("id nullo");
		Optional<Copy> copy = copyRepository.findById(id);
		return copy.orElseThrow(() -> new NotFoundException("Copia non trovata"));
	}

	@Override
	public Copy saveCopy(Copy copy) {
		if (copy == null)
			new NotFoundException("Copia nulla");

		Copy copyTemp = copyRepository.findById(copy.getId()).get();
		if (copy.getId().equals(copyTemp.getId()))
			new NotFoundException("id della copia già esistente");

		return copyRepository.save(copy);
	}

	@Override
	public Copy updateCopyById(Copy copy, Integer id) {
		if (id == null)
			new NotFoundException("id nullo");

		if (copy == null)
			new NotFoundException("Copia nulla");

		Copy copyTemp = copyRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("id della copia non trovato: " + id));

		if (copy.getStatus() != null)
			copyTemp.setStatus(copy.getStatus());

		if(copy.getBook() != null) {
			Book book = findBookInsideCopy(copy);
			copyTemp.setBook(book);
		}
		
		if(copy.getAvailable() != null)
			copyTemp.setAvailable(copy.getAvailable());
		
		return copyRepository.save(copyTemp);
	}

	@Override
	public void deleteCopyById(Integer id) {
		if (id == null)
			new NotFoundException("id nullo");
		
		Copy copyTemp = copyRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("id della copia non trovato: " + id));
		
		copyRepository.delete(copyTemp);
	}

	// metodi privati
	private Book findBookInsideCopy(Copy copy) {
		return bookRepository.findById(copy.getBook().getId())
				.orElseThrow(() -> new NotFoundException("Libro non trovato con id: " + copy.getBook().getId()));
	}

	@Override
	public Boolean isAvailableCopy(Copy copy) {
		
		if(copy.getAvailable() == false)
			if(copy.getStatus() == EnumCopyStatus.DAMAGED || copy.getStatus() == EnumCopyStatus.LOST)
				return false;
		
		return true;
	}
	
	@Override
	public Boolean isLendableCopy(Copy copy) {
		
		if(copy.getBook().getLendable() == true)
			return true;
		
		return false;
	}

	@Override
	public void markAsAvailableCopy(Copy copy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markAsBorrowedCopy(Copy copy) {
		// TODO Auto-generated method stub
		
	}

}
