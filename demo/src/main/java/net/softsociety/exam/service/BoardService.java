package net.softsociety.exam.service;

import java.util.ArrayList;

import net.softsociety.exam.domain.Board;
import net.softsociety.exam.domain.Reply;

public interface BoardService {
	//글 저장
	public int write(Board board);
	//글 읽기
	public Board read(int pnum);
	//글 목록
	public ArrayList<Board> list(String category, String order, String type, String searchWord);
	//글 삭제
	public int delete(Board board);
	//추천수 증가하고 현재 추천수 리턴
	public int recommend(int boardnum);
	//글 수정
	public int update(Board board);
	//리플 쓰기
	public int writeReply(Reply reply);
	//리플 목록
	public ArrayList<Reply> listReply(int boardnum);
	//리플 삭제
	public int deleteReply(Reply reply);

}
