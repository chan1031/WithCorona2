<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>계좌이체 조회</title>
<link rel="stylesheet" href=<c:url value="/resources/css/main.css" />
	type="text/css" media="screen" />
<script src=" http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="main.js"></script>
</head>
<script>
	$(document).ready(
			function() {
				$('#bossList').on(
						'click',
						function() {
							location.href = "/nfc/BossList.do?searchName="
									+ $('#searchName').val() + "&select="
									+ $('#select').val();
						});
				$('#main').on('click', function() {
					location.href = "/board/list";
				});
				$('#bossTable').on('click', 'td', function() {
					// 선택한 id 값
					var boss_id = $(this).text().trim();
					// id action을 "add"로 Setting, submit
					location.href = "/nfc/NfcList.do?boss_id=" + boss_id + "";
				});
			});
</script>
<body>
	<div align=center>
		<header>

			<nav>
				<div class="topMenu">
					<img class="logo" src=/resources/image/logo.PNG>
					<ul>
						<li class="on"><span type="button" id="timeLine"><a href="/TimeLine/list.do">타임라인</a></span></li>
						<li><span><a href="/nfc/BossList.do">내부출입명부</a></span></li>
						<li><span id="Giofencing"><a
								href="/giofencing/giofencingList.do">실외출입명부</a></span></li>
						<li><span><a href="/board/list.do">회원정보조회</a></span></li>
					</ul>
				</div>

			</nav>

		</header>
		<div class=box_info>
			<div class=inner_info>홈 > 실내출입명부</div>
		</div>
		<br>
		<div class="tit_line"></div>
	</div>
	<div class="list">
		<div align=center>

			<%-- 
		<form name="form1" id="form1" method="post" action="PageController">
			
			<input type="hidden" id="action" name="action" value="${action}">
			<input type="button" id="Giofencing" align=right value="실외출입명부">
			<input type="button" id="Nfc" align=right value="실내출입명부"> <input
				type="button" id="timeLine" align=right value="타임라인">
		</form>--%>

			<div class="list_tit">실내출입 확진자</div>
			<table id=table1 border=1>
				<thead>
					<tr>
						<td colspan=9><select id="select">
								<option value="store_name">가게이름 검색</option>
								<option value="boss_id">아이디 검색</option>
								<option value="bs_number">사업자검색</option>
						</select> <input type="text" id="searchName" name="searchName"> <!-- keyup 이벤트 사용에 따른 엔터키 방지용 보이지 않는 input text -->
							<input type="text" style="display: none"> <input
							type="button" id="bossList" align=right value="조회"></td>
					</tr>
					<tr>
						<th>가게이름</th>
						<th>Id</th>
						<th>사업자 번호</th>
					</tr>
				</thead>

				<tbody id="bossTable">
					<c:forEach items="${BossList}" var="BossList">
						<tr>
							<td>${BossList.store_name}</td>
							<td><a href="javascript:;">${BossList.boss_id}</a></td>
							<td>${BossList.bs_number}</td>
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