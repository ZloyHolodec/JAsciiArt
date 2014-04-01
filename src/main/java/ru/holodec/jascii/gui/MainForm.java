package ru.holodec.jascii.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import ru.holodec.App;
import ru.holodec.jascii.processors.ImgProcessor;
import ru.holodec.jascii.processors.ImgProcessorHelper;
import ru.holodec.jascii.writer.ResultWriter;
import ru.holodec.jascii.writer.ResultWriterHelper;

public class MainForm extends JFrame implements StatusPresenter {
	private static final long serialVersionUID = 1L;
	private JButton okButton = new JButton("proceed");
	private JProgressBar progressBar = null;
	private JComboBox<String> processorsBox = new JComboBox<String>();
	private JComboBox<String> writersBox = new JComboBox<String>();
	private ResultWriterHelper writerHelper;
	private ImgProcessorHelper processorHelper;
	private final JFileChooser openDialog = new JFileChooser();
	
	private class onButtonPress implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int openResult = openDialog.showOpenDialog(MainForm.this);
			if (openResult == JFileChooser.APPROVE_OPTION) {
				File fc = openDialog.getSelectedFile();
				ImgProcessor p = (ImgProcessor)App.context.getBean((String)processorsBox.getSelectedItem());
				p.processImage(fc, (ResultWriter)App.context.getBean((String)writersBox.getSelectedItem()));
			}			
		}
	}
	
	private void setBeans() {
		writerHelper = (ResultWriterHelper)App.context.getBean(ResultWriterHelper.WRITER_BEAN);
		processorHelper = (ImgProcessorHelper)App.context.getBean(ImgProcessorHelper.PROCESSOR_BEAN);
	}
	
	private void addBoxes() {
		for (String p: processorHelper.getProcessors()) { processorsBox.addItem(p); }
		for (String w: writerHelper.getWriters()) { writersBox.addItem(w); }
		add(processorsBox);
		add(writersBox);
	}
		
	private void addButton() {
		this.add(okButton);
		okButton.addActionListener(new onButtonPress());
	}
	
	private void addProgressBar() {
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		this.add(progressBar);
	}
	
	public MainForm() {
		this.setSize(400, 200);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addButton();
		addProgressBar();
	}

	public void setProcessStatus(int procents) {
		progressBar.setValue(procents);
		progressBar.update(progressBar.getGraphics());
	}

	public void startWork() {
		setBeans();
		addBoxes();
		setVisible(true);
	}

}
