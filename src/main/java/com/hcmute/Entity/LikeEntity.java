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
    private int status;

    @Column(name = "likedate")
    private Date likeDate;

    public boolean isStatus() {
        return status == 1; // Trạng thái là 1 nếu đã thích, ngược lại là 0
    }

    public void setStatus(boolean newStatus) {
        this.status = newStatus ? 1 : 0; // Nếu newStatus là true, status được đặt là 1, ngược lại là 0

        if (newStatus) {
            this.likeDate = new Date(System.currentTimeMillis()); // Đặt ngày khi thích
        } else {
            this.likeDate = null; // Hoặc set ngày về null nếu muốn
        }
    }
}
