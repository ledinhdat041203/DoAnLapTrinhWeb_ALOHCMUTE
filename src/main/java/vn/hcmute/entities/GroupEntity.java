package vn.hcmute.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	
}
