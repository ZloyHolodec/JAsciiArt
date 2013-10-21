package ru.holodec.jascii.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import ru.holodec.App;
import ru.holodec.jascii.processors.ImgProcessor;
import ru.holodec.jascii.processors.ImgProcessorHelper;

public class MainForm extends JFrame implements StatusPresenter {
	private static final long serialVersionUID = 1L;
	private JButton okButton = new JButton("proceed");
	private JProgressBar progressBar = null;
	private final JFileChooser openDialog = new JFileChooser();
	
	private void addButton() {
		this.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int openResult = openDialog.showOpenDialog(MainForm.this);
				if (openResult == JFileChooser.APPROVE_OPTION) {
					File fc = openDialog.getSelectedFile();
					ImgProcessorHelper processorHelper = (ImgProcessorHelper) App.context.getBean(ImgProcessorHelper.PROCESSOR_BEAN);
					processorHelper.getProcessor().processImage(fc);
				}
			}
		});
	}
	
	private void addProgressBar() {
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		this.add(progressBar);
	}
	
	public MainForm() {
		super();
		this.setSize(400, 200);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addButton();
		addProgressBar();
		setVisible(true);
	}

	public void setProcessStatus(int procents) {
		progressBar.setValue(procents);
		progressBar.update(progressBar.getGraphics());
	}

}
