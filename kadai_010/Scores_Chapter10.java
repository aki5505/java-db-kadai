package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
    public static void main(String[] args) {

        Connection con = null;
        Statement statement01 = null;
        Statement statement02 = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/Challenge_java",
                    "root",
                    "p@ssw0rd");

            System.out.println("データベース接続成功:" + con.toString());

            // SQLクエリを準備
            statement01 = con.createStatement();
            String sql01 = "UPDATE scores SET score_math = 95,score_english = 80 WHERE id = 5;";

            // SQLクエリを実行（DBMSに送信）
            System.out.println("レコード更新を実行します");
            int rowCnt = statement01.executeUpdate(sql01);
            System.out.println(rowCnt + "件のレコードが更新されました");

            statement02 = con.createStatement();
            String sql02 = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";

            // SQLクエリを実行（DBMSに送信）
            System.out.println("数学・英語の点数が高い順に並び替えました");
            ResultSet result = statement02.executeQuery(sql02);

            // SQLクエリの実行結果を抽出
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int score_math = result.getInt("score_math");
                int score_english = result.getInt("score_english");
                System.out.println(result.getRow() + "件目：生徒id=" + id
                        + "／氏名=" + name + "／数学=" + score_math + "／英語" + score_english);
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
