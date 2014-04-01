package ru.holodec.jascii.processors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ru.holodec.App;
import ru.holodec.jascii.gui.GuiFormHelper;
import ru.holodec.jascii.writer.ResultWriter;

public class ImgProcessorImpl implements ImgProcessor {
	private static final int RECT_SIZE = 6;
	
	private BufferedImage img;
	private GuiFormHelper guiHelper;
	private AcceptedSymbols symbols;
	private ArrayList<String> strings;
	
	ArrayList<ArrayList<Color>> colors;
	
	private void getBeans() {
		guiHelper = (GuiFormHelper)App.context.getBean(GuiFormHelper.PRESENTER_BEAN);
		symbols = (AcceptedSymbols)App.context.getBean(AcceptedSymbols.BEAN_NAME);
	}
	
	private int checkEndX(int x) {
		int x_end = x + RECT_SIZE;
		return x_end >= img.getWidth() ? img.getWidth()-1 : x_end;
	}
	
	private int checkEndY(int y) {
		int y_end = y + RECT_SIZE;
		return y_end >= img.getHeight() ? img.getHeight()-1 :  y_end;
	}
	
	private int getMedianFromRGB(Color c) {
		return ((c.getRed() + c.getGreen() + c.getBlue()) / 3 );
	}
	
	private Color getMedianColor(int x, int y) {
		int x_end = checkEndX(x);
		int y_end = checkEndY(y);
		x_end = checkEndX(x_end);
		y_end = checkEndY(y_end);
		int r = 0;
		int g = 0;
		int b = 0;
		int count = 0;
		for (; x<x_end; x++) {
			for (; y<y_end; y++) {
				Color c = new Color(img.getRGB(x, y));
				r += c.getRed(); g += c.getGreen(); b += c.getBlue();
				count++;
			}
		}
		count = count == 0 ? 1 : count;
		
		return new Color(r / count, g / count, b / count);
	}
	
	private char getChar(Color color, int x, int y) {
		int x_end = checkEndX(x);
		int y_end = checkEndY(y);
		
		BufferedImage bufer = new BufferedImage(RECT_SIZE, RECT_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bufer.getGraphics();
		int max_assist = 0;
		char result = symbols.getSymbols().toCharArray()[0];
		Font font = new Font("SansSerif", Font.BOLD, RECT_SIZE / 2);
		g.setFont(font);
		for (char symb: symbols.getSymbols().toCharArray()) {
			g.setColor(Color.WHITE);
			g.clearRect(0, 0, RECT_SIZE, RECT_SIZE);
			g.setColor(color);
			g.drawString(Character.toString(symb), 0, 0);

			int assist = 0;
			for (int cx = x; cx < x_end; cx++) {
				for (int cy = y; cy < y_end; cy++) {
					Color buferColor = new Color(bufer.getRGB(cx-x, cy-y));
					Color imgColor = new Color(img.getRGB(cx, cy));
					if (Math.abs(getMedianFromRGB(imgColor) - getMedianFromRGB(buferColor)) < 50) {
						assist += 1;
					}
				}
			}
			if (assist > max_assist) {
				max_assist = assist;
				result = symb;
			}
		}
		return result;
	}
	
	private void calcImage() {
		strings = new ArrayList<String>();
		colors = new ArrayList<ArrayList<Color>>();
		
		int row_count = img.getHeight() / RECT_SIZE;
		for (int i = 0; i <= row_count; i++) {
			colors.add(new ArrayList<Color>());
		}
		int row = 0;
		for (int y=0; y<img.getHeight(); y+= RECT_SIZE) {
			StringBuilder sb = new StringBuilder();
			for (int x=0; x<img.getWidth(); x+=RECT_SIZE) {
				Color c = getMedianColor(x, y);
				colors.get(row).add(c);
				sb.append(getChar(c, x, y));
			}
			strings.add(sb.toString());
			row += 1;
			guiHelper.getPresenter().setProcessStatus(Math.round((100.0f/row_count)*row));
		}
		guiHelper.getPresenter().setProcessStatus(100);
	}
	
	private void openImage(File file) throws IOException {
		this.img = ImageIO.read(file);
		if (img == null) { throw new IOException("image not open"); }
	}
	
	public ImgProcessorImpl() {}

	
	public void processImage(File file, ResultWriter writer) {
		getBeans();
		try {
			openImage(file);
		} catch(IOException ex) {
			writer.Write(new String[] {ex.getMessage()}, null);
			return;
		}
		calcImage();
		writer.Write(strings.toArray( new String[0] ), colors);
	}
	
	public String getName() {
		return "Standart algorithm";
	}
}
