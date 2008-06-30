/**
 * 
 */
package net.bioclipse.brunn.business.annotation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.AbstractAuditableObject;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.EnumAnnotation;
import net.bioclipse.brunn.pojos.FloatAnnotation;
import net.bioclipse.brunn.pojos.TextAnnotation;
import net.bioclipse.brunn.pojos.User;

/**
 * This class handles everything that has to do with the Annotation classes:
 *  AbstractAnnotation
 *  TextAnnotation
 *  EnumAnnotation
 *  FloatAnnotation
 *  MasterAnnotationType
 *  AnnotationType
 *  
 * It should be instantiated as a Spring bean for everything to work.
 *
 * @author jonathan
 *
 */
public class AnnotationManager extends AbstractDAOBasedAnnotationManager
        implements IAnnotationManager {
	
	public void delete(User user, AbstractAnnotationInstance annotationInstance) {
	    
		auditService.audit(
				user, 
				AuditType.UPDATE_EVENT, 
				(AbstractAuditableObject)annotationInstance.getAbstractAnnotatableObject()
		);
		annotationInstanceDAO.delete(annotationInstance);
    }

	public void delete(User user, Annotation annotation) {
	    
		getAuditService().audit(user, AuditType.DELETE_EVENT, annotation);
		annotationDAO.delete(annotation);
    }

	public void edit(User user, Annotation annotation) {
		
		getAuditService().audit(user, AuditType.UPDATE_EVENT, annotation);
		annotation = annotationDAO.merge(annotation);
		this.annotationDAO.save(annotation);
    }

	public AbstractAnnotationInstance getAnnotationInstance(long annotationId) {
		
	    return this.annotationInstanceDAO.getById(annotationId);
    }

	public Annotation getAnnotation(long annotationId) {
	    return this.annotationDAO.getById(annotationId);
    }

	public long createAnnotation( User creator, 
	                              AnnotationType annotationType, 
	                              String name, 
	                              Set<String> possibleValues ) {
		
	    Annotation annotation = 
	    	new Annotation(creator, name, possibleValues, annotationType);
	    this.annotationDAO.save(annotation);
	    
		getAuditService().audit(creator, AuditType.CREATE_EVENT, annotation);
	    
	    return annotation.getId();
    }

	public void edit(User user, AbstractAnnotationInstance annotationInstance) {
	    
		auditService.audit( user, 
				            AuditType.UPDATE_EVENT, 
				            (AbstractAuditableObject)
				            	annotationInstance.getAbstractAnnotatableObject()
				          );
		annotationInstance = annotationInstanceDAO.merge(annotationInstance);
		this.annotationInstanceDAO.save(annotationInstance);
    }

	public long annotate(User user, AbstractAuditableObject object, Annotation annotation, Object value) {
	    
		AbstractAnnotationInstance annotationInstance;
		switch (annotation.getAnnotationType()) {
        case ENUM_ANNOTATION:
	        annotationInstance = new EnumAnnotation(user, (String)value, annotation);
	        break;
        case FLOAT_ANNOTATION:
        	annotationInstance = new FloatAnnotation(user, (Double)value, annotation);
        	break;
        case TEXT_ANNOTATION:
        	annotationInstance = new TextAnnotation(user, (String)value, annotation);
        	break;
        default:
        	throw new IllegalArgumentException(annotation.getAnnotationType() + " unknown annotationType");
        }
		object = (AbstractAuditableObject) annotationDAO.mergeObject(object);
		object.getAbstractAnnotationInstances().add(annotationInstance);
	    annotationInstance.setAbstractAnnotatableObject(object);
	    
	    auditService.audit(user, AuditType.UPDATE_EVENT, object);
	    
	    annotationInstanceDAO.save(annotationInstance);
	    
	    return annotationInstance.getId();
    }

	public Collection<Annotation> getAllAnnotations() {
		return annotationDAO.findAll();
    }

	public Collection<AbstractAnnotationInstance> getAllAnnotationInstances() {
	    return annotationInstanceDAO.findAll();
    }

	/* 
	 * wrapper that can be used when not having special possibleValues
	 */
	public long createAnnotation(User tester, AnnotationType annotationType, String name) {

		return createAnnotation( tester, annotationType, name, new HashSet<String>() );
    }
}
