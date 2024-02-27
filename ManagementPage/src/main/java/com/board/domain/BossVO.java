package com.board.domain;

public class BossVO {
	//vo의 경우 테이블의 컬럼 이름과 동일하여야 한다.
	private String boss_id;
	private String store_name;
	private String bs_number;
	
	public String getBoss_id() {
		return boss_id;
	}
	public void setBoss_id(String boss_id) {
		this.boss_id = boss_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getBs_number() {
		return bs_number;
	}
	public void setBs_number(String bs_number) {
		this.bs_number = bs_number;
	}
	
	
}
