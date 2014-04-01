package ru.holodec.jascii.writer;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.holodec.App;
import ru.holodec.jascii.writer.ResultWriter;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class HtmlWriter implements ResultWriter {
	private final int COLOR_GROUP = 15;
	private Configuration cfg;
	private Map root;
	private ArrayList<Color> colorsGroups;
	private ArrayList<Block> blocks;
	
	private class Block {
		public String data;
		public int colorGroup;
		public Block(char initChar, int colorGroup) {
			this.data = Character.toString(initChar);
			this.colorGroup = colorGroup;
		}
		public Block() {
			data = "";
			colorGroup = -1;
		}
	}
	private void initFreeMarker() {
		cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setClassForTemplateLoading(App.class, "/");
	}
	
	private String getHTMLColor(Color c) {
		return String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	private void initModel() {
		root = new HashMap();
		ArrayList<String> htmlStyles = new ArrayList<String>();
		for (Color c: colorsGroups) {
			htmlStyles.add(getHTMLColor(c));
		}
		ArrayList<Map> HtmlBlocks = new ArrayList<Map>();
		for (Block block: blocks) {
			Map blockMap = new HashMap();
			blockMap.put("data", block.data);
			blockMap.put("color", new Integer(block.colorGroup));
			HtmlBlocks.add(blockMap);
		}
		root.put("styles", htmlStyles);
		root.put("blocks", HtmlBlocks);
	}
	
	private boolean isInGroup(Color col1, Color col2) {
		int rDif = Math.abs(col1.getRed()-col2.getRed());
		int gDif = Math.abs(col1.getGreen()-col2.getGreen());
		int bDif = Math.abs(col1.getBlue()-col2.getBlue());
		if (rDif+gDif+bDif>COLOR_GROUP) {return false;} else {return true;}
		
	}
	
	private void GroupColors(String[] results, ArrayList<ArrayList<Color>> colors) {
		int currentBlock = -1;
		colorsGroups = new ArrayList<Color>();
		blocks = new ArrayList<HtmlWriter.Block>();
		
		for (int row = 0; row<results.length; row++) {
			for (int column = 0; column<results[row].length(); column++) {
				boolean flag = false;
				if (currentBlock == -1) {
					colorsGroups.add(colors.get(0).get(0));
					blocks.add(new Block(results[row].charAt(column), 0));
					currentBlock = 0;
					continue;
				}
				
				for (int i = 0; i<colorsGroups.size(); i++) {
					if (isInGroup(colorsGroups.get(i), colors.get(row).get(column))) {
						if ((i == blocks.get(currentBlock).colorGroup) && (column!=0)) {
							blocks.get(currentBlock).data += results[row].charAt(column);
						} else {
							blocks.add(new Block(results[row].charAt(column), i));
							currentBlock++;
						}
						flag = true;
						break;
					}
				}
				if (!flag) {
					colorsGroups.add(colors.get(row).get(column));
					blocks.add(new Block(results[row].charAt(column), colorsGroups.size()-1));
					currentBlock++;
				}
			}
			blocks.add(new Block());
			currentBlock++;
		}
	}
	
	public HtmlWriter() {
		initFreeMarker();
	}

	public void Write(String[] resutls, ArrayList<ArrayList<Color>> colors) {
		GroupColors(resutls, colors);
		initModel();
		try {
			Template temp = cfg.getTemplate("template.html");
			//FileOutputStream stream = new FileOutputStream(new File("output.html"));
			SaveDialog dialog = new SaveDialog(".html");
			FileOutputStream stream = dialog.getFileStream();
			temp.process(root, new OutputStreamWriter(stream)); 
		} catch (IOException | TemplateException ex) {
			System.out.println(ex);
		}
	}

}
