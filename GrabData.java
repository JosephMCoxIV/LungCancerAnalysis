import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
public class GrabData {
	public static void main(String args[]){
		ArrayList<String> collection = new ArrayList<String>();
		int atelCounter = 0;
		int otherCounter = 1;
		int firstLine = 0;
		HashSet<String> png = new HashSet<String>();

		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File("Data_Entry_2017.csv")));
			String line;
			try {
				while(bf.ready()){
					line = bf.readLine();
					if(firstLine == 0){
						firstLine++;
						collection.add(line+"Has Disease");
						continue;
					}
					if(line.contains("Atelectasis")){
						if(atelCounter < 5001){
							String[] diseaseNormalize = line.split(",");
							line = line.replace(diseaseNormalize[1], "Atelectasis");
							line = line + ",true";
							collection.add(line);
						}
						atelCounter++;
					}else{
						if(otherCounter < 5001){
							if(line.contains("No Finding")){
								collection.add(line + ",false");
							}else if(!line.contains("No Finding")){
								String[] diseaseNormalize = line.split(",");
								String temp = diseaseNormalize[1];
								if(temp.contains("|")){
									for(int i = 0; i < temp.length(); i++)
										if(temp.charAt(i) == '|'){
											temp = temp.substring(0, i);
											line = line.replace(diseaseNormalize[1], temp);
										}
									}
								collection.add(line+",false");
								//System.out.println(others.add(line));
							}
						}
						otherCounter++;
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileWriter fw = new FileWriter(new File("temp.csv"));
			for(int i = 0; i < collection.size(); i++){
				if(i == 0)
					continue;
				String[] temp = collection.get(i).split(",");
				png.add(temp[0]);
				fw.write(collection.get(i) + "\n");
			}
			System.out.println(png.size());
			Path mover;
			/**File folder = new File("C:\\Users\\Joey\\Desktop\\FinalProjNeural\\image_list\\");
			File[] fileList = folder.listFiles();
			System.out.println(fileList.length);
			int j = 0;
			for(int i = 0; i < fileList.length; i++){
				/**if(png.contains(fileList[i].getName())){
					j++;
					System.out.println("DAMN");
					//System.out.println(++j);
					//png.remove(fileList[i].getName());
				}
				
					//System.out.println(fileList[i].getName());
				//System.out.println(Paths.get(fileList[i].getAbsolutePath()));

				if(png.contains(fileList[i].getName())){
				//mover = Files.move(Paths.get(fileList[i].getAbsolutePath()), 
				//		Paths.get("C:\\Users\\Joey\\Desktop\\FinalProjNeural\\image_list\\" + fileList[i].getName())
				//		,StandardCopyOption.REPLACE_EXISTING);
					png.remove(fileList[i].getName());
				}
				//System.out.println(fileList[i].getAbsolutePath());
				
				
			}
			System.out.println(png.size());
			*/
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
