package net.bioclipse.brunn.ui.dialogs.createMasterPlateFromSDF;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

public class WizardPage2 extends WizardPage {
	
	private Table table;
	private Combo wellId;
	private Combo plateId;
	private Combo name;

	/**
	 * Create the wizard
	 */
	public WizardPage2() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FormLayout());
		//
		setControl(container);

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER );
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new TableLabelProvider());
		table = tableViewer.getTable();
		final FormData formData_6 = new FormData();
		formData_6.left = new FormAttachment(0, 290);
		formData_6.bottom = new FormAttachment(100, -5);
		formData_6.right = new FormAttachment(100, -5);
		formData_6.top = new FormAttachment(0, 5);
		table.setLayoutData(formData_6);
		tableViewer.setInput(new Object());

		final Group group = new Group(container, SWT.NONE);
		final FormData formData_7 = new FormData();
		formData_7.bottom = new FormAttachment(0, 285);
		formData_7.top = new FormAttachment(0, 20);
		formData_7.right = new FormAttachment(0, 270);
		formData_7.left = new FormAttachment(0, 25);
		group.setLayoutData(formData_7);
		group.setLayout(new FormLayout());

		wellId = new Combo(group, SWT.NONE);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 145);
		formData.top = new FormAttachment(0, 120);
		wellId.setLayoutData(formData);

		plateId = new Combo(group, SWT.NONE);
		formData.right = new FormAttachment(plateId, 0, SWT.RIGHT);
		final FormData formData_1 = new FormData();
		formData_1.top = new FormAttachment(0, 40);
		formData_1.bottom = new FormAttachment(0, 65);
		plateId.setLayoutData(formData_1);

		name = new Combo(group, SWT.NONE);
		final FormData formData_2 = new FormData();
		formData_2.right = new FormAttachment(wellId, 0, SWT.RIGHT);
		formData_2.bottom = new FormAttachment(0, 240);
		formData_2.top = new FormAttachment(0, 215);
		name.setLayoutData(formData_2);

		Label theFieldContainingLabel_2;
		theFieldContainingLabel_2 = new Label(group, SWT.NONE);
		formData_1.left = new FormAttachment(theFieldContainingLabel_2, 0, SWT.LEFT);
		formData_1.right = new FormAttachment(theFieldContainingLabel_2, 0, SWT.RIGHT);
		final FormData formData_3 = new FormData();
		formData_3.top = new FormAttachment(0, 8);
		formData_3.bottom = new FormAttachment(0, 25);
		formData_3.left = new FormAttachment(0, 5);
		theFieldContainingLabel_2.setLayoutData(formData_3);
		theFieldContainingLabel_2.setText("The field containing the plate id is:");

		Label theFieldContainingLabel_1;
		theFieldContainingLabel_1 = new Label(group, SWT.NONE);
		formData.left = new FormAttachment(theFieldContainingLabel_1, 0, SWT.LEFT);
		final FormData formData_4 = new FormData();
		formData_4.top = new FormAttachment(0, 88);
		formData_4.bottom = new FormAttachment(0, 105);
		formData_4.right = new FormAttachment(plateId, 208, SWT.LEFT);
		formData_4.left = new FormAttachment(plateId, 0, SWT.LEFT);
		theFieldContainingLabel_1.setLayoutData(formData_4);
		theFieldContainingLabel_1.setText("The field containing the well id is:");

		Label theFieldContainingLabel;
		theFieldContainingLabel = new Label(group, SWT.NONE);
		formData_2.left = new FormAttachment(theFieldContainingLabel, 0, SWT.LEFT);
		final FormData formData_5 = new FormData();
		formData_5.top = new FormAttachment(0, 173);
		formData_5.bottom = new FormAttachment(0, 190);
		formData_5.left = new FormAttachment(wellId, 0, SWT.LEFT);
		theFieldContainingLabel.setLayoutData(formData_5);
		theFieldContainingLabel.setText("The field containing the name is:");
	}

	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[] { "item_0", "item_1", "item_2" };
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			return element.toString();
		}
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}
}
