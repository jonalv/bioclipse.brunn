package net.bioclipse.brunn.business.annotation;

import java.util.Collection;
import java.util.Set;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.AbstractAuditableObject;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.EnumAnnotation;
import net.bioclipse.brunn.pojos.FloatAnnotation;
import net.bioclipse.brunn.pojos.TextAnnotation;
import net.bioclipse.brunn.pojos.User;

/**
 * Definition of the methods in AnnotationtManager. 
 * 
 * @author jonathan
 *
 */
public interface IAnnotationManager {

	public IAuditService getAuditService();
	
	public void delete(User user, AbstractAnnotationInstance annotation);
	public void delete(User user, Annotation annotation);
	
	public void edit( User user, Annotation annotation );
	public void edit( User user, AbstractAnnotationInstance annotation );
	
	public AbstractAnnotationInstance getAnnotationInstance(long annotationInstanceId);
	public Annotation getAnnotation(long annotationId);
	
	public long createAnnotation( User creator, 
	                              AnnotationType annotationType, 
	                              String name, 
	                              Set<String> possibleValues );
	
	public long createAnnotation(User tester, AnnotationType text_annotation, String name);
	
	public void setAuditService(IAuditService auditService);
	public Collection<Annotation> getAllAnnotations();
	public Collection<AbstractAnnotationInstance> getAllAnnotationInstances();
	
	public long annotate(User user, AbstractAuditableObject object, Annotation annotation, Object value);
	
}
