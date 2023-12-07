package vn.hcmute.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Friends")
public class FriendsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendid", columnDefinition = "BIGINT")
	private long friendID;

	
	@ManyToOne
    @JoinColumn(name = "user1")
    private UserInfoEntity user1;
	
	@ManyToOne
    @JoinColumn(name = "user2")
    private UserInfoEntity user2;
	
	@Column(name = "friendshipdate", columnDefinition = "DATE")
	private java.util.Date friendshipDate;
	
	// Bao gồm các trạng thái đã follow hoặc unfollow
	@Column(name = "status", columnDefinition = "bit")
	private boolean status;
}
