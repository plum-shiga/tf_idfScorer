/*	
	tfスコアを出すためのプログラムです．．
	Created by K.UMEKAGE
*/
import java.util.*;
import java.io.*;
import java.lang.*;

public class tfScorer {
	public static void main(String[] argv) throws IOException{
		/*
			どのファイルに関してtfスコアを求めたいものが入りまくっているのか
			名詞一単語が一行ずつ入ってるやつを想定
			文章をバラバラにして、それぞれのtfスコア求めたいならまずは形態素解析をしてください
		*/
		
		String qFile = "ここに";
		//ファイルの行数を読み込む
		BufferedReader fin = new BufferedReader(new FileReader(qFile));
		String aLine;
		int maxL = 0;
		while(null!=(aLine = fin.readLine())){
			maxL++;
		}
		fin.close();
		
		//what:何の文字列に対してtfidfのスコアを求めるのか
		String what[] = new String[maxL+1];
		//tf：実際のtfすこあ
		int tf[] = new int[maxL+1];
		//今見ている行
		String line;

		try {
			//tfスコアを算出したい文字列集団.txtを登録
			FileReader in = new FileReader(qFile);
			BufferedReader br = new BufferedReader(in);
			//ここで最初のファイルのtf語検出
			for(int i=1; i<=maxL; i++){
				what[i] = br.readLine();
				tf[i] = 0;
				boolean tfflag = false;
				for(int j=i-1; j >=0 ;j--){
					tfflag = what[i].equals(what[j]);
					if(tfflag == true){
						break;
					}
				}
				if(tfflag == true){
					continue;
				}
				//ここで次のファイルに移って実際にtfの有無検証
				try {
					FileReader ina = new FileReader(qFile);
					BufferedReader bra = new BufferedReader(ina);
					//このwhileで一行ずつ読み込み
					while ((line = bra.readLine()) != null) {
						//文字列が合致してるかどうかの判定
						if(line.indexOf(what[i]) != -1){
							if(line.lastIndexOf(what[i]) != -1){
								tf[i] = tf[i]+1;
							}
						}
					}
					bra.close();
					ina.close();
				} catch (IOException e){
					System.out.println(e);
				}
			}
			try{
				//ファイルに名前をつけて保存
				String kakiko = qFile+"_tfScore.csv";
      			File file = new File(kakiko);
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				for(int k=1; k<=maxL ; k++){
					if(tf[k]==0){
						continue;
					}
					pw.println(tf[k]+","+what[k]+",");
				}
				pw.close();
    		}catch(IOException e){
				System.out.println(e);
    		}
			br.close();
			in.close();
			System.out.println(qFile+"ファイルが完成したぞクソジャップ共！");
		} catch (IOException e) {   // 入出力エラーをつかまえる
			System.err.println(e);  // エラーメッセージ出力
			System.out.println("Error");
			System.exit(1);         // 終了コード 1 で終了する
		}
	}
}
