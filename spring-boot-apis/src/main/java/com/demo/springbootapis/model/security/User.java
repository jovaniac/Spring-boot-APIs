package com.demo.springbootapis.model.security;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.demo.springbootapis.model.audit.UserDateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_user", catalog = "my_database")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User extends UserDateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NonNull
  @Column(name = "user_name")
  private String username;

  private String password;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @NonNull
  @Column(name = "email")
  private String email;

  @Column(name = "image_url")
  private String imageUrl;

  private Boolean active;

  @NonNull
  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  private String providerId;

  /**
   * Roles are being eagerly loaded here because they are a fairly small collection of items for
   * this example.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles;

  public User(@NonNull String username, String password, @NonNull String email,
      @NonNull AuthProvider provider) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.provider = provider;
  }

}
