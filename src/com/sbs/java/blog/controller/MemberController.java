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
		// 메일 인증
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
					+ "] 입니다.'); location.replace('../member/login'); </script>";
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
			if(sendingEmail("비밀번호 변경", "새로운 비밀번호는 [" + newPw + "] 입니다.", email)==1) {
				return "html:<script> alert('임시 비밀번호가 이메일로 발송되었습니다.'); location.replace('../home/main'); </script>";
			}
			else {
				return "html:<script> alert('메일 발송 실패'); location.replace('../home/main'); </script>";
			}
		}
	}

	private String doActionFindPw(HttpServletRequest req, HttpServletResponse resp) {
		return "member/findPw.jsp";
	}
	
	private String doActionDoAuthMail(HttpServletRequest req, HttpServletResponse resp) {
		if(session.getAttribute("loginedMemberId")!=null) {
			if(memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getMailAuthStatus()==0) {
				if(Util.extra_code.trim().length()!=0) {
					String str = memberService.transformString(""+memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getId());
					if(req.getParameter("code").equals(str)) {
						memberService.doAuthMail((int)session.getAttribute("loginedMemberId"));
						return "html:<script> alert('인증이 되었습니다.'); location.replace('../home/main'); </script>";
					}
				}
				return "html:<script> alert('인증 실패 - 인증 신청 계정과 현재 로그인 중인 계정이 다릅니다.'); location.replace('../home/main'); </script>";
			}
			else {
				return "html:<script> alert('현재 로그인 중인 계정은 이미 인증된 계정입니다.'); location.replace('../home/main'); </script>";
			}
		}
		return "html:<script> alert('인증 실패 - 비로그인 상태'); location.replace('../home/main'); </script>";
	}
	
	private String doActionAuthMail(HttpServletRequest req, HttpServletResponse resp) {
		String email = req.getParameter("email");
		String id = ""+memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getId();
		//난수 코드 생성
		Util.extra_code = memberService.transformString(id);
		if(Util.extra_code.trim().length()!=0) {
			if(sendingEmail("인증 메일"
					, memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getNickname()
					+ "님의 이메일 인증 링크"
					+ "<br>1. [<a href='https://cho04.my.iu.gy/blog/s/member/doAuthMail?code="+Util.extra_code+"'>cho04-인증하기</a>]"
					+ "<br>2. [<a href='http://localhost:8081/blog/s/member/doAuthMail?code="+Util.extra_code+"'>localhost-인증하기</a>]"
					+"<br><span style='color:red;'>인증하기를 누르는 시점에서, 해당 계정으로 로그인 한 상태여야 합니다.</span>"
					, email)==1) {
				return "html:<script> alert('메일을 발송했습니다.'); history.back(); </script>";
			}
			else {
				return "html:<script> alert('메일 발송 실패'); history.back(); </script>";
			}
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
			boolean mailAuthStatusReset=false;
			if (nickname.trim().length() == 0 && newPw.trim().length() == 0 && email.trim().length() == 0) {
				return "html:<script> alert('수정된 정보가 없습니다.'); history.back(); </script>";
			}
			if (newPw.equals(loginPw)) {
				return "html:<script> alert('새로운 비밀번호가 기존 비밀번호와 일치합니다.'); history.back(); </script>";
			}
			if(email.trim().length()!=0) {
				mailAuthStatusReset=true;
			}
			memberService.myPage(id, nickname, newPw, email, mailAuthStatusReset);
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

		String redirectUri = Util.getString(req, "redirectUri", "../home/main");
		return String.format("html:<script> alert('" + nickname + "님, 로그아웃 되었습니다.'); location.replace('" + redirectUri + "'); </script>");
//		return "html:<script> alert('" + nickname + "님, 안녕히 가세요.'); location.replace('../home/main'); </script>";
	}

	private String doActionDoLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");

		Member m = null;
		m = memberService.login(loginId, loginPw);
		if (m != null) {
			session.setAttribute("loginedMemberId", m.getId());
			
			String redirectUri = Util.getString(req, "redirectUri", "../home/main");
			
			System.out.println("redirectUri : "+redirectUri);

			return String.format("html:<script> alert('로그인 되었습니다.'); location.replace('" + redirectUri + "'); </script>");
			
//			return "html:<script> alert('"
//					+ memberService.getMemberById((int) session.getAttribute("loginedMemberId")).getNickname()
//					+ "님, 안녕하세요.'); location.replace('../home/main'); </script>";
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
			if (sendingEmail(name + "님의 회원가입을 축하합니다.",
					"블로그"
					+ "<br>1. [<a href='https://cho04.my.iu.gy'>cho04-블로그 바로가기</a>]"
					+ "<br>2. [<a href='http://localhost:8081/blog/s/home/main'>localhost-블로그 바로가기</a>]"
					+ "<br><br>로그인 시 글쓰기/댓글쓰기가 가능합니다."
					, email) == 1) {
				return "html:<script> alert('" + id + "번째 회원 가입을 환영합니다.'); location.replace('../home/main'); </script>";
			} else {
				return "html:<script> alert('메일 발송 실패'); location.replace('../home/main'); </script>";
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
			msg.setSubject("[cho04-blog] "+title, "UTF-8");

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

	@Override
	public String getControllerName() {
		return "member";
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