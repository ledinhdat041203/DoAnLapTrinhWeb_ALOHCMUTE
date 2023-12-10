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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.hcmute.entities.GroupEntity;
import vn.hcmute.entities.GroupMembersEntity;
import vn.hcmute.entities.PostEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.PostModel;
import vn.hcmute.service.ICommentService;
import vn.hcmute.service.IGroupMemberService;
import vn.hcmute.service.IGroupService;
import vn.hcmute.service.ILikeService;
import vn.hcmute.service.IPostService;
import vn.hcmute.service.IUserInfoService;

@Controller
public class groupController {
	@Autowired
	IGroupService groupService;

	@Autowired
	IGroupMemberService groupMemberService;

	@Autowired
	IUserInfoService userInfo;

	@Autowired
	IPostService postService;
	@Autowired
	ICommentService commentService;
	@Autowired
	ILikeService likeService;

	@GetMapping("listgroup")
	public String findAll(ModelMap model) {
		model.addAttribute("list", groupService.findAll());
		return "listGroup";
	}

	@GetMapping("createGroup")
	public String createGroup(Model model) {
		GroupEntity group = new GroupEntity();
		model.addAttribute("group", group);
		return "createGroup";
	}

	@PostMapping("saveGroup")
	public String saveGroup(@ModelAttribute("group") GroupEntity group, HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		UserInfoEntity current_user = userInfo.findById(userid).get();
		group.setAdmin(current_user);
		group.setCreateDate(new Date(System.currentTimeMillis()));
		groupService.save(group);

		GroupMembersEntity groupMember = new GroupMembersEntity();
		groupMember.setGroup(group);
		groupMember.setUserMember(current_user);
		groupMember.setJoinDate(new Date(System.currentTimeMillis()));
		groupMemberService.save(groupMember);
		return "redirect:/listgroup";
	}

	@GetMapping("modifyNameGroup/{groupID}")
	public String modifyNameGroup(Model model, @PathVariable("groupID") Long groupID) {
		GroupEntity group = groupService.findById(groupID).get();
		model.addAttribute("group", group);
		return "modifyNameGroup";
	}

	@GetMapping("modifyAvataGroup")
	public String modifyAvataGroup(Model model) {
		model.addAttribute("group", new GroupEntity());
		return "modifyAvataGroup";
	}

	@GetMapping("modifyDescriptionGroup")
	public String modifyDescriptionGroup(Model model) {
		model.addAttribute("group", new GroupEntity());
		return "modifyDescriptionGroup";
	}

	@GetMapping("modifyGroup/{groupID}")
	public String modifyGroup(Model model, @PathVariable("groupID") Long groupID) {
		GroupEntity group = groupService.findById(groupID).get();
		model.addAttribute("group", group);
		return "modifyGroup";
	}

	@PostMapping("modifyGroup")
	public String modifyGroup(@ModelAttribute("group") GroupEntity group, HttpSession session, Model model) {
		GroupEntity groupOld = groupService.findById(group.getGroupID()).get();
		Long userid = (long) session.getAttribute("userInfoID");
		if (checkIsAdminGroup(group.getGroupID(), userid) == 1) {
			groupOld.setGroupName(group.getGroupName());
			groupOld.setAvataGroup(group.getAvataGroup());
			groupOld.setDescription(group.getDescription());
			groupService.save(groupOld);
			model.addAttribute("message", "Sửa thành công");
		} else {
			model.addAttribute("message", "Bạn không là quản trị viên!");
		}
		return "redirect:/group/" + group.getGroupID();
	}


