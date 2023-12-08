package vn.hcmute.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.RandomStringUtils;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.websocket.server.PathParam;
import lombok.experimental.PackagePrivate;
import vn.hcmute.entities.ResetPasswordEntity;
import vn.hcmute.entities.StatusAccountEntity;
import vn.hcmute.entities.UserEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.EmailInfo;
import vn.hcmute.model.UserAcountModel;
import vn.hcmute.model.UserInfoModel;
import vn.hcmute.model.updatePassModel;
import vn.hcmute.service.IMailService;
import vn.hcmute.service.IUserInfoService;
import vn.hcmute.service.IUserService;
import vn.hcmute.service.MailService;
import vn.hcmute.service.UserServiceImple;
@Controller
public class UserController {
	@Autowired
	UserAcountModel u;
	@Autowired
	IUserService user_service;
	@Autowired
	IUserInfoService user_info_service;
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	IMailService imail;
	// lưu trữ cookie cho đăng nhập đợt sau

	@GetMapping("/login")
	public String Showlogin(ModelMap model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("email")) {
					model.addAttribute("emailcookie", c.getValue());
				} else if (c.getName().equals("pass")) {
					model.addAttribute("passcookie", c.getValue());
				}
			}
		}

		return "login";
	}

	/*@GetMapping("/login/oauth2/code/google")
	public String User(OAuth2AuthenticationToken oAuth2AuthenticationToken, HttpSession session) {
		System.out.print(toUser(oAuth2AuthenticationToken.getPrincipal().getAttributes()));
		UserEntity user_entity = new UserEntity();
		UserInfoEntity user_info = new UserInfoEntity();
		StatusAccountEntity status = new StatusAccountEntity();
		user_entity.setEmail(toUser(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getEmail());
		user_entity.setUserName(toUser(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getGiven_name());
		user_info.setFullName(toUser(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getName());
		user_entity.setPass("");
		Optional<UserEntity> ue = user_service.findByemailContaining(user_entity.getEmail());
		if (ue.isEmpty()) {
			user_info_service.save(user_info);
			user_entity.setUserInfo(user_info);
			status.setUserCode(user_entity);
			status.setStatus(true);
			status.setCode(0);
			user_service.save(user_entity);
			user_service.save(status);
			session.setAttribute("username", user_service
					.findByemailContaining(toUser(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getEmail())
					.get().getIdAccount());
			return "user";
		} else {
			session.setAttribute("username",
					user_service.findByemailContaining(ue.get().getEmail()).get().getIdAccount());
			return "user";
		}
}
	public LoginGoogleModel toUser(Map<String, Object> map) {
		LoginGoogleModel loginModel = new LoginGoogleModel();
		if (map == null)
			return null;
		else {
			loginModel.setEmail((String) map.get("email"));
			loginModel.setName((String) map.get("name"));
			loginModel.setGiven_name((String) map.get("given_name"));
		}
		return loginModel;
	}*/

	// kiểm tra email pass và status kích hoạt
	@PostMapping("/checklogin")
	public String CheckLoginn(HttpServletResponse response, ModelMap model, @RequestParam("email") String Email,
			@RequestParam("password") String pass, HttpSession session, @ModelAttribute UserAcountModel user_account) {
		Optional<UserEntity> user = user_service.findByemailContaining(Email);
		Optional<StatusAccountEntity> status = user_service.findByuserCode(user.get());
		if (user_service.checkLogin(Email, pass) && status.get().getStatus() == true) {
			System.out.println(user_account.getEmail());
			Cookie name = new Cookie("email", Email);
			Cookie password = new Cookie("pass", pass);
			name.setMaxAge(60);
			// System.out.println(user_account.isRemember());
			if (user_account.isRemember() == true)
				password.setMaxAge(10);
			else
				password.setMaxAge(0);
			response.addCookie(name);
			response.addCookie(password);
			session.setAttribute("username", user_service.findByemailContaining(Email).get().getIdAccount());
			session.setAttribute("email", user_service.findByemailContaining(Email));
			return "home";
		} else if (user_service.checkLogin(Email, pass) && status.get().getStatus() == false) {
			session.setAttribute("email", Email);
			return "redirect:login?false";
		}
		return "redirect:login?wrong";
	}

	@GetMapping("/logout")
	public ModelAndView Logout(HttpSession session, ModelMap model) {
		session.removeAttribute("username");
		return new ModelAndView("redirect:/login", model);
	}

	@GetMapping("/registerOrFail")
	public String Register() {

		return "Register";
	}

	@PostMapping("/registerOrFail")
	public String Show(ModelMap model, @ModelAttribute UserAcountModel user_account,
			@ModelAttribute UserInfoEntity user_info, HttpSession session) {
		// user_account.setUserInfo(user_info);
		UserEntity user_entity = new UserEntity();
		StatusAccountEntity status = new StatusAccountEntity();
		Optional<UserEntity> ua = user_service.findByemailContaining(user_account.getEmail());
		Optional<UserInfoEntity> ui = user_info_service.findByphoneNumberContaining(user_info.getPhoneNumber());
		if (ua.isPresent())
			return "redirect:/registerOrFail?emailexist";
		else if (ui.isPresent())
			// user_service.save(user_account);
			return "redirect:/registerOrFail?phone";
		else if (!user_account.getPass().equals(user_account.getCheckPass()))
			return "redirect:/registerOrFail?pass";
		else {
			BeanUtils.copyProperties(user_account, user_entity);
			user_info_service.save(user_info);
			user_entity.setUserInfo(user_info);
			user_service.save(user_entity);
			status.setStatus(false);
			status.setUserCode(user_entity);// khởi tạo status dưới sql
			user_service.save(status);
			session.setAttribute("email", user_account.getEmail()); // lưu session email để tiến hành gửi code về email
			return "redirect:/verify";
		}

	}


	@GetMapping("/verify")
	public String show(HttpSession session) // nhận emal và gửi code
	{
		String email = (String) session.getAttribute("email");
		System.out.println("Email: -----------------------------------------------------------------------------"+email);
		Optional<UserEntity> user = user_service.findByemailContaining(email);
		System.out.print(user.get().getEmail());
		Optional<StatusAccountEntity> account_status = user_service.findByuserCode(user.get());
		if (user.isPresent()) { //&& account_status.get().getStatus() == false) {
			System.out.print(user.get().getIdAccount());
			System.out.print(account_status.get().getId());
			System.out.print(user.get().getEmail());
			int code = Integer.parseInt(RandomStringUtils.randomNumeric(6));
			user_service.createCode(account_status.get(), code);// tạo code cập nhật code cũ
			try {
				imail.constructCreateCode(code, user.get());
				System.out.print("da in");

			} catch (Exception e) {
				return "redirect:forgot?no";
			}
		} else
			return "redirect:forgot?wrong";
		return "verify";
	}
	@GetMapping("/forgot")
	public String showFormMail(ModelMap model) {
		model.addAttribute("mail", new EmailInfo());
		return "sendMail";
	}

	@PostMapping("/sended")
	public String Send(HttpServletRequest request, ModelMap model, @ModelAttribute("mail") EmailInfo emailInfo) {
		/*
		 * try { Optional<UserEntity> user =
		 * user_service.findByemailContaining(emailInfo.getTo());
		 * imail.send(null,null,user.get()); } catch (Exception e) { return "wrong"; }
		 * return "Correct";
		 */
		Optional<UserEntity> user = user_service.findByemailContaining(emailInfo.getTo());
		if (user.isPresent()) {
			String token = UUID.randomUUID().toString();
			Optional<ResetPasswordEntity> resetPass = user_service.findByUserResetPass(user.get());
			if (!resetPass.isEmpty()) {
				user_service.deleteById(resetPass.get().getId());
				System.out.print("da xoa");
			}
			user_service.createToken(user.get(), token);
			try {
				imail.constructResetTokenEmail(getAppUrl(request), token, user.get());
				System.out.print(user.get().getEmail());
				System.out.print(token);
				System.out.print(getAppUrl(request));

			} catch (Exception e) {
				return "redirect:forgot?no";
			}
		} else
			return "redirect:forgot?wrong";
		return "redirect:forgot?yes";
	}

	private String getAppUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
	}

	@GetMapping("/user/changePassword")
	public String showChangePasswordPage(ModelMap model, @RequestParam(name = "token") String token1) {
		String result = user_service.validToken(token1);
		System.out.print(token1);
		if (result != null)
			return "login";
		else {
			model.addAttribute("tokenvalue", token1);
			model.addAttribute("pass", new updatePassModel());
			return "updatePass";
		}
	}

	/*
	 * @GetMapping("updatePass") public String Show(ModelMap model) {
	 * model.addAttribute("pass", new updatePassModel()); return "updatePass"; }
	 */
	@PostMapping("user/updatePassword")
	public String savePass(@ModelAttribute("pass") updatePassModel pass, HttpServletResponse response) {
		String result = user_service.validToken(pass.getToken());
		if (result != null)
			return "redirect:updatePassword?fail";
		Cookie name = new Cookie("token", pass.getToken());
		name.setMaxAge(60);
		response.addCookie(name);
		UserEntity user = user_service.findByToken(pass.getToken()).getUserResetPass();
		if (user != null) {
			System.out.print(user);
			if (pass.getNewPass().equals(pass.getConfirmPass())) {
				user_service.changePass(user, pass.getConfirmPass());
				return "redirect:updatePassword?success";
			} else
				return "redirect:updatePassword?wrong";
		} else
			return "redirect:updatePassword?noexist";
	}

	@GetMapping("user/updatePassword")
	public String show(HttpServletRequest request, ModelMap model) {
		Cookie[] c = request.getCookies();
		if (c != null) {
			for (Cookie cook : c) {
				if (cook.getName().equals("token")) {
					model.addAttribute("tokenvalue", cook.getValue());
				}
			}
		}
		return "updatePass";
	}

	@PostMapping("/verifyCode")
	public String verify(HttpServletRequest request, ModelMap model,
			@ModelAttribute("status") StatusAccountEntity status) {
		/*
		 * try { Optional<UserEntity> user =
		 * user_service.findByemailContaining(emailInfo.getTo());
		 * imail.send(null,null,user.get()); } catch (Exception e) { return "wrong"; }
		 * return "Correct";
		 */
		Optional<StatusAccountEntity> status_check = user_service.findBycode(status.getCode());
		if (status_check.isPresent()) {
			status_check.get().setStatus(true);
			user_service.save(status_check.get());
			System.out.print(status_check.get().getStatus());
			return "redirect:/verifyCode?success";
		} else
			return "redirect:/verifyCode?fail";
	}

	@GetMapping("/verifyCode")
	public String show() {
		return "verify";
	}

	@GetMapping("/again")
	public String showAgain() {
		return "verify";
	}

}
