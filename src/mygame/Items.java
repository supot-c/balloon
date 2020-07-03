package mygame;

/**
 *
 * @author oat_s
 */
public class Items {
    public double x,y,z,actualsize,height,width,frame=0;
    String name;
    public Items(double x, double y , double z, double actualsize,double height,double width,String name){
        this.x=x;
        this.y=y;
        this.z=z;
        this.actualsize=actualsize;
        this.height=height;
        this.width=width;
        this.name=name;
    }
}
