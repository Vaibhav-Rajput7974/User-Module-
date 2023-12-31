package com.grapplermodule1.GrapplerEnhancement.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grapplermodule1.GrapplerEnhancement.enums.StructureType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	@NotBlank(message = "Name is required")
	@Column(unique = true,nullable = false)
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Column(unique = true,nullable = false)
	private String email;


	@Size(max = 255, message = "Description should not exceed 255 characters")
	private String description;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Structure type must be provided")
	private StructureType structureType;

	@NotBlank(message = "Contact number is required")
	@Pattern(regexp = "\\d{10}", message = "Invalid contact number")
	private String contactNumber;

	@NotBlank(message = "Address is required")
	private String address;

	private LocalDateTime creationTime;

//	//This indicates that these are the "child" entities, and their serialization should be controlled by
//	// the parent entity

}
