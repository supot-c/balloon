package mygame;

/**
 *
 * @author oat_s
 */
import java.awt.Rectangle;

public final class bomb extends Items{
    public double speed;
    int pop,effsize;
    boolean poped;
    Rectangle bound,arbound;
    bomb(){
        super(0,0,0,0,0,0,"bm");
        reset();
    }
    public void move(){
        y+=speed*z/100;
        x-=Game.wind/2;
        
        if(!poped)height=(int)(actualsize*z/100);
        if(!poped)width=(int)(actualsize*z/100);
        if(y>Game.HEIGHT+100&&Game.hp>0){
            Game.hp--;
            poped=true;
        }
    }
    public void reset(){
        poped=false;
        z=(Math.random()*55)+25;
        x=Math.random()*(Game.SCREEN_WIDTH-width-200)+100;
        y=-100;
        actualsize=300;
        speed=(Math.random()*1.3)+Game.pos/10;
    }
    public void hitTest(){
        bound=new Rectangle((int)(x-width/2),(int)(y-height/2),(int)width,(int)height);
        for(Arrow ar:Game.arrow){
            arbound=new Rectangle((int)(ar.x-ar.width/2),(int)(ar.y-ar.height/2),(int)ar.width,(int)ar.height);
            if(bound.intersects(arbound)&&(ar.z<z+20)&&(ar.z>z-5)){
                    Game.FX.add(new Items(x,y,z+1,20,width,height,"FX"));
                    poped=true;
            }
        }
    }
}