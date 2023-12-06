package com.hcmute.Entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "UserInfo")
public class UserInfoEntity {
	@Id
	/* @GeneratedValue(strategy = GenerationType.IDENTITY) */
	@Column(name = "userid", columnDefinition = "BIGINT")
	private long userID;
	@Column(name = "fullname", columnDefinition = "nvarchar(255)", nullable = false)
	private String fullName;
	@Column(name = "dateofbirth", columnDefinition = "DATE")
	private Date dateOfBirth;
	@Column(name = "avata", columnDefinition = "nvarchar(255)")
	private String avata;
	@Column(name = "address", columnDefinition = "nvarchar(255)")
	private String address;
	@Column(name = "phonenumber", columnDefinition = "nvarchar(255)")
	private String phoneNumber;
	
	 @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
	 private UserEntity userAccount;
	 
	 @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
	 private List<MessagesEntity> messageSender;
	 @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
	 private List<MessagesEntity> messageReceiver;
	 
	 @OneToMany(mappedBy = "user1", fetch = FetchType.LAZY)
	 private List<FriendsEntity> listFriend1;
	 @OneToMany(mappedBy = "user2", fetch = FetchType.LAZY)
	 private List<FriendsEntity> listFriend2;
	 
	 @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
	 private List<GroupEntity> listGroups;
	 
	 @OneToMany(mappedBy = "userMember", fetch = FetchType.LAZY)
	 private List<GroupMembersEntity> listGroupMembers;
	 
	public UserInfoEntity(long userID, String fullName) {
	
		this.userID = userID;
		this.fullName = fullName;
	}

	 //@OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
	 //private ImageEntity image;
	 
	 
	
}