	@GetMapping("group/{groupID}")
	public String GroupDetail(ModelMap model, @PathVariable long groupID, HttpSession session, ModelMap post,
			Model listpost) {
		Long userid = (long) session.getAttribute("userInfoID");
		UserInfoEntity user = userInfo.findById(userid).get();
		GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(userid, groupID);
		if (groupMember != null) {
			GroupEntity group = groupService.findById(groupID).get();
			model.addAttribute("group", group);
			post.addAttribute("post", new PostEntity());
			List<GroupEntity> listgroup = groupService.findGroupsByUserId(userid);
			model.addAttribute("listgroup", listgroup);
			model.addAttribute("userInfo", user);

			List<PostModel> posts = postService.getPostsByGroupId(groupID, 0, 2, userid);
			listpost.addAttribute("list", posts);
			return "Group";
		} else {
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
		String result = String.format("<h2>Vô nhóm rồi</h2>" + "<a href=\"/group/%s\" >Xem nhóm</a>", groupID);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/group/remove_member/{groupID}/{userID}")
	public String removeMember(@PathVariable long groupID, HttpSession session, @PathVariable long userID,
			Model model) {
		Long current_userid = (long) session.getAttribute("userInfoID");
		int isAdmin = checkIsAdminGroup(groupID, current_userid);
		System.out.println(isAdmin);
		if (userID == current_userid) {
			if (isAdmin == 1) {
				return "redirect:/group/list_member/" + groupID;
			} else {
				GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(userID,
						groupID);
				groupMemberService.delete(groupMember);
				return "redirect:/listgroup";
			}
		} else {
			if (isAdmin == 1) {
				GroupMembersEntity groupMember = groupMemberService.findByUserMemberUserIDAndGroupGroupID(userID,
						groupID);
				groupMemberService.delete(groupMember);
				String message = "Đã xóa khỏi nhóm!";
				model.addAttribute("message", message);
				System.out.println("Xoa roi ma");
				return "redirect:/group/list_member/" + groupID;
			} else {
				return "redirect:/group/list_member/" + groupID;
			}
		}

	}

	@GetMapping("/reload")
	public String reloadPage() {
		return "redirect:/";
	}

	@GetMapping("/group/list_member/{groupID}")
	public String listMember(@PathVariable Long groupID, Model model, HttpSession session) {

		List<GroupMembersEntity> listGroupMember = groupMemberService.findByGroupGroupID(groupID);
		List<UserInfoEntity> listMember = new ArrayList<UserInfoEntity>();
		for (GroupMembersEntity groupMember : listGroupMember) {
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

	@GetMapping("/listpost/postgroup/{page}")
	public String getPostsByGroupId(@PathVariable int page, @RequestParam(defaultValue = "1") int groupID, Model model,
			HttpSession session) {
		Long userid = (long) session.getAttribute("userInfoID");
		List<PostModel> posts = postService.getPostsByGroupId(groupID, page, 2, userid);
		System.out.println(page);
		model.addAttribute("list", posts);
		model.addAttribute("fragment", "post_template");
		return "Group :: #listpost";

	}

	@GetMapping("/deleteGroup/{groupID}")
	@Transactional
	public String deleteGroup(@PathVariable("groupID") Long groupID, HttpSession session, Model model) {
		Long userid = (long) session.getAttribute("userInfoID");
		if (checkIsAdminGroup(groupID, userid) == 1) {
			// Xóa bài viết khỏi nhóm
			List<PostEntity> listPost = postService.findByGroupPostGroupID(groupID);
			for (PostEntity post : listPost) {
				if (postService.existsById(post.getPostID())) {
					commentService.deleteAllByPostId(post.getPostID());
					likeService.deleteAllByPostPostId(post.getPostID());
					postService.deleteById(post.getPostID());
				}
			}
			// Xóa thành viên của nhóm
			groupMemberService.deleteByGroupGroupID(groupID);

			// Xóa nhóm
			groupService.deleteById(groupID);
			model.addAttribute("message", "Đã xóa nhóm!");
		} else {
			model.addAttribute("message", "Bạn đang vi phạm điều khoản");
		}
		model.addAttribute("list", groupService.findAll());
		return "listgroup";
	}

}
