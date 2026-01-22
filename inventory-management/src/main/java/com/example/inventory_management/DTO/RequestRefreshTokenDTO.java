package com.example.inventory_management.DTO;

public class RequestRefreshTokenDTO {
	
	public String getRefresh_Token() {
		return Refresh_Token;
	}

	public void setRefresh_Token(String refresh_Token) {
		Refresh_Token = refresh_Token;
	}

	private String Refresh_Token;
}
