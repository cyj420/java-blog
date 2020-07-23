package com.sbs.java.blog.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;

public class MemberController extends Controller {
	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		// 회원가입
		case "join":
			return doActionJoin(req, resp);
		case "doJoin":
			return doActionDoJoin(req, resp);
		// 로그인
		case "login":
			return doActionLogin(req, resp);
		case "doLogin":
			return doActionDoLogin(req, resp);
		// 로그아웃
		case "logout":
			return doActionLogout(req, resp);
		// 사용자 페이지
		case "myPage":
			return doActionMyPage(req, resp);
		case "myPageModify":
			return doActionMyPageModify(req,resp);
		case "doMyPage":
			return doActionDoMyPage(req, resp);
		case "authMail":
			return doActionAuthMail(req, resp);
		case "doAuthMail":
			return doActionDoAuthMail(req, resp);
		// 비번 변경하기
		case "findPw":
			return doActionFindPw(req, resp);
		case "doFindPw":
			return doActionDoFindPw(req, resp);
		// 아이디 찾기
		case "findId":
			return doActionFindId(req, resp);
		case "doFindId":
			return doActionDoFindId(req, resp);
		}
		return "";
	}

	private String doActionDoFindId(HttpServletRequest req, HttpServletResponse resp) {
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		String loginId = memberService.findLoginId(name, email);

		if (loginId == null) {
			return "html:<script> alert('정보가 일치하는 계정이 존재하지 않습니다.'); history.back(); </script>";
		} else {
			return "html:<script> alert('" + name + "님의 ID는 [" + loginId
					+ "] 입니다.'); location.replace('../home/main'); </script>";
		}
	}

	private String doActionFindId(HttpServletRequest req, HttpServletResponse resp) {
		return "member/findId.jsp";
	}

	private String doActionDoFindPw(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		int id = memberService.findPw(loginId, name, email);

		if (id == 0) {
			return "html:<script> alert('정보가 일치하는 계정이 존재하지 않습니다.'); history.back(); </script>";
		} else {
			// 비번 재설정 필요
			String newPw = null;
			try {
				newPw = memberService.resetPw(id);
			} catch (NoSuchAlgorithmException e) {
			}
			sendingEmail("cho04 blog - 비밀번호 변경", "새로운 비밀번호는 [" + newPw + "] 입니다.", email);
			return "html:<script> alert('임시 비밀번호가 이메일로 발송되었습니다.'); location.replace('../home/main'); </script>";
		}
	}

	private String doActionFindPw(HttpServletRequest req, HttpServletResponse resp) {
		return "member/findPw.jsp";
	}
	
	private String doActionDoAuthMail(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("MemberController-doActionDoAuthMail");
		System.out.println("인증받기 - extra_code : "+Util.extra_code);
		if(Util.extra_code.trim().length()!=0) {
			System.out.println("===1===");
			String str = memberService.transformString(""+memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getId());
			System.out.println("===2===");
			if(str.equals(Util.extra_code)) {
				System.out.println("===3===");
				memberService.doAuthMail((int)session.getAttribute("loginedMemberId"));
				return "html:<script> alert('인증이 되었습니다.'); location.replace('../home/main'); </script>";
			}
			System.out.println("===4===");
		}
		return "html:<script> alert('인증 실패'); location.replace('../home/main'); </script>";
	}
	
	private String doActionAuthMail(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("MemberController-doActionAuthMail");
		String email = req.getParameter("email");
		String id = ""+memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getId();
		//난수 코드 생성
		Util.extra_code = memberService.transformString(id);
		System.out.println("메일발송 - extra_code : "+Util.extra_code);
		if(Util.extra_code.trim().length()!=0) {
			sendingEmail("[cho04-blog]인증 메일입니다.", "이메일 인증하기 링크 : "
					+ "[<a href='http://localhost:8081/blog/s/member/doAuthMail?code="+Util.extra_code+"'>인증하기</a>]", email);
			return "html:<script> alert('메일을 발송했습니다.'); history.back(); </script>";
		}
		else {
			return "html:<script> alert('메일 발송 실패'); history.back(); </script>";
		}
	}

	private String doActionDoMyPage(HttpServletRequest req, HttpServletResponse resp) {
		String loginPw = req.getParameter("loginPw");
		int id = memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getId();
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");
		String newPw = req.getParameter("newPw");

		if(loginPw.equals(memberService.getMemberById(id).getLoginPw())) {
			if (nickname.trim().length() == 0 && newPw.trim().length() == 0 && email.trim().length() == 0) {
				return "html:<script> alert('수정된 정보가 없습니다.'); history.back(); </script>";
			}
			if (newPw.equals(loginPw)) {
				return "html:<script> alert('새로운 비밀번호가 기존 비밀번호와 일치합니다.'); history.back(); </script>";
			}
			memberService.myPage(id, nickname, newPw, email);
			return "html:<script> alert('회원 정보가 수정되었습니다.'); location.replace('../home/main'); </script>";
		}
		return "html:<script> alert('비밀번호가 일치하지 않습니다.'); history.back(); </script>";
	}

	private String doActionMyPageModify(HttpServletRequest req, HttpServletResponse resp) {
		return "member/mypageModify.jsp";
	}
	
	private String doActionMyPage(HttpServletRequest req, HttpServletResponse resp) {
		return "member/mypage.jsp";
	}

	private String doActionLogout(HttpServletRequest req, HttpServletResponse resp) {
		String nickname = memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getNickname();
		session.removeAttribute("loginedMemberId");

		return "html:<script> alert('" + nickname + "님, 안녕히 가세요.'); location.replace('../home/main'); </script>";
	}

	private String doActionDoLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPw");

		Member m = null;
		m = memberService.login(loginId, loginPw);
		if (m != null) {
			session.setAttribute("loginedMemberId", m.getId());
			return "html:<script> alert('"
					+ memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getNickname()
					+ "님, 안녕하세요.'); location.replace('../home/main'); </script>";
		} else {
			return "html:<script> alert('ID 혹은 PW가 틀렸습니다.'); location.replace('login');</script>";
		}
	}

	private String doActionLogin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/login.jsp";
	}

	private String doActionDoJoin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPw");
		String email = req.getParameter("email");
		String loginPwConfirm = req.getParameter("loginPwConfirm");

		int id = -1;
		if (loginPwConfirm.equals(loginPw)) {
			id = memberService.join(loginId, name, nickname, loginPw, email);
		} else {
			return "html:<script> alert('비밀번호 확인이 틀렸습니다.'); history.back(); </script>";
		}

		if (id > 0) {
			if (sendingEmail(name + "님의 회원가입을 축하합니다.", "축하축은 거꾸로도 축하축", email) == 1) {
				return "html:<script> alert('" + id + "번째 회원 가입을 환영합니다.'); location.replace('../home/main'); </script>";
			} else {
				return "html:<script> alert('이메일 발송 실패'); location.replace('../home/main'); </script>";
			}
		} else {
			return "html:<script> alert('이미 존재하는 ID입니다.'); history.back(); </script>";
		}
	}

	private String doActionJoin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/join.jsp";
	}

	@Action
	public int sendingEmail(String title, String body, String emailAddress) {
		Properties p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true"); // gmail은 무조건 true 고정
		p.put("mail.smtp.host", "smtp.gmail.com"); // smtp 서버 주소
		p.put("mail.smtp.auth", "true"); // gmail은 무조건 true 고정
		p.put("mail.smtp.port", "587"); // gmail 포트

		Authenticator auth = new MyAuthentication();

		// session 생성 및 MimeMessage생성
		Session sess = Session.getDefaultInstance(p, auth);
		MimeMessage msg = new MimeMessage(sess);

		try {
			// 편지보낸시간
			msg.setSentDate(new Date());

			InternetAddress from = new InternetAddress();

			from = new InternetAddress("cho04<jnnna77@gmail.com>");

			// 이메일 발신자
			msg.setFrom(from);

			// 이메일 수신자
			InternetAddress to = new InternetAddress(emailAddress);
			msg.setRecipient(Message.RecipientType.TO, to);

			// 이메일 제목
			msg.setSubject(title, "UTF-8");

			// 이메일 내용
			msg.setText(body, "UTF-8");

			// 이메일 헤더
			msg.setHeader("content-Type", "text/html");

			// 메일보내기
			javax.mail.Transport.send(msg);

		} catch (AddressException addr_e) {
			addr_e.printStackTrace();
			return 0;
		} catch (MessagingException msg_e) {
			msg_e.printStackTrace();
			return 0;
		}
		return 1;
	}
}

class MyAuthentication extends Authenticator {

	PasswordAuthentication pa;

	public MyAuthentication() {
		// 개인정보
		String id = Util.gmailId;
		String pw = Util.gmailPw;

		// ID와 비밀번호를 입력한다.
		pa = new PasswordAuthentication(id, pw);

	}

	// 시스템에서 사용하는 인증정보
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}