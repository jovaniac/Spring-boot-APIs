package com.demo.springbootapis.model.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="app_user", catalog= "my_database")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	@Column(name = "user_name")
    private String username;
	
	@NonNull
	@JsonIgnore
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private Boolean active;
	
//	@Temporal(TemporalType.TIMESTAMP)
//	@CreatedDate
//	@Column(name = "created_on")
//	private Date createdOn;
//	
//	@LastModifiedDate
//	@Column(name = "updated_on")
//	private Date updatedOn;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinTable(name = "app_user", 
//			joinColumns=@JoinColumn(name = "updatedBy", referencedColumnName = "id"))
//	private UserInfo updatedBy;
	
	/**
     * Roles are being eagerly loaded here because they are a fairly small collection of items for this example.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", 
            joinColumns= @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
}
