package com.sedlacek.ld49.main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.sedlacek.ld49.graphics.Animation;
import com.sedlacek.ld49.graphics.SpriteSheet;

public class XMLReader {

	//private File f;
	private Document doc;
	private Element root;

	public XMLReader(String xmlString, String tag){
		/*try {
			doc = loadXMLFromString(readFile(thisFile()+path, Charset.defaultCharset()));
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.parse( new InputSource( new StringReader( xmlString ) ) );
		} catch (Exception e) {
			e.printStackTrace();
		}


		doc.getDocumentElement().normalize();
		Node node = doc.getElementsByTagName(tag).item(0); ///
		root = (Element) node;
	}

	/*private String thisFile(){
	    String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    absolutePath = absolutePath.replaceAll("%20"," "); // Surely need to do this here
	    return absolutePath;
	}*/

	public Document loadXMLFromString(String xml) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	static String readFile(String path, Charset encoding)
			throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public void loadAnimation(Animation anim){
		setTag(anim.getName());
		int ts = getInt("ts");
		BufferedImage[] imgs = new BufferedImage[root.getElementsByTagName("image").getLength()];
		int[] ws = new int[imgs.length];
		int[] hs = new int[imgs.length];

		SpriteSheet ss = new SpriteSheet(anim.getSpriteSheet());

		for(int i = 0; i < imgs.length; i++){
			imgs[i]=ss.grabImage(getInt("c", i), getInt("r", i), getInt("w", i), getInt("h", i), ts);
			ws[i] = getInt("w", i);
			hs[i] = getInt("h", i);
		}

		anim.setImages(imgs);
		anim.setWidths(ws);
		anim.setHeights(hs);
	}
	
	/*public  Document newDocumentFromInputStream(InputStream in) {
	    DocumentBuilderFactory factory = null;
	    DocumentBuilder builder = null;
	    Document ret = null;

	    try {
	      factory = DocumentBuilderFactory.newInstance();
	      builder = factory.newDocumentBuilder();
	    } catch (ParserConfigurationException e) {
	      e.printStackTrace();
	    }

	    try {
	      ret = builder.parse(new InputSource(in));
	    } catch (SAXException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return ret;
	  }*/

	public String getString(String tag){
		try{
			return root.getElementsByTagName(tag).item(0).getTextContent();
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return "Error";
		}
	}

	public int getInt(String tag){
		try{
			return Integer.parseInt(root.getElementsByTagName(tag).item(0).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return 0;
		}
	}

	public int getInt(String tag, int index){
		try{
			return Integer.parseInt(root.getElementsByTagName(tag).item(index).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return 0;
		}
	}

	public long getLong(String tag){
		try{
			return Long.parseLong(root.getElementsByTagName(tag).item(0).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return 0;
		}
	}

	public long getLong(String tag, int index){
		try{
			return Long.parseLong(root.getElementsByTagName(tag).item(index).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return 0;
		}
	}

	public double getDouble(String tag){
		try{
			return Double.parseDouble(root.getElementsByTagName(tag).item(0).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return 0;
		}
	}

	public float getFloat(String tag){
		try{
			return Float.parseFloat(root.getElementsByTagName(tag).item(0).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return 0;
		}
	}

	public boolean getBoolean(String tag){
		try{
			return Boolean.parseBoolean(root.getElementsByTagName(tag).item(0).getTextContent());
		}catch (Exception e){
			System.err.println("Element '"+tag+"' was not found");
			return false;
		}
	}

	public void setTag(String tag){
		Node node = doc.getElementsByTagName(tag).item(0);
		root = (Element) node;
	}

	public void setTag(String tag, int index){
		Node node = doc.getElementsByTagName(tag).item(index);
		root = (Element) node;
	}
}
