package com.example.inventory_management.DTO;

public class RegisterRequestDTO {
   public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	private String Token;
   public RegisterRequestDTO(String token) {
	
		Token = token;
	}

   public RegisterRequestDTO() {
	super();
}
   public RegisterRequestDTO(String token, String username, String password, String role) {
	super();
	Token = token;
	this.username = username;
	this.password = password;
	this.role = role;
}

   private String username;
   private String password;
   private String role;

}
