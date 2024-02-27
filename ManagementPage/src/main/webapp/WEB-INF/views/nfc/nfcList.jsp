<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>계좌이체 조회</title>
<link rel="stylesheet" href=<c:url value="/resources/css/main.css" /> type="text/css" media="screen" />
<script src=" http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="main.js"></script>
</head>
<script>
$(document).ready(function() {
	$('#nfcList').on('click', function() {
		location.href="/nfc/NfcList.do?searchPositiveName="+$('#searchPositiveName').val()+"&select="+$('#select').val()+"&boss_id="+$('#boss_id').val();
	});
	$('#main').on('click', function() {
		location.href="/board/list";
	});
    $('#NfcContactList').on('click', function() {
  		location.href="/nfc/NfcContactList.do?searchMeetName="+$('#searchMeetName').val()+"&contactSelect="+$('#contactSelect').val()+"&boss_id="+$('#boss_id').val();
  	});  
});
</script>
<body>
<div align=center>
	<header>

		<nav>	
			<div class="topMenu">
				<img class="logo" src=/resources/image/logo.PNG >
				<ul>
					<li class="on"><span type="button" id="timeLine">타임라인</span></li>
					<li><span><a href="/nfc/BossList.do">내부출입명부</a></span></li>
					<li><span id="Giofencing"><a href="/giofencing/giofencingList.do">실외출입명부</a></span></li>
					<li><span><a href="/board/list.do">회원정보조회</a></span></li>
				</ul>
			</div>
			
		</nav>


	</header>
			<div class=box_info>
	<div class=inner_info>홈 > 실내출입명부
	</div>
	</div>
<br>
<div class="tit_line"></div></div>
<div class="list">
	<div align=center>
	  <input type="hidden" id="boss_id" name="boss_id" value="${boss_id}">
	<%-- 
		<form name="form1" id="form1" method="post" action="PageController">
			
			<input type="hidden" id="action" name="action" value="${action}">
			<input type="button" id="Giofencing" align=right value="실외출입명부">
			<input type="button" id="Nfc" align=right value="실내출입명부"> <input
				type="button" id="timeLine" align=right value="타임라인">
		</form>--%>

			<div class="list_tit">실내출입명부 확진자</div>
		<table id=table1 border=1>
			<thead>
				<tr>
					<td colspan=9>
						<select id="select">
								<option value="b.mb_id">아이디 검색</option>
								<option value="a.date">시간 검색</option>
								<option value="a.tel">전화 검색</option>
						</select> 
					 <input type="text" id="searchPositiveName"
						name="searchAccountName" align="left"> <!-- keyup 이벤트 사용에 따른 엔터키 방지용 보이지 않는 input text -->
						<input type="text" style="display: none"> <input
						type="button" id="nfcList" align=right value="조회">
					</td>
				</tr>
				<tr>
					<th>Id</th>
					<th>출입시간</th>
					<th>전화번호</th>
				</tr>
			</thead>

			<tbody id=ajaxTable>
			<c:forEach items="${NfcList}" var="NfcList">
					<tr>
						<td>${NfcList.mb_id}</td>
						<td>${NfcList.date}</td>
						<td>${NfcList.tel}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
<br>
		<div class="list_tit">확진자 접촉자</div>
		<table id=table1 border=1>
			<thead>
				<tr>
					<td colspan=9>
					<select id="contactSelect">
								<option value="b.mb_id">아이디 검색</option>
								<option value="a.date">시간 검색</option>
								<option value="a.tel">전화 검색</option>
						</select> 
					 <input type="text" id="searchMeetName"
						name="searchMeetName" align="left"> <!-- keyup 이벤트 사용에 따른 엔터키 방지용 보이지 않는 input text -->
						<input type="text" style="display: none"> <input
						type="button" id="NfcContactList" align=right value="조회">
					</td>
				</tr>
				<tr>
					<th>Id</th>
					<th>출입시간</th>
					<th>전화번호</th>
				</tr>
			</thead>

			<tbody id=ajaxTable>
			<c:forEach items="${meetList}" var="meetList">
					<tr>
						<td>${meetList.mb_id}</td>
						<td>${meetList.date}</td>
						<td>${meetList.tel}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
		</div>
		</div>
	
	<!-- 	<footer>
	<div class="footer">
	<div class="footer_box">
	Copyright © With Corona. All rights reserved.</div></div>
	</footer> -->
</body>
</html>