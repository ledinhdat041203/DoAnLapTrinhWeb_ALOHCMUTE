package vn.hcmute.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.GroupMembersEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.service.IGroupMemberService;
import vn.hcmute.service.IGroupService;


@Controller
public class groupController {
	@Autowired
	IGroupService groupService;
	
	@Autowired
	IGroupMemberService groupMemberService;
	
	
	UserInfoEntity user = new UserInfoEntity(3,"Lê Đình Đạt");
	
	@GetMapping("listgroup")
	public String findAll(ModelMap model)
	{
		model.addAttribute("list", groupService.findAll());
		return "listGroup";
	}
	
	@GetMapping("group/{groupID}")
	public String GroupDetail(ModelMap model, @PathVariable long groupID)
	{
		GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(user.getUserID(), groupID);
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
	public ResponseEntity<String> addToGroup(@PathVariable long groupID) {
		GroupEntity group = new GroupEntity();
		
		
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
	
	@GetMapping("/reload")
    public String reloadPage() {
        // Chuyển hướng về trang hiện tại
        return "redirect:/";
    }

}
