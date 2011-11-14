
import java.util.Date;
import org.joda.time.DateTime;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hendro Steven
 */
public class Testing {
    public static void main(String args[]){
        Date tgl = new Date();
        DateTime dt = new DateTime(tgl.toString());
        System.out.println(dt.toString("yyyy-MM-dd"));
    }
}
