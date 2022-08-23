import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainCanvas extends JPanel implements Runnable{
	int W = 640;
	int H = 480;
	
	Thread runner;
	boolean ativo = true;
	int paintcounter = 0;
	
	BufferedImage imageBuffer;
	byte bufferDeVideo[];
	
	Random rand = new Random();
	
	byte memoriaPlacaVideo[];
	short paleta[][];
	
	int framecount = 0;
	int fps = 0;
	
	Font f = new Font("", Font.PLAIN, 30);
	
	int clickX = 0;
	int clickY = 0;
	int mouseX = 0;
	int mouseY = 0;
	
	int pixelSize = 0;
	int Largura = 0;
	int Altura = 0;
	
	BufferedImage imgtmp = null;
	
	float posx = 100;
	float posy = 100;
	
	boolean LEFT = false;
	boolean RIGHT = false;
	boolean UP = false;
	boolean DOWN = false;

	// Clipping
	int clippingX1 = 50;
	int clippingX2 = 590;
	int clippingY1 = 50;
	int clippingY2 = 430;
	
	public MainCanvas() {
		setSize(640,480);
		setFocusable(true);
		
		Largura = 640;
		Altura = 480;
		
		pixelSize = 640*480;


		
		
		try {
			//imgtmp = ImageIO.read(getClass().getResource("fundo.jpg"));
			imgtmp = ImageIO.read(new File("res/fundo.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);
		//imageBuffer.getGraphics().drawImage(imgtmp, 0, 0, null);
		
		
		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
//		for(int i = 0; i < H;i++) {
//			for(int j = 0; j < W;j++) {
//				int pos = (i*W*4)+(j*4);
//				
//				int soma = bufferDeVideo[pos+1]&0xff;
//				soma += bufferDeVideo[pos+2]&0xff;
//				soma += bufferDeVideo[pos+3]&0xff;
//				
//				int media = soma/3;
//				//System.out.println(""+media);
//				
//				bufferDeVideo[pos+1] = (byte)(Math.min((media*20)/100,255)&0x00ff);
//				bufferDeVideo[pos+2] = (byte)(Math.min((media*60)/100,255)&0x00ff);
//				bufferDeVideo[pos+3] = (byte)(Math.min((media*20)/100,255)&0x00ff);
//			}
//		}
		
		//memoriaPlacaVideo = new byte[W*H];
		
		
		/*paleta = new short[255][3];
		
		for(int i = 0; i < 255;i++){
			paleta[i][0] = (short)rand.nextInt(255);
			paleta[i][1] = (short)rand.nextInt(255);
			paleta[i][2] = (short)rand.nextInt(255);
			
		}*/
		
		//Seta Bugfeer com noise
		/*for(int i = 0; i < bufferDeVideo.length;i+=4){
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			
			bufferDeVideo[i] = (byte)0x00ff;
			bufferDeVideo[i+1] = (byte)(0x00ff&b);
			bufferDeVideo[i+2] = (byte)(0x00ff&g);
			bufferDeVideo[i+3] = (byte)(0x00ff&r);
		}8?
		
//		// 100,20 200,20
//		for(int i = 0; i < 100;i++){
//			int x = 100+i;
//			int y = 20;
//			int bt = x*4+y*640*4;
//			bufferDeVideo[bt] = (byte)0x00ff;
//			bufferDeVideo[bt+1] = (byte)0;
//			bufferDeVideo[bt+2] = (byte)0;
//			bufferDeVideo[bt+3] = (byte)0x00ff;
//		}
		
		/*for(int y = 0; y < H;y++){
			for(int x = 0; x < W;x++){
				memoriaPlacaVideo[x+y*W] = (byte)((y%255)&0x00ff);
			}
		}*/
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_W) {
					UP = false;
				}
				if(key == KeyEvent.VK_S) {
					DOWN = false;
				}
				if(key == KeyEvent.VK_A) {
					LEFT = false;
				}
				if(key == KeyEvent.VK_D) {
					RIGHT = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				//System.out.println("CLICO "+key);
				if(key == KeyEvent.VK_W) {
					UP = true;
				}
				if(key == KeyEvent.VK_S) {
					DOWN = true;
				}
				if(key == KeyEvent.VK_A) {
					LEFT = true;
				}
				if(key == KeyEvent.VK_D) {
					RIGHT = true;
				}
			}
		});		
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				clickX = e.getX();
				clickY = e.getY();
				
				System.out.println("CLICO ");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseX = arg0.getX();
				mouseY = arg0.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
	}
	@Override
	public void paint(Graphics g) {

		for(int i = 0; i < bufferDeVideo.length; i++) {
			bufferDeVideo[i] = 0;
		}

		drawClippingArea(clippingX1, clippingY1, clippingX2, clippingY2);

		//desenhaLinhaHorizontal((int)posx,(int)posy,200);
		//desenhaLinhaHorizontal(10,101,200);
		
		//desenhaLinhaVertical((int)posx,(int)posy,200);

		desenhaLinhaBreseham((int)posx, (int)posy,(int)posx+100, (int)posy+100);
		
		// TODO Auto-generated method stub
		//super.paint(g);
		
//		for(int i = 0; i < bufferDeVideo.length;i+=4){
//			int rr = rand.nextInt(255);
//			int gg = rand.nextInt(255);
//			int bb = rand.nextInt(255);
//			
//			bufferDeVideo[i] = (byte)0x00ff;
//			bufferDeVideo[i+1] = (byte)(0x00ff&bb);
//			bufferDeVideo[i+2] = (byte)(0x00ff&gg);
//			bufferDeVideo[i+3] = (byte)(0x00ff&rr);
//		}
		
		/*for(int i = 0; i < memoriaPlacaVideo.length;i++){
			int bufferindex = i*4;
			bufferDeVideo[bufferindex] = (byte)0x00ff;
			bufferDeVideo[bufferindex+1] = (byte)(paleta[memoriaPlacaVideo[i]&0x00ff][2]&0x00ff);
			bufferDeVideo[bufferindex+2] = (byte)(paleta[memoriaPlacaVideo[i]&0x00ff][1]&0x00ff);
			bufferDeVideo[bufferindex+3] = (byte)(paleta[memoriaPlacaVideo[i]&0x00ff][0]&0x00ff);
		}*/
		
		g.setFont(f);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 640, 480);
//		g.setColor(Color.black);
//		g.drawLine(0, 0, 640, 480);
		
		g.drawImage(imageBuffer,0,0,null);
		
		//g.setColor(Color.BLUE);
		//g.drawLine(clickX, clickY, mouseX, mouseY);
		
		g.setColor(Color.black);
		g.drawString("FPS "+fps, 10, 25);
	}
	
	public void desenhaLinhaHorizontal(int x, int y,int w) {
		byte alpha = (byte)255;
		int pospix = y*(W*4)+x*4;
		
		for(int i = 0; i < w;i++) {
			int currentX = (pospix % (W*4))/4;
			int currentY = pospix / (W*4);

			if(isClipped(currentX, currentY)){
				alpha = (byte)0;
			}

			
			bufferDeVideo[pospix] = alpha;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
			alpha = (byte)255;
		}
	}
	
	public void desenhaLinhaVertical(int x, int y,int h) {
		byte alpha = (byte)255;
		int pospix = y*(W*4)+x*4;
		
		for(int i = 0; i < h;i++) {
			int currentX = (pospix % (W*4))/4;
			int currentY = pospix / (W*4);

			if(isClipped(currentX, currentY)){
				alpha = (byte)0;
			}
			
			bufferDeVideo[pospix] = alpha;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)100;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=(W*4);
			alpha = (byte)255;
		}
	}

	public boolean isClipped(int x, int y) {
		if(x < clippingX1 || x > clippingX2 || y < clippingY1 || y > clippingY2) {
			return true;
		} else {
			return false;
		}
	}

	public void drawClippingArea(int x1, int y1, int x2, int y2){
		int pospix = 0;
		int w,h = 0;

		// Linha horizontal superior
		pospix = y1*(W*4)+x1*4;
		w = x2-x1;
		for(int i = 0; i < w; i++) {
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
		}

		// Linha vertical direita
		pospix = y1*(W*4)+x2*4;
		h = y2-y1;
		for(int i = 0; i < h; i++) {
			bufferDeVideo[pospix] = (byte) 255;
			bufferDeVideo[pospix + 1] = (byte) 0;
			bufferDeVideo[pospix + 2] = (byte) 0;
			bufferDeVideo[pospix + 3] = (byte) 0;
			pospix+=(W*4);
		}

		// Linha horizontal inferior
		pospix = y2*(W*4)+x1*4;
		w = x2-x1;
		for(int i = 0; i < w; i++) {
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
		}

		// Linha vertical esquerda
		pospix = y1*(W*4)+x1*4;
		h = y2-y1;
		for(int i = 0; i < h; i++) {
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=(W*4);
		}
	}

	public void desenhaLinhaBreseham(int x1, int y1, int x2, int y2) {
		/* NOSSA IMPLEMENTAÇÃO */
		int slope;
		int dx, dy, incE, incNE, d, x, y;

		if(x1 > x2) {
			// inverte coordenadas se x1 > x2
			desenhaLinhaBreseham(x2, y2, x1, y1);
			return;
		}
		dx = x2 - x1;
		dy = y2 - y1;

		if(dy < 0) {
			slope = -1;
			dy = -dy;
		} else {
			slope = 1;
		}

		// Bresenham's constant
		incE = 2 * dy;
		incNE = 2 * dy - 2 * dx;
		d = 2 * dy - dx;
		y = y1;

		for(x = x1; x <= x2; x++) {
			byte alpha = (byte)255;
			//int pospix = y*(W*4)+x*4;
			int pospix = y*(W*4)+x*4;

			if(isClipped(x, y)){
				alpha = (byte)0;
			}

			bufferDeVideo[pospix] = alpha;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)100;
			bufferDeVideo[pospix+3] = (byte)0;
			if(d <= 0){
				d += incE;
			} else {
				d += incNE;
				y += slope;
			}
			alpha = (byte)255;
		}
	}
	
	public void start(){
		runner = new Thread(this);
		runner.start();
	}
	
	public void simulaMundo(long diftime){
		
		float difS = diftime/1000.0f;
		float vel = 50;
		
		if(UP) {
			posy -= vel*difS;
		}
		if(DOWN) {
			posy += vel*difS;
		}
		if(LEFT) {
			posx -= vel*difS;
		}
		if(RIGHT) {
			posx += vel*difS;
		}
		
	}
	
	
	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long segundo = time/1000;
		long diftime = 0;
		while(ativo){
			simulaMundo(diftime);
			paintImmediately(0, 0, 640, 480);
			paintcounter+=100;
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long newtime = System.currentTimeMillis();
			long novoSegundo = newtime/1000;
			diftime = System.currentTimeMillis() - time;
			time = System.currentTimeMillis();
			framecount++;
			if(novoSegundo!=segundo) {	
				fps = framecount;
				framecount = 0;
				segundo = novoSegundo;
			}
		}
	}
}
