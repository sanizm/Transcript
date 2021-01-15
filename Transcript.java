package Assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.io.*;

/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to 
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution 
with anybody. I affirm that I have read and understood
the Senate Policy on Academic honesty at 
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a 
result of committing plagiarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name: Saniz Momin
Student Number: 217584749
Course Section: B
*/

                                     /*  Hello TA just Make sure if you test you follow the following format to get desired output
                                         To receive appropriate output of a transcript it is recommended
		                                 this.grade has elements of the format
		                                 CourseCode,CourseCredit,StudentID,[P|E]weightofAssessment(Grade of student got),student name
		                                 Putting spaces in between will give weird result or error in order to get
		                                 perfect output follow the format strictly*/
/**
* This class generates a transcript for each student, whose information is in the text file.
* 
*
*/

/**
 * @author sanizm
 *
 */
public class Transcript {
	private ArrayList<Object> grade = new ArrayList<Object>();
	private File inputFile;
	private String outputFile;

	/**
	 * This the the constructor for Transcript class that initializes its instance
	 * variables and call readFie private method to read the file and construct
	 * this.grade.
	 * 
	 * @param inFile  is the name of the input file.
	 * @param outFile is the name of the output file.
	 */
	public Transcript(String inFile, String outFile) {
		inputFile = new File(inFile);
		outputFile = outFile;
		grade = new ArrayList<Object>();
		this.readFile();
	}// end of Transcript constructor

	/**
	 * This method reads a text file and add each line as an entry of grade
	 * ArrayList.
	 * 
	 * @exception It throws FileNotFoundException if the file is not found.
	 */
	private void readFile() {
		Scanner sc = null;
		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				grade.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	} // end of readFile

	/**
	 * This method extracts all the elements of this.grade one by one then converts
	 * them to store String and store them in String variable A splitter is used to
	 * store all the elements of a line separately in String array than String array
	 * is stored in ArrayList
	 * 
	 * @return returns an ArrayList of String array which contains all the elements
	 *         line by line
	 */
	private ArrayList<String[]> WordOfLineAsElements() {
		ArrayList<String[]> ElementsOfLine = new ArrayList<String[]>(); // ArrayList of String array created
		for (int i = 0; i < this.grade.size(); i++) {
			String Line = (String) this.grade.get(i); // Elements stored in this.grade are stored as String in String
														// variable
			String[] arr = Line.split(","); // A splitter "," is used to store individual element of a line in String
											// array
			ElementsOfLine.add(arr);
		}
		return ElementsOfLine; // ArrayList is returned
	}

	/**
	 * This method creates an ArrayList of Assessment class it calls
	 * WordOfLineAsElements() method to extract the Assessment component such as
	 * type of Assessment and its weight
	 * 
	 * @param i is an Index of which is passed through cycle of methods coming from
	 *          the first method buildStudentArray() which calls
	 *          createsStudentOjb(//containing index i)
	 * @return returns an ArrayList of Assessment class containing type and weight
	 *         of Assessment of particular course and Student
	 */
	private ArrayList<Assessment> ParticularStudentAssessment(int i) {
		ArrayList<Assessment> AsCourseAttribute = new ArrayList<Assessment>(); // ArrayList AsCourseAttribute
																				// Initialized
		for (int j = 3; j < WordOfLineAsElements().get(i).length - 1; j++) {
			AsCourseAttribute.add(Assessment.getInstance(WordOfLineAsElements().get(i)[j].charAt(0),
					Integer.parseInt(WordOfLineAsElements().get(i)[j].substring(1, 3))));
			// Calls getInstance method of Assessment to return a new object of Assessment
			// class and store them in ArrayList
			// Calls WordOfLineAsElements() to access elements of String array at specified
			// position
		}
		return AsCourseAttribute;// returns an ArrayList
	}

