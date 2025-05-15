package com.example.demo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    // Mendapatkan semua pengguna dari database
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // Mendapatkan pengguna berdasarkan ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // Menyimpan pengguna baru ke database dengan pengecekan ID unik
 public void saveUser(User user) {
    // Generate ID kalau belum ada
    if (user.getId() == null || user.getId().isEmpty()) {
        user.setId(idUser());
    }

    // Cek apakah ID sudah ada di DB
    if (userRepository.existsById(user.getId())) {
        throw new RuntimeException("ID user sudah terdaftar");
    }

    // Cek apakah username sudah ada di DB
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
        throw new RuntimeException("Username sudah terdaftar");
    }

    // Enkripsi password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getRole() == null) {
        user.setRole("USER");
    }

    userRepository.save(user);
}


    // Hapus pengguna berdasarkan ID
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Cari pengguna berdasarkan username
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Generate ID unik menggunakan UUID
    private String idUser() {
    Random random = new Random();
    String id;
    do {
        int idRandom = random.nextInt(1000000);
        id = "us" + idRandom;
    } while (userRepository.existsById(id));
    return id;
}

    // Implementasi loadUserByUsername untuk autentikasi
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Mencari user dengan username: " + username);
        Optional<User> optionalUser = userRepository.findByUsername(username);

        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("User ditemukan: " + user.getUsername());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
