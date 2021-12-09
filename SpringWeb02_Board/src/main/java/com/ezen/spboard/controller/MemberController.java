package com.ezen.spboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.spboard.dto.SpMember;
import com.ezen.spboard.service.MemberService;



@Controller
public class MemberController {
	
	@Autowired
	MemberService ms;
	

	//@RequestMapping(value="/", method=RequestMethod.GET)
	@RequestMapping("/")
	public String firstRequest(Model model, HttpServletRequest request) { //전달인수 request 로 세션을 얻어와야함
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") == null)
			return "loginform";
		else
		return "main";
	}
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request ) {
		
		String url="loginform";
		String id=request.getParameter("id");
		String pw=request.getParameter("pw");
		
		SpMember sdto = ms.getMember(id);
		
		if(sdto == null) {
			model.addAttribute("message", "id를 확인하세요");
		}else if(sdto.getPw() == null) {
			model.addAttribute("message", "비번이 null 입니다. 관리자에 문의 하세요");
		}else if(!sdto.getPw().equals(pw)) {
			model.addAttribute("message", "비밀번호가 맞지 않습니다");
		}else if(sdto.getPw().equals(pw) ) {	
			HttpSession session =request.getSession();
			session.setAttribute("loginUser", sdto);
			url="main";
		}else {
			model.addAttribute("message","알수없는 이유로 로그인하지 못했어요");
		}
		return url;
	}
}
