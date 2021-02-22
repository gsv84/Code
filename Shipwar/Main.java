package roto;

import java.util.Scanner;


public class Main {

  public static void main(String[] args) {
   
   System.out.println("    МОРСКОЙ БОЙ\n");
   
   //создаем поле игрока
   Field fldUser = new Field("Игрок");
   System.out.println("Поле игрока создано");
   //вводим корабли игрока
   crtShipMassUser2(fldUser);
   System.out.println("Игрок расставил корабли");
   //создаем поле для компьютера
   Field fldComp = new Field("Комп");
   System.out.println("Поле компьютера создано");
   //создаем и записывем корабли для компьютера
   crtShipMassComp(fldComp);
   System.out.println("Компьютер расставил корабли");
   
   //создаем мозг компьютера
   AI ai = new AI(fldUser, fldComp);
   System.out.println("Мозг компьютера создан..");
   
   boolean temp1;
   
   //далее пошел процесс игры
   int[] xy = new int[2];
   boolean exit = false;
   
   do{
     do{
       //игрок вводит координаты выстрела
       xy = userInput(fldUser);
       }
       //проверка на попадание
   while (chekTarget(xy[0], xy[1], fldUser, fldComp));
  
   //проверка победы
   if (chekWin(fldUser)) 
      {
      System.out.println("Игрок победил!");
      exit = true;
      break;
      }
      
   
   do
     {
    //компьютер вводит координаты выстрела
    //xy = compInputFire();
    xy = ai.getCoorFire();
    
    System.out.println("Компьютер стреляет: " + xy[0] + ", " + xy[1]);
    
    //проверка на попадание
    temp1 = chekTarget(xy[0], xy[1], fldComp, fldUser);
    
    if (temp1) ai.setPreDamage(true);
    else ai.setPreDamage(false);
    }
   while (temp1 == true);
  
   //проверка победы
   if (chekWin(fldComp)) 
      { System.out.println("Компьютер победил!");
      exit = true;
      break;
      }
   }
   while (!exit);
   
   
   
   
   

   
   
   
  }
  
  
  
  
  // метод ввода и создания кораблей на поле игрока
  public static void crtShipMassUser(Field f){
    
    Ship [] shMass = new Ship[10];
    int x, y;
    String r;
    
    //Scanner scan = new Scanner(System.in);
    Scanner scan2 = new Scanner(System.in);
    
    System.out.println("Введите расположение ваших кораблей\n");
    f.printFldHiden();
    
 do {
    System.out.println("4-х палубный корабль(g/v, x, y):");
    r = scan2.nextLine();
    //x = scan.nextInt();
    //y = scan.nextInt();
    x = inpCoor();
    y = inpCoor();
    shMass[0] = new Ship(x, y, 4, r);
    }
 while (!f.setShip(shMass[0]));
    f.printFldHiden();
    
    for(int i = 1; i < 3; i++){
 do {
    System.out.println("3-х палубный корабль" + i + "(g/v, x, y):");
    r = scan2.nextLine();
    //x = scan.nextInt();
    //y = scan.nextInt();
    x = inpCoor();
    y = inpCoor();
    shMass[i] = new Ship(x, y, 3, r);
    }
 while (!f.setShip(shMass[i]));
    f.printFldHiden();
    }
    
    for(int i = 1; i < 4; i++){
 do {
    System.out.println("2-х палубный корабль" + i + "(g/v, x, y):");
    r = scan2.nextLine();
    //x = scan.nextInt();
    //y = scan.nextInt();  
    x = inpCoor();
    y = inpCoor();
    shMass[2 + i] = new Ship(x, y, 2, r);
    }
while (!f.setShip(shMass[2 + i]));
    f.printFldHiden();
    }
    
    for(int i = 1; i < 5; i++){
 do {
    System.out.println("1-х палубный корабль" + i + "(g/v, x, y):");
    r = scan2.nextLine();
    //x = scan.nextInt();
    //y = scan.nextInt();  
    x = inpCoor();
    y = inpCoor();
    shMass[5 + i] = new Ship(x, y, 1, r);
    }
 while (!f.setShip(shMass[5 + i]));
    f.printFldHiden();
    }
   
   //scan.close();
   scan2.close();
   
   
    
  }
  
  
  //метод дает рандом координату
  public static int rndCoor() {
    
    int x;
    
    x = (int) (Math.random() * 10);
    if (x != 0) x -= 1;
    
    return x;
  }
  
  
  //метод дает рандом ротацию
  public static String rndRot() {
    
    int rul = (int) (Math.random() * 10);
    String rot = "g";
    
    for (int i = 0; i < rul; i++){
        if (rot.equals("g")) rot = "v";
        else rot = "g";
    }
    
    return rot;
  }
  
  
  //создаем корабли компьютера
  public static void crtShipMassComp(Field f) {
    
    Ship [] shMass = new Ship[10];
    int x, y;
    String r;
    
    //создаем 4х палуб корабль
 do {
    r = rndRot();
    x = rndCoor();
    y = rndCoor();
    shMass[0] = new Ship(x, y, 4, r);
    }
 while (!f.setShip(shMass[0]));
    
    //создаем 3х палуб корабли
    for(int i = 1; i < 3; i++){
 do {
    r = rndRot();
    x = rndCoor();
    y = rndCoor();
    shMass[i] = new Ship(x, y, 3, r);
    }
 while (!f.setShip(shMass[i]));
    }
    
    //создаем 2х палуб корабли
    for(int i = 1; i < 4; i++){
 do {
    r = rndRot();
    x = rndCoor();
    y = rndCoor();  
    shMass[2 + i] = new Ship(x, y, 2, r);
    }
while (!f.setShip(shMass[2 + i]));
    }
    
    //создаем 1 палубный корабли
    for(int i = 1; i < 5; i++){
 do {
    r = rndRot();
    x = rndCoor();
    y = rndCoor();  
    shMass[5 + i] = new Ship(x, y, 1, r);
    }
 while (!f.setShip(shMass[5 + i]));
    }
    
    
  }
  
  
  //метод хода игрока(ввод координат)
  public static int[] userInput(Field f) {
  
   int [] coor = new int[2];
  
   System.out.println("Ваши корабли");
   f.printFldHiden();
   
   System.out.println("Корабли компьютера");
   f.printFldMask();
   
   System.out.println("Игрок стреляет. Введите координаты");
   
   Scanner scan = new Scanner(System.in);
   
   do{
      if (scan.hasNextInt())
      {
      coor[0] = scan.nextInt();
      }
      }
    while (coor[0] > 9);
    
    do{
       if (scan.hasNextInt())
       {
       coor[1] = scan.nextInt();
       }
       }
     while (coor[1] > 9);
     
     scan.close();
     
   return coor;
    
  }
  
  
  //метод проверки попадания
  public static boolean chekTarget(int x, int y, Field f1, Field f2) {
    
    char ch = f2.getHidenCell(x, y);
    
    if (ch == '*') {
      f1.setMaskCell(x, y, '•');
      f2.setHidenCell(x, y, '•');
      System.out.println(f1.getName() + " не попал!");
      return false;
    }
    
    if (ch == 'O') {
      f1.setMaskCell(x, y, 'X');
      f2.setHidenCell(x, y, 'X');
      System.out.println(f1.getName() + " попал!");
      return true;
    }
    
    
    
    return false;
  }
  
  
  //метод ввода координаты (инт) с клавиатуры
  public static int inpCoor() {
    int c = 0;
    Scanner s = new Scanner(System.in);
    do {
        if (s.hasNextInt()) {
        c = s.nextInt();
        }  
       }
    while (c < 0 & c > 9);
    s.close();
    return c;
    
  }
  
  
  //метод проверки победы. сканирует поле исчитает количество попаданий
  public static boolean chekWin(Field f){
    int count =0;
    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 10; x++) {
        if (f.getMaskCell(x, y) == 'X') count++;
      }
    }
    
    if (count == 20) return true;
    return false;
    
  }
  
  
  //мозг компьютера/ выбор атаки
  public static int[] compInputFire() {
    
    int [] coor = new int[2];
    
    coor[0] = rndCoor();
    coor[1] = rndCoor();
    
    System.out.println("Компьютер стреляет: " + coor[0] + ", " + coor[1]);
    
    return coor;
    
    
  }
  
  
  //метод быстрого создания кораблей для тестирования
  public static void crtShipMassUser2(Field f) {
    
    Ship [] shMass = new Ship [10];
    
    shMass[0] = new Ship(0, 0, 4, "g");
    shMass[1] = new Ship(0, 2, 3, "g");
    shMass[2] = new Ship(0, 4, 3, "g");
    shMass[3] = new Ship(0, 6, 2, "g");
    shMass[4] = new Ship(0, 8, 2, "g");
    shMass[5] = new Ship(6, 1, 2, "v");
    shMass[6] = new Ship(6, 4, 1, "v");
    shMass[7] = new Ship(6, 7, 1, "v");
    shMass[8] = new Ship(8, 4, 1, "v");
    shMass[9] = new Ship(8, 7, 1, "v");
    
    for (int i = 0; i < 10; i++) {
      f.setShip(shMass[i]);
    }
    return ;
    
    
  }
  
  
  
}
