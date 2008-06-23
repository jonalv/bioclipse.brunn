import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class UpdateDBSchema {

	/**
	 * Updates the schema in the database according to the mapping files.
	 * 
	 * @param args  not used
	 */
	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		SchemaExport cfgExp;
		try {
			System.out.println("Updating schema in database...");
			
			cfg.configure();
			cfgExp = new SchemaExport(cfg);
			cfgExp.create(true,true);
			
			System.out.println("Done exporting");
		} 
		catch (org.hibernate.HibernateException e3) {
			e3.printStackTrace();
		}
	}
}
