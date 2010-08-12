package net.bioclipse.brunn.ui.editors.masterPlateEditor.model;

import java.util.ArrayList;
import java.util.Collection;


public class SampleSetCreater {

    public static Collection<JasperCell> createCollection() {
        Collection<JasperCell> cells = new ArrayList<JasperCell>();
        for ( int i = 0 ; i < 384 ; i++ ) {
            JasperCell cell = new JasperCell();
            if ( i%12 == 0 ) {
                cell.setSubstances( "" );
                cell.setConcentrations( "B" );
                cell.setUnits( "" );
            }
            else {
                cell.setSubstances( "Doxorubicin" );
                cell.setConcentrations( "10" );
                cell.setUnits( "M" );
            }
            cell.setCol( i/16+1 + "" );
            cell.setRow( (char)(i/24 + 'A')+"" );
            cells.add( cell );
        }
        return cells;
    }
}
