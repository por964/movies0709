package facades;

import dto.MovieDTO;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<MovieDTO> getAllMovies() {
        EntityManager em = getEntityManager();
        List<MovieDTO> movlist = new ArrayList<MovieDTO>();
        try {
            TypedQuery<Movie> query = 
                       em.createQuery("Select m from Movie m",Movie.class);
            List<Movie> movs = query.getResultList();
            movs.stream().forEach(p -> {
            movlist.add(new MovieDTO(p));
        });
            return movlist;
    } finally {
            em.close();
        }
    
    }
    
    //get movie by ID
    /*public MovieDTO getMovieById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            MovieDTO mov = new MovieDTO(movie);
            return mov;
        } finally {
            em.close();
        }
    }*/
    
    
    
    //Get number of movies
    public long getMovieCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long movieCount = (long)em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return movieCount;
        }finally{  
            em.close();
        }
        
    }

    public MovieDTO getMovieById(Long id) {
            EntityManager em = getEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            MovieDTO mov = new MovieDTO(movie);
            return mov;
        } finally {
            em.close();
        }
    }

}
