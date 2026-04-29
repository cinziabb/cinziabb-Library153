package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Copy;
import com.generation153.library.entity.EnumCopyStatus;

public interface CopyRepository extends JpaRepository<Copy, Integer> {
	List<Copy> findByStatus(EnumCopyStatus status);
}
