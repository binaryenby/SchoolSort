import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class SchoolSort {
	
	public static void main(String[] args) {
		
		SchoolSort ss = new SchoolSort();
		LinkedList<School> schoolList = new LinkedList<School>();
		try {
			schoolList = ss.parseFile();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		schoolList.sort(new EnrollmentComp());
		schoolList.sort(new HighComp());
		schoolList.sort(new ZipComp());
		schoolList.sort(new StateComp());
		
		ss.writeToFile(schoolList, "zip.out.txt");
		
		System.out.println("Done");
		System.exit(0);
	}
	
	private LinkedList<School> parseFile() throws FileNotFoundException {
		
		//Creates a LinkedList to store school info, calls fileSelection to give the
		//user an open dialogue, then creates a Scanner on the chosen file
		LinkedList<School> schools = new LinkedList<School>();
		File file = this.fileSelection();
		Scanner sc = new Scanner(file);
	
		//Scans through the file creating and populating a new School object for each entry
		while(sc.hasNextLine()) {
			String current = sc.nextLine();
			School school = new School();
		
			school.setState(current.substring(0, 18));
			school.setZip(current.substring(19, 24));
			school.setLow(current.substring(25, 27));
			school.setHigh(current.substring(28, 30));
			school.setName(current.substring(31, 54));
			school.setEnrollment(current.substring(55, 60));
			school.setLat(Double.valueOf(current.substring(61, 69)));
			school.setLon(Double.valueOf(current.substring(70, 78)));
		
			schools.add(school);
			
		}
		
		sc.close();
		return schools;
	}
	
	private File fileSelection() {
		//Creates a basic JFrame for the file chooser to occupy
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creates a JFileChooser in the user's current directory
		final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		
		//Adds a filter on the fileChooser so it only shows .txt files
		FileFilter filter = new FileNameExtensionFilter("txt files", "txt");
		fileChooser.addChoosableFileFilter(filter);
		
		//If the user chooses a file, create a File object for it and return it
		if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			System.out.println(file.getName() + " selected.");
			return file;
		}
		
		//If the user declines to choose a file, say as much in the output and close
		else {
			System.out.println("No file selected. Run program again to chooose"
					+ " a new file.");
			System.exit(0);
			//This line has to be here to satisfy the compiler, even though
			//the program exits before reaching it.
			return null;
		}
	}
	
	private void writeToFile(LinkedList<School> schools, String filename) {
		
		//Creates a file with the filename passed to this method
		File output = new File(filename);
		
		//try/catch block to catch an io exception createNewFile may throw
		try {
			
			//createNewFile returns false if the file already exists in that directory
			if(output.createNewFile() == false) {
				System.out.println("File creation failed. Check to ensure a file with"
						+ " the name \"" + filename + "\" does not already exist.");
				System.exit(0);
			}
		}
		
		catch(Exception e) {
			System.out.println("Java IO exception occurred.");
			e.printStackTrace();
		}
		
		//Another try/catch block for IO exceptions
		try {
			FileWriter fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
		
			for(School s : schools) {
				bw.write(s.toString());
				bw.newLine();
			}
			
			bw.close();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class StateComp implements Comparator<School> {
	@Override
	public int compare(School a, School b) {
		return a.getState().compareTo(b.getState());
	}
}

class ZipComp implements Comparator<School> {
	@Override
	public int compare(School a, School b) {
		return a.getZip().compareTo(b.getZip());
	}
}

class LowComp implements Comparator<School> {
	@Override
	public int compare(School a, School b) {
		return a.getLow().compareTo(b.getLow());
	}
}

class HighComp implements Comparator<School> {
	@Override
	public int compare(School a, School b) {
		return -(a.getHigh().compareTo(b.getHigh()));
	}
}

class EnrollmentComp implements Comparator<School> {
	@Override
	public int compare(School a, School b) {
		return -(a.getEnrollment().compareTo(b.getEnrollment()));
	}
}

class LatComp implements Comparator<School> {
	@Override
	public int compare(School a, School b) {
		return -(Double.compare(a.getLat(), b.getLat()));
	}
}
class School {
	
	private String state;
	private String zip;
	private String low;
	private String high;
	private String name;
	private String enrollment;
	private double lat;
	private double lon;
	
	public void setState(String st) {
		this.state = st;
	}
	
	public void setZip(String z) {
		this.zip = z;
	}
	
	public void setLow(String l) {
		this.low = l;
	}
	
	public void setHigh(String h) {
		this.high = h;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public void setEnrollment(String e) {
		this.enrollment = e;
	}
	
	public void setLat(double la) {
		this.lat = la;
	}
	
	public void setLon(double lo) {
		this.lon = lo;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getZip() {
		return this.zip;
	}
	
	public String getLow() {
		return this.low;
	}
	
	public String getHigh() {
		return this.high;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEnrollment() {
		return this.enrollment;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public double getLon() {
		return this.lon;
	}
	
	public String toString() {
		return this.state + " " + this.zip + " " + this.low + " "
				+ this.high + " " + this.name + " " + this.enrollment
				+ " " + Double.toString(this.lat) + " " + Double.toString(this.lon);
	}
}