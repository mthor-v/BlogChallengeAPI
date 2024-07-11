package com.mthor.blogchallenge.domain.service.implement;

import com.mthor.blogchallenge.domain.entity.User;
import com.mthor.blogchallenge.domain.service.IUserService;
import com.mthor.blogchallenge.infra.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

}
