package project2.dao;

import project2.model.Movie;
import project2.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 04-Sep-16.
 */
public class MovieDAO {

    private Connection connection;

    public MovieDAO(){
        connection = DBUtil.getConnection();
    }

    /*
    Gets all movies from the database
     */
    public List<Movie> getAllMovies(){
        List<Movie> movies = new ArrayList<Movie>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from movies");

            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setName(resultSet.getString("movie_name"));
                movie.setCategory(resultSet.getString("category"));
                movie.setYear(resultSet.getInt("year_released"));
                movie.setDescription(resultSet.getString("description"));
                movies.add(movie);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }


    /*
    Gets the movie specified by the id
     */
    public Movie getMovieById(int id){
        Movie movie = new Movie();
        try{
            PreparedStatement statement = connection.prepareStatement("select * from movies where id = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                movie.setId(resultSet.getInt("id"));
                movie.setName(resultSet.getString("movie_name"));
                movie.setCategory(resultSet.getString("category"));
                movie.setDescription(resultSet.getString("description"));
                movie.setYear(resultSet.getInt("year_released"));
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    /*
   Saves a new movie to the database
    */
    public Movie addMovie(Movie movie){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO MOVIES(movie_name, category, year_released, description) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setString(2, movie.getCategory());
            preparedStatement.setInt(3, movie.getYear());
            preparedStatement.setString(4, movie.getDescription());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            // update the id of the inserted object and return it to the user
            int id = resultSet.getInt(1);
            movie.setId(id);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    /*
    Updates movie with specified id
     */
    public Movie update(Movie movie){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE movies SET movie_name = ?, category = ?, description = ?, year_released = ? WHERE id = ?");
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setString(2, movie.getCategory());
            preparedStatement.setString(3, movie.getDescription());
            preparedStatement.setInt(4, movie.getYear());
            preparedStatement.setInt(5, movie.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    /*
    Deletes movie with specified id
     */
    public boolean delete(int id){
        int success = 0;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM movies WHERE id=?");
            preparedStatement.setInt(1, id);
            success = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success == 1;
    }
}
