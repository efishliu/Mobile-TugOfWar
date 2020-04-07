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
		String team = bffer[1];
		String count = bffer[2];
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root","liugang666");
			Statement state=con.createStatement();
			String sql1 = "";
			if(team.equals("red"))
				sql1 = "update room set redcount=redcount+"+count+" where roomid="+roomid;
			else
				sql1 = "update room set bluecount=bluecount+"+count+" where roomid="+roomid;
			state.executeUpdate(sql1);
			String sql2 = "select redcount,bluecount from room where roomid="+roomid;
			ResultSet rs1=state.executeQuery(sql2);
			String redcount = "";
			String bluecount = "";
			while(rs1.next()){
				redcount = rs1.getString("redcount"); 
				bluecount = rs1.getString("bluecount");
			}
			String updatedata = redcount+","+bluecount+",";
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
