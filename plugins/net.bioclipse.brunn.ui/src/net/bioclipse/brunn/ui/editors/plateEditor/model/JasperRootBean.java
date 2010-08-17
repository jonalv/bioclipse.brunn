package net.bioclipse.brunn.ui.editors.plateEditor.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author jonalv
 *
 */
public class JasperRootBean {

    private List<JasperFunction> functions;
    private List<JasperDiagram>  diagrams;
    
    public void setFunctions( List<JasperFunction> functions ) {
        Collections.sort(functions, new Comparator<JasperFunction>() {
            public int compare( JasperFunction o1, JasperFunction o2 ) {
                return o1.getName().compareTo( o2.getName() );
            }
        } ); 
        this.functions = functions;
    }
    
    public List<JasperFunction> getFunctions1() {
        return functions.subList( 0, 
                                  (int)Math.round(functions.size() / 4.0) );
    }
    public List<JasperFunction> getFunctions2() {
        return functions.subList( (int)Math.round(functions.size() / 4.0), 
                                  (int)Math.round(functions.size() / 4.0) * 2 );
    }
    public List<JasperFunction> getFunctions3() {
        return functions.subList( (int)Math.round( functions.size() / 4.0 ) * 2, 
                                  (int)Math.round( functions.size() / 4.0) * 3 
        );
    }
    public List<JasperFunction> getFunctions4() {
        return functions.subList( (int)Math.round( functions.size() / 4.0) * 3, 
                                  functions.size() );
    }
    
    public void setDiagrams( List<JasperDiagram> diagrams ) {
        this.diagrams = diagrams;
    }
    
    public List<JasperDiagram> getDiagrams() {
        return diagrams;
    }
}
