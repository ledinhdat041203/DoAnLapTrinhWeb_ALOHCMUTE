package vn.hcmute.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.GroupMembersEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.service.IGroupMemberService;
import vn.hcmute.service.IGroupService;
import vn.hcmute.service.IUserInfoService;


@Controller
public class groupController {
	@Autowired
	IGroupService groupService;
	
	@Autowired
	IGroupMemberService groupMemberService;
	
	@Autowired
	IUserInfoService userInfo;
	
	@GetMapping("listgroup")
	public String findAll(ModelMap model)
	{
		model.addAttribute("list", groupService.findAll());
		return "listGroup";
	}
	
	@GetMapping("group/{groupID}")
	public String GroupDetail(ModelMap model, @PathVariable long groupID, HttpSession session)
	{
		Long userid = (long) session.getAttribute("userInfoID");
		GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(userid, groupID);
		if ( groupMember != null) {
			GroupEntity group = groupService.findById(groupID).get();
			model.addAttribute("group", group);
			return "Group";
		}
		else {
			model.addAttribute("groupID", groupID);
			return "JoinGroup";
		}
	}
	
	@PostMapping("/addToGroup/{groupID}")
	public ResponseEntity<String> addToGroup(@PathVariable long groupID, HttpSession session) {
		GroupEntity group = new GroupEntity();
		Long userid = (long) session.getAttribute("userInfoID");
		UserInfoEntity user = userInfo.findById(userid).get();
		group.setGroupID(groupID);
		
		
		GroupMembersEntity groupMember = new GroupMembersEntity();
		groupMember.setGroup(group);
		groupMember.setUserMember(user);
		groupMember.setJoinDate(new Date(System.currentTimeMillis()));
		
		groupMemberService.save(groupMember);
		String result = String.format("<h2>Vô nhóm rồi</h2>"
				+ "<a href=\"/group/%s\" >Xem nhóm</a>", groupID);
		return ResponseEntity.ok(result);
	}
	
	
	
	@GetMapping("/group/remove_member/{groupID}/{userID}")
	public String removeMember(@PathVariable long groupID, HttpSession session, @PathVariable long userID, Model model) {
		Long current_userid = (long) session.getAttribute("userInfoID");
		int isAdmin = checkIsAdminGroup(groupID, current_userid);
		System.out.println(isAdmin);
		if(userID == current_userid) {
			if(isAdmin == 1) {
				return "redirect:/group/list_member/"+groupID;
			}
			else {
				GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(userID, groupID);
				groupMemberService.delete(groupMember);
				return "redirect:/listgroup";
			}
		}
		else
		{
			if(isAdmin == 1) {
				GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(userID, groupID);
				groupMemberService.delete(groupMember);
				String message = "Đã xóa khỏi nhóm!";
				model.addAttribute("message", message);
				System.out.println("Xoa roi ma");
				return "redirect:/group/list_member/"+groupID;
			}
			else {
				return "redirect:/group/list_member/"+groupID;
			}
		}
		
	}	
	
	@GetMapping("/reload")
    public String reloadPage() {
        return "redirect:/";
    }
	
	
	
	@GetMapping("/group/list_member/{groupID}")
	public String listMember(@PathVariable Long groupID, Model model, HttpSession session)
	{
		
		List<GroupMembersEntity> listGroupMember = groupMemberService.findByGroupGroupID(groupID);
		List<UserInfoEntity> listMember = new ArrayList<UserInfoEntity>();
		for(GroupMembersEntity groupMember : listGroupMember) {
			UserInfoEntity user = groupMember.getUserMember();
			listMember.add(user);
		}
		Long current_userid = (long) session.getAttribute("userInfoID");
		GroupEntity group = groupService.findById(groupID).get();
		
		int isAdmin = checkIsAdminGroup(groupID, current_userid);

		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("group", group);
		model.addAttribute("listMember", listMember);
		return "listMember";
	}
	
	public int checkIsAdminGroup(Long groupID, Long userID) {
	    Optional<GroupEntity> optionalGroup = groupService.findById(groupID);
	    // Check if the optionalGroup has a value
	    if (optionalGroup.isPresent()) {
	        GroupEntity group = optionalGroup.get();
	        // Check if the admin is present
	        if (group.getAdmin() != null && group.getAdmin().getUserID() == userID) {
	            return 1; 
	        }
	    }
	    return 0;
	}

	

}
