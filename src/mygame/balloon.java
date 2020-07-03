package mygame;

/**
 *
 * @author oat_s
 */
import java.awt.Rectangle;

public final class balloon extends Items{
    public double speed;
    int pop,effsize,colour;
    boolean poped;
    Rectangle bound,arbound;
    balloon(){
        super(0,0,0,0,0,0,"bl");
        reset();
    }
    public void move(){
        y-=speed*z/100;
        x-=Game.wind/2;
        
        if(!poped)height=(int)(actualsize*z/100);
        if(!poped)width=(int)(actualsize*z/100);
        if(y<-300)
            reset();
    }
    public void reset(){
        poped=false;
        pop=0;
        colour=(int)(Math.random()*3);
        z=(Math.random()*55)+25;
        x=Math.random()*(Game.SCREEN_WIDTH-width);
        y=Game.HEIGHT+(Math.random()*500)+200;
        //actualsize=(int) ((Math.random() * 100)+50);
        actualsize=200;
        speed=(Math.random()*4)+1;
    }
    public void hitTest(){
        bound=new Rectangle((int)(x-width/2),(int)(y-height/2),(int)width,(int)height);
        for(Arrow ar:Game.arrow){
            arbound=new Rectangle((int)(ar.x-ar.width/2),(int)(ar.y-ar.height/2),(int)ar.width,(int)ar.height);
            if(bound.intersects(arbound)&&(ar.z<z+20)&&(ar.z>z-2)){
                
                if(poped==false){
                    ar.p.score+=(colour+1)*10;
                    Game.FX.add(new Items(x,y,z+1,20,width,height,"FX"));
                    //Game.bomb.setFramePosition(0);
                    Game.hit++;
                    speed=-speed;
                    pop=1000;
                }
                poped=true;
                //Game.bomb.start();
            }
        }
    }
    public Arrow haveSha(){
        bound=new Rectangle((int)(x-width/2),(int)(y-height/2),(int)width,(int)height-20);
        for(Arrow ar:Game.arrow){
            arbound=new Rectangle((int)(ar.x-ar.width/2),(int)(ar.y-ar.height/2+30),(int)(ar.width),(int)(ar.height));
            if(bound.intersects(arbound)&&(ar.z-z)<10){
                return ar;
            }
        }
        return null;
    }
}