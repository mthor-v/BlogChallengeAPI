package com.mthor.blogchallenge.controller;

import com.mthor.blogchallenge.domain.dto.auth.CreateUserDTO;
import com.mthor.blogchallenge.domain.dto.auth.LoginCredentialsDTO;
import com.mthor.blogchallenge.domain.dto.auth.TokenDataDTO;
import com.mthor.blogchallenge.domain.dto.auth.UserResponseDTO;
import com.mthor.blogchallenge.domain.entity.User;
import com.mthor.blogchallenge.domain.service.IUserService;
import com.mthor.blogchallenge.infra.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @PostMapping({"/login"})
    public ResponseEntity<?> authUser(@RequestBody @Valid LoginCredentialsDTO credentialsDTO){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                credentialsDTO.email(), credentialsDTO.password()
        );
        Authentication authUser = authenticationManager.authenticate(authToken);
        String JWTToken = tokenService.generateToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new TokenDataDTO(JWTToken, "Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> newUser(@RequestBody @Valid CreateUserDTO body,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        CreateUserDTO newDTO = new CreateUserDTO(body.name(), body.email(), passwordEncoder.encode(body.password()));
        User user = userService.createUser(new User(newDTO));
        URI url = uriComponentsBuilder.path("/api/posts/{id}").buildAndExpand(user.getId()).toUri();
        UserResponseDTO response = new UserResponseDTO(user);
        return ResponseEntity.created(url).body(response);
    }

}
