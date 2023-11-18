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
@Table(name = "Comments")
public class CommentsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commentid", columnDefinition = "BIGINT")
	private long commentidID;
	
	@Column(name = "content", columnDefinition = "text")
	private long content;
	
	@ManyToOne
    @JoinColumn(name = "postid")
    private PostEntity postCommnent;
	
	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity userComment;
	
	@Column(name = "commentdate", columnDefinition = "DATE")
	private Date commentDate;
}
