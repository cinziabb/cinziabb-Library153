package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import com.generation153.library.entity.Consultation;
import com.generation153.library.entity.Copy;
import com.generation153.library.entity.User;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.ConsultationRepository;
import com.generation153.library.repository.CopyRepository;
import com.generation153.library.repository.UserRepository;

public class ConsultationServiceImpl implements ConsultationService {

	private final ConsultationRepository consultationRepository;
	private final UserRepository userRepository;
	private final CopyRepository copyRepository;

	public ConsultationServiceImpl(ConsultationRepository consultationRepository, UserRepository userRepository,
			CopyRepository copyRepository) {
		this.consultationRepository = consultationRepository;
		this.userRepository = userRepository;
		this.copyRepository = copyRepository;
	}

	@Override
	public List<Consultation> findAllConsultations() {
		return consultationRepository.findAll();
	}

	@Override
	public Consultation saveConsultation(Consultation consultation) {
		if (consultation == null)
			new NotFoundException("Consultazione nulla");
		if (consultationRepository.existsById(consultation.getId()))
			new DuplicatedResourceException("Consultazione esistente con id: " + consultation.getId());

		User user = findUserInsideConsultation(consultation);
		consultation.setUser(user);

		Copy copy = findCopyInsideConsultation(consultation);
		consultation.setCopy(copy);

		return consultationRepository.save(consultation);
	}

	@Override
	public Consultation findConsultationById(Integer id) {
		if (id == null)
			new NotFoundException("Id nullo");

		return consultationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Consultazione non trovata con id :" + id));
	}

	@Override
	public Consultation updateConsunltationById(Consultation consultation, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteConsutationById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Consultation startConsultation(Copy copy, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endConsultation(Consultation consultation, Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Consultation> findConsultationByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Consultation> findConsultationByCopy(Copy copy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Consultation> findActiveConsultation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Consultation> findCompletedConsultation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> findMostWantedBook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findBorrowedBook() {
		// TODO Auto-generated method stub
		return null;
	}

	// privati medoti per validazione
	private User findUserInsideConsultation(Consultation consultation) {
		return userRepository.findById(consultation.getUser().getId())
				.orElseThrow(() -> new NotFoundException("User non trovato con id: " + consultation.getUser().getId()));
	}

	private Copy findCopyInsideConsultation(Consultation consultation) {
		return copyRepository.findById(consultation.getCopy().getId())
				.orElseThrow(() -> new NotFoundException("Copia non trovata con id " + consultation.getCopy().getId()));
	}
}
