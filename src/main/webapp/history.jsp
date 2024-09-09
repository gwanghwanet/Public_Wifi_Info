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
	function getLocHstList() {
		//XMLHttpRequest 객체 생성
	    var xhr = new XMLHttpRequest();

	    //요청을 보낼 방식, url, 비동기여부 설정
	    xhr.open('GET', 'history.do', true);

	    //Callback
	    xhr.onreadystatechange = function() {
	    	if (xhr.readyState === XMLHttpRequest.DONE) { // 요청 상태 확인
	            if (xhr.status == 200) {
	            	//success
                	var responseElements = document.querySelector('#locationHst tbody');
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

	                    const td = document.createElement('td');
	                	const button = document.createElement('button');
	                	button.textContent = '삭제';
	                	button.type = "button";
	                	button.id = 'Delete' + item.seq; // 버튼에 ID 설정
	                	
	                	button.addEventListener('click', deleteLocHst);
	                    
	                	td.appendChild(button);
	                    row.appendChild(td)
	                	
	                	// tbody에 새로운 행 추가
	                    responseElements.appendChild(row);
	                });
	            } else {
	                //failed
	            	alert('조회 요청을 실패하였습니다.');
	            }
	    	}
	    };

	    xhr.send(); // 요청 전송
	};
	
	function deleteLocHst(event) {
		var seq = event.target.id.substring("Delete".length);
		
		//XMLHttpRequest 객체 생성
	    var xhr = new XMLHttpRequest();

	    //요청을 보낼 방식, url, 비동기여부 설정
	    xhr.open('POST', 'history.do', true);

	 	// 요청 헤더 설정
	    xhr.setRequestHeader('Content-Type', 'application/json');
	    
	    //Callback
	    xhr.onreadystatechange = function() {
	    	if (xhr.readyState === XMLHttpRequest.DONE) { // 요청 상태 확인
	            if (xhr.status == 200) {
	            	getLocHstList();
	            } else {
	                //failed
	            	alert('삭제 요청을 실패하였습니다.');
	            }
	    	}
	    };

	 	// 전송할 데이터 준비
        var data = JSON.stringify({ TYPE : 'delete', SEQ: seq }); 
	    
	    xhr.send(data); // 요청 전송
	};

	window.onload = getLocHstList();
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
			<thead>
				<tr>
					<th>ID</th>
					<th>X좌표</th>
					<th>Y좌표</th>
					<th>조회일자</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
</html>