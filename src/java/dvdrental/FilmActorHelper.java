
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
}
