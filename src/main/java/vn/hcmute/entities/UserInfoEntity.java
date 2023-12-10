package vn.hcmute.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserInfo")
public class UserInfoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", columnDefinition = "BIGINT")
	private Long userID;
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

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<NotificationEntity> userNotify;

	public UserInfoEntity(long userID, String fullName) {

		this.userID = userID;
		this.fullName = fullName;
	}

	public UserInfoEntity(Long userID, String fullName, Date dateOfBirth, String avata, String address,
			String phoneNumber, UserEntity userAccount, List<MessagesEntity> messageSender,
			List<MessagesEntity> messageReceiver, List<FriendsEntity> listFriend1, List<FriendsEntity> listFriend2,
			List<GroupEntity> listGroups, List<GroupMembersEntity> listGroupMembers,
			List<NotificationEntity> userNotify) {
		this.userID = userID;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.avata = avata;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.userAccount = userAccount;
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
		this.listFriend1 = listFriend1;
		this.listFriend2 = listFriend2;
		this.listGroups = listGroups;
		this.listGroupMembers = listGroupMembers;
		this.userNotify = userNotify;
	}

	public UserInfoEntity() {
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public UserInfoEntity(Long userID, String fullName, Date dateOfBirth, String avata, String address,
			String phoneNumber, UserEntity userAccount, List<MessagesEntity> messageSender,
			List<MessagesEntity> messageReceiver, List<FriendsEntity> listFriend1, List<FriendsEntity> listFriend2,
			List<GroupEntity> listGroups, List<GroupMembersEntity> listGroupMembers) {
		this.userID = userID;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.avata = avata;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.userAccount = userAccount;
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
		this.listFriend1 = listFriend1;
		this.listFriend2 = listFriend2;
		this.listGroups = listGroups;
		this.listGroupMembers = listGroupMembers;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAvata() {
		return avata;
	}

	public void setAvata(String avata) {
		this.avata = avata;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserEntity getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserEntity userAccount) {
		this.userAccount = userAccount;
	}

	public List<MessagesEntity> getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(List<MessagesEntity> messageSender) {
		this.messageSender = messageSender;
	}

	public List<MessagesEntity> getMessageReceiver() {
		return messageReceiver;
	}

	public void setMessageReceiver(List<MessagesEntity> messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public List<FriendsEntity> getListFriend1() {
		return listFriend1;
	}

	public void setListFriend1(List<FriendsEntity> listFriend1) {
		this.listFriend1 = listFriend1;
	}

	public List<FriendsEntity> getListFriend2() {
		return listFriend2;
	}

	public void setListFriend2(List<FriendsEntity> listFriend2) {
		this.listFriend2 = listFriend2;
	}

	public List<GroupEntity> getListGroups() {
		return listGroups;
	}

	public void setListGroups(List<GroupEntity> listGroups) {
		this.listGroups = listGroups;
	}

	public List<GroupMembersEntity> getListGroupMembers() {
		return listGroupMembers;
	}

	public void setListGroupMembers(List<GroupMembersEntity> listGroupMembers) {
		this.listGroupMembers = listGroupMembers;
	}


	public List<NotificationEntity> getUserNotify() {
		return userNotify;
	}

	public void setUserNotify(List<NotificationEntity> userNotify) {
		this.userNotify = userNotify;
	}
	
}

