package vn.hcmute.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "UserInfo")
public class UserInfoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 
	 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	 private List<PostEntity> listPosts;
	 
	 @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
	 private List<GroupEntity> listGroups;
	 
	 @OneToMany(mappedBy = "userLike", fetch = FetchType.LAZY)
	 private List<LikeEntity> lisLikes;
	 
	 @OneToMany(mappedBy = "userComment", fetch = FetchType.LAZY)
	 private List<CommentsEntity> listComments;
	 
	 @OneToMany(mappedBy = "userMember", fetch = FetchType.LAZY)
	 private List<GroupMembersEntity> listGroupMembers;
}
