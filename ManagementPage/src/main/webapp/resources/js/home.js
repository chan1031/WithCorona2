$(document).ready(
		function() {
			function simpleParallax() {
				// 스크롤된 거리
				var scrolled = $(window).scrollTop() + 1;

				// 백그라운드 스크롤 속도 변경 시 0.3 을 수정
				$('.scroll').css('background-position',
						'0' + -(scrolled * 0.3) + 'px');
			}
			// Everytime we scroll, it will fire the function
			$(window).scroll(function(e) {
				simpleParallax();
			});
		}); // ready
