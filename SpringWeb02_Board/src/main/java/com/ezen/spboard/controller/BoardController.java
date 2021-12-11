package com.ezen.spboard.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.spboard.dto.ReplyVO;
import com.ezen.spboard.dto.SpBoard;
import com.ezen.spboard.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	BoardService bs;
//MemberController.java에서 호출해가지고 여기로 넘어옴
	//Controller 에서 Service의 메서드 호출 ->Service의 메서드에서 Dao의 메서드 호출
	//Dao의 메서드에서 Service의 메서드로 리턴 -> Service 메서들에서 Controller 메서드로 리턴
	//리턴받은 내용을 model에 싣고, ~.jsp로 이동
	
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request ) {		
		
		ArrayList<SpBoard>list=bs.selectBoard(); //게시물을 다 조회해서 list에 저장
		model.addAttribute("boardlist", list);
		//게시물 조화 후 main.jsp로 이동
		return "main";
	}
	
	@RequestMapping("/boardView")
	public String boardView(Model model, HttpServletRequest request ) {
		
		String num=request.getParameter("num"); 
		SpBoard sb = bs.boardView(num);  
		model.addAttribute("board",sb);
		
		ArrayList<ReplyVO>list=bs.selectReply(num);
		model.addAttribute("replyList",list);
			
		return "board/boardView";
	}
	
	@RequestMapping("/addReply")
	public String add_reply(Model model, HttpServletRequest request) {
		String boardnum = request.getParameter("boardnum");
				ReplyVO rvo=new ReplyVO();
				rvo.setUserid(request.getParameter("userid"));
				rvo.setContent(request.getParameter("reply"));
				rvo.setBoardnum(Integer.parseInt(boardnum));
				bs.addReply(rvo);
				return "readirect:/boardViewWithoutcount?num=" + boardnum;
			
	}
	
	@RequestMapping("/boardViewWithoutcount")
	public String boardViewNextUpdate(Model model, HttpServletRequest request) {
	String num = request.getParameter("num");
	SpBoard sb = bs.getBoard(num);
	model.addAttribute("board", sb);
	ArrayList<ReplyVO> list=bs.selectReply(num);
	model.addAttribute("replyList", list);
	return "board/boardView";
	}
	
	
	
	
}
