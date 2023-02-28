package net.softsociety.exam.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import net.softsociety.exam.domain.Board;
import net.softsociety.exam.domain.Reply;

/**
 * 게시판 관련 매퍼
 */
@Mapper
public interface BoardDAO {
    //글 저장
	public int insertBoard(Board board);
	//글 읽기
	public Board selectBoard(int boardnum);
	//조회수 증가
	public int increase(int boardnum);
	//글 목록
	public ArrayList<Board> selectBoardList(HashMap<String, String> map);
	//글 삭제
	public int deleteBoard(Board board);
	//추천수 증가
	public int recommend(int boardnum);
	//글 수정
	public int updateBoard(Board board);
	//리플 저장
	public int insertReply(Reply reply);
	//리플 목록
	public ArrayList<Reply> listReply(int boardnum);
	//리플 삭제
	public int deleteReply(Reply reply);
}
