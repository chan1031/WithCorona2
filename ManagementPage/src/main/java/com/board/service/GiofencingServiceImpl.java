package com.board.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.GiofencingDAO;
import com.board.domain.GiofencingVO;

@Service
public class GiofencingServiceImpl implements GiofencingService {
	
	@Inject
	private GiofencingDAO dao;
	
	@Override
	public List<GiofencingVO> enterPositiveList(HashMap<String,String> param) throws Exception {
		// TODO Auto-generated method stub
		return dao.enterPositiveList(param);
	}

	@Override
	public List<GiofencingVO> meetPositiveList(HashMap<String,String> param) throws Exception {
		// TODO Auto-generated method stub
		return dao.meetPositiveList(param);
	}

	@Override
	public String getMinTime() throws Exception {
		// TODO Auto-generated method stub
		return dao.getMinTime();
	}

}
