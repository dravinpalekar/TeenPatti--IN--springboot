package com.TeenPatti.demo.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  
@NoArgsConstructor
@Entity
@Table(name = "StoreBasicUserInformation")
public class UserSTOREOtherInformationEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;
	
	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String dateofbirth;
	
	@Column(nullable = false)
	private String country;

	@OneToOne(
			 //			 fetch = FetchType.LAZY,
			// 			 cascade = CascadeType.ALL,
			mappedBy = "UserSTOREOtherInformationEntity")
	private UserEntity userentity;
	
	@CreationTimestamp
    private LocalDateTime createDateTime;
	
	@UpdateTimestamp
    private LocalDateTime updateDateTime;

}
