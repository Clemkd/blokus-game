package utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import entities.CellColor;

public class BufferedHelper 
{
	/**
	 * Obtient les configurations de la librairie de dessin
	 * @return Les configurations
	 */
    public static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public static Font getFontFromFile(String file)
    {
    	Font customFont = null;
		try 
		{
			File fontFile = new File(file);
			customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(12f);
		
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        //register the font
	        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontFile));
	        
		} 
		catch (FontFormatException | IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return customFont;
    }
    
    /**
     * Génère le mask d'une image donnée
     * @param imgSource L'image donnée
     * @param color La couleur du mask
     * @param alpha La transparence du mask
     * @return Le mask de l'image
     */
	public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage image = getGraphicsConfiguration().createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        image.coerceData(true);
        
        Graphics2D g2 = image.createGraphics();

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        return image;
    }
	
	/**
	 * Génère un mask simple
	 * @param width La largeur du mask
	 * @param height La hauteur du mask
	 * @param alpha La transparence du mask
	 * @return Le mask
	 */
	public static BufferedImage generateSampleMask(int width, int height, float alpha)
	{
		BufferedImage b = new BufferedImage(width,  height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = b.createGraphics();
		
		g.setPaint(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, width, height);
		
		g.dispose();
		
		return b;
	}
}
