

package dvdrental;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author Angel
 */
public class ActorHelper {
    
    Session session = null;
    
    public ActorHelper(){
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int insertActor(Actor a){
        int result = 0;
        
        // create SQL statement as a String
        // :fName, :update are placeholders for actual values
        String sql = "insert into actor(first_name, last_name, last_update)"
                + "values (:fName, :update)";
        
        try{
            // starting a transaction if one isn't active
            if(!this.session.getTransaction().isActive()){
                
                // creating an actual query that can be executed
                SQLQuery q = session.createSQLQuery(sql);
                // associating our Actor POJO and table with a query
                q.addEntity(Actor.class);
                
                // binding values to the placeholders in the query
                q.setParameter("fName", a.getFirstName());
                q.setParameter("lName", a.getLastName());
                q.setParameter("update", a.getLastUpdate());
                
                // executing the query
                result = q.executeUpdate();
                
                // commiting the query to the database
                session.getTransaction().commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
