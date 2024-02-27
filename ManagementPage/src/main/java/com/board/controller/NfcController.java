package com.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.service.NfcService;

@Controller 
@RequestMapping("/nfc/*")
public class NfcController {
	
	String Boss_id;
	
	@Inject
	private NfcService service;

	@RequestMapping(value = "/BossList.do", method = RequestMethod.GET)
	public String getBossList(@RequestParam(value="searchName",defaultValue="")String searchName, 
			@RequestParam(value="select",defaultValue="store_name")String select,
			Model model) throws Exception {
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("searchName", searchName);
		param.put("select", select);
		
		System.out.println("searchName is "+searchName);
		List list = null;
		list = service.bossList(param);
		model.addAttribute("BossList", list);
		
		return "/nfc/BossList";
	}
	
	@RequestMapping(value = "/NfcList.do", method = RequestMethod.GET)
	public String getNfcList(@RequestParam(value="searchPositiveName",defaultValue="")String searchName,
			@RequestParam(value="boss_id",defaultValue="")String boss_id,
			@RequestParam(value="select",defaultValue="b.mb_id")String select,
			Model model) throws Exception {
		
		System.out.println("사장님 id는"+Boss_id);
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("searchName", searchName);
		param.put("boss_id", boss_id);
		param.put("select", select);
		
		System.out.println("searchName is "+searchName);
		List list = null;
		list = service.nfcList(param);
		model.addAttribute("NfcList", list);
		model.addAttribute("boss_id",boss_id);
		
		return "/nfc/nfcList";
	}
	@RequestMapping(value = "/NfcContactList.do", method = RequestMethod.GET)
	public String getNfcContactList(@RequestParam(value="searchMeetName",defaultValue="")String searchName,
			@RequestParam(value="boss_id",defaultValue="")String boss_id,
			@RequestParam(value="contactSelect",defaultValue="b.mb_id")String select,
			Model model) throws Exception {
		
		String minTime = getMinTime(boss_id);
		System.out.println("접촉자 사장님 id는"+boss_id);
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("searchName", searchName);
		param.put("boss_id", boss_id);
		param.put("minTime",minTime);
		param.put("select", select);
		
		System.out.println("searchName is "+searchName);
		List list = null;
		list = service.nfcContactList(param);
		model.addAttribute("meetList", list);
		model.addAttribute("boss_id",boss_id);
		
		return "forward:/nfc/NfcList.do";
	}
	
	public String getMinTime(String boss_id) throws Exception {
		HashMap<String,String> param = new HashMap<String,String>();
		
		System.out.println("getMinTIme id "+boss_id);
		param.put("boss_id", boss_id);
		
		return service.getMinTime(param);
	}
}