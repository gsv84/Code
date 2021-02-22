package roto;

public class Ship {

private int x;
private int y;
private int size;
private String rot;

public Ship() {
  x = 0;
  y = 0;
  size = 0;
  rot = "";
}

public Ship(int xx, int yy, int ss, String r){
  x = xx;
  y = yy;
  size = ss;
  rot = r;
  //System.out.println("обьект шип создан");
}

  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getSize() {
    return size;
  }
  
  public String getRot() {
    return rot;
  }

}
