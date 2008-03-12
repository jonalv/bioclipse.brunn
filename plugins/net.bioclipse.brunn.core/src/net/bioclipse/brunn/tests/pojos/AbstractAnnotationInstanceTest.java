package net.bioclipse.brunn.tests.pojos;

import java.util.HashSet;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.FloatAnnotation;
import net.bioclipse.brunn.pojos.User;

public class AbstractAnnotationInstanceTest {

	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester");
		Annotation annotation = new Annotation(tester, "", new HashSet<String>(), AnnotationType.FLOAT_ANNOTATION);
		
		AbstractAnnotationInstance ai = new FloatAnnotation(tester, 23, annotation);
		
		assertTrue(annotation.getAnnotationInstances().contains(ai));
		assertTrue(ai.getAnnotation() == annotation);
	}
}
