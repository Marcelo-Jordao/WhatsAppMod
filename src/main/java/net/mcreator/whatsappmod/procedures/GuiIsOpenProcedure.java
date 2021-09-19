package net.mcreator.whatsappmod.procedures;

import net.minecraft.util.DamageSource;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import net.mcreator.whatsappmod.gui.CellphoneGUIGuiWindow;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.microsoft.playwright.*;
import com.microsoft.playwright.Mouse.ClickOptions;
import com.microsoft.playwright.Playwright.CreateOptions;
import com.microsoft.playwright.options.MouseButton;

public class GuiIsOpenProcedure {

	// variaveis do browser
	//input de teclado
	public static boolean keyIsPressed = false;
	public static int keyPressed = 0;
	
	//input de mouse
	public static boolean mouseIsClicked = false;
	public static double mouseXClick = 0;
	public static double mouseYClick = 0;
	public static int buttonMouseClick = 0;
	

	public static BufferedImage buffImage = null;


	public static boolean firstOpen = true;
	public static boolean guiIsOpen = true;
	public static boolean browserIsActive = false;
	public static boolean screenIsOpen = true;
	
	public static void executeProcedure(Map<String, Object> dependencies) {
		
		//System.out.println("gui is open");		
		if(browserIsActive == false){
			//System.out.println("reachIF");
			new Thread(t1).start();
				//thread.start();
				
				//thread.interrupt();
			browserIsActive = true;
			}
		//if(guiIsOpen == true){
			//System.out.println("gui is open");
		//	}
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
				
	       		//System.out.println("reachBrowser");
				BrowserType firefox = playwright.firefox();
				Browser browser = firefox.launch(new BrowserType.LaunchOptions()
	    			.setHeadless(true) //////////////////////////////////////////////////////////////////////////// mudar para false aparece o browser
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
				//System.out.println("before page screenshot");
				//page.pause();
				//System.out.println("afterPause");
				boolean noRetWhileScreen = false;
				
				//necessario para mudanca de cordenada
				int initX = CellphoneGUIGuiWindow.initXScreen;
				int initY = CellphoneGUIGuiWindow.initYScreen;
				
				//cordenada com referencia no (0,0) da tela
				//double mouseXClick = CellphoneGUIGuiWindow.mouseXClick;
				//double mouseYClick = CellphoneGUIGuiWindow.mouseYClick;
				
				//loop infinito de repeticao do playwrite (prototipo)
				while(true) {
					
					//imita o mouse na tela (drag e escroll so nao funciona)
					if(mouseIsClicked) {
						//System.out.println("mouse pressionado");
						//Mouse.ClickOptions opt = new Mouse.ClickOptions();
						MouseButton buttonOnClick = null;
						switch (buttonMouseClick) {
						//caso 0
						default:
							buttonOnClick = MouseButton.LEFT;
							break;
						case 1:
							buttonOnClick = MouseButton.RIGHT;
							break;
						case 2:
							buttonOnClick = MouseButton.MIDDLE;
							break;
							}
						//System.out.println(buttonOnClick);
						//page.click("#frame", new Page.ClickOptions().setPosition(mouseXClick-initX, mouseYClick-initY));
						double xScreen = (mouseXClick - (double) (initX))/0.7;
						double yScreen = (mouseYClick - (double) (initY))/0.7;
						// a escala da tela afeta a posicao do mouse da pagina
						
						//System.out.println( xScreen +" , "+ yScreen );
						//System.out.println( xScreen); // +" = "+ mouseXClick +" - "+ initX);
						//System.out.println( yScreen); // +" = "+ mouseYClick +" + "+ initY);
						page.mouse().click(xScreen , yScreen , new Mouse.ClickOptions().setButton(buttonOnClick));
						}
					
					//imita o teclado na tela
					if(keyIsPressed) {
						//System.out.println( (char) keyPressed);
						System.out.println(keyPressed);
						switch(keyPressed) {
						case 259:
							page.keyboard().press("Backspace");
							break;
						case 257:
							page.keyboard().press("Enter");
							break;
						case 340: // L shift
							
							break; 
						case 344: // R shift
							
							break;
						case 258: // tab
							page.keyboard().type("	");
							break;
						case 280: // caps- lock
							
							break;
						case 256: // esc
							break;
							
						default:
							page.keyboard().type( (((char)(keyPressed))+"").toLowerCase() ); // +"" so converte char pra string 
							break;
						}
						keyIsPressed = false;
					}
						
					
					//imprime a tela so se agui estiver aberta, e salva a sessao ao fechar gui
					if(CellphoneGUIGuiWindow.getScreenIsOpen()) {
						noRetWhileScreen = false;
//						byte[] buffer = page.screenshot();
//						//bufino = buffer;
//						//System.out.println("after page screenshot");
//						InputStream is = new ByteArrayInputStream(buffer);
//						BufferedImage newBI = ImageIO.read(is);
//						buffImage = newBI; 
						// é a mesma coisa
						//buffImage = ImageIO.read(new ByteArrayInputStream(page.screenshot()));
						//System.out.println("gui na tela");
						CellphoneGUIGuiWindow.screenImg = ImageIO.read(new ByteArrayInputStream(page.screenshot()));
						
					}else{
						if(noRetWhileScreen == false) {
							System.out.println("session save on while");
							context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(sessionSaveStateFilePath)));
							noRetWhileScreen = true;
						}
					}
					
					//System.out.println("executou");
					
					//intervalo de tempo necessario, se nao o getScreenIsOpen nao retorna valor, ja que a taxa de atualizacao do GuiIsOpen é maior que a da window
					Thread.sleep(5);
				}
				
				
				//page.pause();

				//detect when the browser is closed and save the session
				//if(browser.contexts().size() == 0){
//					System.out.println("session saved");
//					context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(sessionSaveStateFilePath)));
//					//page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("C:/Users/USER/Desktop/screenshot.png")));
//					GuiIsOpenProcedure.browserIsActive = false;
		//			}
									
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        //BufferedImage bi = newBI;
	    }
		
	};

}
