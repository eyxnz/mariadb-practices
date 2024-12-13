package emaillist.dao;

import emaillist.vo.EmaillistVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmaillistDao {
    public Boolean insert(EmaillistVo vo) {
        boolean result = false;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            String sql = "insert into emaillist values (null, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, vo.getFirstName());
            pstmt.setString(2, vo.getLastName());
            pstmt.setString(3, vo.getEmail());

            int count = pstmt.executeUpdate();

            result = count == 1;
        } catch(SQLException e) {
            System.out.println("error : " + e);
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }

                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<EmaillistVo> findAll() {
        List<EmaillistVo> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "select id, first_name, last_name, email from emaillist order by id desc";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while(rs.next()) {
                Long id =  rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);

                result.add(new EmaillistVo(id, firstName, lastName, email));
            }

        } catch(SQLException e) {
            System.out.println("error : " + e);
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }

                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Boolean deleteByEmail(String email) {
        boolean result = false;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            String sql = "delete from emaillist where email = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);

            int count = pstmt.executeUpdate();

            result = count == 1;
        } catch(SQLException e) {
            System.out.println("error : " + e);
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }

                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Long count() {
        Long result = 0L;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "select count(*) from emaillist";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            if(rs.next()) {
               result =  rs.getLong(1);
            }

        } catch(SQLException e) {
            System.out.println("error : " + e);
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }

                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            String url = "jdbc:mariadb://192.168.0.13:3306/webdb";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch(ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패 : " + e);
        }

        return conn;
    }
}
