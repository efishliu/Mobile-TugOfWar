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
                String username = bffer[0];
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root","liugang666");
			Statement state=con.createStatement();
			int mean = 0;
			int wintimes = 0;
			int winradio = 0;
			int i = 0;
			String hs1 = "0,0,0,#";
			String hs2 = "0,0,0,#";
			String hs3 = "0,0,0,#";	
       		 	String sql1 = "select * from mygame where username='"+username+"' order by roomid desc";
			ResultSet rs1=state.executeQuery(sql1);
			while(rs1.next()){
				String roomid=rs1.getString("roomid");
				String mycount=rs1.getString("mycount");
				String result=rs1.getString("result");
				mean = mean + Integer.parseInt(mycount);
				if(result.equals("win"))	wintimes++;
				if(i == 0){
					hs1 = roomid+","+mycount+","+result+",#";
				}
				if(i == 1){
                                        hs2 = roomid+","+mycount+","+result+",#";
                                }
				if(i == 2){
                                        hs3 = roomid+","+mycount+","+result+",#";
                                }
				i++;
					
			}
			mean = mean / i;
			winradio = wintimes * 100 / i;
			String senddata = mean+","+wintimes+","+winradio+",#"+hs1+hs2+hs3;
			out.println(senddata);
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
