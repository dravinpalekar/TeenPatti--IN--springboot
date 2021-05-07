package com.TeenPatti.demo.Entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Client_User")
public class UserEntity {
	
	private static final long serialVersionUID = -1798070786993154676L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@JsonIgnore
	@Column(nullable = true)
	private String role = "none";

	@JsonIgnore
	@Column(nullable = true)
	private String active = "Not-verify";

	@Column(nullable = false, unique = true)
	private String email;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserSTOREOtherInformationEntity_ID", referencedColumnName = "id", nullable = true)
	private UserSTOREOtherInformationEntity UserSTOREOtherInformationEntity;
	
	@CreationTimestamp
    private LocalDateTime createDateTime;
	
	@UpdateTimestamp
    private LocalDateTime updateDateTime;
}
