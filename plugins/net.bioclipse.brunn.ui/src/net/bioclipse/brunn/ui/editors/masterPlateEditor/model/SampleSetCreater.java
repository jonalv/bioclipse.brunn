package net.bioclipse.brunn.ui.editors.masterPlateEditor.model;

import java.util.ArrayList;
import java.util.Collection;


public class SampleSetCreater {

    public static Collection<JasperCell> createCollection() {
        Collection<JasperCell> cells = new ArrayList<JasperCell>();
        for ( char c = 'A' ; c <= 'P' ; c++ ) {
            JasperCell cell = new JasperCell();
            cell.setSubstances( "" );
            cell.setUnits( "" );
            cell.setCol( "0" );
            cell.setConcentrations( c + "" );
            cells.add( cell );
        }
        for ( int i = 0 ; i < 384 ; i++ ) {
            JasperCell cell = new JasperCell();
            if ( i%12 == 0 ) {
                cell.setSubstances( "" );
                cell.setConcentrations( "C1" );
                cell.setUnits( "" );
            }
            else {
                cell.setSubstances( "Doxorubicin" );
                cell.setConcentrations( "10" );
                cell.setUnits( "M" );
            }
            cell.setCol( i/16 + 1 + "" );
            cells.add( cell );
        }
        return cells;
    }
}
