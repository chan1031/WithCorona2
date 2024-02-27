package com.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.service.GiofencingService;

@Controller
@RequestMapping("/giofencing/*")
public class GiofencingController {
	
	@Inject
	private GiofencingService service;
	
	@RequestMapping(value = "/giofencingList.do", method = RequestMethod.GET)
	public String getGiofencingList(@RequestParam(value="searchPositiveName",defaultValue="")String searchPositiveName,
			@RequestParam(value="positiveSelect",defaultValue="a.mb_id")String select,
			Model model) throws Exception {
		System.out.println("select is "+select);
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("searchPositiveName", searchPositiveName);
		param.put("select", select);
		
		System.out.println("searchName is "+searchPositiveName);
		List list = null;
		list = service.enterPositiveList(param);
		model.addAttribute("list", list);
		
		return "giofencing/giofencingList";
	}
	
	@RequestMapping(value = "/meetPositiveList", method = RequestMethod.GET)
	public String getPositiveList(@RequestParam(value="searchMeetName",defaultValue="")String searchMeetName,
			@RequestParam(value="select",defaultValue="a.mb_id")String select,
			Model model) throws Exception {
		
		HashMap<String,String> param = new HashMap<String,String>();
		String minTime = getMinTime();
		
		param.put("select", select);
		param.put("searchMeetName", searchMeetName);
		param.put("minTime", minTime);
		
		System.out.println("minTime is"+minTime);
		System.out.println("searchName is "+searchMeetName);
		List list = null;
		list = service.meetPositiveList(param);
		model.addAttribute("meetList", list);
		
		return "forward:/giofencing/giofencingList.do";
	}
	
	public String getMinTime() throws Exception {
		return service.getMinTime();
	}
	
	
}
