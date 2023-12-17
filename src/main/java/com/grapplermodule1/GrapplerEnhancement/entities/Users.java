    package com.grapplermodule1.GrapplerEnhancement.entities;
    
    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
    import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotEmpty;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.Data;

    import java.util.List;
    
    @Entity
    @Table(name = "users")
    @Data
    public class Users {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long id;

        @Column(name = "name")
        @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Name is required")
        @Size(max = 255, message = "Name should not exceed 255 characters")
        private String name;
    
        @Column(name = "email", unique = true)
        @Email(groups = {PostValidation.class, PutValidation.class}, message = "Email should be a valid email address")
        @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Email is required")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        private String email;
    
        @Column(name = "designation")
        @Size(max = 255, message = "Designation should not exceed 255 characters")
        @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Designation is required")
        private String designation;
    
        @Column(name = "password")
        @NotEmpty(groups = {PostValidation.class}, message = "Password is required")
        private String password;

        @JsonManagedReference
        @OneToMany(mappedBy = "reportingUser")
        private List<Users> subordinates;

        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "reporting_id")
        @NotNull(groups = {PostValidation.class}, message = "Reporting User ID is required")
        private Users reportingUser;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonManagedReference
        @NotNull(groups = {PostValidation.class}, message = "Role is required")
        private Role role;

        @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
        private List<TeamMembers> teamMembers;

        private Long projectId;


//        @JsonIgnore
//        @JsonBackReference
//        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//        private List<Ticket> ticket;
//
//        @JsonIgnore
//        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//        private List<TicketAssignment> ticketAssignment;
    

    }
