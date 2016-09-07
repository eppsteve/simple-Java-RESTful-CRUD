package project2.webservice;
import com.owlike.genson.Genson;
import project2.dao.MovieDAO;
import project2.model.Movie;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


//Sets the path to base URL + /movies
@Path("/movies")
public class Service {

    private MovieDAO movieDAO = new MovieDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies(){
        List<Movie> movies = movieDAO.getAllMovies();
//        GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(list) {};
//        Response response = Response.ok(entity).build();
//        return response;
        Genson genson = new Genson();
        return genson.serialize(movies);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieById(@PathParam("id") int id){
        Movie movie = movieDAO.getMovieById(id);
        Genson genson = new Genson();
        return genson.serialize(movie);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addMovie(Movie movie){
        Movie mov = movieDAO.addMovie(movie);
        Genson genson = new Genson();
        return genson.serialize(mov);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String update(Movie movie){
        Movie mov = movieDAO.update(movie);
        Genson genson = new Genson();
        return genson.serialize(mov);
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public boolean delete(@PathParam("id") int id){
        return movieDAO.delete(id);
    }
}