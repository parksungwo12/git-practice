package net.softsociety.exam.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.exam.service.BoardService;

/**
 * 게시판 관련 Ajax 요청 처리 콘트롤러
 */
@Slf4j
@RequestMapping("board")
@ResponseBody
@Controller
public class BoardRestController {
	
	@Autowired
	BoardService service;
	
	/**
	 * 추천수 증가시키고 현재 추천수 리턴 
	 * @param boardnum 추천할 글번호
	 */
	@PostMapping("recommend")
	public int recommend(int boardnum) {
		log.debug("추천할 글번호:{}", boardnum);
		int result = service.recommend(boardnum);
		return result;
	}
	

}
