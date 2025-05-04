package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReceptionistService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ReceptionistRepository receptionistRepository;

	public ReceptionistResponse createReceptionist(CreateReceptionistRequest request) {
		Users user = Users.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_RECEPTIONIST)
				.isEnabled(true)
				.isAccountNonLocked(true)
				.build();
		userRepository.save(user);

		Receptionist receptionist = Receptionist.builder()
				.name(request.getName())
				.email(request.getEmail())
				.phone(request.getPhone())
				.user(user)
				.build();
		return toReceptionistDto(receptionistRepository.save(receptionist));
	}

	public List<ReceptionistResponse> getAllReceptionists() {
		return receptionistRepository.findAll()
				.stream()
				.map(this::toReceptionistDto)
				.collect(Collectors.toList());
	}

	// ---------- MAPPERS ----------
	private ReceptionistResponse toReceptionistDto(Receptionist r) {
		return ReceptionistResponse.builder()
				.id(r.getId())
				.name(r.getName())
				.email(r.getEmail())
				.phone(r.getPhone())
				.username(r.getUser().getUsername())
				.build();
	}
}
