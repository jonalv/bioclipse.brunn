package net.bioclipse.brunn.ui.editors.plateEditor.model;

import java.util.List;


/**
 * @author jonalv
 *
 */
public class JasperRootBean {

    private List<JasperFunction> functions;
    private List<JasperDiagram>  diagrams;
    
    public void setFunctions( List<JasperFunction> functions ) {
        this.functions = functions;
    }
    
    public List<JasperFunction> getFunctions() {
        return functions;
    }
    
    public void setDiagrams( List<JasperDiagram> diagrams ) {
        this.diagrams = diagrams;
    }
    
    public List<JasperDiagram> getDiagrams() {
        return diagrams;
    }
}
