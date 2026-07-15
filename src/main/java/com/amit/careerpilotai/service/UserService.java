package com.amit.careerpilotai.service;

import com.amit.careerpilotai.dto.UpdateProfileRequest;
import com.amit.careerpilotai.entity.User;
import com.amit.careerpilotai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.amit.careerpilotai.util.JwtUtil;
import com.amit.careerpilotai.dto.LoginResponse;
import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private FileStorageService fileStorageService;

    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public LoginResponse loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid Password!");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
    public User updateProfile(Long id, UpdateProfileRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setCareerGoal(request.getCareerGoal());

        return userRepository.save(user);
    }
    public void saveResumePath(Long id, String resumePath) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setResumePath(resumePath);

        userRepository.save(user);
    }
    public String analyzeResume(Long id) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resumeText =
                fileStorageService.readResume(user.getResumePath());

        return geminiService.analyzeResume(resumeText);
    }
}