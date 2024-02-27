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
	$('#positiveList').on('click', function() {
		location.href="/board/positiveList.do?searchName="+$('#searchName').val();
	});
	$('#list').on('click', function() {
		location.href="/board/list.do?searchName="+$('#searchName').val()+"&select="+$('#select').val();
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
	<div class=inner_info>홈 > 회원정보조회
	</div>
	</div>
	<br>
<div class="title">회원정보조회
<br>
<div class="tit_line"></div></div>
<div class="list">
	<div align=center>

	<%-- 
		<form name="form1" id="form1" method="post" action="PageController">
			
			<input type="hidden" id="action" name="action" value="${action}">
			<input type="button" id="Giofencing" align=right value="실외출입명부">
			<input type="button" id="Nfc" align=right value="실내출입명부"> <input
				type="button" id="timeLine" align=right value="타임라인">
		</form>--%>

			<div class="list_tit">WithCorona 회원정보</div>
		<table id=table1 border=1>
			<thead>
				<tr>
					<td colspan=9>
					<select id="select">
						<option value="mb_id">아이디 검색</option>
						<option value="mb_name">이름 검색</option>
						<option value="mb_tel">전화번호 검색</option>
					</select>
					 <input type="text" id="searchName"
						name="searchName" align="left"> <!-- keyup 이벤트 사용에 따른 엔터키 방지용 보이지 않는 input text -->
						<input type="text" style="display: none"> <input
						type="button" id="list" align=right value="조회">
					</td>
				</tr>
				<tr>
					<th>Id</th>
					<th>이름</th>
					<th>전화번호</th>
					<th>확진 여부</th>
				</tr>
			</thead>

			<tbody id=ajaxTable>
			<c:forEach items="${list}" var="list">
				<tr>
					<td>${list.mb_Id}</td>
					<td>${list.mb_Name}</td>
					<td>${list.mb_Tel}</td>
					<td>${list.corona_positive}</td>
				</tr>
			</c:forEach>
			</tbody>

		</table>
		<br>
		<input type="button" id="positiveList" value="확진자만">
<br>
		
		</div>
		</div>
	</div>
	
	<!-- 	<footer>
	<div class="footer">
	<div class="footer_box">
	Copyright © With Corona. All rights reserved.</div></div>
	</footer> -->
</body>
</html>