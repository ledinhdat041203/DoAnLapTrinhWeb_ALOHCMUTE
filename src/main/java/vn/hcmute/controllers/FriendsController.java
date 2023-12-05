package vn.hcmute.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import vn.hcmute.entities.FriendsEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.services.IFriendsService;
import vn.hcmute.services.IUserInfoService;
import vn.hcmute.services.IUserService;

@Controller
@RequestMapping("user/friends")
public class FriendsController {
	@Autowired
	IFriendsService friendsService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IUserService userService;
	long idUsercurrent = (long) 1; //hard code
	
	// Xuất danh sách những user mà current user đang theo dõi
    @GetMapping("/following-list")
    public String list(ModelMap model) {
    	
        List<FriendsEntity> listFollowing = friendsService.findByuser1userID(idUsercurrent);
        List<UserInfoEntity> listUser2 = new ArrayList<>();
        
        for (FriendsEntity following : listFollowing)
        {
        	if (following.isStatus())
        	{
        		UserInfoEntity user2 = following.getUser2();
        		listUser2.add(user2);
        	}
        }
        
        model.addAttribute("listUser2", listUser2);
        return "friends/following-list";
    }
    
    
    // Xử lý theo dõi
    @GetMapping("/follow/{user2Id}")
    public ResponseEntity<String> befriendUsers(@PathVariable long user2Id) {
		System.out.println(user2Id);
        FriendsEntity friends = friendsService.findByUser1IDAndUser2ID(idUsercurrent, user2Id);

        if (friends != null) {
        	if (friends.isStatus())
        	{
        		friends.setStatus(false);
        	} else
        	{
        		friends.setStatus(true);
        	}
        		
        	friendsService.save(friends);
        } else {
        	UserInfoEntity user1 = userInfoService.findById(idUsercurrent).get();
        	UserInfoEntity user2 = userInfoService.findById(user2Id).get();
        	
        	friendsService.createFriendsByUser1AndUser2(user1, user2, true);

        }
        return ResponseEntity.ok("yes");
    }
    
	
	
	// Đề xuất theo dõi
	@GetMapping("/suggest-follow-up")
	public String suggestFollowUp(ModelMap model) {
	    Random random = new Random();
	    List<UserInfoEntity> userList = new ArrayList<>();
	    long sizeList = 20; // cần kiểm tra hệ thống có đủ bạn cho usercurrent hay không
	    
	    // Kiểm tra xem hệ thống có còn đủ 20 user để đề xuất hay không
	    long numberUser = userService.count();
	    long numberFollowing = 0;
	    List<FriendsEntity> listFollowing = friendsService.findByuser1userID(idUsercurrent);
	    for (FriendsEntity following : listFollowing)
        {
        	if (following.isStatus())
        		numberFollowing += 1;
        }
	    if ((numberUser - numberFollowing - 1) < sizeList)
	    	sizeList = (numberUser - numberFollowing - 1);
	    
	    List<Long> listuserid = new ArrayList<>();
	    for (long i = 0; i < sizeList; ) {
	        long randomId = random.nextLong(5) + 1;
	        if (randomId != idUsercurrent) {
	            FriendsEntity friend = friendsService.findByUser1IDAndUser2ID(idUsercurrent, randomId);
	            boolean shouldAdd = false;

	            if (friend == null) {
	                shouldAdd = userInfoService.findById(randomId).isPresent() && !listuserid.contains(randomId);
	            } else {
	                shouldAdd = !friend.isStatus() && !listuserid.contains(randomId);
	            }

	            if (shouldAdd) {
	                listuserid.add(randomId);
	                i++;
	            }
	        }
	    }

	    
	    for (long userid : listuserid) {
	        UserInfoEntity user = userInfoService.findById(userid).get();	
	        userList.add(user);
	    }

	    model.addAttribute("userList", userList);
	    return "friends/suggest-follow-up";
	}

}
