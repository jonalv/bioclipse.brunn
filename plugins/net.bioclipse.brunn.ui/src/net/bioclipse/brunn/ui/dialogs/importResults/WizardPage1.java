package net.bioclipse.brunn.ui.dialogs.importResults;

import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class WizardPage1 extends WizardPage {

	private Text path;
	private String thePath;
	private FormData formData_1;
	/**
	 * Create the wizard
	 */
	public WizardPage1() {
		super("wizardPage");
		this.setPageComplete(false);
		setTitle("Select a file");
		setDescription("When doing Orca results choose the FluoOptima_384.log file");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		//
		setControl(container);
		container.setLayout(new FormLayout());

		path = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		{
		    formData_1 = new FormData();
		    formData_1.bottom = new FormAttachment(100, -53);
		    formData_1.left = new FormAttachment(0, 10);
		    path.setLayoutData(formData_1);
		}

		final Button browseButton = new Button(container, SWT.NONE);
		formData_1.right = new FormAttachment(100, -86);
		{
		    FormData formData = new FormData();
		    formData.left = new FormAttachment(path, 6);
		    formData.top = new FormAttachment(path, -2, SWT.TOP);
		    formData.bottom = new FormAttachment(path, 0, SWT.BOTTOM);
		    formData.right = new FormAttachment(100);
		    browseButton.setLayoutData(formData);
		}
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = 
					new FileDialog( PlatformUI.getWorkbench().getDisplay().getActiveShell() );
				String s = fd.open();
				if(s != null) {
					path.setText(s);
					thePath = s;
					setPageComplete(true);
				}
			}
		});
		browseButton.setText("Browse");
	}

	public String getPath() {
		return thePath;
	}
	
	
}
