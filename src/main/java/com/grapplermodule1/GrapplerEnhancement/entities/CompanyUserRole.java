package com.grapplermodule1.GrapplerEnhancement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.grapplermodule1.GrapplerEnhancement.enums.RoleEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companyUserRole")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUserRole {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	 @Enumerated(EnumType.STRING)
	 private RoleEnum role;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Users user ;

	private Long companyId ;
}
