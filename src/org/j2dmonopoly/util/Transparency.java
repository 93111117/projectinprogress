package org.j2dmonopoly.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Transparency
{
	public static BufferedImage makeTransparent(final String path) throws IOException
	{
		final BufferedImage tmpImage = ImageIO.read(new File(path));
		final int h = tmpImage.getHeight(null);
		final int w = tmpImage.getWidth(null);
		final BufferedImage resultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final int transparentColor = tmpImage.getRGB(0, 0);
		for (int y = 0; y < tmpImage.getHeight(null); y++)
		{
			for (int x = 0; x < w; x++)
			{
				int color = tmpImage.getRGB(x, y);
				if (color == transparentColor)
				{
					color = color & 0x00FFFFFF; // clear the alpha flag
				}
				resultImage.setRGB(x, y, color);
			}
		}
		return resultImage;
	}
}
