package com.generation153.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Integer id;
	@Column(name = "first_name", nullable = false, length = 128)
	private String firstName;
	@Column(name = "last_name", nullable = false, length = 128)
	private String lastName;
	@Column(nullable = false, unique = true, length = 128)
	private String email;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EnumRole role;
	@Column(nullable = false)
	private boolean blocked;
}
