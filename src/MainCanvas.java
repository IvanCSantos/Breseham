import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
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
	
	public MainCanvas() {
		setSize(640,480);
		
		Largura = 640;
		Altura = 480;
		
		pixelSize = 640*480;
		
		
		try {
			//imgtmp = ImageIO.read(getClass().getResource("fundo.jpg"));
			imgtmp = ImageIO.read(new File("../src/fundo.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);
		//iamgeBuffer.getGraphics().drawImage(imgtmp, 0, 0, null);
		
		
		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
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
		
		desenhaLinhaHorizontal(10,100,200);
		desenhaLinhaHorizontal(10,101,200);
		
		desenhaLinhaVertical(300,200,200);
		
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
		int pospix = y*(W*4)+x*4;
		
		for(int i = 0; i < w;i++) {
			
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
		}
	}
	
	public void desenhaLinhaVertical(int x, int y,int h) {
		int pospix = y*(W*4)+x*4;
		
		for(int i = 0; i < h;i++) {
			
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)100;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=(W*4);
		}
	}
	
	public void start(){
		runner = new Thread(this);
		runner.start();
	}
	
	public void simulaMundo(){
		/*if((paintcounter*4)>=bufferDeVideo.length) {
			paintcounter = bufferDeVideo.length/4;
		}
		for(int i = 0; i < paintcounter;i++) {
			int pix = i*4;
			bufferDeVideo[pix] = (byte)255;
			bufferDeVideo[pix+1] = (byte)255;
			bufferDeVideo[pix+2] = (byte)0;
			bufferDeVideo[pix+3] = (byte)0;
		}*/
		
		//Apaga buffer com Branco
		/*for(int i = 0; i < pixelSize;i++) {
			int pos = i*4;
			bufferDeVideo[pos] = (byte)255;
			bufferDeVideo[pos+1] = (byte)255;
			bufferDeVideo[pos+2] = (byte)255;
			bufferDeVideo[pos+3] = (byte)255;
		}*/
		
		//Desenha Linha Horizontal
		/*
		for(int px = clickX; px < mouseX;px += clickX > mouseX ? -1 : 1) {
			int pos = (px*4)+((clickY*Largura)*4);
			bufferDeVideo[pos] = (byte)255;
			bufferDeVideo[pos+1] = (byte)255;
			bufferDeVideo[pos+2] = (byte)0;
			bufferDeVideo[pos+3] = (byte)0;
		}*/
		
		//desenhar cor da paleta por linha
		/*for(int j = 0; j < Altura;j++) {
			for(int i = 0; i < Largura;i++) {
				int pos = (i+j*Largura)*4;
				
				short corpalet[] = paleta[j%255];
				
				bufferDeVideo[pos] = (byte)255;
				bufferDeVideo[pos+1] = (byte)corpalet[0];
				bufferDeVideo[pos+2] = (byte)corpalet[1];
				bufferDeVideo[pos+3] = (byte)corpalet[2];
			}
		}*/
		
		/*
		imageBuffer.getGraphics().drawImage(imgtmp, 0, 0, null);
		
		//desenhar cor da paleta por linha
		for(int j = 0; j < Altura;j++) {
			
			for(int i = 0; i < Largura;i++) {
				int pos = (i+(j*Largura))*4;
				
				short corpalet[] = paleta[j%255];
				
				bufferDeVideo[pos] = (byte)255;
				
				int b = bufferDeVideo[pos+1]&0xff;
				int g = bufferDeVideo[pos+2]&0xff;
				int r = bufferDeVideo[pos+3]&0xff;
				
				//System.out.println(""+(Math.max(bufferDeVideo[pos+1]&0x00ff-1,0))+" "+bufferDeVideo[pos+1]);
				bufferDeVideo[pos+1] = (byte)(((int)(corpalet[0]*0.4 + b*0.6))&0xff);
				bufferDeVideo[pos+2] = (byte)(((int)(corpalet[1]*0.4 + g*0.6))&0xff);
				bufferDeVideo[pos+3] = (byte)(((int)(corpalet[2]*0.4 + r*0.6))&0xff);
			}
		}
		*/
		
	}
	
	
	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long segundo = time/1000;
		while(ativo){
			simulaMundo();
			paintImmediately(0, 0, 640, 480);
			paintcounter+=100;
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long newtime = System.currentTimeMillis();
			long novoSegundo = newtime/1000;
			framecount++;
			if(novoSegundo!=segundo) {	
				fps = framecount;
				framecount = 0;
				segundo = novoSegundo;
			}
		}
	}
}