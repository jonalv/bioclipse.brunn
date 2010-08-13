package net.bioclipse.brunn.ui.editors.masterPlateEditor.model;

import java.util.ArrayList;
import java.util.Collection;


public class SampleSetCreater {

    public static Collection<JasperCell> create384Collection() {
        return createCollection( 'P', 384 );
    }
    
    public static Collection<JasperCell> create96Collection() {
        return createCollection( 'H', 96 );
    }
    
    public static Collection<JasperCell> createCollection( char toChar, 
                                                           int numOfWells ) {
        Collection<JasperCell> cells = new ArrayList<JasperCell>();
        for ( char c = 'A' ; c <= toChar ; c++ ) {
            JasperCell cell = new JasperCell();
            cell.setSubstances( "" );
            cell.setUnits( "" );
            cell.setCol( "0" );
            cell.setConcentrations( c + "" );
            cells.add( cell );
        }
        for ( int i = 0 ; i < numOfWells ; i++ ) {
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
            cell.setCol( i/(toChar - 'A' + 1 ) + 1 + "" );
            cells.add( cell );
        }
        return cells;
    }
}
