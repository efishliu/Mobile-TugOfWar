<%@ page language="java" contentType="charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.naming.*" %>
<%@ page import="java.sql.*" %>
<%
try{
	InputStream in = request.getInputStream();
	if(in != null){
		byte[] buf = new byte[1000];
        	in.read(buf);
                String data = new String(buf);
                String [] bffer = data.split(",");
                String roomid = bffer[0];
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root","liugang666");
			Statement state=con.createStatement();
			//更新开始游戏标志位
      		 	String sql1 = "update start set flag=1 where roomid="+roomid;
		        state.executeUpdate(sql1);
			state.close();
			con.close();
			out.println("success");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			out.println("failed");
		}
	
	}
	else{
		out.println("failed");
	}
}
catch (Exception e) {
out.println("failed");
}
%>
