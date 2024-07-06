package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement01 = null;
        Statement statement02 = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/challenge_java",
                    "root",
                    "p@ssw0rd");

            System.out.println("データベース接続成功:" + con.toString());

            // SQLクエリを準備
            String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES " +
                    "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13), " +
                    "(1002, '2023-02-08', 'お疲れ様です！', 12), " +
                    "(1003, '2023-02-09', '今日も頑張ります！', 18), " +
                    "(1001, '2023-02-09', '無理は禁物ですよ！', 17), " +
                    "(1002, '2023-02-10', '明日から連休ですね！', 20);";

            statement01 = con.prepareStatement(sql);
            int rowCnt = 0;

            // SQLクエリを実行（DBMSに送信）
            System.out.println("レコード追加を実行します");
            rowCnt = statement01.executeUpdate();
            System.out.println(rowCnt + "件のレコードが追加されました");

            statement02 = con.createStatement();
            String sql02 = "select posted_at, post_content, likes from posts where user_id = 1002;";

            ResultSet result = statement02.executeQuery(sql02);

            // SQLクエリの実行結果を抽出
            while (result.next()) {
                Date posted_at = result.getDate("posted_at");
                String post_content = result.getString("post_content");
                String likes = result.getString("likes");
                System.out
                        .println(result.getRow() + "件目：投稿日時=" + posted_at + "／投稿内容=" + post_content + "/いいね数" + likes);
            }

        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if (statement01 != null) {
                try {
                    statement01.close();
                } catch (SQLException ignore) {
                }
            }
            if (statement02 != null) {
                try {
                    statement01.close();
                } catch (SQLException ignore) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}
