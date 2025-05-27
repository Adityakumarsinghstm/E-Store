package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.*;
import com.aditya.electronic.store.entities.Providers;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.exceptions.BadApiRequestException;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.security.JwtHelper;
import com.aditya.electronic.store.services.RefreshTokenService;
import com.aditya.electronic.store.services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller",description = "API for authentication!!")
@SecurityRequirement(name = "scheme1")
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

    @Value("${app.google.client_id}")
    private String googleClientId;

    @Value("${app.google.default_password}")
    private String googleProviderDefaultPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;




    @PostMapping("/regenerate-token")
   public ResponseEntity<JwtResponse> regenerateToken(@RequestBody RefreshTokenRequest request)
   {
       RefreshTokenDto refreshTokenDto = refreshTokenService.findByToken(request.getRefreshToken());
       RefreshTokenDto refreshTokenDto1 = refreshTokenService.verifyRefreshToken(refreshTokenDto);

       UserDto user = refreshTokenService.getUser(refreshTokenDto1);
       String jwtToken = jwtHelper.generateToken(modelMapper.map(user, User.class));

       JwtResponse response = JwtResponse.builder()
               .token(jwtToken)
               .refreshToken(refreshTokenDto)
               .user(user)
               .build();

       return  ResponseEntity.ok(response);
   }

    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
    {
        logger.info( "Email of the user is : "+request.getEmail());
        logger.info("Password of the user is : "+request.getPassword());

        this.doAuthenticate(request.getEmail(),request.getPassword());

       // User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtHelper.generateToken(user);

        RefreshTokenDto refreshTokenDto = refreshTokenService.createRefreshToken(user.getUsername());

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(token)
                .user(modelMapper.map(user, UserDto.class))
                .refreshToken(refreshTokenDto)
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


    @PostMapping("/login-with-google")
    public ResponseEntity<JwtResponse> handleGoogleLogin(@RequestBody GoogleLoginRequest loginRequest) throws GeneralSecurityException, IOException {
        logger.info("Id  Token : {}", loginRequest.getIdToken());

//        token verify

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new GsonFactory()).setAudience(List.of(googleClientId)).build();


        GoogleIdToken googleIdToken = verifier.verify(loginRequest.getIdToken());

        if (googleIdToken != null) {
            //token verified

            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            String email = payload.getEmail();
            String userName = payload.getSubject();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            logger.info("Name {}", name);
            logger.info("Email {}", email);
            logger.info("Picture {}", pictureUrl);
            logger.info("Username {}", userName);


            UserDto userDto = new UserDto();
            userDto.setName(name);
            userDto.setEmail(email);
            userDto.setImageName(pictureUrl);
            userDto.setPassword(googleProviderDefaultPassword);
            userDto.setAbout("user is created using google ");
            userDto.setProvider(Providers.GOOGLE);
            //

            UserDto user = null;
            try {

                logger.info("user is loaded from database");
                user = userService.getUserByEmail(userDto.getEmail());

                // logic implement
                //provider
                logger.info(user.getProvider().toString());
                if (user.getProvider().equals(userDto.getProvider())) {
                    //continue
                } else {
                    throw new BadCredentialsException("Your email is already registered !! Try to login with username and password ");
                }


            } catch (ResourceNotFoundException ex) {
                logger.info("This time user created: because this is new user ");
                user = userService.createUser(userDto);
            }


            //
            this.doAuthenticate(user.getEmail(), userDto.getPassword());


            User user1 = modelMapper.map(user, User.class);


            String token = jwtHelper.generateToken(user1);
            //send karna hai response

            JwtResponse jwtResponse = JwtResponse.builder().token(token).user(user).build();

            return ResponseEntity.ok(jwtResponse);


        } else {
            logger.info("Token is invalid !!");
            throw new BadApiRequestException("Invalid Google User  !!");
        }


    }
    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        String name = principal.getName();
        return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

}
