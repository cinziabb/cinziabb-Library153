package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Copy;
import com.generation153.library.entity.EnumCopyStatus;

public interface CopyService {

	List<Copy> findAllCopies();
	List<Copy> findCopyByStatus(EnumCopyStatus status);
	Copy findCopyById(Integer id);
	Copy saveCopy(Copy copy);
	Copy updateCopyById(Copy copy, Integer id);
	void deleteCopyById(Integer id);
	Boolean isAvailableCopy(Copy copy);
	Boolean isLendableCopy(Copy copy);
	void markAsAvailableCopy(Copy copy);
	void markAsBorrowedCopy(Copy copy);
	void markAsIntactCopy(Copy copy);
	void markAsDamagedCopy(Copy copy);
	void markAsLostCopy(Copy copy);
	
}
