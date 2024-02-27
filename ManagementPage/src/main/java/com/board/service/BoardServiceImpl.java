package com.board.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.BoardDAO;
import com.board.domain.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;

	@Override
	public List list(HashMap<String, String> param) throws Exception {

		return dao.list(param);
	}


	@Override
	public List<BoardVO> PositiveList(String searchName) throws Exception {
		// TODO Auto-generated method stub
		return dao.PositiveList(searchName);
	}

}