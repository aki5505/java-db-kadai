package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement01 = null;
        Statement statement02 = null;

        // ユーザーリスト
        String[][] userList = {
                { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
                { "1002", "2023-02-08", "お疲れ様です！", "12" },
                { "1003", "2023-02-09", "今日も頑張ります！", "18" },
                { "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
                { "1002", "2023-02-10", "明日から連休ですね！", "20" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/challenge_java",
                    "root",
                    "p@ssw0rd");

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
            statement01 = con.prepareStatement(sql);

            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            for (int i = 0; i < userList.length; i++) {

                // SQLクエリの「?」部分をリストのデータに置き換え
                statement01.setString(1, userList[i][0]); // ユーザーID
                statement01.setString(2, userList[i][1]); // 投稿日時
                statement01.setString(3, userList[i][2]); // 投稿内容
                statement01.setString(4, userList[i][3]); // いいね数

                // SQLクエリを実行（DBMSに送信）
                System.out.println("レコード追加:" + statement01.toString());
                rowCnt = statement01.executeUpdate();
                System.out.println(rowCnt + "件のレコードが追加されました");

            }

            statement02 = con.createStatement();
            String sql02 = "select posted_at, post_content, likes from posts;";

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
