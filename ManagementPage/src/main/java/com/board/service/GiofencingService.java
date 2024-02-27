package com.board.service;

import java.util.HashMap;
import java.util.List;

import com.board.domain.GiofencingVO;

public interface GiofencingService {
	
	public List<GiofencingVO> enterPositiveList(HashMap<String,String> param) throws Exception;
	public List<GiofencingVO> meetPositiveList(HashMap<String,String> param) throws Exception;
	public String getMinTime() throws Exception;
	
}
