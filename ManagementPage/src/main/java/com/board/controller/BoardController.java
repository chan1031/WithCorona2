package com.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.dao.BoardDAO;
import com.board.domain.BoardVO;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	private BoardService service;

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String getList(@RequestParam(value="searchName",defaultValue="")String searchName,
			@RequestParam(value="select",defaultValue="mb_id")String select,
			Model model) throws Exception {
		System.out.println("select is "+select);
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("searchName", searchName);
		param.put("select", select);
		
		System.out.println("searchName is "+searchName);
		List list = null;
		list = service.list(param);
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	@RequestMapping(value = "/positiveList", method = RequestMethod.GET)
	public String getPositiveList(@RequestParam(value="searchName",defaultValue="")String searchName,Model model) throws Exception {

		List list = null;
		list = service.PositiveList(searchName);
		model.addAttribute("list", list);
		return "board/list";
	}
	
}