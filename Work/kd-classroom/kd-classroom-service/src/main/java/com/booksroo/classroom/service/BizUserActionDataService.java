package com.booksroo.classroom.service;

import com.booksroo.classroom.common.enums.UserEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author liujianjian
 * @date 2018/6/10 22:51
 */
@Service("bizUserActionDataService")
public class BizUserActionDataService extends BaseService {

    @Autowired
    private DataSource dataSource;

    public Timestamp getTFirstLoginTime(long teacherId, int platform) {
        return getFirstLoginTime(teacherId, UserEnum.TEACHER.getValue(), platform);
    }

    public Timestamp getSFirstLoginTime(long studentId, int platform) {
        return getFirstLoginTime(studentId, UserEnum.STUDENT.getValue(), platform);
    }

    public void updateChangeTPwdTime(long teacherId, Timestamp time, int platform) {
        updateChangePwdTime(teacherId, UserEnum.TEACHER.getValue(), time, platform);
    }

    public void updateChangeSPwdTime(long studentId, Timestamp time, int platform) {
        updateChangePwdTime(studentId, UserEnum.STUDENT.getValue(), time, platform);
    }

    public void updateTeacherLoginTime(long teacherId, Timestamp time, int platform) {
        updateUserLoginTime(teacherId, UserEnum.TEACHER.getValue(), time, platform);
    }

    public void updateStudentLoginTime(long studentId, Timestamp time, int platform) {
        updateUserLoginTime(studentId, UserEnum.STUDENT.getValue(), time, platform);
    }

    public void updateUserLoginTime(long userId, int userType, Timestamp time, int platform) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                updateLoginTimeDB(userId, userType, time, platform);
            }
        });
    }

    public void updateChangePwdTime(long userId, int userType, Timestamp time, int platform) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                updateChangePwdTimeDB(userId, userType, time, platform);
            }
        });
    }

    private Timestamp getFirstLoginTime(long userId, int userType, int platform) {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            String querySql = "select last_login_time from user_action_data where user_id=" + userId + " and user_type=" + userType + " and plat_form=" + platform + " order by last_login_time asc";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(querySql);
            Timestamp time = null;
            if (rs.next()) {
                time = rs.getTimestamp(1);
            }
            return time;
        } catch (Exception e) {
            log.error("获取用户登录时间出错: " + e.getMessage(), e);
        } finally {
            releaseDB(conn, ps, stmt, rs);
        }

        return null;
    }

    private void updateLoginTimeDB(long userId, int userType, Timestamp time, int platform) {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();

            String querySql = "select count(1) from user_action_data where user_id=" + userId + " and user_type=" + userType + " and plat_form=" + platform;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(querySql);
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            if (count > 0) {
                String sql = "update user_action_data set last_login_time=? where user_id=? and user_type=? and plat_form=?";
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, time);
                ps.setLong(2, userId);
                ps.setInt(3, userType);
                ps.setInt(4, platform);
                int n = ps.executeUpdate();
//                System.out.println("n====" + n);
            } else {
                String sql = "insert into user_action_data(user_id,user_type,plat_form,last_login_time) value(?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setLong(1, userId);
                ps.setInt(2, userType);
                ps.setInt(3, platform);
                ps.setTimestamp(4, time);
                int n = ps.executeUpdate();
//                System.out.println("n====" + n);
            }
        } catch (Exception e) {
            log.error("更新用户登录时间出错: " + e.getMessage(), e);
        } finally {
            releaseDB(conn, ps, stmt, rs);
        }
    }

    private void updateChangePwdTimeDB(long userId, int userType, Timestamp time, int platform) {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();

            String sql = "update user_action_data set last_update_pwd_time=? where user_id=? and user_type=? and plat_form=?";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, time);
            ps.setLong(2, userId);
            ps.setInt(3, userType);
            ps.setInt(4, platform);
            ps.executeUpdate();
        } catch (Exception e) {
            log.error("更新用户修改密码时间出错: " + e.getMessage(), e);
        } finally {
            releaseDB(conn, ps, stmt, rs);
        }
    }

    private void releaseDB(Connection conn, PreparedStatement ps, Statement stmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
