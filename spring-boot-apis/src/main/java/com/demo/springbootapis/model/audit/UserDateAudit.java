package com.demo.springbootapis.model.audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class UserDateAudit extends DateAudit {

  @CreatedBy
  @Column(name = "created_by")
  private Long createdBy;

  @LastModifiedBy
  @Column(name = "updated_by")
  private Long updatedBy;
}
