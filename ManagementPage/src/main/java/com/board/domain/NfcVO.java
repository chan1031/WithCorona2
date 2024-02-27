package com.board.domain;

public class NfcVO {
	//vo의 경우 테이블의 컬럼 이름과 동일하여야 한다.
	private String date;
	private String boss_id;
	private String tel;
	private String mb_id;
	
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBoss_id() {
		return boss_id;
	}
	public void setBoss_id(String boss_id) {
		this.boss_id = boss_id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
