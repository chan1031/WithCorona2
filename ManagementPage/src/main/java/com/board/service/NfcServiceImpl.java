package com.board.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.NfcDAO;
import com.board.domain.BossVO;
import com.board.domain.NfcVO;

@Service
public class NfcServiceImpl implements NfcService {
	
	@Inject
	private NfcDAO dao;
	
	@Override
	public List<BossVO> bossList(HashMap<String,String> param) throws Exception {
		return dao.bossList(param);
	}

	@Override
	public List<NfcVO> nfcList(HashMap<String, String> param) throws Exception {
		return dao.nfcList(param);
	}

	@Override
	public String getMinTime(HashMap<String,String> param) throws Exception {
		return dao.getMinTime(param);
	}

	@Override
	public List<NfcVO> nfcContactList(HashMap<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		return dao.nfcContactList(param);
	}

}
