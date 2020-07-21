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

public class MemberController extends Controller {

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "join":
			return doActionJoin(req, resp);
		case "doJoin":
			return doActionDoJoin(req, resp);
		case "login":
			return doActionLogin(req, resp);
		case "doLogin":
			return doActionDoLogin(req, resp);
		case "logout":
			return doActionLogout(req, resp);
		case "myPage":
			return doActionMyPage(req, resp);
		case "doMyPage":
			return doActionDoMyPage(req, resp);
		case "findPw":
			return doActionFindPw(req, resp);
		case "doFindPw":
			return doActionDoFindPw(req, resp);
		}
		return "";
	}

	private String doActionDoFindPw(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		
		int id = memberService.findPw(loginId, name, email);
		
		if(id==0) {
			return "html:<script> alert('정보가 일치하는 계정이 존재하지 않습니다.'); history.back(); </script>";
		}
		else {
			// 비번 재설정 필요
			String newPw=null;
			try {
				newPw = memberService.resetPw(id);
			} catch (NoSuchAlgorithmException e) {
				System.out.println("gjf");
			}
			sendingEmail("cho04 blog - 비밀번호 변경", "새로운 비밀번호는 ["+newPw+"] 입니다.", email);
			return "html:<script> alert('임시 비밀번호가 이메일로 발송되었습니다.'); location.replace('../home/main'); </script>";
		}
	}

	private String doActionFindPw(HttpServletRequest req, HttpServletResponse resp) {
		return "member/findPw.jsp";
	}

	private String doActionDoMyPage(HttpServletRequest req, HttpServletResponse resp) {
		int id = memberService.getMemberById((int)session.getAttribute("loginedMemberId")).getId();
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPw");
		String email = req.getParameter("email");
		
		if(nickname.trim().length()==0 && loginPw.trim().length()==0 && email.trim().length()==0) {
			return "html:<script> alert('수정된 정보가 없습니다.'); history.back(); </script>";
		}
		memberService.myPage(id, nickname, loginPw, email);
		return "html:<script> alert('회원 정보가 수정되었습니다.'); location.replace('../home/main'); </script>";
	}

	private String doActionMyPage(HttpServletRequest req, HttpServletResponse resp) {
		return "member/mypage.jsp";
	}

	private String doActionLogout(HttpServletRequest req, HttpServletResponse resp) {
		String nickname = memberService.getMemberById((int)session.getAttribute("loginedMemberId")).getNickname();
		session.removeAttribute("loginedMemberId");

		return "html:<script> alert('" + nickname + "님, 안녕히 가세요.'); location.replace('../home/main'); </script>";
	}

	private String doActionDoLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPw");

		Member m = null;
		m = memberService.login(loginId, loginPw);
		if(m!=null) {
			session.setAttribute("loginedMemberId", m.getId());
				return "html:<script> alert('" + memberService.getMemberById((int)session.getAttribute("loginedMemberId")).getNickname()
						+ "님, 안녕하세요.'); location.replace('../home/main'); </script>";
		}
		else {
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
			if(sendingEmail(name+"님의 회원가입을 축하합니다.", "축하축은 거꾸로도 축하축", email)==1) {
				return "html:<script> alert('" + id + "번째 회원 가입을 환영합니다.'); location.replace('../home/main'); </script>";
			}
			else {
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
	public int sendingEmail(String title, String body, String address) {
		Properties p = System.getProperties();
        p.put("mail.smtp.starttls.enable", "true");		// gmail은 무조건 true 고정
        p.put("mail.smtp.host", "smtp.gmail.com");		// smtp 서버 주소
        p.put("mail.smtp.auth","true");					// gmail은 무조건 true 고정
        p.put("mail.smtp.port", "587");					// gmail 포트
           
        Authenticator auth = new MyAuthentication();
         
        //session 생성 및  MimeMessage생성
        Session sess = Session.getDefaultInstance(p, auth);
        MimeMessage msg = new MimeMessage(sess);
         
        try{
            //편지보낸시간
            msg.setSentDate(new Date());
             
            InternetAddress from = new InternetAddress() ;
             
             
            from = new InternetAddress("cho04<@gmail.com>");
             
            // 이메일 발신자
            msg.setFrom(from);
             
             
            // 이메일 수신자
            InternetAddress to = new InternetAddress(address);
            msg.setRecipient(Message.RecipientType.TO, to);
             
            // 이메일 제목
            msg.setSubject(title, "UTF-8");
             
            // 이메일 내용
            msg.setText(body, "UTF-8");
             
            // 이메일 헤더
            msg.setHeader("content-Type", "text/html");
             
            //메일보내기
            javax.mail.Transport.send(msg);
             
        }catch (AddressException addr_e) {
            addr_e.printStackTrace();
            return 0;
        }catch (MessagingException msg_e) {
            msg_e.printStackTrace();
            return 0;
        }
        return 1;
	}
}
class MyAuthentication extends Authenticator {
    
    PasswordAuthentication pa;
    
    public MyAuthentication(){
    	// 개인정보
        String id = "";	// 구글 ID
        String pw = "";		// 구글 비밀번호(에러로 인해 2차 비번 입력)
 
        // ID와 비밀번호를 입력한다.
        pa = new PasswordAuthentication(id, pw);
      
    }
 
    // 시스템에서 사용하는 인증정보
    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
}