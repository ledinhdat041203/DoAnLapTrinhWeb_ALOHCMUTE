package vn.hcmute.entities;

import java.sql.Date;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "LikePost")
public class LikeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "likeid", columnDefinition = "BIGINT")
	private long likeID;
	
	@ManyToOne
    @JoinColumn(name = "postid")
    private PostEntity post;
	@ManyToOne
    @JoinColumn(name = "userid")
    private UserInfoEntity userLike;
	
	@Column(name = "status", columnDefinition = "bit")

	private boolean status;
	@Column(name = "likedate", columnDefinition = "DATETIME")
	private Date likeDate;
	
}
