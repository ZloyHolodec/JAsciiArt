package ru.holodec.jascii.writer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ResultWriterImpl implements ResultWriter {

	public ResultWriterImpl() {}

	public void Write(String[] resutls, ArrayList<ArrayList<Color>> colors) {
		int width = resutls[0].length()*16;
		int height = resutls.length*16;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		for (int y = 0; y<resutls.length; y++) {
			for (int x = 0; x<resutls[y].length(); x++) {
				g.setColor(colors.get(y).get(x));
				System.out.println(colors.get(y).get(x).toString());
				g.drawString(Character.toString(resutls[y].charAt(x)), x*16, y*16);
			}
		}
		try {
			ImageIO.write(img, "png", new File("output.png"));
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
