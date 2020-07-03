package mygame;

/**
 *
 * @author oat_s
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
        
public class Game extends JPanel implements KeyListener,MouseListener {
    static final int SCREEN_WIDTH=1600,HEIGHT=900;
    public static int hit=0,hp=10;
    public static double shasize=0,wind=Math.random()*0.5-0.25,pos=0;
    public static JLabel hitL,score1=new JLabel(""),score2=new JLabel(""),hplv=new JLabel("");
    private BufferedImage over,menubg,ray,bmb,ex,aim,floor,bg,pop,myPicture,ballpointer[]=new BufferedImage[3],player,player1[]=new BufferedImage[11],player2[]=new BufferedImage[11],heart,life[]=new BufferedImage[11];
    public static ArrayList<balloon> balloon=new ArrayList();
    private ArrayList<balloon> balloon4draw= new ArrayList();
    public static ArrayList<Arrow> arrow=new ArrayList();
    public static ArrayList<Arrow> arrow4draw=new ArrayList();
    public static ArrayList<bomb> bombar=new ArrayList();
    public static ArrayList<bomb> bombar4draw=new ArrayList();
    Player p1=new Player(SCREEN_WIDTH/2-300,HEIGHT-250,200,350,7);
    Player p2=new Player(SCREEN_WIDTH/2+200,HEIGHT-250,200,350,7);
    Font font=new Font("Consolas",1,20);

    public static Clip bomb,bgm;
    public static ArrayList<Items> FX=new ArrayList();
    private ArrayList<Items> items=new ArrayList();
    private ArrayList<Items> map=new ArrayList();
    private String scene;
    ImageIcon me=new ImageIcon("src/pic/map/dimmer.png");
    Game() throws IOException{
        try {
            //bomb=AudioSystem.getClip();
            //bomb.open(AudioSystem.getAudioInputStream(new File("src/sound/bomb.wav")));
            myPicture = ImageIO.read(new File("src/pic/arrow.png"));
            player = ImageIO.read(new File("src/pic/player.png"));
            for(int index=0;index<3;index++)
                ballpointer[index] = ImageIO.read(new File("src/pic/balloon/old/ball"+index+".png"));
            pop=ImageIO.read(new File("src/pic/shadow.png"));
            bg = ImageIO.read(new File("src/pic/menubg.jpg"));
            menubg = ImageIO.read(new File("src/pic/menubg.png"));
            over = ImageIO.read(new File("src/pic/over.png"));
            floor = ImageIO.read(new File("src/pic/map/floor.png"));
            aim = ImageIO.read(new File("src/pic/aim.png"));
            ex=ImageIO.read(new File("src/pic/ex.png"));
            heart=ImageIO.read(new File("src/pic/life.png"));
            ray=ImageIO.read(new File("src/pic/light.png"));
            bmb=ImageIO.read(new File("src/pic/balloon/old/bomb.png"));
            for(int i=0;i<10;i++)
                life[i]=heart.getSubimage(i*103, 0, 103, 101);
            //player

            for(int i=0;i<11;i++){
                player2[i]=player.getSubimage(i*204,0,204,350);
                player1[i]=player.getSubimage(i*204,350,204,350);
            }
        } catch (IOException ex){}
        catch (NullPointerException ex){throw ex;}
        for(int i =25;i>0;i--)
            balloon.add(new balloon());
        hitL=new JLabel();
        hitL.setFont(font);
        score1.setFont(font);
        score2.setFont(font);
        hplv.setFont(font);
        add(score1);
        add(score2);
        add(hplv);
        
        
        //add floor
        for(int i=0;i<100;i+=1)
            map.add(new Items(i,900-i*2,100-i,2000,1600*i/100,1600*i/100,"fl"));
        //create dimmer
        for(int i=0;i<80;i+=4)
            map.add(new Items(0,0,100-i+0.1,1600,900,900,"dm"));
        for(int i =0;i<100;i+=20)
            map.add(new Items(450,0,i,1600,900,900,"lt"));
        
        scene="menu";
    }
    public void move() {
        if(hp<=0)scene="over";
        switch(scene){
            case"game":
                if(balloon.size()<=20)
                    balloon.add(new balloon());
                if(bombar.size()<10)
                    if(Math.random()*1000<(pos+=0.001))
                        bombar.add(new bomb());
                p1.move();
                p2.move();
                bombar4draw=(ArrayList<bomb>) bombar.clone();
                for(bomb bb:bombar4draw)
                    bb.move();
                balloon4draw=(ArrayList<balloon>) balloon.clone();
                for(balloon ball:balloon4draw)
                    ball.move();
                arrow4draw=(ArrayList<Arrow>)arrow.clone();
                arrow4draw.forEach((a) -> {
                    a.move();
                });
                repaint();
                break;
            case"menu":
                repaint();
            case"over":
                repaint();
        }
        
    }
    @Override
    public void paint(Graphics g) {
        switch(scene){
            case"game": items.addAll(FX);
                        items.addAll(balloon4draw);
                        items.addAll(arrow4draw);
                        items.addAll(bombar4draw);
                        items.addAll(map);
                        g.drawImage(bg, 0, 0, SCREEN_WIDTH, HEIGHT, this);
                        g.drawImage(ray, 0, 0, SCREEN_WIDTH, HEIGHT, this);
                        //sort order
                        Collections.sort(items, (Items ths, Items tht) -> ths.z<tht.z?-1:ths.z==tht.z?0:1);
                        //draw sort by z
                        for(Items thing:items){
                            if(thing instanceof bomb){
                                ((bomb) thing).hitTest();
                                g.drawImage(ray, (int)(thing.x-0.5*thing.width), 0,(int)(thing.width-=thing.width/20),HEIGHT,this);
                                g.drawImage(bmb, (int)(thing.x-0.5*thing.width), (int)(thing.y-0.5*thing.height),(int)(thing.width-=thing.width/20),(int)(thing.height-=thing.height/20),this);
                            }
                            if(thing instanceof balloon){
                                balloon ball=(balloon)thing;
                                ball.hitTest();
                                if(ball.pop-->0){
                                    g.drawImage(pop, (int)(ball.x-0.5*ball.width), (int)(ball.y-0.5*ball.height),(int)(ball.width-=ball.width/20),(int)(ball.height-=ball.height/20),this);
                                    g.setColor(Color.white);
                                    g.setFont(font);
                                    g.drawString("+"+(ball.colour+1)*10, (int)(ball.x-5), (int)(ball.y-5));
                                }
                                if(ball.poped==false){
                                    g.drawImage(ballpointer[ball.colour], (int)(ball.x-0.5*ball.width), (int)(ball.y-0.5*ball.height),(int)ball.width,(int)ball.height,this);
                                    if(ball.haveSha()!=null){
                                        Arrow a=ball.haveSha();
                                        g.drawImage(aim, (int)(a.x-0.5*(a.z-ball.z)), (int)(a.y-0.5*(a.z-ball.z)+(a.z-ball.z)),(int)(a.z-ball.z),(int)(a.z-ball.z),this);
                                    }
                                }
                            }
                            else if(thing instanceof Arrow){
                                Arrow a=(Arrow)thing;
                                g.drawImage(myPicture,(int)(a.x-a.width/2),(int)(a.y-a.height/2),(int)a.width,(int)a.height,this);
                            }
                            switch(thing.name){
                                case "fl": g.drawImage(floor, (int) (-100), (int)thing.y+10, (int) (1800), 20, this);break;
                                case "dm": g.drawImage(me.getImage(),(int)thing.x,(int)thing.y,(int)thing.actualsize,900,this);break;
                                case "lt": g.drawImage(ray, (int) thing.x, (int) thing.y,(int)thing.width, (int) thing.height,this);break;
                                case "FX": if(thing.frame<4)thing.frame+=0.25;
                                            g.drawImage(ex.getSubimage((int) ((int)thing.frame*ex.getWidth()/5), 0, ex.getWidth()/5, ex.getHeight()),(int)(thing.x-(thing.width+10)/2),(int)(thing.y-thing.height+10/2),(int)thing.width+10, (int)thing.height+10,this);break;
                            }
                        }
                        Predicate<bomb> poped = (bm) -> bm.poped;
                        bombar.removeIf(poped);
                        Predicate<balloon> ardpop = (bl) -> bl.poped==true&&bl.width<=1;
                        balloon.removeIf(ardpop);
                        Predicate<Arrow> a = (ar) -> ar.out==true;
                        arrow.removeIf(a);
                        Predicate<Items> outframe = (it) -> it.name.equals("FX")&&it.frame==4;
                        FX.removeIf(outframe);
                        g.drawImage(player1[p1.chargeL/10], (int)(p1.x), (int)(p1.y),(int)p1.width,(int)p1.height, this);
                        g.drawImage(player2[p2.chargeL/10], (int)(p2.x), (int)(p2.y),(int)p2.width,(int)p2.height, this);
                        g.drawImage(life[10-hp], SCREEN_WIDTH-150, 60, 100, 100, this);
                        //hitL.setText("HIT: "+String.valueOf(hit)+"   REMAINING: "+balloon.size()+"player1: "+p1.score+" player2: "+p2.score);
                        score1.setText("Player1: "+p1.score);
                        score2.setText("Player2: "+p2.score);
                        hplv.setText(String.valueOf(hp*10));
                        g.setFont(font);
                        g.setColor(Color.white);
                        g.drawString(score1.getText(), SCREEN_WIDTH-(score1.getWidth()+165), 100);
                        g.drawString(score2.getText(), SCREEN_WIDTH-(score2.getWidth()+165), 125);
                        g.drawString(hplv.getText(), SCREEN_WIDTH-(hplv.getWidth()+90), 120);
                        items.clear();
                        break;
            case"menu": g.drawImage(menubg, 0, 0, 1600, 900, this);
                        break;
            case"over": g.drawImage(over, 0, 0,1600,900, this);
                        g.setColor(Color.white);
                        g.setFont(new Font("Consolas",1,100));
                        g.drawString("GAME OVER", SCREEN_WIDTH/2-300, 330);
                        g.setFont(new Font("Consolas",1,50));
                        g.drawString(score1.getText(), SCREEN_WIDTH/2-300, 430);
                        g.drawString(score2.getText(), SCREEN_WIDTH/2-300, 500);
                        break;
        }
    }
    public void addball(){
        balloon.add(new balloon());
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        switch(scene){
            case"game": switch (ke.getKeyChar()) {
                        //case ' ':Game.aA.add(new Arrow(p1));break;
                        case '0':p2.charge=true;break;
                        case ' ':p1.charge=true;break;
                        case 'c':balloon.clear();bombar.clear();break;
                        case '\'':arrow.add(new Arrow(p1,100));break;

                        case 'a':p1.mL=true;break;
                        case 'd':p1.mR=true;break;
                        case '1':wind+=0.1;break;
                        case '2':wind-=0.1;break;
                        case '4':hp--;
                        case '3':bombar.add(new bomb());break;
                        default:addball();break;
                    }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    switch(ke.getKeyCode()){
            case 37 :p2.mL=true;break;
            //case 38 :p1.mU=true;break;
            case 39 :p2.mR=true;break;
            //case 40 :p1.mD=true;break;
        }}

    @Override
    public void keyReleased(KeyEvent ke) {
    switch(ke.getKeyCode()){
            case 37 :p2.mL=false;break;
            case 39 :p2.mR=false;break;
        }
    switch(ke.getKeyChar()){
        case 'a':p1.mL=!true;break;
        case 'd':p1.mR=!true;break;
        
        case '0':arrow.add(new Arrow(p2,p2.chargeL));
        p2.chargeL=0;p2.charge=false;break;
        case ' ':arrow.add(new Arrow(p1,p1.chargeL));
        p1.chargeL=0;p1.charge=false;break;
    }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if("menu".equals(scene)){
            Rectangle b=new Rectangle(1129,646,364,138),m=new Rectangle(me.getX(),me.getY(),10,10);
            if(b.intersects(m))
                scene="game";
        }
        if("over".equals(scene)){
            Rectangle b=new Rectangle(1129,646,364,138),m=new Rectangle(me.getX(),me.getY(),10,10);
            if(b.intersects(m)){
                reset();
                scene="game";
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    private void reset() {
        p1.x=SCREEN_WIDTH/2-300;
        p2.x=SCREEN_WIDTH/2+200;
        hp=10;
        balloon.clear();
        bombar.clear();
        arrow.clear();
        pos=0;
        p1.score=p2.score=0;
    }
}