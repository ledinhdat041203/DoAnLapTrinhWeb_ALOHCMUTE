package com.hcmute.Entity;

import java.io.Serializable;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "UserAccount")
public class UserEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "idaccount", columnDefinition = "BIGINT")
	private long idAccount;
	@Column(name = "username", columnDefinition = "nvarchar(255)", nullable = false)
	private String userName;
	@Column(name = "email", columnDefinition = "nvarchar(255)", nullable = false)
	private String email;
	@Column(name = "pass", columnDefinition = "nvarchar(32)", nullable = false)
	private String pass;
	
	@OneToOne
    @JoinColumn(name = "User_info_id", referencedColumnName = "userid")
    private UserInfoEntity userInfo;	
	
}
