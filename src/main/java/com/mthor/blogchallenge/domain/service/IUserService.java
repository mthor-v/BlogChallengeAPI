package com.mthor.blogchallenge.domain.service;

import com.mthor.blogchallenge.domain.entity.User;

public interface IUserService {

    User getUserByEmail(String email);

    User createUser(User user);
}
