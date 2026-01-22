package com.example.inventory_management.DTO;

public class AuthResponseDto {
	
	private String accessToken;
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}

	private String RefreshToken;

	public AuthResponseDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		RefreshToken = refreshToken;
	}
}
