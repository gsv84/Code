package roto;

public class Field {
  
  private char [][] fldHiden;
  private char [][] fldMask;
  String name;
  
  //конструктор делает два массива-поля скрытое для реального расположения кораблей и маска для отображения
  public Field(String n) {
    
   fldHiden = new char[10][10];
   fldMask = new char[10][10];
   name = n;
   
   for(int i = 0; i < fldHiden.length; i++){
     for(int j = 0; j < fldHiden.length; j++){
       fldHiden[i][j] = '*';
     }
   }
   
   for(int i = 0; i < fldMask.length; i++){
     for(int j = 0; j < fldMask.length; j++){
       fldMask[i][j] = '*';
     }
   }
    
  }
  
  //установка знака в ячейку скрытого поля
  public void setHidenCell(int x, int y, char ch) {
  fldHiden[y][x] = ch;
    
         }
   
   //получение знака из ячейки скрытого поля      
  public char getHidenCell(int x, int y) {
    return fldHiden[y][x];
  }
  
  
  //установка знака в ячейку поля маски
  public void setMaskCell(int x, int y, char ch) {
    fldMask[y][x] = ch;
  }
  
  //получение знака из ячейки поля маски
  public char getMaskCell(int x, int y) {
    return fldMask[y][x];
  }
  
  
  //получение имени поля
  public String getName() {
    return name;
  }
  
  
  //распечатка поля хидн
  public void printFldHiden() {
    System.out.print("  ");
    for(int o = 0; o < fldHiden.length; o++){
      System.out.print(o + " ");
    }
    System.out.println();
    
    for (int y = 0; y < fldHiden.length; y++) {
      System.out.print(y + "|");
      for(int x = 0; x < fldHiden.length; x++){
        System.out.print(fldHiden[y][x] + " ");
      }
      System.out.println();
    }
  }  
  
  
  //распечатка поля маски
  public void printFldMask() {
    System.out.print("  ");
    for(int o = 0; o < fldMask.length; o++){
      System.out.print(o + " ");
    }
    System.out.println();
    for (int y = 0; y < fldMask.length; y++) {
      System.out.print(y + "|");
      for(int x = 0; x < fldMask.length; x++){
        System.out.print(fldMask[y][x] + " ");
      }
      System.out.println();
    }
  }
  
  
  //установка коробля в хидн поле
  public boolean setShip(Ship sh){
    
    int x = sh.getX();
    int y = sh.getY();
    int size = sh.getSize();
    String rot = sh.getRot();
    
    // если координаты не в поле или длина корабля выходит за пределы то возврат фальш
    if ((x > 9) || (y > 9)) return false;
    
    if (rot.equals("g") & (x + size - 1) > 9) return false;
    
    if (rot.equals("v") & (y + size - 1) > 9) return false;
    
    if (!rot.equals("g") & !rot.equals("v")) return false;
    
    //запускаем проверку на неправильное расположение кораблей на поле
    if (!chekLay(sh)) return false;
    
    //установка корабля горизонтального
    if (rot.equals("g")) {
    for(int i = x; i < (x + size); i++){
    fldHiden[y][i] = 'O';
    }
    }
    
    if (rot.equals("v")) {
      for (int i = y; i < (y + size); i++) {
        fldHiden[i][x] = 'O';
      }
    }
    
    return true;
    
  }
  
  
  //метод проверки на неправильное расположение нового корабля на поле
  public boolean chekLay(Ship k) {
    
    int x = k.getX();
    int y = k.getY();
    int size = k.getSize();
    String r = k.getRot();
    
    int tx, ty, sufix;
    
    //проверка для горизонтальных кораблей
    if (r.equals("g")) {
      
      if (x == 0) {
         tx = x;
         sufix = 1;
         }
         else {
         tx = x - 1;
         sufix = 2;
         }
         
      if ((x + size) > 9) sufix = 1;
      
      if (y > 0) {
      ty = y - 1;
      for (int i = tx; i < (tx + size + sufix); i++){
        if (fldHiden[ty][i] == 'O') return false;
      }
      }
      
      if (y < 9) {
      ty = y + 1;
      for (int i = tx; i < (tx + size + sufix); i++){
        if (fldHiden[ty][i] == 'O') return false;
        }
        }
        
      if (x > 0 && fldHiden[y][x - 1] == 'O') return false;
      if ((x + size - 1) < 9 && fldHiden[y][x + size] == 'O') return false;
      
    }
    
    //проверка для вертикальных кораблей
    if (r.equals("v")) {
      
      if (y == 0) {
      ty = y;
      sufix = 1;
      }
         else {
         ty = y - 1;
         sufix = 2;
         }
         
      if ((y + size) > 9) sufix = 1;
      
      if (x > 0) {
      tx = x - 1;
      for (int i = ty; i < (ty + size + sufix); i++) {
        if (fldHiden[i][tx] == 'O') return false;
      }
      }
      
      if (x < 9) {
      tx = x + 1;
      for (int i = ty; i < (ty + size + sufix); i++) {
        if (fldHiden[i][tx] == 'O') return false;
      }
      }
      
      if (y > 0 && fldHiden[y - 1][x] == 'O') return false;
      
      if ((y + size - 1) < 9 && fldHiden[y + size][x] == 'O') return false;
      
      
    }
    
    return true;
    
  }


}
