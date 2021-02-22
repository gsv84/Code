package roto;

public class AI {

    Field fldUser;
    Field fldComp;

    int lastX, lastY;//последние координаты выстрела
    int [] xy; //возвращаемые координаты для выстрела

    boolean preDamage;
    boolean prepreDamage;
    
    int curPlan;
    
    int[] centrB;
    int[] lastFireB;

    //конструктор
    public AI (Field f1, Field f2) {
        fldUser = f1;
        fldComp = f2;

        xy = new int[2];
        preDamage = false;
        prepreDamage = false;
        curPlan = 1;
        
        centrB = new int[2];
        lastFireB = new int[2];
        
        lastX = 0;
        lastY = 0;
    }

    //метод возврата координат выстрела
    public int[] getCoorFire() {
      int[] xy = new int[2];
      do {
        xy = Process();
      }
      while (fldComp.getMaskCell(xy[0], xy[1]) == 'X' || fldComp.getMaskCell(xy[0], xy[1]) == '•');
        return xy;
    }

    //главный метод вычислений
    private int [] Process() {
    
    if (curPlan == 2) {
      String cv = chekVector(centrB[0], centrB[1]);
      if (!cv.equals("non")) { 
        curPlan = 3;
        }
    }
    
    if (curPlan == 3) {
      xy = PlanC(centrB[0], centrB[1]);
      if (xy[0] != -1) return xy;
      curPlan = 1;
    }
     
     if (preDamage == true) {
       curPlan = 2;
       centrB[0] = lastX;
       centrB[1] = lastY;
       }
     
     if (preDamage == false & curPlan == 1) {
    xy = PlanA();
    lastX = xy[0];
    lastY = xy[1];
    return xy;
    }
     
     if (curPlan == 2) {
     
       xy = PlanB(centrB[0], centrB[1]);
       lastX = xy[0];
       lastY = xy[1];
       }
       
     if (xy[0] == -1) {
        xy = PlanA();
        curPlan = 1;
        }


    return xy;
    }
    
    //метод установки флага попадания от предыдущего ввстрела
    public void setPreDamage(boolean b) {
      preDamage = b;
    }

    //метод обстрела по плану А - рандомно
    private int[] PlanA() {
    
    System.out.println("работае план А");
        boolean flag;
        int[] coor = new int[2];
        int x2, y2;
        
        do {
        flag = false;
        x2 = Main.rndCoor();
        y2 = Main.rndCoor();
        
        int[][] arrCoor = {
                 {x2, y2 - 1},
                 {x2 - 1, y2 - 1},
                 {x2 - 1, y2},
                 {x2 - 1, y2 + 1},
                 {x2, y2 + 1},
                 {x2 + 1, y2 + 1},
                 {x2 + 1, y2},
                 {x2 + 1, y2 - 1}
                 };
        
     for(int i = 0; i < 8; i++){
       coor = arrCoor[i];
       if (coor[0] < 0 || coor[0] > 9 ) continue;
          if (coor[1] < 0 || coor[1] > 9) continue;
       if (fldComp.getMaskCell(coor[0], coor[1]) == 'X') flag = true;
     }
     }
     while (flag);
     coor[0] = x2;
     coor[1] = y2;
        return coor;
    }

    //метод обстрела по плану Б - вокруг предыдущего попадания, на входе координаты центра вокруг которого обстрел. возвращает -1 если никуда не стрельнул
    private int[] PlanB(int x, int y) {
      
      System.out.println("работает план Б");
        int[] coor = new int[2];
        //int fr = 0;
       // int lst = 8;
        //String status;
        
        int[][] arraundFire = {
                       {x, y - 1},
                       {x - 1, y},
                       {x, y + 1},
                       {x + 1, y}
                       };
                      
                       
        for (int i = 0; i < 4; i++) {
          coor = arraundFire[i];
          if (coor[0] < 0 || coor[0] > 9 ) continue;
          if (coor[1] < 0 || coor[1] > 9) continue;
          if(fldUser.getHidenCell(coor[0], coor[1]) == 'O' || fldUser.getHidenCell(coor[0], coor[1]) == '*') {
          return coor;
          }
        }
        
        coor[0] = -1;
        coor[1] = -1;
        
        return coor;


    }
    
    
  //метод опрелеления примыкания к кромкам
  private String chekConnect(int x, int y) {
    String status = "non";
    
    if (x == 0) status = "l";
    if (x == 9) status = "r";
    if (y == 0) status = "u";
    if (y == 9) status = "d";
    if (x == 0 & y == 0) status = "lu";
    if (x == 0 & y == 9) status = "ld";
    if (x == 9 & y == 0) status = "ru";
    if (x == 9 & y == 9) status = "rd";
    
    return status;
    
  }
  
  
  //метод проверки наличия вектора
  private String chekVector(int x, int y){
 
  if (y > 0 && fldComp.getMaskCell(x, y - 1) == 'X') return "v";
  
  if (y < 9 && fldComp.getMaskCell(x, y + 1) == 'X') return "v";
  
  if (x > 0 && fldComp.getMaskCell(x - 1, y) == 'X') return "g";
  
  if (x < 9 && fldComp.getMaskCell(x + 1, y) == 'X') return "g";
                       
    return "non";
    
  }
  
  //метод обстрела по плану С. по линии вектора
  private int[] PlanC(int x, int y) {
    
    System.out.println("работает план С");
    
    String cv = chekVector(x, y); 
    int[] xy = new int[2];
    
    if (cv.equals("g")) {
    
    for (int i = 1; i < 4; i++) {
      xy[0] = x + i;
      xy[1] = y;
      if (xy[0] < 10 && fldComp.getMaskCell(xy[0], xy[1]) == '*') return xy;
      if (xy[0] < 10 && fldComp.getMaskCell(xy[0], xy[1]) == '•') break;
      
    }
    
    for (int o = 1; o < 4; o++) {
      xy[0] = x - o;
      xy[1] = y;
      if (xy[0] > -1 && fldComp.getMaskCell(xy[0], xy[1]) == '*') return xy;
      if (xy[0] > -1 && fldComp.getMaskCell(xy[0], xy[1]) == '•') break;
    }
    }
    
    if (cv.equals("v")) {
    
    for (int i = 1; i < 4; i++) {
      xy[0] = x;
      xy[1] = y + i;
      if (xy[1] < 10 && fldComp.getMaskCell(xy[0], xy[1]) == '*') return xy;
      if (xy[1] < 10 && fldComp.getMaskCell(xy[0], xy[1]) == '•') break;
      
    }
    
    for (int o = 1; o < 4; o++) {
      xy[0] = x;
      xy[1] = y - o;
      if (xy[1] > -1 && fldComp.getMaskCell(xy[0], xy[1]) == '*') return xy;
      if (xy[1] > -1 && fldComp.getMaskCell(xy[0], xy[1]) == '•') break;
    }
    }
    
    xy[0] = -1;
    xy[1] = -1;
    return xy;
    
  }




}
