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

public class WizardPage1 extends WizardPage {

	private Text path;
	private String thePath;
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

		path = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		path.setBounds(140, 62, 80, 25);

		final Button browseButton = new Button(container, SWT.NONE);
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
		browseButton.setBounds(256, 58, 60, 30);
	}

	public String getPath() {
		return thePath;
	}
	
	
}
