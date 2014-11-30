/*
	idf�X�R�A�̎Z�o�v���O�����ł�
	qfile�ɃX�R�A�����߂����t�@�C��.txt������āC�����f�B���N�g���ɔ�r���镶��.txt�������
	idf�X�R�A.csv�Ɍ��ʂ��o�͂���܂�
	qfile�͌`�ԑf��͍ς݂̒P�ꂪ��s�������Ă邠�ꂪ�]�܂����ˁI
	Created by K.UMEKAGE
*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class idfScorer {
	public static void main(String[] argv) throws IOException{
		//�ǂ̃t�@�C���Ɋւ���df�X�R�A�����߂������̂�����܂����Ă���̂�
		String qFile = "C:/research/0610/main_Kzumi.txt";
		//what:���̕�����ɑ΂���tfidf�̃X�R�A�����߂�̂�
		String line;

		//�t�@�C���̍s����ǂݍ���
		BufferedReader fin = new BufferedReader(new FileReader(qFile));
		String aLine;
		int maxL = 0;
		while(null!=(aLine = fin.readLine())){
			maxL++;
		}
		fin.close();

		//�ydf�X�R�A�̂��߂̊ȈՃA���S���Y���z
        String dirName ="C:/research/0610";// ���ׂ����f�B���N�g����
		File dir = new File( dirName );
		//A.list()�FA�f�B���N�g���ɂ���t�@�C���y�уf�B���N�g������z��ŕԂ�
		String ls[] = dir.list();
		int maxF = ls.length;// maxF=�t�@�C���̐�
		
		double df[] = new double[maxL];//�����̐��͍s���ł���ׂ��Ȃ̂� �� maxF�ł���
		String what[] = new String[maxL];
		try {
			//df�X�R�A���Z�o������������W�c.txt��o�^
			FileReader in = new FileReader(qFile);
			BufferedReader br = new BufferedReader(in);
			//�����ōŏ��̃t�@�C����df�ꌟ�o
			Boolean dfflg;
			for( int i=0; i<=maxL-1; i++ ){
				dfflg = false;
				what[i] = br.readLine();

				//�����Ŏ��̃t�@�C���Ɉڂ��Ď��ۂ�df�̗L������
				for(int j=0; j<=maxF-1;j++){
					try {
						FileReader ina = new FileReader( dirName+"/"+ls[j]);
						BufferedReader bra = new BufferedReader(ina);
						//����while�ň�s���ǂݍ���
						while ((line = bra.readLine()) != null) {	
							//��������df�̔���
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
			
			//�����܂�df�L���̌���
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
		System.out.println(qFile+"��idfScore���o�͂��܂���");
		br.close();
		in.close();
		}catch( IOException e ) {  // �G���[������
			System.err.println("Error....");
			System.exit(-1);
		}
	}
}
