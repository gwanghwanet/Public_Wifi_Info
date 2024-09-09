<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>

<style type="text/css">
        #locationHst {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
            margin-top: 10px;
        }
        
        #locationHst td, #locationHst th {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        
        #locationHst tr:nth-child(even) {
            background-color: #FFFFFFF;
        }
        
        #locationHst tr:hover {
            background-color: #ddd;
        }
        
        #locationHst th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            background-color: #04AA6D;
            color: white;
        }
    </style>

<script type="text/javascript">
window.onload = function() {
	//XMLHttpRequest 객체 생성
    var xhr = new XMLHttpRequest();

    //요청을 보낼 방식, url, 비동기여부 설정
    xhr.open('GET', 'history.do', true);

    //Callback
    xhr.onreadystatechange = function() {
    	if (xhr.readyState === XMLHttpRequest.DONE) { // 요청 상태 확인
            if (xhr.status == 200) {
            	//success
                var data = JSON.parse(xhr.responseText);
            	var responseElement = document.getElementById('locationHst');            	
            	
            	// JSON 배열을 순회하면서 리스트 항목 생성
                data.forEach(item => {
                	const row = responseElement.createElement('tr');
                	Object.values(item).forEach(text => {
                        const td = document.createElement('td');
                        td.textContent = text;
                        row.appendChild(td);
                    });
                	/*
                	responseElement.innerHTML += "<tr>";
                	responseElement.innerHTML += "<td>" + item.seq + "</td>";
                	responseElement.innerHTML += "<td>" + item.lat + "</td>";
                	responseElement.innerHTML += "<td>" + item.lnt + "</td>";
                	responseElement.innerHTML += "<td>" + item.inqDate + "</td>";
                	responseElement.innerHTML += "<td><button>삭제</button></td>";
                	responseElement.innerHTML += "</tr>";
                	*/
                    //responseElement.appendChild(listItem);
                });
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
    <H1>위치 히스토리 목록</H1>
    <p>
        <a href="./">홈</a>
        |
        <a href="./history.jsp">위치 히스토리 목록</a>
        |
        <a href="./load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    </p>
	<div>
		<table id="locationHst">
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</table>
	</div>
</body>
</html>