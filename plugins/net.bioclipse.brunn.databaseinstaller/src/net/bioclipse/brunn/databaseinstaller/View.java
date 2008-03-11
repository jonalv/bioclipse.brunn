package net.bioclipse.brunn.databaseinstaller;

import net.bioclipse.brunn.Springcontact;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {

	private Text passwordText;
	private Text usernameText;
	private Text urlText;
	private ProgressBar progressBar;
	public static final String ID 
		= "net.bioclipse.brunn.databaseinstaller.view";

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		final Label urlLabel = new Label(parent, SWT.NONE);
		FormData fd_urlLabel;
		fd_urlLabel = new FormData();
		fd_urlLabel.top = new FormAttachment(0, 53);
		urlLabel.setLayoutData(fd_urlLabel);
		urlLabel.setText("URL:");

		Label usernameLabel;
		usernameLabel = new Label(parent, SWT.NONE);
		fd_urlLabel.left = new FormAttachment(usernameLabel, 0, SWT.LEFT);
		final FormData fd_usernameLabel = new FormData();
		fd_usernameLabel.top = new FormAttachment(0, 123);
		usernameLabel.setLayoutData(fd_usernameLabel);
		usernameLabel.setText("Username:");

		Label passwordLabel;
		passwordLabel = new Label(parent, SWT.NONE);
		fd_usernameLabel.left = new FormAttachment(passwordLabel, 0, SWT.LEFT);
		final FormData fd_passwordLabel = new FormData();
		fd_passwordLabel.left = new FormAttachment(0, 72);
		fd_passwordLabel.top = new FormAttachment(0, 210);
		passwordLabel.setLayoutData(fd_passwordLabel);
		passwordLabel.setText("Password:");

		urlText = new Text(parent, SWT.BORDER);
		urlText.setText("localhost/brunn");
		final FormData fd_urlText = new FormData();
		fd_urlText.left = new FormAttachment(usernameLabel, 5, SWT.RIGHT);
		fd_urlText.right = new FormAttachment(100, -62);
		fd_urlText.top = new FormAttachment(urlLabel, -27, SWT.BOTTOM);
		urlText.setLayoutData(fd_urlText);

		usernameText = new Text(parent, SWT.BORDER);
		usernameText.setText("admin");
		final FormData fd_usernameText = new FormData();
		fd_usernameText.right = new FormAttachment(urlText, 0, SWT.RIGHT);
		fd_usernameText.top = new FormAttachment(usernameLabel, -27, SWT.BOTTOM);
		fd_usernameText.left = new FormAttachment(urlText, 0, SWT.LEFT);
		usernameText.setLayoutData(fd_usernameText);

		passwordText = new Text(parent, SWT.BORDER);
		passwordText.setText("masterkey");
		final FormData fd_text_2 = new FormData();
		fd_text_2.right = new FormAttachment(usernameText, 0, SWT.RIGHT);
		fd_text_2.top = new FormAttachment(passwordLabel, -27, SWT.BOTTOM);
		fd_text_2.left = new FormAttachment(usernameText, 0, SWT.LEFT);
		passwordText.setLayoutData(fd_text_2);

		final Button performSetupButton = new Button(parent, SWT.NONE);
		performSetupButton.addSelectionListener(new SelectionAdapter() {
			/*
			 * Perform Setup pushed
			 */
			public void widgetSelected(final SelectionEvent e) {
				progressBar.setVisible(true);
				setupDataBase();
			}
		});
		final FormData fd_performSetupButton = new FormData();
		fd_performSetupButton.bottom = new FormAttachment(100, -13);
		fd_performSetupButton.right = new FormAttachment(100, -14);
		performSetupButton.setLayoutData(fd_performSetupButton);
		performSetupButton.setText("Perform Setup");

		progressBar = new ProgressBar(parent, SWT.NONE);
		progressBar.setVisible(false);
		final FormData fd_progressBar = new FormData();
		fd_progressBar.right = new FormAttachment(performSetupButton, 0, SWT.RIGHT);
		fd_progressBar.bottom = new FormAttachment(performSetupButton, -5, SWT.DEFAULT);
		progressBar.setLayoutData(fd_progressBar);
		initializeToolBar();
	}

	protected void setupDataBase() {
		
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
	private void initializeToolBar() {
		IToolBarManager toolBarManager 
			= getViewSite().getActionBars().getToolBarManager();
	}
}