package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.DoctorNoteRequest;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorNoteResponse;
import com.Abhijith.HospitalManagementSystem.DTO.MedicationDTO;
import com.Abhijith.HospitalManagementSystem.Model.Appointment;
import com.Abhijith.HospitalManagementSystem.Model.DoctorNote;
import com.Abhijith.HospitalManagementSystem.Model.Medication;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorNoteRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorNoteService {

	private final DoctorNoteRepository doctorNoteRepository;
	private final AppointmentRepository appointmentRepository;
	private final UserRepository userRepository;


	public DoctorNoteResponse saveDoctorNote(DoctorNoteRequest request,String doctorUsername) {
		Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found"));

		if (!appointment.getDoctor().getUser().getUsername().equals(doctorUsername)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized access to this appointment");
		}

		DoctorNote doctorNote = DoctorNote.builder()
				.diagnosis(request.getDiagnosis())
				.instructions(request.getInstructions())
				.appointment(appointment)
				.build();

		List<Medication> medications = request.getMedications().stream()
				.map(med -> Medication.builder()
						.name(med.getName())
						.dosage(med.getDosage())
						.frequency(med.getFrequency())
						.timing(med.getTiming())
						.doctorNote(doctorNote)
						.build())
				.collect(Collectors.toList());

		doctorNote.setMedications(medications);

		DoctorNote savedNote = doctorNoteRepository.save(doctorNote);

		return mapToResponse(savedNote);
	}

	public DoctorNoteResponse getDoctorNoteByAppointmentIdForDoctor(Long appointmentId, String doctorUsername) {
		// Fetch the DoctorNote
		DoctorNote note = doctorNoteRepository.findByAppointmentId(appointmentId)
				.orElseThrow(() -> new RuntimeException("Doctor note not found"));

		// Ensure the doctor has access to the appointment
		if (!note.getAppointment().getDoctor().getUser().getUsername().equals(doctorUsername)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access to this doctor note");
		}
		return mapToResponse(note);
	}

	public DoctorNoteResponse getDoctorNoteByAppointmentId(Long appointmentId) {
		DoctorNote note = doctorNoteRepository.findByAppointmentId(appointmentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AppointmentId  not found"));

		List<MedicationDTO> meds = note.getMedications().stream()
				.map(med -> MedicationDTO.builder()
						.name(med.getName())
						.dosage(med.getDosage())
						.frequency(med.getFrequency())
						.timing(med.getTiming())
						.build())
				.collect(Collectors.toList());

		return DoctorNoteResponse.builder()
				.id(note.getId())
				.diagnosis(note.getDiagnosis())
				.instructions(note.getInstructions())
				.appointmentId(note.getAppointment().getId())
				.medications(meds)
				.build();
	}

	public DoctorNoteResponse getDoctorNoteByAppointmentIdForPatient(Long appointmentId, String username) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found"));

		// Check if appointment belongs to logged-in user
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

		if (!appointment.getPatient().getUser().getId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied to this appointment’s doctor note.");
		}

		DoctorNote note = doctorNoteRepository.findByAppointmentId(appointmentId)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor note not found for this appointment."));

		List<MedicationDTO> meds = note.getMedications().stream()
				.map(m -> new MedicationDTO(m.getName(), m.getDosage(), m.getFrequency(), m.getTiming()))
				.collect(Collectors.toList());

		return DoctorNoteResponse.builder()
				.id(note.getId())
				.diagnosis(note.getDiagnosis())
				.instructions(note.getInstructions())
				.appointmentId(appointmentId)
				.medications(meds)
				.build();
	}

	private DoctorNoteResponse mapToResponse(DoctorNote note) {
		List<MedicationDTO> meds = note.getMedications().stream()
				.map(m -> MedicationDTO.builder()
						.name(m.getName())
						.dosage(m.getDosage())
						.frequency(m.getFrequency())
						.timing(m.getTiming())
						.build())
				.collect(Collectors.toList());

		return DoctorNoteResponse.builder()
				.id(note.getId())
				.diagnosis(note.getDiagnosis())
				.instructions(note.getInstructions())
				.appointmentId(note.getAppointment().getId())
				.medications(meds)
				.build();
	}
}

