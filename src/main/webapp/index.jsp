<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    
    <style type="text/css">
        #LocWifiList {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            margin-top: 10px;
        }
        
        #LocWifiList td, #LocWifiList th {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        
        #LocWifiList tr:nth-child(even) {
            background-color: #FFFFFFF;
        }
        
        #LocWifiList tr:hover {
            background-color: #ddd;
        }
        
        #LocWifiList th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }
    </style>
    
    <script>
	    function insertLocHst() {
	
	        var lat = document.getElementById("lat").value;
	        var lnt = document.getElementById("lnt").value;
	        	    	
	        var xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성
	
	        xhr.open('POST', 'history.do', true); //요청을 보낼 방식, url, 비동기여부 설정
	        xhr.setRequestHeader('Content-Type', 'application/json'); // 요청 헤더 설정
	        
	        //Callback
	        xhr.onreadystatechange = function() {
	        	if (xhr.readyState === XMLHttpRequest.DONE) { // 요청 상태 확인
	                if (xhr.status == 200) {
	                	//success
	                	var responseElements = document.querySelector('#LocWifiList tbody');
	                	// 초기화
	                    responseElements.innerHTML = '';

		                var data = JSON.parse(xhr.responseText);
	                	
		            	// JSON 배열을 순회하면서 리스트 항목 생성
		                data.forEach(item => {	                		                	
		                	const row = document.createElement('tr');
		                	Object.values(item).forEach(text => {
		                        const td = document.createElement('td');
		                        td.textContent = text;
		                        row.appendChild(td);
		                    });
		                	
		                	// tbody에 새로운 행 추가
		                    responseElements.appendChild(row);
		                });
		            } else {
		                //failed
		            	alert('조회 요청을 실패하였습니다.');
		            }
	        	}
	        };
	
	     	// 전송할 데이터 준비
	        var data = JSON.stringify({ TYPE : 'insert', LAT: lat, LNT: lnt }); 
	        xhr.send(data); // 요청 전송
	    };
	    
        function getLocation() {
            // Geolocation API를 사용하여 위치를 가져옴
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition, showError);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        };
    
        function showPosition(position) {
            // 위치 정보 표시
            const lat = position.coords.latitude;
            const lnt = position.coords.longitude;
            
            document.getElementById("lat").value = lat;
	        document.getElementById("lnt").value = lnt;
        };
    
        function showError(error) {
            switch(error.code) {
                case error.PERMISSION_DENIED:
                    Window.alert("위치 정보 공유가 거부되었습니다.");
                    break;
                case error.POSITION_UNAVAILABLE:
                    Window.alert("위치 정보 사용할 수 없습니다.");
                    break;
                case error.TIMEOUT:
                	Window.alert("위치 정보 요청 시간이 초과되었습니다.");
                    break;
                case error.UNKNOWN_ERROR:
                	Window.alert("알 수 없는 오류가 발생하였습니다.");
                    break;
            }
        };
    </script>
</head>
<body>
    <H1>와이파이 정보 구하기</H1>
    <p>
        <a href="./">홈</a>
        |
        <a href="./history.jsp">위치 히스토리 목록</a>
        |
        <a href="./load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    </p>
    
    <div>
        <label>LAT: </label>
        <input id="lat" value="0.0" />
        <label>, LNT: </label>
        <input id="lnt" value="0.0" />
        <button onclick="getLocation()">내 위치 가져오기</button>
        <button onClick="insertLocHst()">근처 WIFI 정보 보기</button>
    </div>

    <table id="LocWifiList">
		<thead>
			<tr>
               <th>거리(Km)</th>
               <th>관리번호</th>
               <th>자치구</th>
               <th>와이파이명</th>
               <th>도로명주소</th>
               <th>상세주소</th>
               <th>설치위치(층)</th>
               <th>설치유형</th>
               <th>설치기관</th>
               <th>서비스구분</th>
               <th>망종류</th>
               <th>설치년도</th>
               <th>실내외구분</th>
               <th>WIFI접속환경</th>
               <th>X좌표</th>
               <th>Y좌표</th>
               <th>작업일자</th>
            </tr>
		</thead>
		<tbody>
			<tr>
               	<td colspan=17 > 위치 정보를 입력한 후 조회해 주세요. </td>
            </tr>
		</tbody>
    </table>
</body>
</html>