/*	
	tf�X�R�A���o�����߂̃v���O�����ł��D�D
	Created by K.UMEKAGE
*/
import java.util.*;
import java.io.*;
import java.lang.*;

public class tfScorer {
	public static void main(String[] argv) throws IOException{
		/*
			�ǂ̃t�@�C���Ɋւ���tf�X�R�A�����߂������̂�����܂����Ă���̂�
			������P�ꂪ��s�������Ă���z��
			���͂��o���o���ɂ��āA���ꂼ���tf�X�R�A���߂����Ȃ�܂��͌`�ԑf��͂����Ă�������
		*/
		
		String qFile = "������";
		//�t�@�C���̍s����ǂݍ���
		BufferedReader fin = new BufferedReader(new FileReader(qFile));
		String aLine;
		int maxL = 0;
		while(null!=(aLine = fin.readLine())){
			maxL++;
		}
		fin.close();
		
		//what:���̕�����ɑ΂���tfidf�̃X�R�A�����߂�̂�
		String what[] = new String[maxL+1];
		//tf�F���ۂ�tf������
		int tf[] = new int[maxL+1];
		//�����Ă���s
		String line;

		try {
			//tf�X�R�A���Z�o������������W�c.txt��o�^
			FileReader in = new FileReader(qFile);
			BufferedReader br = new BufferedReader(in);
			//�����ōŏ��̃t�@�C����tf�ꌟ�o
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
				//�����Ŏ��̃t�@�C���Ɉڂ��Ď��ۂ�tf�̗L������
				try {
					FileReader ina = new FileReader(qFile);
					BufferedReader bra = new BufferedReader(ina);
					//����while�ň�s���ǂݍ���
					while ((line = bra.readLine()) != null) {
						//�����񂪍��v���Ă邩�ǂ����̔���
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
				//�t�@�C���ɖ��O�����ĕۑ�
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
			System.out.println(qFile+"�t�@�C���������������N�\�W���b�v���I");
		} catch (IOException e) {   // ���o�̓G���[�����܂���
			System.err.println(e);  // �G���[���b�Z�[�W�o��
			System.out.println("Error");
			System.exit(1);         // �I���R�[�h 1 �ŏI������
		}
	}
}
