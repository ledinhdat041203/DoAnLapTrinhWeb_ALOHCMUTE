package vn.hcmute.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Groups")
public class GroupEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupid", columnDefinition = "BIGINT")
	private long groupID;
	@Column(name = "groupname", columnDefinition = "nvarchar(255)")
	private String groupName;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "admin")
    private UserInfoEntity admin;
	
	@Column(name = "createdate", columnDefinition = "DATE")
	private Date createDate;
	
	 @OneToMany(mappedBy = "groupPost", fetch = FetchType.LAZY)
	 private List<PostEntity> listPosts;
	 
	 @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	 private List<GroupMembersEntity> listMembers;

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserInfoEntity getAdmin() {
		return admin;
	}

	public void setAdmin(UserInfoEntity admin) {
		this.admin = admin;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<PostEntity> getListPosts() {
		return listPosts;
	}

	public void setListPosts(List<PostEntity> listPosts) {
		this.listPosts = listPosts;
	}

	public List<GroupMembersEntity> getListMembers() {
		return listMembers;
	}

	public void setListMembers(List<GroupMembersEntity> listMembers) {
		this.listMembers = listMembers;
	}

	public GroupEntity(long groupID, String groupName, String description, UserInfoEntity admin, Date createDate,
			List<PostEntity> listPosts, List<GroupMembersEntity> listMembers) {
		super();
		this.groupID = groupID;
		this.groupName = groupName;
		this.description = description;
		this.admin = admin;
		this.createDate = createDate;
		this.listPosts = listPosts;
		this.listMembers = listMembers;
	}

	public GroupEntity() {
		super();
	}
	
	
}
