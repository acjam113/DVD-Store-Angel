
package dvdrental;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import java.sql.Timestamp;
import java.util.List;
/**
 *
 * @author Angel
 */
public class FilmActorHelper {
    
    Session session = null;
    
    public FilmActorHelper(){
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List getActors(){
        
        // setting up local variable that will be used to return
        // a list of languages
        List<Actor> actorList = null;
        
        // creating out query as a String
        String sql = "select * from actor";
        
        // if the current transaction isn't active, begin one
        try{
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);
            
            // associating the language POJO and table with the query
            q.addEntity(Actor.class);
            
            // executing the query
            actorList = (List<Actor>) q.list();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return actorList;
    }
    
    public List getCategories(){
        
        List<Category> categoryList = null;
        
        String sql = "select * from catagory";
        
        try{
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(Category.class);
            
            categoryList = (List<Category>)q.list();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return categoryList;
    }
    
    public List getLanguages(){
        
        List<Language> languageList = null;
        
        String sql = "select * from language";
        
        try{
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(Language.class);
            
            languageList = (List<Language>)q.list();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return languageList;
    }
    
    // this method is going to insert a film into the film table
    private int insertFilm(String title, String description, int language,
            String rating, Timestamp timeStamp){
        
        int result = 0;
        
        String sql = "insert into film "
                + "(title, description, language_id, rental_duration, rental_rate, "
                + "replacement_cost, rating, last_update) "
                + "values (:title, :description, :languageId, :rentalDuration, "
                + ":rentalRate, :replacementCost, :rating, :update)";
        
        try{
            // if the current transaction isn't active, begin one
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(Film.class);
            
            q.setParameter("title", title);
            q.setParameter("description", description);
            q.setParameter("languageId", language);
            q.setParameter("rentalDuration", 3);
            q.setParameter("rentalRate", 4.99);
            q.setParameter("replacementCost", 19.99);
            q.setParameter("rating", rating);
            q.setParameter("update", timeStamp);
            
            result = q.executeUpdate();
            
            session.getTransaction().commit();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        
        return result;
    }
    
    // this method is going to return the film id of the last film inserted
    // into the film table
    private int getFilmId(){
        
        List<Film> filmList = null;
        
        String sql = "select * from film order by last_update desc limit 1";
        
        try{
            // if the current transaction isn't active, begin one
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(Film.class);
            
            filmList = (List<Film>) q.list();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return filmList.get(0).getFilmId();
    }
    
    // this method is going to insert a film actor into the film actor table
    private int insertFilmActor(int actor, int film, Timestamp timeStamp){
        int result = 0;
        
        String sql = "insert into film_actor values (:actorId, :filmId, :update)";
        
        try{
            // if the current transaction isn't active, begin one
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(FilmActor.class);
            
            q.setParameter("actorId", actor);
            q.setParameter("filmId", film);
            q.setParameter("update", timeStamp);
            
            result = q.executeUpdate();
            
            session.getTransaction().commit();
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    // this method is going to insert a film category into the film category table
    private int insertFilmCategory(int film, int category, Timestamp timeStamp){
        
        int result = 0;
        
        String sql = "insert into film_category values (:filmId, :categoryId, :update)";
        
        try{
            // if the current transaction isn't active, begin one
            if(!this.session.getTransaction().isActive()){
                session.beginTransaction();
            }
            
            // creating actual query that will be executed against the database
            SQLQuery q = session.createSQLQuery(sql);
            
            q.addEntity(FilmCategory.class);
            
            q.setParameter("categoryId", category);
            q.setParameter("filmId", film);
            q.setParameter("update", timeStamp);
            
            result = q.executeUpdate();
            
            session.getTransaction().commit();
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int insert (String title, String description, int actor, int category,
            int language, String rating, Timestamp timeStamp){
        
        int result = 0;
        
        int filmResult = insertFilm(title, description, language, rating, timeStamp);
        int filmId = getFilmId();
        int actorResult = insertFilmActor(actor, filmId, timeStamp);
        int categoryResult = insertFilmCategory(filmId, category, timeStamp);
        
        if(filmResult == 1 && actorResult == 1 && categoryResult == 1){
            result = 1;
        }
        
        return result;
    }
}
