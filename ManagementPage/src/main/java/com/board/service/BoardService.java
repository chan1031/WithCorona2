package com.board.service;

import java.util.HashMap;
import java.util.List;

import com.board.domain.BoardVO;

public interface BoardService {
	
	public List<BoardVO> list(HashMap<String, String> param) throws Exception;
	public List<BoardVO> PositiveList(String searchName) throws Exception;
	
}
