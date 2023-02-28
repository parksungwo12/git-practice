package net.softsociety.exam.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.softsociety.exam.domain.Board;
import net.softsociety.exam.domain.Reply;
import net.softsociety.exam.service.BoardService;

/**
 * 게시판 관련 콘트롤러
 */
@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {
	
	@Autowired
	BoardService service;
	
	/**
	 * 게시판 글목록으로 이동
	 */
	@GetMapping("list")
	public String list(Model model
		, String category, String order, String type, String searchWord) {
		
		log.debug("category:{} / order:{} / type:{} / searchWord:{}", category, order, type, searchWord);
		
		ArrayList<Board> boardlist = service.list(category, order, type, searchWord);

		model.addAttribute("boardlist", boardlist);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		
		return "list";
	}

	/**
	 * 글쓰기 폼으로 이동
	 */
	@GetMapping("write")
	public String write() {
		return "writeForm";
	}
	
	/**
	 * 글 저장 
	 * @param board 사용자가 폼에 입력한 게시글 정보
	 * @param user 인증정보
	 */
	@PostMapping("write")
	public String write(
			Board board
			, @AuthenticationPrincipal UserDetails user) {
		
		board.setMemberid(user.getUsername());
		
		int result = service.write(board);
		return "redirect:/board/list";
	}
	
	/**
	 * 글 읽기 
	 * @param boardnum 읽을 글번호
	 */
	@GetMapping("read")
	public String read(
			Model model
			, @RequestParam(name="boardnum", defaultValue = "0") int boardnum) { 

		Board board = service.read(boardnum);
		if (board == null) {
			return "redirect:/board/list";
		}
		
		ArrayList<Reply> list = service.listReply(boardnum);
		
		model.addAttribute("board", board);
		model.addAttribute("list", list);
		return "read";
	}	

	/**
	 * 글 삭제
	 * @param pnum 삭제할 글 번호
	 * @param user 인증정보
	 */
	@GetMapping ("delete")
	public String delete(int boardnum, @AuthenticationPrincipal UserDetails user) {
		Board board = new Board();
		board.setBoardnum(boardnum);
		board.setMemberid(user.getUsername());
		int result = service.delete(board);
		
		return "redirect:list";
	}

	/**
	 * 글수정 폼으로 이동
	 */
	@GetMapping("update")
	public String update(
			@RequestParam(name = "boardnum", defaultValue = "0") int boardnum
			, @AuthenticationPrincipal UserDetails user
			, Model model) {
		String id = user.getUsername();
		Board board = service.read(boardnum);
		
		log.debug("수정할 글: {}", board);
		if (board == null || !id.equals(board.getMemberid())) {
			return "redirect:list";
		}
		
		model.addAttribute("board", board);
		return "updateForm";
	}
	
	/**
	 * 글 수정 
	 * @param board 사용자가 폼에 입력한 게시글 정보
	 * @param user 인증정보
	 */
	@PostMapping("update")
	public String update(
			Board board
			, @AuthenticationPrincipal UserDetails user) {
		
		board.setMemberid(user.getUsername());
		
		int result = service.update(board);
		return "redirect:read?boardnum=" + board.getBoardnum();
	}
	
	/**
	 * 리플 저장
	 * @param reply 사용자가 폼에 입력한 리플 정보
	 * @param user 인증정보
	 */
	@PostMapping("writeReply")
	public String writeReply(
			Reply reply
			, @AuthenticationPrincipal UserDetails user) {
		
		reply.setMemberid(user.getUsername());
		
		int result = service.writeReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}

	/**
	 * 리플 삭제
	 * @param reply 사용자가 폼에 입력한 리플 정보
	 * @param user 인증정보
	 */
	@GetMapping("deleteReply")
	public String deleteReply(
			Reply reply
			, @AuthenticationPrincipal UserDetails user) {
		 
		reply.setMemberid(user.getUsername());
		log.debug("삭제할 리플정보 : {}", reply);
		
		int result = service.deleteReply(reply);
		return "redirect:read?boardnum=" + reply.getBoardnum();
	}

}
