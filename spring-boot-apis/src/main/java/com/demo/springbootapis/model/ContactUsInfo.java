package com.demo.springbootapis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.springbootapis.model.audit.UserDateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact_us", catalog = "my_database")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
public class ContactUsInfo extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 1, max = 20)
	private String name;

	@Email
	@Size(min = 1, max = 100)
	private String email;

	@NotNull
	@Size(min = 1, max = 12)
	private String phoneNumber;

	@NotEmpty
	@Size(min = 1, max = 50)
	private String subject;

	@NotEmpty
	@Size(min = 1, max = 500)
	private String message;

	@Column(name = "resolved")
	private Boolean isResolved;

	@Column(name = "resolved_by")
	private String resovedBy;
}
