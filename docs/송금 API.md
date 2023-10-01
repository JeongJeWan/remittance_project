# /송금 Rest Api

1. 사용자 생성

* Request

```
POST http://localhost:8080/api/user
Content-Type: application/json

{
  "username" : "정제완",
  "maxLimit" : 2000000
}
```

* Response

```
POST http://localhost:8080/api/user

HTTP/1.1 201 
Content-Length: 0
Date: Sun, 01 Oct 2023 08:35:07 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>

Response code: 201; Time: 1270ms (1 s 270 ms); Content length: 0 bytes (0 B)
```

2. 사용자 금액 충전 성공

* Request

```
PATCH http://localhost:8080/api/user/1
Content-Type: application/json

{
  "balance" : 1500000
}
```

* Response

```
PATCH http://localhost:8080/api/user/1

HTTP/1.1 200 
Content-Length: 0
Date: Sun, 01 Oct 2023 08:37:27 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>

Response code: 200; Time: 854ms (854 ms); Content length: 0 bytes (0 B)
```

3. 사용자 금액 충전 실패 - 한도 초과

* Request

```
PATCH http://localhost:8080/api/user/1
Content-Type: application/json

{
  "balance" : 15000000
}

```

* Reponse

```
PATCH http://localhost:8080/api/user/1

HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Oct 2023 08:38:02 GMT
Connection: close

{
  "error": "사용자의 한도를 초과했습니다.",
  "status": 400,
  "timestamp": "2023-10-01 17:38:02"
}
Response file saved.
> 2023-10-01T173802.400.json

Response code: 400; Time: 1326ms (1 s 326 ms); Content length: 75 bytes (75 B)
```

4. 사용자가 다른 사용자에게 송금 성공

* Request

```
POST http://localhost:8080/api/user/remittance/1/2
Content-Type: application/json

{
  "amount" : 50000
}
```

* Response

```
POST http://localhost:8080/api/user/remittance/1/2

HTTP/1.1 201 
Content-Length: 0
Date: Sun, 01 Oct 2023 08:39:02 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>

Response code: 201; Time: 1376ms (1 s 376 ms); Content length: 0 bytes (0 B)
```

5. 사용자가 다른 사용자에게 송금 실패 - 잔액 부족

* Request

```
POST http://localhost:8080/api/user/remittance/1/2
Content-Type: application/json

{
  "amount" : 10000000
}
```

* Response

```
POST http://localhost:8080/api/user/remittance/1/2

HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Oct 2023 08:39:30 GMT
Connection: close

{
  "error": "잔액이 부족합니다.",
  "status": 400,
  "timestamp": "2023-10-01 17:39:30"
}
Response file saved.
> 2023-10-01T173930.400.json

Response code: 400; Time: 155ms (155 ms); Content length: 69 bytes (69 B)
```

6. 사용자가 다른 사용자에게 송금 실패 - 수신자 최대 한도 초과

* Request

```
POST http://localhost:8080/api/user/remittance/1/2
Content-Type: application/json

{
  "amount" : 1000000
}
```

* Response

```
POST http://localhost:8080/api/user/remittance/1/2

HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Oct 2023 08:40:49 GMT
Connection: close

{
  "error": "수신자의 한도를 초과했습니다.",
  "status": 400,
  "timestamp": "2023-10-01 17:40:49"
}
Response file saved.
> 2023-10-01T174049.400.json

Response code: 400; Time: 61ms (61 ms); Content length: 75 bytes (75 B)
```

7. 사용자에 대한 송금 목록 조회

* Request
```
GET http://localhost:8080/api/user/remittance/1
Content-Type: application/json
```

* Response

```
GET http://localhost:8080/api/user/remittance/1

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Oct 2023 08:41:28 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
  {
    "senderName": "정제완",
    "receiverName": "홍길동",
    "amount": 50000,
    "createdAt": "2023-10-01T17:39:02"
  },
  {
    "senderName": "정제완",
    "receiverName": "홍길동",
    "amount": 500000,
    "createdAt": "2023-10-01T17:41:24"
  }
]
Response file saved.
> 2023-10-01T174128.200.json

Response code: 200; Time: 60ms (60 ms); Content length: 184 bytes (184 B)
```