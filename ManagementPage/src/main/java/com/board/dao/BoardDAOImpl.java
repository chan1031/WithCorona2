package com.board.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	private SqlSession sql;

	private static String namespace = "com.board.mappers.board";

	// 게시물 목록
	@Override
	public List list(HashMap<String, String> param) throws Exception {

		return sql.selectList(namespace + ".list", param);
	}

	@Override
	public List<BoardVO> PositiveList(String searchName) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(namespace + ".positiveList", searchName);
	}

}