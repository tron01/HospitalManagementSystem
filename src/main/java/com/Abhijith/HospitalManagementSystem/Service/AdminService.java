package com.Abhijith.HospitalManagementSystem.Service;
import com.Abhijith.HospitalManagementSystem.Controller.UserResponse;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

private final UserRepository userRepository;

	public UserResponse enableUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setEnabled(true);
		return toDto(userRepository.save(user));
	}

	public UserResponse disableUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setEnabled(false);
		return toDto(userRepository.save(user));
	}

	public UserResponse lockUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setAccountNonLocked(false);
		return toDto(userRepository.save(user));
	}

	public UserResponse unlockUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setAccountNonLocked(true);
		return toDto(userRepository.save(user));
	}

	private Users getUserOrThrow(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
	}
	
	private UserResponse toDto(Users user) {
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.role(user.getRole())
				.isEnabled(user.isEnabled())
				.isAccountNonLocked(user.isAccountNonLocked())
				.build();
	}
}
