package com.hcmute.Entity;

import java.sql.Date;

import jakarta.persistence.*;
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
	private Date friendshipDate;
}
