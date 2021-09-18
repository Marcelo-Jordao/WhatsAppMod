package net.mcreator.whatsappmod.procedures;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;

import net.mcreator.whatsappmod.gui.CellphoneGUIGuiWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.spongepowered.asm.mixin.Implements;

import java.util.*;

import com.microsoft.playwright.*;
import com.microsoft.playwright.Playwright.CreateOptions;

public class GuiIsOpenProcedure {


	public static BufferedImage buffImage = null;
	//public static byte[] bufino = null;

	public static boolean firstOpen = true;
	public static boolean guiIsOpen = true;
	public static boolean browserIsActive = false;
	public static boolean screenIsOpen = true;
	
	public static void executeProcedure(Map<String, Object> dependencies) {
		
		System.out.println("gui is open");		
		if(browserIsActive == false){
			System.out.println("reachIF");
			new Thread(t1).start();
				//thread.start();
				
				//thread.interrupt();
			browserIsActive = true;
			}
		if(guiIsOpen == true){
			System.out.println("gui is open");
			}
			//guiIsOpen = false;
			
	
	}
	
	public static Runnable t1 = new Runnable() {
		public void run() {
	    	Map<String, String> pathToEnv = new HashMap<String, String>();
			//pathToEnv.put("setEnv", System.getenv("APPDATA").replaceAll("\\\\", "/").concat("/.minecraft/mods/whatsappModFiles") );
			//pathToEnv.put("PLAYWRIGHT_BROWSERS_PATH", "C:\\Users\\USER\\Desktop\\teste");
			pathToEnv.put("PLAYWRIGHT_BROWSERS_PATH",System.getenv("APPDATA").replaceAll("\\\\", "/").concat("/.minecraft/mods/whatsappModFiles") );
			//pathToEnv.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
			//new CreateOptions().setEnv(pathToEnv)
	        try( Playwright playwright = Playwright.create( new CreateOptions().setEnv(pathToEnv) ) ) {

				String sessionSaveStatePath = System.getProperty("java.io.tmpdir").concat("saveWhatsAppModSession");
				String sessionSaveStateFilePath = System.getProperty("java.io.tmpdir").concat("saveWhatsAppModSession/state.json");
				
				try{ Files.createDirectories(Paths.get(sessionSaveStatePath)); }catch(Exception e){ System.out.println(e); }
				
				File stateFileForState = new File(sessionSaveStateFilePath);
				try{ stateFileForState.createNewFile(); }catch(Exception e){System.out.println(e);}
				
	       		System.out.println("reachBrowser");
				BrowserType firefox = playwright.firefox();
				Browser browser = firefox.launch(new BrowserType.LaunchOptions()
	    			.setHeadless(false)
					);
				BrowserContext context = browser.newContext( new Browser.NewContextOptions()
					.setStorageStatePath(Paths.get(sessionSaveStateFilePath))
					.setViewportSize(480, 380) // x2 por causa do GUI scale (será)
					.setDeviceScaleFactor(0.7)
					);
				context.clearPermissions();

				GuiIsOpenProcedure.browserIsActive = true;
				
				Page page = context.newPage();
				page.navigate("https://web.whatsapp.com/");

				page.setViewportSize( (int) (Math.round(480/0.7)) , (int) (Math.round(380/0.7)));
				System.out.println("before page screenshot");
				//page.pause();
				//System.out.println("afterPause");
				boolean continuos = true;
				boolean noRetWhileScreen = false;
				
				//loop infinito de repeticao do playwrite (prototipo)
				while(continuos) {

					if(screenIsOpen == true) {
						noRetWhileScreen = false;
//						byte[] buffer = page.screenshot();
//						//bufino = buffer;
//						//System.out.println("after page screenshot");
//						InputStream is = new ByteArrayInputStream(buffer);
//						BufferedImage newBI = ImageIO.read(is);
//						buffImage = newBI; // é a mesma coisa
						buffImage = ImageIO.read(new ByteArrayInputStream(page.screenshot()));
						
					}else{
						if(noRetWhileScreen == false) {
							context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(sessionSaveStateFilePath)));
							noRetWhileScreen = true;
						}
					}
					//System.out.println("executou");
					
					//Thread.sleep(10);
				}
				
				
				//page.pause();

				//detect when the browser is closed and save the session
				if(browser.contexts().size() == 0){
					System.out.println("browserWasClosed");
					context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(sessionSaveStateFilePath)));
					//page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("C:/Users/USER/Desktop/screenshot.png")));
					GuiIsOpenProcedure.browserIsActive = false;
					}
									
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        //BufferedImage bi = newBI;
	    }
		
	};

}
