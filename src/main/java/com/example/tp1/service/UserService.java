package com.example.tp1.service;

import com.example.tp1.entity.User;
import com.example.tp1.repository.UserRepository;
import com.example.tp1.util.EntityManagerProducer;
import jakarta.persistence.EntityManager;

public class UserService {

    public User login(String username, String password) {
        EntityManager em = EntityManagerProducer.getEntityManager();
        try {
            UserRepository userRepo = new UserRepository(em);
            User user = userRepo.findByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        } finally {
            em.close();
        }
    }
}