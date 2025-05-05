package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ReceptionistService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ReceptionistRepository receptionistRepository;

	public ReceptionistResponse createReceptionist(CreateReceptionistRequest request) {

		Optional<Users> existingUser = userRepository.findByUsername(request.getUsername());
		if (existingUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
		}

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

		Receptionist saved = receptionistRepository.save(receptionist);

		return new ReceptionistResponse(
				saved.getId(),
				saved.getUser().getUsername(),
				saved.getName(),
				saved.getEmail(),
				saved.getPhone()
		);
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
