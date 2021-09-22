
package net.mcreator.whatsappmod.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.HashMap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.mcreator.whatsappmod.procedures.GuiIsOpenProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CellphoneGUIGuiWindow extends ContainerScreen<CellphoneGUIGui.GuiContainerMod> {
	
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	
	public static BufferedImage screenImg = null;
	
	private static boolean screenIsOpen;
	
	public static int initXScreen = 0;
    public static int initYScreen = 0;
    
	public static double mouseXPos = 0;
    public static double mouseYPos = 0;
    
	public static double xPadding = 0.1; // distancia x da GUI ate a margem da tela em porcentagem
    public static double yPadding = 0.1; // distancia y da GUI ate a margem da tela em porcentagem
    
    public static double xGuiPadding = 0.01; // distancia x entre a margem da imagem ate a borda da GUI em porcentagem
    public static double yGuiPadding = 0.015; // distancia y entre a margem da imagem ate a borda da GUI em porcentagem
    public static int pixelsDeCorreçaoX = 1; // devido a diferenca de cor na gui da a impressao que o espacamento da esquerda e maior 
    public static int pixelsDeCorreçaoY = 0; // devido a diferenca de cor na gui da a impressao que o espacamento da esquerda e maior 
	
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = CellphoneGUIGui.guistate;

	
	public CellphoneGUIGuiWindow(CellphoneGUIGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
//		this.xSize = 500;
//		this.ySize = 400;
	}
	private static final ResourceLocation texture = new ResourceLocation("whatsappmod:textures/cellphone_gui.png");

	
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		//System.out.println(Minecraft.getInstance().currentScreen.width +" , "+Minecraft.getInstance().currentScreen.height);
		//System.out.println(screenWidth +" , "+ screenHeight);
		//ambos funcionam, meu monitor padrao é de 840, 494
		
		//initXScreen = 10;
		// é possivel mudar a posicao inicial da tela no render()
		
		mouseXPos = mouseX;
		mouseYPos = mouseY;
		
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		
		initXScreen = (int) (screenWidth*xPadding);
		initYScreen = (int) (screenHeight*yPadding);
		
		int imgWidth = (int) ((1-2*xPadding)*(1-2*xGuiPadding)*screenWidth);
		int imgHeight = (int) ((1-2*yPadding)*(1-2*yGuiPadding)*screenHeight);
		
		BufferedImage loadingImg = null;
		
		if(loadingImg == null) {
			
			// Constructs a BufferedImage of one of the predefined image types.
			loadingImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
	    
	    	// Create a graphics which can be used to draw into the buffered image
	    	Graphics2D g2d = loadingImg.createGraphics();
	    
	    	// fill all the image with white
	    	g2d.setColor(Color.white);
	    	g2d.fillRect(0, 0, imgWidth, imgHeight);
	    		    
//	    	// create a string with yellow
//	    	g2d.setColor(Color.black);
//	    	g2d.drawString("CARREGANDO", 200, 190);
//	    	
//	    	//aviso do key pressed
//	    	//g2d.setColor(Color.black);
//	    	g2d.drawString("essa janela so fecha com esc", 160, 210);
	    	
	    	g2d.setColor(Color.black);
	    	Rectangle rect = new Rectangle(imgWidth, imgHeight-30);
	    	Rectangle rect2 = new Rectangle(imgWidth, imgHeight);
	    	
	    	drawCenteredString(g2d, "CARREGANDO", rect, g2d.getFont());
	    	drawCenteredString(g2d, "essa janela so fecha com esc", rect2, g2d.getFont());
	    	
	 
	    	// Disposes of this graphics context and releases any system resources that it is using. 
	    	g2d.dispose();
	    	//loadingImgExist = true;
		}
		
//	    // Save as PNG
//        File file = new File("C:\\Users\\USER\\Desktop\\myimage.png");
//        try {
//			ImageIO.write(loadingImg, "png", file);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
    	
	    
        //try {
        //	screenImg = GuiIsOpenProcedure.buffImage;
		//	}catch(Exception e){ System.out.println(e); }
		
        
        if(screenImg == null) {
        	screenImg = loadingImg;
        	}
        BufferedImage guiImage = drawGui((int) ((1-2*xPadding)*screenWidth), (int) ((1-2*yPadding)*screenHeight));
        
        BufferedImage finalImage = new BufferedImage((int) ((1-2*xPadding)*screenWidth), (int) ((1-2*yPadding)*screenHeight), BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = finalImage.getGraphics();
        
        
        
        g.drawImage(guiImage, 0, 0, null);
        g.drawImage(screenImg, (int) ((1-2*xPadding)*xGuiPadding*screenWidth) + pixelsDeCorreçaoX, (int) ((1-2*yPadding)*yGuiPadding*screenHeight) + pixelsDeCorreçaoY, null); // mais 
        
        g.dispose();
        
        int w = finalImage.getWidth(null);
        int h = finalImage.getHeight(null);
	    Raster raster = finalImage.getData();
        int[] pixel = new int[4];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                raster.getPixel(x, y, pixel);
                int red = (pixel[2] & 0xFF) << 0;
                int green = (pixel[1] & 0xFF) << 8;
                int blue = (pixel[0] & 0xFF) << 16;
                int alpha = (pixel[3] & 0xFF) << 24;
                //bits[x + y * w] = alpha | red | green | blue; 
                AbstractGui.fill(ms, initXScreen+x, initYScreen+y, initXScreen+x+1, initYScreen+y+1, alpha | red | green | blue);
            }
        } 
        
        if(GuiIsOpenProcedure.keyIsPressed) {
//        	System.out.println( keyPressed );
        	GuiIsOpenProcedure.keyIsPressed = false;
        }
