/*
	idfスコアの算出プログラムです
	qfileにスコアを求めたいファイル.txtをいれて，同じディレクトリに比較する文書.txtがあれば
	idfスコア.csvに結果が出力されます
	qfileは形態素解析済みの単語が一行ずつ入ってるあれが望ましいね！
	Created by K.UMEKAGE
*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class idfScorer {
	public static void main(String[] argv) throws IOException{
		//どのファイルに関してdfスコアを求めたいものが入りまくっているのか
		String qFile = "C:/research/0610/main_Kzumi.txt";
		//what:何の文字列に対してtfidfのスコアを求めるのか
		String line;

		//ファイルの行数を読み込む
		BufferedReader fin = new BufferedReader(new FileReader(qFile));
		String aLine;
		int maxL = 0;
		while(null!=(aLine = fin.readLine())){
			maxL++;
		}
		fin.close();

		//【dfスコアのための簡易アルゴリズム】
        String dirName ="C:/research/0610";// 調べたいディレクトリ名
		File dir = new File( dirName );
		//A.list()：Aディレクトリにあるファイル及びディレクトリ名を配列で返す
		String ls[] = dir.list();
		int maxF = ls.length;// maxF=ファイルの数
		
		double df[] = new double[maxL];//ここの数は行数であるべきなので ≠ maxFである
		String what[] = new String[maxL];
		try {
			//dfスコアを算出したい文字列集団.txtを登録
			FileReader in = new FileReader(qFile);
			BufferedReader br = new BufferedReader(in);
			//ここで最初のファイルのdf語検出
			Boolean dfflg;
			for( int i=0; i<=maxL-1; i++ ){
				dfflg = false;
				what[i] = br.readLine();

				//ここで次のファイルに移って実際にdfの有無検証
				for(int j=0; j<=maxF-1;j++){
					try {
						FileReader ina = new FileReader( dirName+"/"+ls[j]);
						BufferedReader bra = new BufferedReader(ina);
						//このwhileで一行ずつ読み込み
						while ((line = bra.readLine()) != null) {	
							//ここからdfの発見
							if(line.indexOf(what[i]) != -1){
								if(line.lastIndexOf(what[i]) != -1){
									df[i] = df[i]+1;
									break;
								}
							}
						}
						bra.close();
						ina.close();
					} catch (IOException e){
						System.out.println(e);
					}
				}
				df[i] = maxF/df[i];
				df[i] = Math.log10(df[i]);
			}
			
			//ここまでdf有無の検証
			try{
				File file = new File(qFile+"_idfScore.csv");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				for(int k=0; k<=maxL-1 ; k++){
					/*if(df[k]==0){
						continue;
					}*/
					pw.println(df[k]+","+what[k]+",");
				}
				pw.close();
    		}catch(IOException e){
				System.out.println(e);
    		}
		System.out.println(qFile+"のidfScoreを出力しました");
		br.close();
		in.close();
		}catch( IOException e ) {  // エラーが発生
			System.err.println("Error....");
			System.exit(-1);
		}
	}
}
