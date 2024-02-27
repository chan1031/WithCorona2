package com.board.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.BossVO;
import com.board.domain.NfcVO;

@Repository
public class NfcDAOImpl implements NfcDAO {

	@Inject
	private SqlSession sql;

	private static String namespace = "com.board.mappers.nfc";

	@Override
	public List<BossVO> bossList(HashMap<String,String> param) throws Exception {
		return sql.selectList(namespace + ".bossList", param);
	}

	@Override
	public List<NfcVO> nfcList(HashMap<String, String> param) throws Exception {
		return sql.selectList(namespace + ".nfcList", param);
	}

	@Override
	public String getMinTime(HashMap<String,String> param) throws Exception {
		return sql.selectOne(namespace + ".getMinTime",param);
	}

	@Override
	public List<NfcVO> nfcContactList(HashMap<String, String> param) throws Exception {
		return sql.selectList(namespace + ".nfcContactList",param);
	}


}