package mygame;
/**
 *
 * @author oat_s
 */
public class Arrow extends Items{
    public double speed,maxspeed=13 ,Yspeed,rate;
    public final Player p;
    public boolean out;
    public Arrow(Player p,int spdRate){
        super(p.x+p.width/2,p.y+20,100,p.width/2,0,0,"ar");
        out=false;
        this.p=p;
        height=width=actualsize*z/100;
        Yspeed=speed=maxspeed*spdRate/100;
        rate=spdRate;
    }
    public void move(){
        Yspeed-=0.12;
        x-=Game.wind;
        if(z>0)z-=speed*Math.cos(43)/20;
        y+=Yspeed*Math.sin(43)-0.5;
        height=width=actualsize*(z+15)/100;
        
        if(this.x>Game.SCREEN_WIDTH||this.x<0||this.y>Game.HEIGHT+100)
            this.out=true;
    }
}
