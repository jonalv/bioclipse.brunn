package net.bioclipse.brunn.pojos;

public enum AnnotationType {
	
	TEXT_ANNOTATION("Text Annotation"),
	FLOAT_ANNOTATION("Float Annotation"),
	ENUM_ANNOTATION("Enum Annotation");
	
	private String type;
	
	private AnnotationType(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
}
