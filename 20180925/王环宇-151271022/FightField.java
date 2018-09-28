package javahw3;
import java.util.ArrayList;
import java.util.Scanner;

public class FightField {
	private static final int N = 15;
	private Warrior[][] fields;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		FightField ff = new FightField();
		//登录每队的战士，并且显示其基本信息
		Team teamGood = new Team();
		Team teamBad = new Team();
		loading(teamGood.team, teamBad.team);
		teamGood.showWarriors();
		teamBad.showWarriors();
		//初始化阵型
		Formation.initialization();
		/*----------------------------------------------------*/
		System.out.println("ROUND1");
		ff.round(teamGood, teamBad, in);
		ff.showFields();
		
		System.out.println();
		/*----------------------------------------------------*/
		ff.initialization();
		System.out.println("ROUND2");
		ff.round(teamGood, teamBad, in);
		ff.showFields();
		
		in.close();
		
		teamGood.showWarriors();
		teamBad.showWarriors();
	}
	
	public FightField() {
		initialization();
	}
	
	public void initialization() {
		fields = new Warrior[N][N];
	}
	//loading the warriors of two teams.
	public static void loading(ArrayList<Warrior> teamGood, ArrayList<Warrior> teamBad) {
		System.out.println("创建即将上场进行对决的对象......");
		String name = "大二三四五六七";
		for (int i=0; i<7; i++) {
			teamGood.add(new CalabashBrothers(name.charAt(i) + "娃", ""));
		}
		teamGood.add(new Warrior("老爷爷", "吃瓜", 1));
		teamBad.add(new Warrior("蝎子精", "领队", 2));
		for (int i=0; i<6; i++) {
			teamBad.add(new Warrior("小兵 ", "送头", 2));
		}
		teamBad.add(new Warrior("蛇精", "吃瓜", 2));
	}
	
	public void showFields() {
		System.out.println("*****************************************************战场对阵情况*****************************************************");
		for (int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				if(fields[i][j]==null)
					System.out.print("---");
				else
					System.out.print(fields[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("*****************************************************战场对阵情况*****************************************************");
	}
	
	public void goBattle(ArrayList<Warrior> team, int[][] place) {
		for (int i=0; i<team.size(); i++) {
			int m = place[i][0];
			int n = place[i][1];
			team.get(i).changePosiion(m, n);
			fields[m][n] = team.get(i);
		}
	}
	
	public void round(Team teamGood, Team teamBad, Scanner in) {
		System.out.println("**************双方战士上场*************");
		System.out.println("**********请选择妖精上场的阵型***********");
		System.out.println("**********0----------长蛇阵**********");
		System.out.println("**********1----------雁形阵**********");
		System.out.println("**********2----------鹤翼阵**********");
		System.out.println("**********3----------冲轭阵**********");
		int f1 = in.nextInt();
		this.goBattle(teamGood.team, Formation.getForm(0, 1));
		this.goBattle(teamBad.team, Formation.getForm(f1, 2));
	}
}


class Team{
	public ArrayList<Warrior> team;
	
	Team(){
		team = new ArrayList<Warrior>();
	}
	public void showWarriors() {
		for(Warrior k:team)
			k.showMe();
	}
}


class Formation{
	public static ArrayList<int[][]> book1;
	public static ArrayList<int[][]> book2;
	
	public static void initialization() {
		book1 = new ArrayList<int[][]>();
		book2 = new ArrayList<int[][]>();
		int x1[][] = {{4,4},{5,4},{6,4},{7,4},{8,4},{9,4},{10,4},{7,2}} ;
		int x2[][] = {{4,6},{5,5},{6,4},{7,3},{8,2},{9,1},{10,0},{5,1}} ;
		int x3[][] = {{4,6},{5,5},{6,4},{7,3},{6,2},{5,1},{4,0},{4,3}} ;
		int x4[][] = {{3,3},{4,4},{5,3},{6,4},{7,3},{8,4},{9,3},{6,1}} ;
		
		book1.add(x1);
		book2.add(convert(x1));
		book1.add(x2);
		book2.add(convert(x2));
		book1.add(x3);
		book2.add(convert(x3));
		book1.add(x4);
		book2.add(convert(x4));
	}
	
	public static int[][] getForm(int m, int team){
		if (team == 1)
			return book1.get(m);
		else
			return book2.get(m);
	}
	private static int[][] convert(int[][] xx){
		int[][] y = new int[8][2];
		for (int i=0; i<7; i++) {
			y[i][1] = 14 - xx[i][1];
			y[i][0] = xx[i][0];
		}
		y[7][1] = 14 - xx[7][1];
		y[7][0] = xx[7][0];
		return y;
	}
	
}


/*
七个葫芦娃，老爷爷，蛇精，蝎子精都是属于独一无二的个体，而小妖精是可以有很多个的
敌对的双方各自有一个备战区，备战区用于生成将要上场的对象，有一个对象：战场，
在这个对象里，记录着每个位置上的个体的名字，这个对象有：查询位置、更改位置信息、打印等操作
双方的对象：都具备一个位置信息，记录了这个对象在战场中的位置，记录着各自的名字，以及所属的战队
战法对象：记录了队形信息，根据所属的战队不同，其在战场上排列的位置不同

葫芦娃可以使用枚举类型，也可以使用受限的类（枚举类型的使用作为自己的练习）
用二维的ArrayList似乎会比较麻烦，Java中的存取没有cpp中那么方便，所以对于战场该如何实现？？？
 * 
 * */
