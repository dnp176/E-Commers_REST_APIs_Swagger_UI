package com.main.controller;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.User;
import com.main.services.UserService;
import com.main.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication Controller", description = "This is used for Login, Logout and Register User.")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
		@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
    @Operation(summary = "Register User",description = "User Role : 1 - Admin | 2 - User")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        return ResponseEntity.ok(response);
    }

    @ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
		@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
    @Operation(summary = "Login User",description = "User Role : 1 - Admin | 2 - User")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws Exception {
//    	String email = loginRequest.get("email");  // Extract email
//        String password = loginRequest.get("password");  // Extract password
        try {
            // Authenticate using email and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        Date expiryDate = jwtUtil.extractExpiration(token);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String readableExpiry = sdf.format(expiryDate);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", token);
        responseBody.put("status", "success");
        responseBody.put("expires_at", readableExpiry);

        return ResponseEntity.ok(responseBody);
    }
    
    @Operation(summary = "Logout User",description = "Enter your JWT authentication key")
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String jwt = authorizationHeader.substring(7);
			jwtUtil.blacklistToken(jwt); 
			return ResponseEntity.ok("Logout successful, token invalidated.");
		}

		return ResponseEntity.status(400).body("No token provided.");
	}
    
	@Operation(summary = "Get All Users",description = "Show all Users Details")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
		List<User> allUsers = userService.allUsersDetails();
    	return ResponseEntity.status(200).body(allUsers);
    }
	
	/*
	 * @GetMapping("/decodeToken") public String[] decodeJWT(String jwtToken) {
	 * String[] parts = jwtToken.split("\\."); // Split JWT into three parts
	 * 
	 * if (parts.length != 3) { throw new
	 * IllegalArgumentException("Invalid JWT token format"); }
	 * 
	 * String header = new String(Base64.getUrlDecoder().decode(parts[0])); String
	 * payload = new String(Base64.getUrlDecoder().decode(parts[1]));
	 * 
	 * return new String[]{header, payload}; }
	 */
}