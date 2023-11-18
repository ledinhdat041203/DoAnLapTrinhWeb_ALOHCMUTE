package vn.hcmute.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "GroupMembers")
public class GroupMembersEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupmemberid", columnDefinition = "BIGINT")
	private long groupmemberID;
	
	@ManyToOne
    @JoinColumn(name = "groupid")
    private GroupEntity group;
	
	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity userMember;
	
	@Column(name = "joindate", columnDefinition = "DATE")
	private Date joinDate;
}
