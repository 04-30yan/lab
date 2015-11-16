package lab.teacher.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lab.bean.Task;
import lab.util.DbUtil;
import lab.util.FileUtil;

public class TaskService {

	/*
	 * 教师发布实验任务
	 * */
	public int addTask(int teacherCourseId, String taskName,
			String url, String workDir, String fileNameF) {
		int b = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(currentTime);
		String sql = "insert ignore into task(teacherCourseId,taskName,url,workDir,fileNameF,addTime) value(?,?,?,?,?,?)";
		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, teacherCourseId);
			ps.setString(2, taskName);
			ps.setString(3, url.replaceAll("\\\\", "\\\\\\\\"));
			ps.setString(4, workDir.replaceAll("\\\\", "\\\\\\\\"));
			ps.setString(5, fileNameF);
			ps.setString(6, date);
			if(ps.executeUpdate() > 0){
				b = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.free(ps, conn);
		}
		return b;
	}
	
	/*
	 * 教师修改实验任务
	 * */
	public int updateTask(Task tk){
		int b = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update task set fileNameF=?,url=? ,taskName=? where id=?";
		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, tk.getFileNameF());
			ps.setString(2, tk.getUrl().replaceAll("\\\\", "\\\\\\\\"));
			ps.setString(3, tk.getTaskName());
			ps.setInt(4, tk.getId());
			if(ps.executeUpdate() > 0){
				b = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.free(ps, conn);
		}
		return b;
	}
	//只修改任务名称
	public int onlyUpdateTask(Task tk){
		int b = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update task set taskName=? where id=?";
		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, tk.getTaskName());
			ps.setInt(2, tk.getId());
			if(ps.executeUpdate() > 0){
				b = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.free(ps, conn);
		}
		return b;
	}
	/*
	 * 教师删除自己发布的实验任务
	 * */
	public boolean deleteTaskById(Task tk){
		boolean b = false;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		//删除任务
		String delTaskSql = "delete from task where id="+tk.getId();
		//删除work
		String delWorkSql = "delete from work where id="+tk.getId();
		try {
			conn = DbUtil.getConnection();
			//删除实验任务
			ps = conn.prepareStatement(delTaskSql);
			if(ps.executeUpdate() > 0 && tk.getFileNameF() != null)
				//删除实验指导书
				b = FileUtil.deleteFile(tk.getUrl());
				//删除学生作业表中的数据
				ps1 = conn.prepareStatement(delWorkSql);
				if(ps1.executeUpdate() >= 0){
					//删除学生作业文件
					File dir = new File(tk.getWorkDir());
					b = FileUtil.deleteDir(dir);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtil.free(ps, conn);
		}
		return b;
	}
}
