	package com.example.inventory_management.entity;
	
	import java.time.OffsetDateTime;
	import java.util.Collection;
	import java.util.HashSet;
	import java.util.Set;
	import java.util.stream.Collectors;
	
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.authority.SimpleGrantedAuthority;
	
	import com.example.inventory_management.Enum.Status;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.EnumType;
	import jakarta.persistence.Enumerated;
	import jakarta.persistence.FetchType;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.JoinTable;
	import jakarta.persistence.ManyToMany;
	import jakarta.persistence.Table;
	
	@Entity
	@Table(name="log_in_details")
	public class LogIn {
	
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;
		
		
		private String username;
		
		private String Password;
		@Column(columnDefinition="TIMESTAMP  WITH TIME ZONE")
		private OffsetDateTime created_at;
		
		@Enumerated(EnumType.STRING)
		@Column(nullable=false)
		private Status status;
		
		
		@ManyToMany(fetch=FetchType.EAGER)
		@JoinTable(
				name="user_role",
				joinColumns=@JoinColumn(name="user_id"),
				inverseJoinColumns=@JoinColumn(name="role_id")
				)
		   private Set<Roles> roles=new HashSet<>();
			 
		
		public Set<Roles> getRoles() {
			return roles;
		}
	
		public void setRoles(Set<Roles> roles) {
			this.roles = roles;
		}
	
		public OffsetDateTime getCreated_at() {
			return created_at;
		}
	
		public void setCreated_at(OffsetDateTime created_at) {
			this.created_at = created_at;
		}
	
		public Status getStatus() {
			return status;
		}
	
		public void setStatus(Status status) {
			this.status = status;
		}
	
		
		public Integer getId() {
			return id;
		}
	
		public void setId(Integer id) {
			this.id = id;
		}
	
		public String getUsername() {
			return username;
		}
	
		public void setUsername(String username) {
			this.username = username;
		}
	
		public String getPassword() {
			return Password;
		}
	
		
		public void setPassword(String password) {
			Password = password;
		}
		
		
	
	}