	/**
	 * This method creates an ArrayList of Course class it calls
	 * WordOfLineAsElements() and ParticularStudentAssesement() to initialize its
	 * instance variables
	 * 
	 * @param i is an Index of which is passed through cycle of methods coming from
	 *          the very first method buildStudentArray() which calls
	 *          createsStudentOjb(//containing index i)
	 * @return returns an ArrayList of Course containing course code , Assessment
	 *         ArrayList and course credit
	 */
	private ArrayList<Course> ParticularStudentCourse(int i) {
		ArrayList<Course> AsStudentAttribute = new ArrayList<Course>(); // ArrayList of AsStudnetAttribute created
		AsStudentAttribute.add(new Course(WordOfLineAsElements().get(i)[0], ParticularStudentAssessment(i),
				Double.parseDouble(WordOfLineAsElements().get(i)[1])));
		// stores course object in this ArrayList which calls WordOfLinesAsElements()
		// ParticularStudentAssessment() to initialize its instance variable
		return AsStudentAttribute; // returns ArrayList
	}

	/**
	 * This method is particularly used to store all the courses that are taken by
	 * same student it creates a new Course Object and returns it for example
	 * student name john takes multiple course EECS2030 and EECS2012 this method is
	 * called to return EECS2012 course details
	 * 
	 * @param i this is the index of all the other Courses stored in
	 *          WordOfLineAsElements taken by the same student
	 * @return returns a Course object of same student taking multiple courses
	 */
	private Course StudentMultipleCourse(int i) {
		return new Course(WordOfLineAsElements().get(i)[0], ParticularStudentAssessment(i),
				Double.parseDouble(WordOfLineAsElements().get(i)[1]));
		// returns new course object which is another course taken by the same student
	}

