package com.board.domain;

public class BoardVO {
	//vo의 경우 테이블의 컬럼 이름과 동일하여야 한다.
	private String mb_Id;
	private String mb_Name;
	private String mb_Tel;
	private Boolean corona_positive;
	private String time;
	public String getMb_Id() {
		return mb_Id;
	}
	public void setMb_Id(String mb_Id) {
		this.mb_Id = mb_Id;
	}
	public String getMb_Name() {
		return mb_Name;
	}
	public void setMb_Name(String mb_Name) {
		this.mb_Name = mb_Name;
	}
	public String getMb_Tel() {
		return mb_Tel;
	}
	public void setMb_Tel(String mb_Tel) {
		this.mb_Tel = mb_Tel;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Boolean getCorona_positive() {
		return corona_positive;
	}
	public void setCorona_positive(Boolean corona_positive) {
		this.corona_positive = corona_positive;
	}
	
}
