package com.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.service.BoardService;


@Controller
@RequestMapping("/TimeLine/*")
public class TimeLineController {

	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String getList(Model model) throws Exception {
		
		return "timeLine/TimelineList";
	}

}