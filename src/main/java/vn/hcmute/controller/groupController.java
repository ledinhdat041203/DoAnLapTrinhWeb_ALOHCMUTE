package vn.hcmute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hcmute.Entity.GroupEntity;
import vn.hcmute.Entity.UserInfoEntity;
import vn.hcmute.Repository.GroupMemberRepository;
import vn.hcmute.Service.IGroupMemberService;
import vn.hcmute.Service.IGroupService;

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
		if (groupMemberService.findByUserMemberUserIDAndGroupGroupID(groupID, user.getUserID()) != null) {
			GroupEntity group = groupService.findById(groupID).get();
			model.addAttribute("group", group);
			return "Group";
		}
		else {
			return "JoinGroup";
		}
	}

}
