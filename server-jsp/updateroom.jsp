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
			int redcount = 0;
	        	int bluecount = 0;
        		String reduser = "";
			String blueuser = "";
			//查询各队的人数和用户
       		 	String sql1 = "select username from roominfo where roomid="+roomid+"  and team='red' ";
			String sql2 = "select username from roominfo where roomid="+roomid+"  and team='blue' ";
			ResultSet rs1=state.executeQuery(sql1);
			while(rs1.next()){
				String user=rs1.getString("username");
				reduser = reduser + user + ",";
				redcount++;
			}
                        ResultSet rs2=state.executeQuery(sql2);
                        while(rs2.next()){
                                String user=rs2.getString("username");
                                blueuser = blueuser + user + ",";
                                bluecount++;
                        }
			//查询游戏是否开始
			String sql3 = "select flag from start where roomid="+roomid;
                        ResultSet rs3=state.executeQuery(sql3);
                        rs3.next();
                        String flag=rs3.getString("flag");
			String updatedata = ""+redcount+","+reduser+"#"+bluecount+","+blueuser+"#"+flag+"#";
			out.println(updatedata);
			state.close();
			con.close();
			
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
