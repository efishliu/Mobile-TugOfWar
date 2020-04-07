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
		String [] userinfo = data.split(",");
		String username = userinfo[0];
		//String username = new String(username1.getBytes("ISO-8859-1"),"GB2312");
		String passwd = userinfo[1];
		//out.print("username:"+username+",passwd:"+passwd);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root","liugang666");
			Statement state=con.createStatement();
			String sql1 = "insert into userinfo(username,passwd) values('"+username+"','"+passwd+"')";
			//out.println(sql1);
			//String sql1 = "insert into userinfo(username,passwd) values('liugang','1qasw2')";
			state.executeUpdate(sql1);
			String sql2 = "select userid,username from userinfo where username='"+username+"'";
			ResultSet rs=state.executeQuery(sql2);
			rs.next();
			String rsUserid=rs.getString("userid");
			String rsUsername=rs.getString("username");
			if(rsUserid != null)
				out.println("success,"+rsUserid+","+rsUsername+",");
			else	
				out.println("failed");
			state.close();
			con.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			out.println("failed");
			//out.print("username:"+username+",passwd:"+passwd);
		}
	
	}
	else{
		out.println("failed");
	}
}
catch (Exception e) {
out.println("failed");
}

/*
try{
//加载驱动
Class.forName("com.mysql.jdbc.Driver");
////建立连接 注：下面的test为数据库名字，root为mysql用户名 123456 为root的密码
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test1","root","liugang666");
////创建状态
Statement state=con.createStatement();
////插入
String sql="insert into student values('1','liugang')";
state.executeUpdate(sql);
////更新
//String sql1="update student set id='001' where name='liu'";
//state.executeUpdate(sql1);
////修改
////String sql3="update student set name='liu' where id='2'";
////state.executeUpdate(sql3);
////查询
//String sql2="select id,name from student where id='1'";
//
String sql2="select * from student";
ResultSet rs=state.executeQuery(sql2);
while(rs.next()){
String id=rs.getString("id");
String name=rs.getString("name");

out.println("id:"+id+" "+"name:"+name+"<br>");
}
out.println("xxxxxxsuccess..."+"<br>");
state.close();
con.close();

} catch (Exception e) {
e.printStackTrace();
}
*/
%>
