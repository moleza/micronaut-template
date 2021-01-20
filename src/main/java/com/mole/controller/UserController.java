package com.mole.controller;

import com.mole.entity.User;
import com.mole.entity.UserDTO;
import com.mole.exceptions.NotFoundException;
import com.mole.repository.UserRepository;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@Controller("/api/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;

    @Secured({"ADMIN"})
    @Get
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Secured({"ADMIN"})
    @Get("/{id}")
    public User findById(@PathVariable long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(String.valueOf(id), "ID", "User"));
    }

    @Secured({"ADMIN"})
    @Get("/email/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return repository.findByEmailAndEnabled(email, true);
    }
}