	/**
	 * This is the most fundamental method of this is method from where all the
	 * other private method of this class are called to create an Student object it
	 * stores all the details of the student Number of course a student has taken
	 * the grades received by the student all the Assesments taken by that student
	 * it calls a number of method so as to successfully create an student object
	 * calls WordOfLineAsElements() ParticularStudentCourse(i)
	 * StudentMultipleCourse(a)) addGrade(D,I)
	 * 
	 * @param i this is an index given when buildstudentArray() called this method
	 *          which is an index of student located at particular position in
	 *          WordOfLinesAsElements()
	 * @return this returns the final successful creation of student object which
	 *         contains all the specified details of a particular student
	 */
	private Student createStudentObj(int i) {
		ArrayList<Integer> arr = new ArrayList<Integer>();// ArrayList created to store all the index of course taken by
															// same student
		Student student = new Student(WordOfLineAsElements().get(i)[2],
				WordOfLineAsElements().get(i)[WordOfLineAsElements().get(i).length - 1], ParticularStudentCourse(i));
		// Student constructor called to initialize the studentID Name and Courses taken
		// by a particular student
		// it calls WordOfLineAsElements() to store studentID and Name and calls
		// ParticularStudentCourse(i) to store course taken
		for (int j = 0; j < WordOfLineAsElements().size(); j++) {
			// this for loop is basically used to store all the indexes of student taking
			// multiple courses
			if (Integer.parseInt(WordOfLineAsElements().get(i)[2]) == Integer
					.parseInt(WordOfLineAsElements().get(j)[2])) {
				/*
				 * To know that a Particular student has taken multiple courses I am using
				 * student ID of that particular student on the left part of if statement and i
				 * use WordOfLineAsElements() on the right of if statement to compare all the
				 * studentIDs stored in it if a studentID of this student matches with the one
				 * or more of the student IDs of that list than it means that this student is
				 * taking multiple courses and all those courses need to be added in this
				 * student object
				 */

				arr.add(j);// Add all the index where student IDs are found same
			}
		}
		// The following for loop is used to add all the other courses taken by this
		// particular student it calls StudentMultipleCourse(arr.get(y))
		for (int y = 1; y < arr.size(); y++) {
			student.addCourse(StudentMultipleCourse(arr.get(y)));// Multiple courses taken are added to this student by
																	// calling addCourse which are added in Course
																	// ArrayList
		}
		ArrayList<Double> D = new ArrayList<Double>();// ArrayList created to store all the Assessment weight of a
														// particular course taken by student
		ArrayList<Integer> I = new ArrayList<Integer>();// ArrayList created to store all the grades received by student
														// in all Assessment of that course

		// The following for loop is used to store grades and weight of all courses
		// taken by student one by one this is also used to call addGrade method to
		// calculate final grade and GPA
		for (int z = 0; z < student.getCourseTaken().size(); z++) {
			// The following for loop is used to store all the grades received by a student
			// in particular course it calls WordOfLineAsElements() to store grades received
			// this uses several if else statement because grades are stored in the format
			// P10(30) or E10(30) is uses substring to extract 30 and basically covers all
			// the possible scenerios
			// through if else if statements
			for (int a = 3; a < WordOfLineAsElements().get(arr.get(z)).length - 1; a++) {
				try {
				if (WordOfLineAsElements().get(arr.get(z))[a].length() == 7) {
					if(Double.parseDouble(WordOfLineAsElements().get(arr.get(z))[a].substring(4, 6)) > 0) {//checks if student has a negative grade
					D.add(Double.parseDouble(WordOfLineAsElements().get(arr.get(z))[a].substring(4, 6)));//if its not than all good
					}else {
						D.add(0.0);//0 is added in case student receive a negative grade
						throw new IllegalArgumentException("Grade received by a student cannot be less than 0 that student will receive 0 for the assignment");//Exception is thrown 
					}
				} else if (WordOfLineAsElements().get(arr.get(z))[a].length() == 8) {
					if(Double.parseDouble(WordOfLineAsElements().get(arr.get(z))[a].substring(4, 7)) >0) {//checks if student has a negative grade
					D.add(Double.parseDouble(WordOfLineAsElements().get(arr.get(z))[a].substring(4, 7)));
					}else {
						D.add(0.0);//0 is added in case student receive a negative grade
						throw new IllegalArgumentException("Grade received by a student cannot be less than 0 that student will receive 0 for the assignment");//Exception is thrown 
					}
				} else if (WordOfLineAsElements().get(arr.get(z))[a].length() == 9) {
					if(Double.parseDouble(WordOfLineAsElements().get(arr.get(z))[a].substring(5, 8)) > 0) {//checks if student has a negative grade
					D.add(Double.parseDouble(WordOfLineAsElements().get(arr.get(z))[a].substring(5, 8)));
					}else {
						D.add(0.0);//0 is added in case student receive a negative grade
						throw new IllegalArgumentException("Grade received by a student cannot be less than 0 that student will receive 0 for the assignment");//Exception is thrown 
					}
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			// this for loop is used to store all the weights of the assessment of a
			// particular
			for (int b = 0; b < student.getCourseTaken().get(z).getAssignment().size(); b++) {
				I.add(student.getCourseTaken().get(z).getAssignment().get(b).getWeight());
			}

			student.addGrade(D, I); // calls addGrade method to calculate finalGrade and weightedGPA
			D.clear();
			I.clear();// clears both ArrayList of all elements so that it can store new elements of a
						// new course
		}
		arr.clear();
		return student;// student object created returned successfully

	}

	/**
	 * This method contains no parameter it is basically used to store all the
	 * unique student IDS of all students there input file. this is one of the most
	 * important methods because it gives exact number of students that are present
	 * in input.txt for example though there are 28 entries in input.txt but there
	 * are nine unique student IDs ranging from 1000-9000
	 * 
	 * @return returns an ArrayList of unique student IDs
	 */
	private ArrayList<Integer> uniqueStudentID() {
		ArrayList<Integer> uniqueStudentID = new ArrayList<Integer>();// an ArrayList created
		for (int i = 0; i < WordOfLineAsElements().size(); i++) {
			uniqueStudentID.add(Integer.parseInt(WordOfLineAsElements().get(i)[2]));// stores all the studentIDs present
																					// in WordOfLineAsElements()
		}
		LinkedHashSet<Integer> specific = new LinkedHashSet<>(uniqueStudentID);// creates an LinkedHashSet to store all
																				// unique elements of uniqueStudentID
		uniqueStudentID.clear();// All elements are removed
		uniqueStudentID.addAll(specific);// unique students IDs are added
		Collections.sort(uniqueStudentID);// this helps transcript to be printed in sorted manner of student Ids
		return uniqueStudentID;// student ID returned
	}

	/**
	 * This method is used to return an index of student that occurs first time in
	 * the list WordOFLineAsElements because thats all is needed if the same student
	 * occurs again it means the student is taking multiple courses this issue is
	 * already solved in createStudentObj method hence there is no need for storing
	 * indexes of same student again which really makes sense returning one index
	 * best
	 * 
	 * @param i this is the parameter called from buildStudentArray() which calls
	 *          this method to return the index of a student occurs first in
	 *          WordOfLineAsElements
	 * @return returns index of student which occurred for the first time
	 */
	private int uniqueStudentIdPosition(int i) {
		while (i != uniqueStudentID().size()) {// i is the index of for loop of buildStudentArray()
			for (int j = 0; j < WordOfLineAsElements().size(); j++) {
				if (uniqueStudentID().get(i) == Integer.parseInt(WordOfLineAsElements().get(j)[2])) { // compares student ids if found returns index j
					return j;// returns and method stops
				}
			}
		}
		return -1; // returns -1 if its unable to find studentIDs exactly same as stored in
					// uniqueStudentID()
	}

	/**
	 * This is the method from where everything is started it has the specific
	 * studentIDs which gives the count of total student based on their unique IDs
	 * Its main job is to create student object and stores them in an ArrayList of
	 * Student object
	 * 
	 * @return returns an ArrayList of students
	 */
	public ArrayList<Student> buildStudentArray() {
		ArrayList<Student> students = new ArrayList<Student>(); // An ArrayList of student object created
		for (int i = 0; i < uniqueStudentID().size(); i++) {
			Student object = createStudentObj(uniqueStudentIdPosition(i));// calls createStudentobj passing an index of student who occurred first												
			students.add(object);// adds student object to the list
		}
		return students;// returns ArrayList of Students
	}

	/**
	 * This method is used to print Transcript to a text file. It creates a file
	 * object calls the attribute this.outputFile to store the grades of students
	 * and the course they have taken in a specific order
	 * 
	 * @param students this method takes ArrayList as parameter returned by
	 *                 buildStudentArray
	 * @exception throws FileNotFoundException
	 */
	public void printTranscript(ArrayList<Student> students) {
		File file = new File(this.outputFile);// file created passes this.outputFile attribute to give specified name
		try {
			PrintStream stream = new PrintStream(file);
			System.setOut(stream);
			for (int i = 0; i < students.size(); i++) {
				System.out.println(students.get(i).getName() + "\t" + students.get(i).getStudentID() + "\n" // Prints name and student Id with "\t" space													
						+ "--------------------");
				for (int j = 0; j < students.get(i).getFinalGrade().size(); j++) {// used to print result of all the courses taken								
					System.out.println(students.get(i).getCourseTaken().get(j).getCode() + "\t"
							+ students.get(i).getFinalGrade().get(j));// prints course code and grade received
				}
				System.out.println("--------------------" + "\n" + "GPA: " + students.get(i).weightedGPA() + "\n");// prints
																													// weightedGPA
			}
			stream.close();// file closed
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * this is getter getGrade returns list of all the students that are stored in
	 * the file
	 * 
	 * @return ArrayList of all the elements line by line
	 */
	public ArrayList<Object> getGrade() {
		ArrayList<Object> getgrade = new ArrayList<Object>();
		for (Object o : this.grade) {
			getgrade.add(o);
		}
		return getgrade;
	}

	/**
	 * this method returns the inputFile given by client in the transcript
	 * constructor
	 * 
	 * @return returns File
	 */
	public File getInputFile() {
		return inputFile;
	}

	/**
	 * this is Setter method for inputFile attribute it sets a new file
	 * 
	 * @param inputFile
	 */
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
		this.readFile();
	}

	/**
	 * this method is Getter for ouputFile attribute
	 * 
	 * @return String stored in outputFile
	 */
	public String getOutputFile() {
		return outputFile;
	}

	/**
	 * this is Setter method for this.outFile it sets the name of the outputfile
	 * incase
	 * 
	 * @param outputFile
	 */
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * this is the Setter method for this.grade it sets grades of arrayList object
	 * 
	 * @param grade
	 */
	public void setGrade(ArrayList<Object> grade) {
		// To receive appropriate output of a trascript it is recommended
		// this.grade has elements of the format
		// CourseCode,CourseCredit,StudentID,[P|E]weightofAssessment(Grade of student
		// got),student name
		// Putting spaces in between will give wierd result or error in order to get
		// perfect output follow the format strictly
		ArrayList<Object> setgrade = new ArrayList<Object>();
		for (Object o : grade) {
			setgrade.add(o);
		}
		this.grade = setgrade;
	}

} // end of Transcript

class Student {
	private String studentID;
	private String name;
	private ArrayList<Course> courseTaken;
	private ArrayList<Double> finalGrade;

	/**
	 * This is the Student Constructor it is used to set all the attributes to its
	 * default values
	 */
	public Student() {
		this.studentID = null;
		this.name = null;
		this.courseTaken = new ArrayList<Course>();
		this.finalGrade = new ArrayList<Double>();
	}

	/**
	 * This is the Student Constructor it initializes all the values for its
	 * attributes through the one given in () this constructor creates deep copies
	 * for course ArrayList which is passed
	 * 
	 * @param studentID is the name of the studentId
	 * @param name      is the name of the Student
	 * @param course    is ArrayList of all the courses taken by a student
	 */
	public Student(String studentID, String name, ArrayList<Course> course) {
		this.studentID = studentID;
		this.courseTaken = new ArrayList<Course>();
		for (Course c : course) {
			this.courseTaken.add(new Course(c));
		}
		this.name = name;
		this.finalGrade = new ArrayList<Double>();
	}

	/**
	 * this method is one of the most important method it is used to calculate grade
	 * of a student based on its weight and add it to final grade
	 * 
	 * @param D is an ArrayList which stores
	 * @param I
	 * @exception throws InvalidTotalException if the sum of total weight of
	 *                   Assessment does not sum up to 100 and if total grade of a
	 *                   student is more than 100 or if particular grade in an
	 *                   Assessment is more than 100;
	 */
	public void addGrade(ArrayList<Double> D, ArrayList<Integer> I) {
		try {
			double Grade = 0.0;
			int sumInteger = I.stream().mapToInt(Integer::intValue).sum();// Shortcut to calculate sum of weights
			if (sumInteger == 100) {// only enter if the sum of weights is equal to 100
				for (int i = 0; i < D.size(); i++) {
					if (D.get(i) > 100) {// throws exception if a student received more than 1-- on a particular
											// Assessment
						this.finalGrade.add(0.0);// In that case 0 is added
						throw new InvalidTotalException("Grade received by student named " + this.name
								+ " cannot not be more than 100 contact Department for Investigation ");// Exception
																										// thrown
					}
					Grade += (D.get(i) / 100) * I.get(i);// computes the grade of particular assessment and add it to
															// Grade
					if (Grade > 100) {// if grade is greater than 100 than exception is thrown
						this.finalGrade.add(0.0);// In that case student will receive 0
						throw new InvalidTotalException("Total grade of " + this.name
								+ " shouldnot be more than 100 hence the student received 0.0");
					}
				}
				Grade = Math.round(Grade * 10.0) / 10.0;// round to one decimal place
				this.finalGrade.add(Grade);
			} else {// if sum of weights of Assessment does not add up to 100 throw an exception
				this.finalGrade.add(0.0);// student will receive 0 in that case
				throw new InvalidTotalException(
						"Total weight of Assessment does not add up to 100. Grade for that course is 0.0");
			}
		} catch (InvalidTotalException e) {// exception handled
			e.printStackTrace();
		}
	}

	/**
	 * this method calculate the GPA for all the courses taken by particular student
	 * it uses ArrayList finalGrade which had grades of all the courses taken by a
	 * student because of addCourse method which helped in storing the grades
	 * 
	 * @return returns the computed GPA of all the courses
	 */
	public double weightedGPA() {
		double average = 0.0;
		int credits = 0;
		for (int i = 0; i < this.finalGrade.size(); i++) {
			average += (GPARange(this.finalGrade.get(i)) * this.getCourseTaken().get(i).getCredit());
			// uses this formula to calculate GPA calls GPARange method to get a single digit int													
			credits += this.getCourseTaken().get(i).getCredit();// credits of a particular course are added
		}

		double weightedGPA = average / credits;// gpa is calculated by dividing average with credits
		weightedGPA = Math.round(weightedGPA * 10.0) / 10.0;// rounds to one decimal place
		return weightedGPA;// returns the computed gpa
	}

	/**
	 * this method returns an single digit int in order to compute gpa it returns an
	 * single digit int depending on the ranges given below
	 * 
	 * @param d this is the double value received from weightedGPA
	 * @return returns a single digit int depending on the range of d
	 */
	private double GPARange(double d) {
		if (d >= 90 && d <= 100) {
			return 9;// return 9 if d falls in this range
		} else if (d >= 80 && d <= 89.99) {
			return 8;// returns 8 if d falls in this range
		} else if (d >= 75 && d <= 79.99) {
			return 7;// returns 7 if d falls in this range
		} else if (d >= 70 && d <= 74.99) {
			return 6;// returns 6 if d falls in this range
		} else if (d >= 65 && d <= 69.99) {
			return 5;// returns 5 if d falls in this range
		} else if (d >= 60 && d <= 64.99) {
			return 4;// returns 4 if d falls in this range
		} else if (d >= 55 && d <= 59.99) {
			return 3;// returns 3 if d falls in this range
		} else if (d >= 50 && d <= 54.99) {
			return 2;// returns 2 if d falls in this range
		} else if (d >= 47 && d <= 49.99) {
			return 1;// return 0 if d falls in this range
		} else {
			return 0;// otherwise returns 0 if grade received are inappropriate
		}
	}

	/**
	 * this method adds all the extra courses taken by a particular student
	 * 
	 * @param course of this student
	 */
	public void addCourse(Course course) {
		this.courseTaken.add(new Course(course)); // deep copy of course added
	}

	/**
	 * this is the getmethod and returns deep copy of the arraylist of courses taken
	 * by a particular student
	 * 
	 * @return arraylist of courses taken by a student
	 */
	public ArrayList<Course> getCourseTaken() {
		ArrayList<Course> getcourse = new ArrayList<Course>();
		for (Course c : this.courseTaken) { // deep copy of courses created
			getcourse.add(new Course(c));
		}
		return getcourse; // returns deep copy
	}

	/**
	 * this is the setmethod for courses taken by a particular student sets deep
	 * copy of the course taken by a student and adds them in an arraylist
	 * 
	 * @param courseTaken ArrayList of courses taken added by the client
	 */
	public void setCourseTaken(ArrayList<Course> courseTaken) {
		ArrayList<Course> setcourse = new ArrayList<Course>();
		for (Course c : courseTaken) {
			setcourse.add(new Course(c)); // deep copy of courses created
		}
		this.courseTaken = setcourse; // sets deepcopy of courses to this.courseTaken
	}

	/**
	 * this is the get method for finalGrade it creates a deep copy of
	 * this.finalGrade
	 * 
	 * @return returns deep copy of the arraylist
	 */
	public ArrayList<Double> getFinalGrade() {
		ArrayList<Double> getfinalGrade = new ArrayList<Double>();
		for (Double d : this.finalGrade) {
			getfinalGrade.add(new Double(d)); // adds deep copy of finalgrade
		}
		return getfinalGrade; // returns deep copy of finalgrade
	}

	/**
	 * this method creates an ArrayList and do deep copies of the parameter and adds
	 * it to this.finalGrade
	 * 
	 * @param finalGrade this is an ArrayList of of grades sent by client
	 */
	public void setFinalGrade(ArrayList<Double> finalGrade) {
		ArrayList<Double> setfinalGrade = new ArrayList<Double>();
		for (Double d : finalGrade) {
			setfinalGrade.add(new Double(d)); // deep copies are created and added to setfinalGrade arraylist
		}

		this.finalGrade = setfinalGrade;// sets this.finalGrade with the value that the ArrayList
	}

	/**
	 * this method returns the studentID of the student
	 * 
	 * @return studentID of this student
	 */
	public String getStudentID() {
		return studentID; // returns studentID
	}

	/**
	 * this method sets the student ID of a student given by the client
	 * 
	 * @param studentID is the studentID of the student
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID; // sets studentID of this student
	}

	/**
	 * this is the get method that returns the name of this student
	 * 
	 * @return returns name of the student
	 */
	public String getName() {
		return name; // returns name of student
	}

	/**
	 * this is the set method which sets the name of student given by the client
	 * 
	 * @param name sets name of this student
	 */
	public void setName(String name) {
		this.name = name;
	}

}

class Course {
	private String code;
	private ArrayList<Assessment> assignment;
	private double credit;

	/**
	 * this is the Course constructor it sets all the values of its attributes to
	 * default values
	 */
	public Course() {
		this.code = null;
		this.assignment = new ArrayList<Assessment>();
		this.credit = 0.00;
	}

	/**
	 * this is the custom course constructor which sets all the values of the
	 * attributes to the values in the parameter it create deep copy of the
	 * ArrayList assignment
	 * 
	 * @param code       is the name of the course code
	 * @param assignment is the ArrayList of assignment consisting type and weight
	 *                   of assignments
	 * @param credit     is name for course credit
	 */
	public Course(String code, ArrayList<Assessment> assignment, double credit) {
		this.code = code;
		this.assignment = new ArrayList<Assessment>();
		for (Assessment a : assignment) {// creates deepcopy
			this.assignment.add(Assessment.getInstance(a.getType(), a.getWeight()));
		}
		this.credit = credit;
	}

	/**
	 * this is the copy constructor of Course since strings are immutable it copies
	 * directly it creates deep copy for the Assessment ArrayList
	 * 
	 * @param other is the name of the Course object that is passed
	 */
	public Course(Course other) {
		this.code = other.code;
		this.assignment = new ArrayList<Assessment>();
		for (Assessment a : other.assignment) {
			this.assignment.add(Assessment.getInstance(a.getType(), a.getWeight()));
		}
		this.credit = other.credit;
	}

	/**
	 * this is the equals method which is override version and it compares this
	 * object with other object and it also compares all the attributes of this
	 * object's elements with the other's elements
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true; // compares address of the object if aliasing is done
		}

		if (obj == null) {
			return false; // returns false if obj is null
		}

		if (this.getClass() != obj.getClass()) {
			return false; // return false if this.getClass() is not equal to obj.getClass()
		}

		Course course = (Course) obj; // Course object casted
		if (this.code != course.code) { // comparing attribute of Course object
			return false;
		}

		if (Double.doubleToLongBits(this.credit) != Double.doubleToLongBits(course.credit)) { // compares attribute of
																								// Course object
			return false;
		}

		for (int i = 0; i < this.assignment.size(); i++) { // compares content of the arraylist this.assignment with the other assignment(course.assignment)
			if (this.assignment.get(i).getType() != course.assignment.get(i).getType()) { // compare attribute of Assessment class
				return false;
			}
			
			if (this.assignment.get(i).getWeight() != course.assignment.get(i).getWeight()) {// comapre attribute of Assessment class												
				return false;
			}
		}
		return true;// returns true if other condition are not satisfied which means object compared is equal
					
	}

	/**
	 * this is the get method which returns Course code of this student
	 * 
	 * @return returns Course code of this student
	 */
	public String getCode() {
		return this.code;// returns this.code
	}

	/**
	 * this is the set method which set the value of Course code given by the client
	 * 
	 * @param code is the course code given by the client
	 */
	public void setCode(String code) {
		this.code = code; // sets the course code
	}

	/**
	 * this method returns an arraylist of Assessments this is the get method so it
	 * creates deep copy of the arraylist before returning the list
	 * 
	 * @return an arralist of Assessment
	 */
	public ArrayList<Assessment> getAssignment() {
		ArrayList<Assessment> getassignment = new ArrayList<Assessment>();
		for (Assessment a : this.assignment) {
			getassignment.add(Assessment.getInstance(a.getType(), a.getWeight())); // adds element by creating deep
																					// copies of the arraylist
		}
		return getassignment;
	}

	/**
	 * this is the set method which sets the ArrayList of Assessment in case there
	 * is some wrong marking done professor can change the marks for that particular
	 * method
	 * 
	 * @param assignment Arraylist of Assessment
	 */
	public void setAssignment(ArrayList<Assessment> assignment) {
		ArrayList<Assessment> setassignment = new ArrayList<Assessment>();
		for (Assessment a : assignment) {
			setassignment.add(Assessment.getInstance(a.getType(), a.getWeight())); 
			// adds element by creating deep copy of the content of Arraylist sent by the client															
		}
		this.assignment = setassignment;// sets this.assignment
	}

	/**
	 * this method returns course credit for this course
	 * 
	 * @return returns course credit
	 */
	public double getCredit() {
		return this.credit; // return course credit
	}

	/**
	 * this is the set method which sets course credit of this course by the
	 * department
	 * 
	 * @param credit is the course credit that is assigned by the client
	 */
	public void setCredit(double credit) {
		this.credit = credit; // sets course credit
	}

}

class Assessment {
	private char type;
	private int weight;

	/**
	 * this is a private default Assessment Constructor calls the custom Assessment
	 * constructor which sets default values of the all the Attributes
	 */
	private Assessment() {
		this('\u0000', 0); // '\u0000' means null character
	}

	/**
	 * this is a private Assessment constructor that assgns values of the parameters
	 * its attributes
	 * 
	 * @param type   is the name of type of Assessment E for exam P for practical
	 * @param weight is the weight of the Assessment
	 */
	private Assessment(char type, int weight) {
		this.type = type;
		this.weight = weight;
	}

	/**
	 * this is a static factory method which works same as a constructor it takes
	 * the value from the client and returns it a newly created object by calling
	 * the private custom constructor
	 * 
	 * @param type   is the name of the type of Assessment E for exam and P for
	 *               practica;
	 * @param weight is the weight of the Assessment
	 * @return
	 */
	public static Assessment getInstance(char type, int weight) {
		return new Assessment(type, weight);
	}

	/**
	 * this is the overridden version of equals method it compares this object with
	 * the other object it compares all the attributes of this class and returns
	 * true if they are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true; // returns true if address of this object and obj object are equal
		}
		if (obj == null) {
			return false; // returns false if obj object is null
		}
		if (this.getClass() != obj.getClass()) {
			return false; // returns false if this.getClass() is not equal to obj.getClass()
		}
		Assessment assignment = (Assessment) obj;// Assessment object is casted
		if (this.type != assignment.type) { // comparing attribute of this object with other object
			return false; // returns false if this attribute is not same
		}
		if (this.weight != assignment.weight) {// comparing attribute of this object with other object
			return false;// returns false if this attribute is not same
		}
		return true; // if all conditions are not satisfied that it returns true means object are
						// same
	}

	/**
	 * this method returns the Attribute this.type of this class
	 *
	 * @return return type
	 */
	public char getType() {
		return this.type;// return this.type
	}

	/**
	 * this is the set method which sets the Attribute this.type of this class
	 * 
	 * @param type is the char given by the client and its set to this.type
	 */
	public void setType(char type) {
		this.type = type;// sets this.type
	}

	/**
	 * this is the get method which returns the weight attribute for this class
	 * 
	 * @return returns this.weight of this class
	 */
	public int getWeight() {
		return this.weight;// returns this.weight of object
	}

	/**
	 * this is the set method which sets the weight of Assessment class
	 * 
	 * @param weight is the int value given by client and sets this.weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;// sets this.weight
	}

}

@SuppressWarnings("serial")
class InvalidTotalException extends Exception {

	protected InvalidTotalException(String message) {
		super(message);
	}
}
