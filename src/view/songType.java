//Yifan Zhang, yz745
//Yifei Li, yl1160
package view;



public class songType implements Comparable<songType>   {

	String name;
	String artist;
	String album;
	 String year;
	
	
	public songType(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear() {
		return year;
	}
	
	
	public String toString() {
		return "\n(Name: "+this.name+", Artist: "+ this.artist+", Album: "
				+this.album+", Year: "+this.year+")\n";
	}

	@Override
	public int compareTo(songType o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
	
}
