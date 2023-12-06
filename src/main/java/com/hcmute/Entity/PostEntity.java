package com.hcmute.Entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Post")
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postid", columnDefinition = "BIGINT")
	private long postID;
	
	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity user;
	
	@ManyToOne
    @JoinColumn(name = "groupid")
    private GroupEntity groupPost;
	
	@Column(name = "content", columnDefinition = "NTEXT")
	private String content;
	@Column(name = "image", columnDefinition = "nvarchar(255)")
	private String image;
	@Column(name = "postdate", columnDefinition = "DATE")
	private Date postDate;
	
	 @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	 private List<LikeEntity> listLikes;
	 
	 @JsonManagedReference
	 @OneToMany(mappedBy = "postCommnent")
	 private List<CommentEntity> comments;
	
	
}
