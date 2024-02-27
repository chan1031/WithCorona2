<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WithCorona 회원정보</title>
<link rel="stylesheet" href=<c:url value="/resources/css/accounttransfer.css" /> rel="stylesheet"
 type="text/css" media="screen" />
<script src=" http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src=<c:url value="/resources/js/home.js" />></script>
<style type="text/javascript">

</style>
</head>
<body>
	<header>
		<nav>
			<div class="topMenu">
				<img class="logo" src=/resources/image/logo.PNG>
				<ul>
					<li class="on"><span type="button" id="timeLine"><a href="/TimeLine/list.do">타임라인</a></span></li>
					<li><span><a href="/nfc/BossList.do">내부출입명부</a></span></li>
					<li><span id="Giofencing"><a href="/giofencing/giofencingList.do">실외출입명부</a></span></li>
					<li><span><a href="/board/list.do">회원정보조회</a></span></li>
				</ul>
			</div>
		</nav>

	</header>
	<section>
		<div>
			<img class="banner" src=/resources/image/banner1.png>
			<div class="top">함께해요!</div>
			<div class="front">With Corona</div>
			<div class="bottom">#코로나 없는 세상을 위해</div>
		</div>
		<div id="nc-main" class="nc-main bg-cover bg-cc">

			<div class="full-wh">

				<!-- STAR ANIMATION -->
				<div class="bg-animation">
					<div id='stars'></div>
					<div id='stars2'></div>
					<div id='stars3'></div> 
					<div id='stars4'></div>
				</div>
			</div>
		</div>
	</section>
	<section>

		<br>
		<div class="about">Menu</div>
		<div class="item_box">
			<div class=item></div>
			<img class="item1" src=/resources/image/item1.png> <img class="item2"
				src=/resources/image//item2.png> <img class="item3" src=/resources/image//item3.PNG>
			<img class="item4" src=/resources/image//item4.png>


		</div>
	</section>
	<section>
		<div class="item_box_contant">
			<input type="button" class="item1_button" value="Click"> </input> <input
				type="button" class="item2_button" value="Click"> </input> <input
				type="button" class="item3_button" value="Click"> </input> <input
				type="button" class="item4_button" value="Click"> </input>


		</div>

	</section>

	<section>
	<div class=scroll_box>

		</div>
	</section>

</body>

</html>
