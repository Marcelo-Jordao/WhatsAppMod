
package net.mcreator.whatsappmod.gui;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.mcreator.whatsappmod.procedures.GuiIsOpenProcedure;
import net.minecraft.client.Minecraft;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class CellphoneGUIGuiWindow extends ContainerScreen<CellphoneGUIGui.GuiContainerMod> {
	
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
		this.xSize = 500;
		this.ySize = 400;
	}
	private static final ResourceLocation texture = new ResourceLocation("whatsappmod:textures/cellphone_gui.png");

	
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);
		
		int imgWidth = 480;
		int imgHeight = 380;
		
		BufferedImage loadingImg = null;
		
		if(loadingImg == null) {
			
			// Constructs a BufferedImage of one of the predefined image types.
			loadingImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
	    
	    	// Create a graphics which can be used to draw into the buffered image
	    	Graphics2D g2d = loadingImg.createGraphics();
	    
	    	// fill all the image with white
	    	g2d.setColor(Color.white);
	    	g2d.fillRect(0, 0, imgWidth, imgHeight);
	    
	    	// create a string with yellow
	    	g2d.setColor(Color.black);
	    	g2d.drawString("CARREGANDO", 200, 190);
	    	
	    	//aviso do key pressed
	    	g2d.setColor(Color.black);
	    	g2d.drawString("essa janela so fecha com esc", 160, 210);
	 
	    	// Disposes of this graphics context and releases any system resources that it is using. 
	    	g2d.dispose();
	    	//loadingImgExist = true;
		}
		
//	    // Save as PNG
//        File file = new File("C:\\Users\\USER\\Desktop\\myimage.png");
//        try {
//			ImageIO.write(loadingImg, "png", file);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	
	    BufferedImage screenImg = null;
	    
        try {
        	screenImg = GuiIsOpenProcedure.buffImage;
			}catch(Exception e){ System.out.println(e); }
		
        int initXScreen = 180;
        int initYScreen = 55;
        
        if(screenImg == null) {
        	screenImg = loadingImg;
        	}
        
        
        int w = screenImg.getWidth(null);
        int h = screenImg.getHeight(null);
	    Raster raster = screenImg.getData();
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
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
		
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		
		//imprime o background
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(ms, k, l, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
		RenderSystem.disableBlend();
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
		if(key == 256) {
			this.minecraft.player.closeScreen();
			return true;
		}
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
		super.init(minecraft, width, height);
		minecraft.keyboardListener.enableRepeatEvents(true);
	}
}
