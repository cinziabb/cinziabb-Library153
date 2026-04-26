package com.generation153.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "authors")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private Integer id;
	@Column(name = "first_name", nullable = false, length = 128)
	private String firstName;
	@Column(name = "last_name", nullable = false, length = 128)
	private String lastName;
	
	  @Override
	    public int hashCode() {
	        return getClass().hashCode();
	    }
		
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Author other = (Author) o;
	        return id != null && id.equals(other.id);
	    }
	  
}
