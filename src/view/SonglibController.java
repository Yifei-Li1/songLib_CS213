///Yifan Zhang, yz745
///Yifei Li, yl1160

package view;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class SonglibController  {

	@FXML ListView<String> listView1;  
	@FXML Button delete;
	@FXML Button add;
	@FXML Button edit;
	@FXML TextField name;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	
	
	private ObservableList<songType> fullList;  
	private ObservableList<String>   showList;

	public void start(Stage mainStage) throws IOException {                
		

		showList = FXCollections.observableArrayList(); 
		fullList = FXCollections.observableArrayList();
		
		Scanner s = new Scanner(new File("file.txt"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNextLine()){
    		list.add(s.nextLine());
		}
		s.close();
		
		for(int i=0; i<list.size();i++) {
 			
				String name = list.get(i).split(";")[0];
				String artist = list.get(i).split(";")[1];
				String album = list.get(i).split(";")[2];
				String year = list.get(i).split(";")[3];
				fullList.add(new songType(name,artist,album,year));
	
		}
		
		if(list.size()>0) {
			Collections.sort(fullList, (p1, p2) -> (p1.getName()+p1.getArtist()).compareToIgnoreCase(p2.getName()+p2.getArtist()));
			
			for(int i = 0; i<fullList.size(); i++) {
				showList.add(fullList.get(i).getName()+", "+fullList.get(i).getArtist());
			}
			
			
			selectSong(0);
			listView1.setItems(showList); 
			
			
			listView1
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) -> 
					showFullInfo(mainStage));
			
		}
		
		
		
//		System.out.println(fullList);
	}
	
	public void selectSong(int index) {
		
		listView1.getSelectionModel().select(index);
		name.setText(fullList.get(index).getName());
		artist.setText(fullList.get(index).getArtist());
		album.setText(fullList.get(index).getAlbum());
		year.setText(fullList.get(index).getYear());
	}
	
	

	private void showFullInfo(Stage mainStage) {                
	
		int songIndex = listView1.getSelectionModel().getSelectedIndex();
		name.setText(fullList.get(songIndex).getName());
		artist.setText(fullList.get(songIndex).getArtist());
		album.setText(fullList.get(songIndex).getAlbum());
		year.setText(fullList.get(songIndex).getYear());
		
	}
	
	
	public void addSong(ActionEvent e) throws IOException {
		
		Button b = (Button)e.getSource();
		if (b == add) {
		
			
			String newSongName = String.valueOf(name.getText());
			String newSongArtist = String.valueOf(artist.getText());
			String newSongAlbum = String.valueOf(album.getText());
			String newSongYear = String.valueOf(year.getText());
			
			if (newSongName.length()==0 || newSongArtist.length()==0 ){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error occurs....");
				alert.setHeaderText("Name or artist cannot be empty");
				alert.setContentText("You have to enter a valid name or artist");

				alert.showAndWait();
			}
			
			else {	
				
				songType newSong = new songType(newSongName, newSongArtist, newSongAlbum , newSongYear);
				
				for(int i = 0; i<fullList.size();i++) {
					if(fullList.get(i).compareTo(newSong)==0) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error occurs....");
						alert.setHeaderText("Repeated Song have found");
						alert.setContentText("You can only add a new song");

						alert.showAndWait();
						return;
					}
				}
				
				fullList.add(newSong);
				Collections.sort(fullList, (p1, p2) -> (p1.getName()+p1.getArtist()).compareToIgnoreCase(p2.getName()+p2.getArtist()));
	
				
				for(int i=0; i<fullList.size(); i++) {
					if (fullList.get(i).equals(newSong)){
						
						showList.add(i,fullList.get(i).getName()+", "+fullList.get(i).getArtist());
						selectSong(i);
					}
					
				}
				
				
				
				System.out.println(fullList.toString());
				System.out.println(showList.toString());
				listView1.setItems(showList); 
				writeFile(fullList);
	//			System.out.println("write files successfully!");
				
			}
			
		}
	}
	
	public void deleteSong(ActionEvent e) throws IOException {
		
	
			Button b = (Button)e.getSource();
			if (b == delete) {
				int index = listView1.getSelectionModel().getSelectedIndex();
				int oldSize = fullList.size();
				
				
				if(oldSize==0) {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error occurs....");
					alert.setHeaderText("Deletion fails");
					alert.setContentText("Empty List");
					alert.showAndWait();
					
				}else if(oldSize==1){
			
					showList.remove(showList.size()-1);
					fullList.remove(fullList.size()-1);
					name.setText("");
					artist.setText("");
					album.setText("");
					year.setText("");
		

					
				}else if(oldSize-1 > index && oldSize>1 ) {
					
					fullList.remove(index);
					showList.remove(index);
					selectSong(index);
					
				}else if (oldSize-1 == index && oldSize>1) {
					
					fullList.remove(index);
					showList.remove(index);
					selectSong(fullList.size()-1);
					
				}
			
				writeFile(fullList);
			//	System.out.println("write files successfully!");
			
			
			//	System.out.println("fullList: "+fullList+", Size: "+fullList.size()+", index:="+index);
			//	System.out.println("showList: "+showList+", Size: "+showList.size()+", index:="+index);
			}
		
		
		
			
			
		
	}
	
	public void editSong(ActionEvent e) throws IOException {
		
		Button b = (Button)e.getSource();
		
		if (b == edit) {
			
			int index = listView1.getSelectionModel().getSelectedIndex();
			String songName = String.valueOf(name.getText());
			String songArtist = String.valueOf(artist.getText());
			String songAlbum = String.valueOf(album.getText());
			String songYear = String.valueOf(year.getText());
			
			songType editSong = new songType(songName, songArtist, songAlbum, songYear);
			
			if (songName.length()==0 || songArtist.length()==0 ){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error occurs....");
				alert.setHeaderText("Name or artist cannot be empty");
				alert.setContentText("You have to select a valid song to edit");

				alert.showAndWait();
			}else {
			
				String a = songName+", "+songArtist;
				for(int i=0; i<showList.size(); i++) {
					if(a.equalsIgnoreCase(showList.get(i))){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error occurs....");
						alert.setHeaderText("Editing fails");
						alert.setContentText("Song exists");
						alert.showAndWait();
					}
				}
			
				showList.set(index, a);
				fullList.set(index, editSong);
				Collections.sort(fullList, (p1, p2) -> (p1.getName()+p1.getArtist()).compareToIgnoreCase(p2.getName()+p2.getArtist()));
				Collections.sort(showList,(p1,p2) -> (p1.compareToIgnoreCase(p2)));
			
				writeFile(fullList);
				System.out.println("write files successfully!");
				System.out.println("showList: "+showList);
				System.out.println("fullList: "+fullList);
			}
			
		}
		
		
		
		
	}

	public  void writeFile(ObservableList<songType> inputList) throws IOException {
	
	
		PrintWriter writer = new PrintWriter("file.txt");
		writer.print("");
		writer.close();
		
		
		File fout = new File("file.txt");
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (int i = 0; i < inputList.size(); i++) {
			bw.write(inputList.get(i).getName()+";"+inputList.get(i).getArtist()+";"
						+inputList.get(i).getAlbum()+";"+inputList.get(i).getYear());
			bw.newLine();
		}
	 
		bw.close();
		
	}
	


	
	
}
