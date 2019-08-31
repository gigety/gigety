package com.gigety.ur.db.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Password Rest token. THese tokens are assigned to users who need to reset
 * password via email.
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PWResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String token;
	private Date expiryDate;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "pwRestToken")
	//@JoinColumn(nullable = false, name = "user_id")
	@NonNull
    //@ForeignKey(deleteAction = ForeignKeyAction.CASCADE, updateAction = ForeignKeyAction.CASCADE)
	private GigUser gigUser;

}
