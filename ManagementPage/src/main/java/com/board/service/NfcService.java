package com.board.service;

import java.util.HashMap;
import java.util.List;

import com.board.domain.BossVO;
import com.board.domain.NfcVO;

public interface NfcService {
	
	public List<BossVO> bossList(HashMap<String,String> param) throws Exception;
	public List<NfcVO> nfcList(HashMap<String,String> param) throws Exception;
	public List<NfcVO> nfcContactList(HashMap<String,String> param) throws Exception;
	public String getMinTime(HashMap<String,String> param) throws Exception;
}
