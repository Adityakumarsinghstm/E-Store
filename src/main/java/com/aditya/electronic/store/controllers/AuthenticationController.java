package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.JwtRequest;
import com.aditya.electronic.store.dtos.JwtResponse;
import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
    {
        logger.info( "Email of the user is : "+request.getEmail());
        logger.info("Password of the user is : "+request.getPassword());

        this.doAuthenticate(request.getEmail(),request.getPassword());

        //User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtHelper.generateToken(user);
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(token)
                .user(modelMapper.map(user, UserDto.class))
                .build();

        return ResponseEntity.ok(jwtResponse);
    }

    private void doAuthenticate(String email, String password) {
        try {

            Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
            authenticationManager.authenticate(authentication);
        }catch (BadCredentialsException ex)
        {
            throw new BadCredentialsException("Username and password is incorrect "+ex.getMessage());
        }
    }
}
