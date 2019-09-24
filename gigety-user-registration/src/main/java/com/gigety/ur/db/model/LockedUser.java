package com.gigety.ur.db.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class LockedUser implements Serializable{

	private static final long serialVersionUID = 5731562849239380261L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="locked_user_id")
	private GigUser lockedUser;
	
	@OneToOne
	@JoinColumn(name="lock_enforcer_id")
	private GigUser lockEnforcer;
	
	@Column(nullable = false)
	private String lockedUserName;
	private Calendar created = Calendar.getInstance();
}
