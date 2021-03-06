package net.bioclipse.brunn.pojos;

public interface LisObjectVisitor {

	public void visit(PlateType plateType);
	public void visit(PlateLayout plateLayout);
	public void visit(MasterPlate masterPlate);
	public void visit(DrugOrigin drugOrigin);
	public void visit(CellOrigin cellOrigin);
	public void visit(Plate plate);
	public void visit(Folder folder);
	public void visit(UniqueFolder folder);
	public void visit(PatientOrigin patientOrigin);
}
