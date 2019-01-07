package com.demo.springbootapis.model.projects;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="projects", catalog= "my_database")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class ProjectInfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Integer projectId;
	
	@NotEmpty
	@Size(max=100)
	@Column(name = "project_name")
	private String projectName;
	
	@NotEmpty
	@Size(max=500)
	@Column(name = "project_summary")
	private String projectSummary;
	
	@Size(max=500)
	@Column(name = "required_skills")
	private String requiredSkills;
	
	@FutureOrPresent
	@Column(name = "due_date")
	private Date dueDate;
	
	@Digits(integer=11, fraction=2) 
	@PositiveOrZero
	@Column(name = "estimated_cost")
	private BigDecimal estimatedCost;
	
	@Column(name = "status_id")
	private int statusId = 1;//Default status is Pending
	
	@OneToOne
	@JoinColumn(name="status_id", insertable=false, updatable=false)
	private ProjectStatus projectStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@LastModifiedDate
	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "lastupdated_by")
	private String lastUpdatedBy;
}
