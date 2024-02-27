package com.board.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.GiofencingVO;

@Repository
public class GiofencingDAOImpl implements GiofencingDAO {
	
	@Inject
	private SqlSession sql;

	private static String namespace = "com.board.mappers.giofencing";
	
	@Override
	public List<GiofencingVO> enterPositiveList(HashMap<String,String> param) throws Exception {
		return sql.selectList(namespace + ".enterPositiveList", param);
	}

	@Override
	public List<GiofencingVO> meetPositiveList(HashMap<String,String> param) throws Exception {
		return sql.selectList(namespace + ".meetPositiveList", param);
	}

	@Override
	public String getMinTime() throws Exception {
		return sql.selectOne(namespace + ".getMinTime");
	}

}
