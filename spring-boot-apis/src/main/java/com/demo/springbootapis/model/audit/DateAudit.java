package com.demo.springbootapis.model.audit;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdOn", "updatedOn"},
        allowGetters = true
)
@Getter
@Setter
public abstract class DateAudit {

	@CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false)
    private Instant createdOn;

    @LastModifiedDate
    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;
}
