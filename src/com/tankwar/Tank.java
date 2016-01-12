package com.tankwar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


public class Tank {
	public int id;//̹�˵�ID��
	public int x,y;//λ������
	public static final int XSPEED=5;//̹�˺����ٶ�
	public static final int YSPEED=5;//̹��������
	public static final int WIDTH=30;//̹�˿���
	public static final int HEIGHT=30;
	
	private   boolean tlive=true;//����̹�˵Ĵ��
	
	private boolean bL=false;
	private boolean bR=false;
	private boolean bU=false;
	private boolean bD=false;
	TankClient tc=null;
	
	private boolean good;
	public Direction dir=Direction.STOP;
	public Direction ptDir=Direction.D;//��Ͳ����
	
	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.good=good;
		
	}
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc){
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
	}
	public void draw(Graphics g){
		if(!tlive) {
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
			//��ʼ�����˸��ǵ����⣬ԭ����û����һ��draw()�ͻ�ˢ��һ�α���ɫ�����ԣ�����ɫ��ˢ��һ�ξ����ˣ����ڴ����Լ���̹�˵�ʱ��ˢ�±���һ�ξ�OK
			 if(good){
				 g.setColor(Color.RED);
			 }else{
				 g.setColor(Color.BLUE);
			 }
			 g.fillOval(x,y,WIDTH,HEIGHT);
			 g.drawString("id="+id,x, y-10);
			 
			 g.setColor(Color.BLACK);//��Ͳ����ɫ����
			 //���������Ͳ�ķ��򣬸���̹�˵�λ��ȷ����ȷ�������㣬��һ�����ߴ�����Ͳ
		 switch(ptDir){
	case L:
		
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
		break;
	case LU:
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
		break;
	case U:
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
		break;
	case RU:
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH,y);
		break;
	case R:
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2);
		break;
	case RD:
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
		break;
	case D:	
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
		break;
	case LD:
		g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
		break;
	case STOP:
		break;
	default:
		break;
		 }
		 move();
	}
	public void move(){
		switch (dir) {
		case L:
			x-=XSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case RU:
			x+=XSPEED;
			y-=YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		case D:	
			y+=YSPEED;
			break;
		case LD:
			x-=XSPEED;
			y+=YSPEED;
			break;
		case STOP:
			break;
		default:
			break;
		}
		if(this.dir!=Direction.STOP){
			this.ptDir=this.dir;
		}
		//����̹�˵Ļ��Χ
		if(x<0 ){x=0;}
		if(y<30) {y=30;}
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH){
			x=TankClient.GAME_WIDTH-Tank.WIDTH;
		}
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) {
			y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		}
		//����Ĵ����ǵ����汾����Ϊ���û���̹���Լ���������
		/*if(!good){
			Direction[] dirs=Direction.values();//��enum�ķ����D�Q�ɔ��M
			if(step==0){
			step=r.nextInt(10)+3;
			int rn=r.nextInt(dirs.length);//�������һ����
			dir=dirs[rn];
			}
			
			step--;
			if(r.nextInt(40)>38){
				this.fire();
			}
		}*/
	}
	
	public void keyPressed(KeyEvent e){

		int key=e.getKeyCode();
		switch (key) {
		//ʹ̹�˶����������÷���
		case KeyEvent.VK_LEFT:
			 bL=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
	 
		default:
			break;
		}
		locateDirection();
	}
	public void keyReleased(KeyEvent e) {

		int key=e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			 fire();
			break;
		//ʹ̹�˶����������÷���
		case KeyEvent.VK_LEFT:
			 bL=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
	 
		default:
			break;
		}
		locateDirection();
	}
	public void locateDirection(){
		Direction oldDir=this.dir;//��¼ԭ���ķ���ֻҪ�����Ըı�͸������ͻ��˷���Ϣ
		if(bL && !bU && !bR && !bD) dir=Direction.L;
		else if(bL && bU && !bR && !bD) dir=Direction.LU;
		else if(!bL && bU && !bR && !bD) dir=Direction.U;
		else if(!bL && bU && bR && !bD) dir=Direction.RU;
		else if(!bL && !bU && bR && !bD) dir=Direction.R;
		else if(!bL && !bU && bR && bD) dir=Direction.RD;
		else if(!bL && !bU && !bR && bD) dir=Direction.D;
		else if(bL && !bU && !bR && bD) dir=Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir=Direction.STOP;
		//�������ı��ˣ��ͷ���һ��̹���ƶ�����Ϣ�������ͻ��ˣ��������˵Ľ�����֪�����λ��
		if(dir!=oldDir){
			TankMoveMsg msg=new TankMoveMsg(id,x,y, dir,ptDir);
			tc.nc.send(msg);
		}
	}
	public Missile fire(){
		if(!tlive){
			return null ;
		}
		int x=this.x+Tank.WIDTH/2-Missile.MWIDTH/2;//�ӵ��ĳ�����λ��
		int y=this.y+Tank.HEIGHT/2-Missile.MHEIGHT/2;
		Missile m=new Missile(id,x,y,this.good,this.ptDir,this.tc);
		tc.missiles.add(m);
		//һ����ͷ���һ���¼�����ӵ���Ϣ��
		MissileNewMsg msg=new MissileNewMsg(m);
		tc.nc.send(msg);
		return m;
		
	}
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	 
	public   boolean isTlive() {
		return tlive;
	}
	 
	public void setTlive(boolean tlive) {
		this.tlive = tlive;
	}
	 
	public boolean isGood() {
		return good;
	}
	 
	public void setGood(boolean good) {
		this.good = good;
	}
	 
}