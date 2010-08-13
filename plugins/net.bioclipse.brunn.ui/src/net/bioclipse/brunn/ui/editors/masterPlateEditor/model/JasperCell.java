package net.bioclipse.brunn.ui.editors.masterPlateEditor.model;


public class JasperCell {

    String substances;
    String concentrations;
    String units;
    String col;
    
    public void setSubstances( String substances ) {
        this.substances = substances;
    }
    public String getSubstances() {
        return substances;
    }
    public void setConcentrations( String concentrations ) {
        this.concentrations = concentrations;
    }
    public String getConcentrations() {
        return concentrations;
    }
    public void setUnits( String units ) {
        this.units = units;
    }
    public String getUnits() {
        return units;
    }
    public void setCol( String col ) {

        this.col = col;
    }
    public String getCol() {

        return col;
    }
}
