package models;

public class ExamSession {

    //Declaring variables
    private String name;
    private String id;
    private int year;
    private int semester;
    private String subject1;
    private int sub1Internal1;
    private int sub1Internal2;
    private int sub1Sem;
    private String subject2;
    private int sub2Internal1;
    private int sub2Internal2;
    private int sub2Sem;
    private String subject3;
    private int sub3Internal1;
    private int sub3Internal2;
    private int sub3Sem;

    //Constructor
    public ExamSession(String name, String id, int year, int semester, 
    String subject1, int sub1Internal1, int sub1Internal2, int sub1Sem,
    String subject2, int sub2Internal1, int sub2Internal2, int sub2Sem,
    String subject3, int sub3Internal1, int sub3Internal2, int sub3Sem){
        this.name = name;
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.subject1 = subject1;
        this.sub1Internal1 = sub1Internal1;
        this.sub1Internal2 = sub1Internal2;
        this.sub1Sem = sub1Sem;
        this.subject2 = subject2;
        this.sub2Internal1 = sub2Internal1;
        this.sub2Internal2 = sub2Internal2;
        this.sub2Sem = sub2Sem;
        this.subject3 = subject3;
        this.sub3Internal1 = sub3Internal1;
        this.sub3Internal2 = sub3Internal2;
        this.sub3Sem = sub3Sem;
    }

    //Getters for student info
    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public int getYear(){
        return year;
    }

    public int getSemester(){
        return semester;
    }

    public String getSubject1(){
        return subject1;
    }

    public int getSub1Internal1(){
        return sub1Internal1;
    }

    public int getSub1Internal2(){
        return sub1Internal2;
    }

    public int getSub1Sem(){
        return sub1Sem;
    }

    public String getSubject2(){
        return subject2;
    }

    public int getSub2Internal1(){
        return sub2Internal1;
    }

    public int getSub2Internal2(){
        return sub2Internal2;
    }

    public int getSub2Sem(){
        return sub2Sem;
    }

    public String getSubject3(){
        return subject3;
    }

    public int getSub3Internal1(){
        return sub3Internal1;
    }

    public int getSub3Internal2(){
        return sub3Internal2;
    }

    public int getSub3Sem(){
        return sub3Sem;
    }

    //Calculating total marks for each subject
    public double getSub1Total(){
        return sub1Internal1 + sub1Internal2 + sub1Sem;
    }
    
    public double getSub2Total(){
        return sub2Internal1 + sub2Internal2 + sub2Sem;
    }
    
    public double getSub3Total(){
        return sub3Internal1 + sub3Internal2 + sub3Sem;
    }

    //Percentage per subject (out of 140)
    public double getSub1Percentage(){
        return (getSub1Total() / 140.0) * 100;  
    }

    public double getSub2Percentage(){
        return (getSub2Total() / 140.0) * 100;  
    }

    public double getSub3Percentage(){
        return (getSub3Total() / 140.0) * 100;  
    }

    //Calculating overall percentage and GPA
    public double getsgpa(){
        double overallTotal = getSub1Total() + getSub2Total() + getSub3Total();
        double overallPercentage = (overallTotal / 420.0) * 100;
        return overallPercentage / 10.0;
    }
}
