package ru.holodec.jascii.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class SaveDialog {
	JFileChooser fileChooser = new JFileChooser();
	String extension;
	
	public SaveDialog(final String fileExtension) {
		extension = fileExtension;
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "*" + fileExtension.toLowerCase() + "," + "*" + fileExtension.toUpperCase();
			}
			
			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) return true;
				if (!file.getName().toLowerCase().endsWith(fileExtension.toLowerCase())) return false;
				return true;
			}
		});
	}
	
	public FileOutputStream getFileStream() {
		File fl = getFile();
		try {
			return new FileOutputStream(fl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public File getFile() {
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File fl = fileChooser.getSelectedFile();
			if (!fl.getName().toLowerCase().endsWith(extension.toLowerCase())) {
				fl = new File(fl.getAbsolutePath()+extension);
			}
 			return fl;
		}
		return null;
	}
}
