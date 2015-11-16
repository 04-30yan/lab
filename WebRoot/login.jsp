<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$("#it").focus();
	});
</script>
<form id="user-loginForm" method="post">
	<table>
  		<tr>
  			<td style="font-weight:bold;font-size:13px;">账号</td>
  			<td><input id="it" type="text" name="userId"/></td>
  		</tr>
  		<tr>
  			<td style="font-weight:bold;font-size:13px;">密码</td>
  			<td><input type="password"  name="password"/></td>
  		</tr>
  		<tr>
  			<td style="font-weight:bold;font-size:13px;">身份</td>
  			<td>
  				<select name="type">
  					<option value="student" selected>学&nbsp;&nbsp;生</option>
       				<option value="teacher">教&nbsp;&nbsp;师</option>
       				<option value="admin">管理员</option>
  				</select>
  			</td>
  		</tr>
  	</table>    	
</form>
