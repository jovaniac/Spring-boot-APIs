package com.demo.springbootapis.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="app_role")
@Getter
@Setter
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private RoleName name;

	@Column(name="description")
	private String description;

	private Boolean active;

	@Override
	public String getAuthority() {
		return name.name();
	}
		
//	@Override
//	public String getAuthority() {
//		return name.name();
//	}
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
}
