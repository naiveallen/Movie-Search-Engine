import java.io.*;
import java.sql.*;

public class populate {

    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    public static final String USER = "scott";
    public static final String PASSWORD = "tiger";

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            deleteData(con);
            populateMovies(con, pstm);
            populateMoviesAndCountries(con, pstm);
            populateMoviesAndGenres(con, pstm);
            populateMoviesAndLocations(con, pstm);
            populateTags(con, pstm);
            populateMoviesAndTags(con, pstm);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
                if (pstm != null) pstm.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    private static void deleteData(Connection con) throws Exception{
        con.prepareStatement("DELETE FROM MOVIE").execute();
        con.prepareStatement("DELETE FROM MOVIE_GENRES").execute();
        con.prepareStatement("DELETE FROM MOVIE_COUNTRIES").execute();
        con.prepareStatement("DELETE FROM MOVIE_LOCATIONS").execute();
        con.prepareStatement("DELETE FROM TAG").execute();
        con.prepareStatement("DELETE FROM MOVIE_TAGS").execute();
        System.out.println("Delete data successfully.");
    }

    private static void populateMovies(Connection con, PreparedStatement pstm) throws Exception {
        String sql = "INSERT INTO MOVIE" +
                    "(id, title, year, rtAllCriticsRating, rtAllCriticsNumReviews, " +
                    "rtTopCriticsRating, rtTopCriticsNumReviews, " +
                    "rtAudienceRating, rtAudienceNumRatings) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";

        pstm = con.prepareStatement(sql);
        BufferedReader in = new BufferedReader(new FileReader("data/movies.dat"));
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] attrs = line.split("\t");

            pstm.setInt(1, Integer.parseInt(attrs[0]));
            pstm.setString(2, attrs[1]);
            pstm.setInt(3, Integer.parseInt(attrs[5]));
            if (attrs[7].equals("\\N")) {
                for (int i = 4; i < 10; i++) {
                    pstm.setString(i, "");
                }
                pstm.execute();
                continue;
            }
            pstm.setDouble(4, Double.parseDouble(attrs[7]));
            pstm.setInt(5, Integer.parseInt(attrs[8]));
            pstm.setDouble(6, Double.parseDouble(attrs[12]));
            pstm.setInt(7, Integer.parseInt(attrs[13]));
            pstm.setDouble(8, Double.parseDouble(attrs[17]));
            pstm.setInt(9, Integer.parseInt(attrs[18]));

            pstm.execute();

        }
        System.out.println("Populate Movies successfully.");

    }



    private static void populateMoviesAndCountries(Connection con, PreparedStatement pstm) throws Exception{
        String sql = "INSERT INTO MOVIE_COUNTRIES(movieID, country) VALUES(?,?)";

        pstm = con.prepareStatement(sql);
        BufferedReader in = new BufferedReader(new FileReader("data/movie_countries.dat"));
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] attrs = line.split("\t");
            pstm.setInt(1, Integer.parseInt(attrs[0]));
            if (attrs.length < 2) {
                pstm.setString(2, "");
            } else {
                pstm.setString(2,attrs[1]);
            }
            pstm.execute();
        }
        System.out.println("Populate MoviesAndCountries successfully.");

    }



    private static void populateMoviesAndGenres(Connection con, PreparedStatement pstm) throws Exception{
        String sql = "INSERT INTO MOVIE_GENRES(movieID, genre) VALUES(?,?)";

        pstm = con.prepareStatement(sql);
        BufferedReader in = new BufferedReader(new FileReader("data/movie_genres.dat"));
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] attrs = line.split("\t");
            pstm.setInt(1, Integer.parseInt(attrs[0]));
            pstm.setString(2,attrs[1]);
            pstm.execute();
        }
        System.out.println("Populate MoviesAndGenres successfully.");
    }

    private static void populateMoviesAndLocations(Connection con, PreparedStatement pstm) throws Exception{
        String sql = "INSERT INTO MOVIE_LOCATIONS(movieID, location1, location2, location3, location4) " +
                    "VALUES(?,?,?,?,?)";

        pstm = con.prepareStatement(sql);
        BufferedReader in = new BufferedReader(new FileReader("data/movie_locations.dat"));
        String line = in.readLine();
        while ((line = in.readLine()) != null) {
            String[] attrs = line.split("\t", -1);
            pstm.setInt(1, Integer.parseInt(attrs[0]));
            switch (attrs.length) {
                case 2:
                    pstm.setString(2, attrs[1]);
                    pstm.setString(3, "");
                    pstm.setString(4, "");
                    pstm.setString(5, "");
                    break;
                case 3:
                    pstm.setString(2, attrs[1]);
                    pstm.setString(3, attrs[2]);
                    pstm.setString(4, "");
                    pstm.setString(5, "");
                    break;
                case 4:
                    pstm.setString(2, attrs[1]);
                    pstm.setString(3, attrs[2]);
                    pstm.setString(4, attrs[3]);
                    pstm.setString(5, "");
                    break;
                case 5:
                    pstm.setString(2, attrs[1]);
                    pstm.setString(3, attrs[2]);
                    pstm.setString(4, attrs[3]);
                    pstm.setString(5, attrs[4]);
                    break;
            }

            pstm.execute();
        }
        System.out.println("Populate MoviesAndLocations successfully.");
    }

    private static void populateTags(Connection con, PreparedStatement pstm) throws Exception{
        String sql = "INSERT INTO TAG(id, value) VALUES(?,?)";

        pstm = con.prepareStatement(sql);
        BufferedReader in = new BufferedReader(new FileReader("data/tags.dat"));
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] attrs = line.split("\t");
            pstm.setInt(1, Integer.parseInt(attrs[0]));
            pstm.setString(2,attrs[1]);
            pstm.execute();
        }
        System.out.println("Populate Tags successfully.");
    }

    private static void populateMoviesAndTags(Connection con, PreparedStatement pstm) throws Exception{
        String sql = "INSERT INTO MOVIE_TAGS(movieID, tagID, tagWeight) VALUES(?,?,?)";

        pstm = con.prepareStatement(sql);
        BufferedReader in = new BufferedReader(new FileReader("data/movie_tags.dat"));
        String line = in.readLine();

        while ((line = in.readLine()) != null) {
            String[] attrs = line.split("\t");
            pstm.setInt(1, Integer.parseInt(attrs[0]));
            pstm.setInt(2, Integer.parseInt(attrs[1]));
            pstm.setInt(3, Integer.parseInt(attrs[2]));

            pstm.execute();
        }
        System.out.println("Populate MoviesAndTags successfully.");
    }


}
