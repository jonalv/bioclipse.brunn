package net.bioclipse.brunn.tests;

import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class LightBaseTest {
	
	protected static ApplicationContext context = 
		new ClassPathXmlApplicationContext("applicationContext.xml");;
	protected Session session;
	protected SessionFactory sessionFactory;
	protected UniqueFolder projects;
	protected UniqueFolder plateTypes;
	protected UniqueFolder plateLayouts;
	protected UniqueFolder compounds;
	protected UniqueFolder cellTypes;
	protected UniqueFolder masterPlates;
	protected Folder       folder;
	
	public LightBaseTest() {
		super();
		sessionFactory = (SessionFactory)context.getBean("sessionFactory");
		session = SessionFactoryUtils.getSession(sessionFactory,true);
	}
	
	protected void setUp() throws Exception {

		net.bioclipse.brunn.tests.Tools.newCleanDatabase();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));	
		
		User tester = new User("tester");
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		userDAO.save(tester);
		
		IUniqueFolderDAO uniqueFolderDAO = (IUniqueFolderDAO)context.getBean("uniqueFolderDAO");
		
		projects = new UniqueFolder(tester, "Projects", "projects");
		uniqueFolderDAO.save(projects);
		
		plateTypes = new UniqueFolder(tester, "Plate Types", "plateTypes");
		uniqueFolderDAO.save(plateTypes);
		
		plateLayouts = new UniqueFolder(tester, "Plate Layouts", "plateLayouts");
		uniqueFolderDAO.save(plateLayouts);
		
		compounds = new UniqueFolder(tester, "Compounds", "compounds");
		uniqueFolderDAO.save(compounds);
		
		cellTypes = new UniqueFolder(tester, "Cell Types", "cellTypes");
		uniqueFolderDAO.save(cellTypes);
		
		masterPlates = new UniqueFolder(tester, "Master Plates", "masterPlates");
		uniqueFolderDAO.save(masterPlates);
		
		folder = new Folder(tester, "folder", projects);
		IFolderDAO folderDAO = (IFolderDAO)context.getBean("folderDAO");
		folderDAO.save(folder);
	}
	
	protected void tearDown() throws Exception {
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}
}
