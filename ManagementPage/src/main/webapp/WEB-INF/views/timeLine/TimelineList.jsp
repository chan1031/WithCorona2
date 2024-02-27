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
		$('#GiofencingList').on('click', function() {
			location.href="/giofencing/meetPositiveList?searchMeetName="+$('#searchMeetName').val()+"&select="+$('#select').val();
		});
		$('#gioPositiveList').on('click', function() {
			location.href="/giofencing/giofencingList.do?searchPositiveName="+$('#searchPositiveName').val()+"&positiveSelect="+$('#selectPositive').val();
		});
		$('#main').on('click', function() {
			location.href="/board/list";
		});
		$('#nfc').on('click', function () {
	    	location.href="/nfc/BossList.do";
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
	<div class=inner_info>홈 > 타임라인
	</div>
	</div>
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

			<div class="list_tit">타임라인 정보</div>
		<table id=table1 border=1>
			<thead>
				<tr>
					<td colspan=9>
					 <select id="selectPositive">
						<option value="a.mb_id">아이디 검색</option>
						<option value="b.mb_name">이름 검색</option>
						<option value="a.time">출입시간</option>
					</select>
					 <input type="text" id="searchPositiveName"
						name="searchAccountName" align="left"> <!-- keyup 이벤트 사용에 따른 엔터키 방지용 보이지 않는 input text -->
						<input type="text" style="display: none"> <input
						type="button" id="gioPositiveList" align=right value="조회">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<th>위치</th>
					<th>시간</th>
				</tr>
			</thead>

			<tbody id=ajaxTable>
					<tr>
						<td>test</td>
						<td>인천 연수구 용담로 149번길 24</td>
						<td>2020-12-09 10시23분</td>
					</tr>
					<tr>
						<td>test</td>
						<td>인천 연수구 용담로 149번길 25</td>
						<td>2020-12-09 10시25분</td>
					</tr>
					<tr>
						<td>test2</td>
						<td>인천 연수구 송도 테크노파크 321번길 11</td>
						<td>2020-12-10 8시03분</td>
					</tr>
					<tr>
						<td>test2</td>
						<td>인천 연수구 벚꽃샘로 233번길 23</td>
						<td>2020-12-10 9시00분</td>
					</tr>
			</tbody>

		</table>
		</div>
		</div>
	
</body>
</html>