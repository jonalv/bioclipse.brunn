package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.EnumAnnotation;
import net.bioclipse.brunn.pojos.FloatAnnotation;
import net.bioclipse.brunn.pojos.TextAnnotation;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

/**
 * Tests the functionality of the persistant class 
 * <code>Annotation</code>
 * 
 * @author jonathan
 *
 */
public class AnnotationTest {

	@Test
	public void testEqualsAndHashcode() {
		User creator = new User("tester");
		
		Annotation textAnnotation = new Annotation(
				creator, 
				"text", 
				new HashSet<String>(),
				AnnotationType.TEXT_ANNOTATION
		);
		
		Annotation floatAnnotation = new Annotation(
				creator,
				"float",
				new HashSet<String>(),
				AnnotationType.FLOAT_ANNOTATION
		);
		
		Set<String> possibleValues = new HashSet<String>();
		possibleValues.add("red");
		possibleValues.add("green");
		possibleValues.add("blue");
		
		Annotation enumAnnotation = new Annotation(
			creator,
			"enum",
			possibleValues,
			AnnotationType.ENUM_ANNOTATION
		);
		
		
		TextAnnotation textAnnotation1 = new TextAnnotation(creator, "a", textAnnotation);
		TextAnnotation textAnnotation2 = textAnnotation1.deepCopy();
		TextAnnotation textAnnotation3 = new TextAnnotation(User.createUser("other guy"), "b", textAnnotation);
		
		assertEquals(textAnnotation1, textAnnotation2);
		assertEquals(textAnnotation2, textAnnotation1);
		assertFalse(textAnnotation1.equals(textAnnotation3));
		assertFalse(textAnnotation3.equals(textAnnotation1));
		assertEquals(textAnnotation1.hashCode(), textAnnotation2.hashCode());

		EnumAnnotation enumAnnotation1 = new EnumAnnotation(creator, "green", enumAnnotation);
		EnumAnnotation enumAnnotation2 = enumAnnotation1.deepCopy();
		EnumAnnotation enumAnnotation3 = new EnumAnnotation(
				User.createUser("other guy"), "blue", enumAnnotation );
		
		assertEquals(enumAnnotation1, enumAnnotation2);
		assertEquals(enumAnnotation2, enumAnnotation1);
		assertFalse(enumAnnotation1.equals(enumAnnotation3));
		assertFalse(enumAnnotation3.equals(enumAnnotation1));
		assertEquals(enumAnnotation1.hashCode(), enumAnnotation2.hashCode());

		FloatAnnotation floatAnnotation1 = new FloatAnnotation(creator, 3.14, floatAnnotation);
		FloatAnnotation floatAnnotation2 = floatAnnotation1.deepCopy();
		FloatAnnotation floatAnnotation3 = new FloatAnnotation(
				User.createUser("other guy"), 3.1415, floatAnnotation);

		assertEquals(floatAnnotation1, floatAnnotation2);
		assertEquals(floatAnnotation2, floatAnnotation1);
		assertFalse(floatAnnotation1.equals(floatAnnotation3));
		assertFalse(floatAnnotation3.equals(floatAnnotation1));
		assertEquals(floatAnnotation1.hashCode(), floatAnnotation2.hashCode());

	}
	
	@Test
	public void testDeepDelete(){
		
		User tester = new User("tester");
		Annotation annotation = new Annotation(tester, "annotation", null, AnnotationType.FLOAT_ANNOTATION);
		new FloatAnnotation(tester, 23.5, annotation);
		
		assertFalse( annotation.isDeleted() );
		for( AbstractAnnotationInstance a : annotation.getAnnotationInstances() ) {
			assertFalse( a.isDeleted() );
        }
		
		annotation.delete();
		
		assertTrue( annotation.isDeleted() );
		for( AbstractAnnotationInstance a : annotation.getAnnotationInstances() ) {
			assertTrue( a.isDeleted() );
        }
	}
}
