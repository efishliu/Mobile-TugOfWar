## 概述  
Mobile-TugOfWar是一个手机拔河游戏APP，通过手机传感器来对手机摇动的状态进行计数，玩家可以加入不同的阵营进行“拔河PK”。
我们团队开发的软件是基于Android的手机拔河APP，通过http协议与云服务器进行通信，实现拔河时的数据实时在服务器端进行处理。  

## 系统架构与处理流程  
软件采用C/S模式，服务器负责对收集到的所以客户端数据进行处理，并将结果返回到手机，以可视化形式显示；客户端负责采集安卓手机上加速度传感器的数据（左右摇/上下摇），并将其传送到服务器，同时接收服务端返回的计数统计结果，实时在客户端上显示这一结果。  

软件整体设计采用MVC的架构模式。系统大致分为五层：控制层（Controller）、业务逻辑层（Service）、数据访问层（DAO）、数据模型层（Model）、视图层（View）。
数据访问层主要对数据库进行操作，包括增加数据、修改数据和删除数据。业务逻辑层基于数据访问层之上，它主要针对业务流程来实现系统的功能。控制层又基于业务逻辑层之上，主要控制请求流转，并处理业务逻辑层通过数据访问层得到的数据在视图层进行展示。视图层为软件提供展示界面。  

下图描述了整个游戏的运行流程：  
<div align=center><img src="https://github.com/efishliu/Mobile-TugOfWar/blob/master/image/flow-chart.png" width = 50% height = 50% /></div>  

## 系统主要功能模块
* **客户端APP：**  基于Android平台进行开发，主要是app的设计与实现，并采集安卓手机上加速度传感器的数据（左右摇/上下摇），并将其传送到服务器。同时接收服务端返回的计数统计结果，实时在客户端上显示这一结果。  

HTTP通信接口：  
内部接口：Http回调监听接口。实现客户端服务器之间的Http请求处理。[HttpCallbackListener.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/HttpCallbackListener.java)  
回调接口的实现：[HttpUtil.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/HttpUtil.java)(注意修改IP地址与端口，默认：~~106.13.37.201:8000~~)

| 代码 | 描述 | 代码 | 描述 |  
|:----: |:----: |:----: |:----:| 
| [MainActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/MainActivity.java) | APP主界面 | [GameMainActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/GameMainActivity.java) | 登录后主界面 | 
| [SigninActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/SigninActivity.java) | 注册界面 | [LoginActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/LoginActivity.java) | 登录界面 | 
| [JoinRoomActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/JoinRoomActivity.java) | 加入房间界面 | [RoomActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/RoomActivity.java) | 房间等待界面 | 
| [PlayingActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/PlayingActivity.java) | 游戏界面 | [ResultActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/ResultActivity.java) | 游戏结果界面 | 
| [MyHistoryActivity.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/MyHistoryActivity.java) | 个人战绩界面 | []() |  | 
| [CountdownView.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/CountdownView.java) | 时间流逝圆盘 | [ShakeCapture.java](https://github.com/efishliu/Mobile-TugOfWar/blob/master/App-TugOfWar/app/src/main/java/com/example/liugang/tugofwar/ShakeCapture.java) | 抖动次数获取 | 



* **服务器端：**  对手机端传来的数据进行处理并返回。  

| 代码 | 描述 | 代码 | 描述 |  
|:----: |:----: |:----: |:----:|  
| [login.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/login.jsp) | 登录的http请求处理 | [signin.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/signin.jsp) | 注册的http请求处理 |  
| [createroom.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/createroom.jsp) | 创建房间的http请求处理 | [joinroom.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/joinroom.jsp) | 加入房间的http请求处理 |  
| [start.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/start.jsp) | 开始游戏的http请求处理 | [updateroom.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/updateroom.jsp) | 实时更新房间的http请求处理 |  
| [updatecount.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/updatecount.jsp) | 实时更新摇晃次数的http请求处理 | [finish.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/finish.jsp) | 游戏结束的http请求处理 |  
| [history.jsp](https://github.com/efishliu/Mobile-TugOfWar/blob/master/server-jsp/history.jsp) | 查询历史记录的http请求处理 |   |   |  

