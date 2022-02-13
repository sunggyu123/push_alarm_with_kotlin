# push_alarm_with_kotlin

푸시 알림 수신기

firebase 토큰을 확인할 수 있다.

일반,확장형,커스텀 알림을 볼 수 있다.

firebase를 통해 알람을 보낼 수 있다.
# Firebase

# Notification

# 01. Build UI 

Show result and token

Add textIsSelectable = true (copy function)

# 02. Build cloud messging

# 03. Peristalsis

1. kt class 를 생성한후 onMessageReceived 를 override한다

2. Manifest파일에 service를 등록한다.

3. 사진 공유와 같은 공유를 활용하는 걸 막기위해 exported한다.

# 04. implementation of Notification 
 
1. 채널 생성 (android: Ver.8 ~)

2. 일반 텍스트 알림 추가

3. 큰 텍스트 블록 추가

4. 커스텀 블록 생성

5. 알림을 클릭했을때 앱이동

 -> single_top 을 사용해서 기존화면에 갱신

6.갱신 작업

# 05 Result.

![Hnet-image](https://user-images.githubusercontent.com/72656002/153744671-2f887367-da46-4ffe-ab5f-93a1f1b66613.gif)
