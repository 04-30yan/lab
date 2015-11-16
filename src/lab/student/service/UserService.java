package lab.student.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lab.bean.User;
import lab.util.DbUtil;

public class UserService {

	/*
	 * 学生修改自己的信息
	 * */
	public boolean updateUser(User user){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String sql = "update user set userId=?, userName=?,academy=?,discipline=?,cls=?,grade=?,phone=?,sex=?,type=?,password=? where userId=?";
		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserId().trim());
			ps.setString(2, user.getUserName().trim());
			ps.setString(3, user.getAcademy().trim());
			ps.setString(4, user.getDiscipline().trim());
			ps.setString(5, user.getCls().trim());
			ps.setString(6, user.getGrade().trim());
			ps.setString(7, user.getPhone().trim());
			ps.setString(8, user.getSex().trim());
			ps.setString(9, user.getType().trim());
			ps.setString(10, user.getPassword().trim());
			ps.setString(11, user.getUserId().trim());
			if(ps.executeUpdate() > 0){
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.free(ps, conn);
		}
		return b;
	}
}
