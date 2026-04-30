package com.generation153.library.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.generation153.library.entity.Consultation;
import com.generation153.library.entity.Copy;
import com.generation153.library.entity.User;
import com.generation153.library.exception.BadTimeException;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.ConsultationRepository;
import com.generation153.library.repository.CopyRepository;
import com.generation153.library.repository.UserRepository;

public class ConsultationServiceImpl implements ConsultationService {

	private final ConsultationRepository consultationRepository;
	private final UserRepository userRepository;
	private final CopyRepository copyRepository;
	private final CopyService copyService;

	public ConsultationServiceImpl(ConsultationRepository consultationRepository, UserRepository userRepository,
			CopyRepository copyRepository, CopyService copyService) {
		this.consultationRepository = consultationRepository;
		this.userRepository = userRepository;
		this.copyRepository = copyRepository;
		this.copyService = copyService;
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
		if (id == null)
			new NotFoundException("Id nullo");

		if (consultation == null)
			new NotFoundException("Consultazione nulla");

		Consultation consultationUpdate = consultationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Consultazione non trovata con id :" + id));

		if (consultation.getInitDate() != null)
			consultationUpdate.setInitDate(consultation.getInitDate());

		if (consultation.getInitTime() != null)
			consultationUpdate.setInitTime(consultation.getInitTime());

		if (consultation.getEndTime().isAfter(consultation.getInitTime()))
			consultationUpdate.setEndTime(consultation.getEndTime());

		if (consultation.getUser() != null) {
			User user = findUserInsideConsultation(consultation);
			consultationUpdate.setUser(user);
		}

		if (consultation.getCopy() != null) {
			Copy copy = findCopyInsideConsultation(consultation);
			consultationUpdate.setCopy(copy);
		}

		return consultationRepository.save(consultationUpdate);
	}

	@Override
	public void deleteConsutationById(Integer id) {
		if (id == null)
			new NotFoundException("Id nullo");

		Consultation consultationUpdate = consultationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Consultazione non trovata con id :" + id));

		consultationRepository.delete(consultationUpdate);

	}

	@Override
	public Consultation startConsultation(Copy copy, User user) {

		if (copy == null)
			new NotFoundException("Copia nulla");

		if (user == null)
			new NotFoundException("User nullo");

		Copy copyNew = copyRepository.findById(copy.getId())
				.orElseThrow(() -> new NotFoundException("Copia non trovata con id :" + copy.getId()));

		copyService.isAvailableCopy(copyNew);

		Consultation consultation = new Consultation();
		LocalDate date = LocalDate.now();
		LocalTime initTime = LocalTime.now();
		LocalTime endTime = null;

		User userNew = userRepository.findById(user.getId())
				.orElseThrow(() -> new NotFoundException("User non trovato con id:" + user.getId()));

		consultation.setInitDate(date);
		consultation.setInitTime(initTime);
		consultation.setEndTime(endTime);
		consultation.setCopy(copyNew);
		consultation.setUser(userNew);

		copyService.markAsBorrowedCopy(copyNew);

		return consultationRepository.save(consultation);
	}

	@Override
	public void endConsultation(Consultation consultation, Integer id) {
		if (id == null)
			new NotFoundException("Id nullo");
		if (consultation == null)
			new NotFoundException("Consultazione nulla");

		Consultation consultationNew = consultationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Consultazione non esistente con id: " + id));
		
		LocalTime endTime = LocalTime.now();
		if(consultationNew.getInitTime().isAfter(endTime))
			throw new BadTimeException("Tempo iniziale avviene dopo il tempo finale");
		
		consultationNew.setEndTime(endTime);
		copyService.markAsAvailableCopy(consultationNew.getCopy());

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
