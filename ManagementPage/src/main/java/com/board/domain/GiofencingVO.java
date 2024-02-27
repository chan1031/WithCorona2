package com.board.domain;

public class GiofencingVO {
	//vo의 경우 테이블의 컬럼 이름과 동일하여야 한다.
	private String mb_id;
	private String mb_name;
	private String time;
	
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	public String getMb_name() {
		return mb_name;
	}
	public void setMb_name(String mb_name) {
		this.mb_name = mb_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
