package mygame;

/**
 *
 * @author oat_s
 */
public class Player{
    public boolean mL=false,mU=false,mR=false,mD=false,charge=false;
    public double tarX,tarY,x,y,width,height,movingSpeed,frame=0;
    public int chargeL=0,score=0;
    public Player(int x,int y,int width,int height,double mSpd){
        tarX=x;
        this.x=x;
        tarY=y;
        this.y=y;
        this.width=width;
        this.height=height;
        movingSpeed=mSpd;
    }
    public void move() {
        x-=(x-(tarX))/2;
        y-=(y-(tarY))/2;
        if(mL&&tarX>0)tarX-=movingSpeed;
        if(mR&&tarX<Game.SCREEN_WIDTH-width-2)tarX+=movingSpeed;
        if(mU&&tarY>0)tarY-=movingSpeed;
        if(mD&&tarY<Game.HEIGHT-height-25)tarY+=movingSpeed;
        frame+=frame>7?-8:0.25;
        if(charge&&chargeL<100)
            chargeL+=2;
    }

}