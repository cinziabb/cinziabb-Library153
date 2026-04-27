package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import com.generation153.library.entity.Copy;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.CopyRepository;

public class CopyServiceImpl implements CopyService {
	private final CopyRepository copyRepository;

	public CopyServiceImpl(CopyRepository copyRepository) {
		this.copyRepository = copyRepository;
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
		return copy.get();
	}

	@Override
	public Copy saveCopy(Copy copy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Copy updateCopyById(Copy copy, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCopyById(Integer id) {
		// TODO Auto-generated method stub

	}

}
