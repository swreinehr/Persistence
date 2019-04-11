import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
public class Pers {

  static final BigInteger TWO   = new BigInteger("2");
  static final BigInteger THREE = new BigInteger("3");
  static final BigInteger FIVE  = new BigInteger("5");
  static final BigInteger SEVEN = new BigInteger("7");
  static class Comp implements Comparator<int[]>{
    double l2 = Math.log(2);
    double l3 = Math.log(3);
    double l5 = Math.log(5);
    double l7 = Math.log(7);
    public double lo(int[] x){
      return l2*x[0]+l3*x[1]+l5*x[2]+l7*x[3];
    }
    @Override
    public int compare(int[] o1, int[] o2) {
      int x = Double.compare(lo(o1),lo(o2));
      if(x!=0)return x;
      for(int i=0;i<4;++i){
        x = Integer.compare(o1[i],o2[i]);
        if(x!=0)return x;
      }
      return 0;
    }
  }
  public static void main(String[] args) throws Exception{
    //go through all powers of 2, 3, and 7
    long s = System.nanoTime();
    int md = Integer.parseInt(args[0]);
    System.setErr(new PrintStream(new File(md+"error")));
    System.setOut(new PrintStream(new File(md+"out")));
    TreeMap<int[],BigInteger> ds = new TreeMap<int[],BigInteger>(new Comp());
    ArrayList<BigInteger> p7s = new ArrayList<>();
    ArrayList<BigInteger> p5s = new ArrayList<>();
    ArrayList<BigInteger> p3s = new ArrayList<>();
    ArrayList<BigInteger> p2s = new ArrayList<>();
    BigInteger p7= BigInteger.ONE;
    while(p7.toString().length()<md){
      p7s.add(p7);
      p7 = p7.multiply(SEVEN);
    }
    BigInteger p5= BigInteger.ONE;
    while(p5.toString().length()<md){
      p5s.add(p5);
      p5 = p5.multiply(FIVE);
    }
    BigInteger p3= BigInteger.ONE;
    while(p3.toString().length()<md){
      p3s.add(p3);
      p3 = p3.multiply(THREE);
    }
    BigInteger p2= BigInteger.ONE;
    while(p2.toString().length()<md){
      p2s.add(p2);
      p2 = p2.multiply(TWO);
    }
    int[][][] vals732 = new int[p7s.size()][][];
    int[][][] vals735 = new int[p7s.size()][][];
    int po7 = 0;
    //index 7 3 5 2
    while(po7<p7s.size()){
      int po3=0;
      ArrayList<int[]>in732 = new ArrayList<>();
      ArrayList<int[]>in735 = new ArrayList<>();
      System.out.println(String.format("%d th power of 7 reached in %d time",po7,(System.nanoTime()-s)));
      while(po3<p3s.size()){
	ArrayList<Integer>inin732 = new ArrayList<>();
        ArrayList<Integer>inin735 = new ArrayList<>();
        BigInteger m73 = p7s.get(po7).multiply(p3s.get(po3));
        if(m73.toString().length()>=md)break;//too big
        int po2=0;
        while(po2<p2s.size()){
          BigInteger d = p2s.get(po2).multiply(m73);
          if(d.toString().length()>=md)break;
          int[] x = {po2,po3,0,po7};
          ds.put(x,d);
          ++po2;
          inin732.add(0);
        }
        int po5=0;
        while(po5<p5s.size()){
          BigInteger d = p5s.get(po5).multiply(m73);
          if(d.toString().length()>=md)break;
          int[] x = {0,po3,po5,po7};
          ds.put(x,d);
          ++po5;
          inin735.add(0);
        }
        in732.add(new int[inin732.size()]);
        in735.add(new int[inin735.size()]);
        ++po3;
      }
      vals732[po7] = in732.toArray(new int[in732.size()][]);
      vals735[po7] = in735.toArray(new int[in735.size()][]); 
      ++po7;
    }
    System.out.println("Total number:" + ds.size());
    //this only deals with even numbers for now
    System.out.println("Time to prep:" + (System.nanoTime()-s));
    int max = 0;
    int ml = 1;
    oot: for(Entry<int[], BigInteger> e :ds.entrySet()){
      String x = e.getValue().toString();
      int[] K = e.getKey();
      if(x.length()>ml){
        System.out.println("Now processing " + ml+" digits, starting with "+x);
        ml = x.length();
      }
      if(x.length()==1){
        if(K[0]>0){
          vals732[K[3]][K[1]][K[0]] = 0;
        }else{
          vals735[K[3]][K[1]][K[2]] = 0;
        }
        continue;
      }
      int c2 = 0;
      int c3 = 0;
      int c5 = 0;
      int c7 = 0;
      for(char c:x.toCharArray()){
        int y=c-48;
        if(y==0){
          //next is 0, is dead
          if(K[0]>0){
            vals732[K[3]][K[1]][K[0]] = 1;
          }
          else{
            vals735[K[3]][K[1]][K[2]] = 1;
          }
          continue oot;}
        while(y%2==0){
          ++c2;
          y/=2;}
        while(y%3==0){
          ++c3;
          y/=3;}
        while(y%7==0){
          ++c7;
          y/=7;}
        if(y%5==0){
          ++c5;
          y/=5;
        }
      }
      if(c2>0 && c5>0){
        //0 in next one
        if(K[0]>0){
            vals732[K[3]][K[1]][K[0]] = 2;
        }
        else{
            vals735[K[3]][K[1]][K[2]] = 2;
        }
        continue oot;
      }
      int[] f = {c2,c3,c5,c7};
      int m = 0;
      if(c2>0){
          m = vals732[c7][c3][c2]+1;
      }
      else{
          m = vals735[c7][c3][c5]+1;
      }
      if(m>=max){
        System.out.println("Local max "+Arrays.toString(K)+" "+m);
        max=m;
      }
      if(K[0]>0){
          vals732[K[3]][K[1]][K[0]] = m;
      }
      else{
          vals735[K[3]][K[1]][K[2]] = m;
      }
    }

    System.out.println("TOTAL TIME:" + (System.nanoTime()-s));
    
  }
}
