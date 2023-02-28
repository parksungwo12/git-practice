package net.softsociety.exam.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.softsociety.exam.dao.BoardDAO;
import net.softsociety.exam.domain.Board;
import net.softsociety.exam.domain.Reply;


@Transactional
@Service
public class BoardSeviceImpl implements BoardService {

    @Autowired
    private BoardDAO boardDAO;

	@Override
	public int write(Board board) {
		int result = boardDAO.insertBoard(board);
		return result;
	}

	@Override
	public Board read(int boardnum) {
		boardDAO.increase(boardnum);
		Board board = boardDAO.selectBoard(boardnum);
		return board;
	}

	@Override
	public ArrayList<Board> list(String category, String order, String type, String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("category", category);
		map.put("order", order);
		map.put("type", type);
		map.put("searchWord", searchWord);
		
		ArrayList<Board> boardlist = boardDAO.selectBoardList(map); 
		return boardlist;
	}
	
	@Override
	public int delete(Board board) {
		int result = boardDAO.deleteBoard(board);
		return result;
	}

	@Override
	public int recommend(int boardnum) {
		int rec = 0;
		int result = boardDAO.recommend(boardnum);
		if (result != 0) {
			rec = boardDAO.selectBoard(boardnum).getRecommend();
		}
		return rec;
	}

	@Override
	public int update(Board board) {
		int result = boardDAO.updateBoard(board);
		return result;
	}

	@Override
	public int writeReply(Reply reply) {
		int result = boardDAO.insertReply(reply);
		return result;
	}

	@Override
	public ArrayList<Reply> listReply(int boardnum) {
		ArrayList<Reply> result = boardDAO.listReply(boardnum);
		return result;
	}

	@Override
	public int deleteReply(Reply reply) {
		int result = boardDAO.deleteReply(reply);
		return result;
	}
	
}
