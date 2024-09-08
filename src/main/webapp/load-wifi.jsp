<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>

<script type="text/javascript">
window.onload = function() {
			//XMLHttpRequest 객체 생성
	        var xhr = new XMLHttpRequest();
	
	        //요청을 보낼 방식, url, 비동기여부 설정
	        xhr.open('GET', 'loadWifi.do', true);
		
	        //Callback
	        xhr.onreadystatechange = function() {
	        	if (xhr.readyState === XMLHttpRequest.DONE) { // 요청 상태 확인
		            if (xhr.status == 200) {
		            	//success
		                var data = JSON.parse(xhr.responseText);
		            	console.log(data.test);
		            	document.getElementById('result').innerHTML = '<h1>' + data.count + '개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>';
		            } else {
		                //failed
		            }
	        	}
	        };

            xhr.send(); // 요청 전송
	    };
</script>

</head>
<body>
    <div style="display: block; text-align: center">
    	<div id="result">
    		<h1> 정보를 가져오는 중입니다. </h1>
    	</div>
    	<br>
    	<a href="/">홈으로 가기</a>
    </div>
</body>
</html>