//        if(mouseIsClicked) {
//        	System.out.println(mouseXClick + " , " + mouseYClick + " , " + buttonMouseClick);
//        }
        //System.out.println(mouseX + " , " + mouseY);
        
	}
	
	// funciona para o open procedure
	public static boolean getScreenIsOpen() {
		//System.out.println(screenIsOpen);
		return screenIsOpen;
	}

	/**
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String to draw.
	 * @param rect The Rectangle to center the text in.
	 */
	public void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		
		//se desenhou background do container eh pq a gui ta na tela
		//System.out.println("draw background");
		screenIsOpen = true;
		
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		
		//ScreenManager aa = new ScreenManager();
		//Minecraft.getInstance().currentScreen.height;
		
		
		//imprime o background
//		Minecraft.getInstance().getTextureManager().bindTexture(texture);
//		int k = (this.width - this.xSize) / 2;
//		int l = (this.height - this.ySize) / 2;
//		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
//		RenderSystem.disableBlend();
		
		
	}
	
	
	// desenha o background da GUI por meio do Graphics2d
	public BufferedImage drawGui( int GUIWidth, int GUIHeight ) {
		
		//int GUIWidth = 200;
	    //int GUIHeight = 200;
	 
	    // Constructs a BufferedImage of one of the predefined image types.
	    BufferedImage backgroundGUI = new BufferedImage(GUIWidth, GUIHeight, BufferedImage.TYPE_INT_ARGB);
	 
	    // Create a graphics which can be used to draw into the buffered image
	    Graphics2D g2d = backgroundGUI.createGraphics();
	    
	    g2d.setBackground(new Color(0, 0, 0, 0));
	    
	    // fill all the image with white
	    g2d.setColor(Color.black);
	    Color grayFromGUI = new Color(198,198,198);
	    g2d.setColor(grayFromGUI);
	    g2d.fillRect(0, 0, GUIWidth, GUIHeight);
	    
	    g2d.setColor(Color.white);
	    Stroke stroke = new BasicStroke(3f);
	    g2d.setStroke(stroke);
	    g2d.drawLine(0, 1, GUIWidth, 1);                               // linha horizontal branca superior
	    g2d.drawLine(1, 0, 1, GUIHeight);                              // linha vertical branca esquerda
	    
	    
	    Color grayForSideGUI = new Color(85,85,85);
	    g2d.setColor(grayForSideGUI);
	    g2d.drawLine(GUIWidth-2, 0, GUIWidth-2, GUIHeight);  		   // linha vertical cinza direita
	    g2d.drawLine(0, GUIHeight-2, GUIWidth, GUIHeight-2); 		   // linha horizontal cinza inferior
	    
	    
	    Stroke defaultStroke = new BasicStroke(1f);
	    g2d.setStroke(defaultStroke);
	    
	    
	    g2d.setColor(Color.black);
	    g2d.drawLine(2, 0, GUIWidth-4, 0);               	            // linha superior preta
	    g2d.drawLine(0, 2, 0, GUIHeight-4);			  		            // linha lateral preta esquerda
	    g2d.drawLine(GUIWidth-1, 3, GUIWidth-1, GUIHeight-3);           // linha lateral preta direita
	    g2d.drawLine(3, GUIHeight-1, GUIWidth-3, GUIHeight-1);          // linha inferior preta
	    
	    
	    g2d.drawLine(2, 0, 0, 2);                                       // chanfro lateral esquerdo superior
	    g2d.drawLine(GUIWidth-3, 1, GUIWidth-2, 2);		                // chanfro lateral direito superior
	    g2d.drawLine(1, GUIHeight-3, 2, GUIHeight-2);                   // chanfro lateral esquerdo inferior
	    g2d.drawLine(GUIWidth-3, GUIHeight-1, GUIWidth-1, GUIHeight-3); // chanfro lateral direito inferior
	    
	    
	    g2d.setColor(Color.white);
	    g2d.drawLine(3, 3, 3, 3);	                                    // detalhe branco da GUI
	    
	    g2d.setColor(grayForSideGUI);
	    g2d.drawLine(GUIWidth-4, GUIHeight-4, GUIWidth-4, GUIHeight-4); // detalhe cinza da GUI
	    
	    
	    g2d.setColor(grayFromGUI);
	    g2d.drawLine(2, GUIHeight-3, 2, GUIHeight-3);                   // detalhe dentro do quadrado inferior esquerdo
	    g2d.drawLine(GUIWidth-3, 2, GUIWidth-3, 2);                     // detalhe dentro do quadrado superior direito
	    
	    
	    g2d.clearRect(0, 0, 2, 1);                                      // chanfro esquerdo superior 
	    g2d.clearRect(0, 0, 1, 2);
	    
	    g2d.clearRect(GUIWidth-2, GUIHeight-1, GUIWidth, GUIHeight);    // chanfro direito inferior 
	    g2d.clearRect(GUIWidth-1, GUIHeight-2, GUIWidth, GUIHeight);
	    
	    g2d.clearRect(GUIWidth-3, 0, GUIWidth, 1);                      // chanfro direito superior 
	    g2d.clearRect(GUIWidth-2, 1, GUIWidth, 1);
	    g2d.clearRect(GUIWidth-1, 1, GUIWidth, 2);
	    
	    g2d.clearRect(0, GUIHeight, 4, GUIHeight-1);                    // chanfro esquerdo inferior 
	    g2d.clearRect(0, GUIHeight-1, 3, GUIHeight-2);
	    g2d.clearRect(0, GUIHeight-2, 2, GUIHeight-3);
	    g2d.clearRect(0, GUIHeight-3, 1, GUIHeight-4);
	    
	    g2d.dispose();
	    
	    return backgroundGUI;
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		
		//256 = esc , 69 = e
		
//		if (key == 256) {
//			this.minecraft.player.closeScreen();
//			return true;
//		}
//		return super.keyPressed(key, b, c);
		//System.out.println(key);
		
		// o valor de key esta em caracter ASCII, para converter basta colocar (char) (key)
		
		GuiIsOpenProcedure.keyIsPressed = true;
		GuiIsOpenProcedure.keyPressed = key;
		
		if(key == 256) {
			this.minecraft.player.closeScreen();
			screenIsOpen = false;
			return true;
		}
		return false; 
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		//button é 0 pra L_click, 1 pra R_click e 2 é o mouse3
		GuiIsOpenProcedure.mouseIsClicked = true;
		GuiIsOpenProcedure.mouseXClick = mouseX;
		GuiIsOpenProcedure.mouseYClick = mouseY;
		GuiIsOpenProcedure.buttonMouseClick = button;
		//try { Thread.sleep(5); } catch (InterruptedException e) { }
		//System.out.println(mouseX + " ; " + mouseY + " ; " + button);
		return false;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		
		GuiIsOpenProcedure.mouseIsClicked = false;
		GuiIsOpenProcedure.mouseXClick = 0;
		GuiIsOpenProcedure.mouseYClick = 0;
		GuiIsOpenProcedure.buttonMouseClick = 4;
		
		return false;
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		screenWidth = width;
		screenHeight = height;
		super.init(minecraft, width, height);
		minecraft.keyboardListener.enableRepeatEvents(true);
	}
}
