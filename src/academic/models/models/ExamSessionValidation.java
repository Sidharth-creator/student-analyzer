package models;

public class ExamSessionValidation {

    // Validate Name 
    public static boolean isValidName(String name) {
        if (name == null) return false;
        return name.matches("^[A-Za-z ]+$");
    }

    // Validate Year
    public static boolean isValidYear(int year) {
        return year >= 1 && year <= 4;
    }

    // Validate Semester
    public static boolean isValidSemester(int semester) {
        return semester >= 1 && semester <= 8;
    }

    // Validate Internal Marks
    public static boolean isValidInternalMark(int mark) {
        return mark >= 0 && mark <= 40;
    }

    // Validate Semester Marks
    public static boolean isValidSemMark(int mark) {
        return mark >= 0 && mark <= 60;
    }

    // Validate entire ExamSession object & display error message
    public static String getValidationMessage(ExamSession exam) {
        if (exam == null){ 
            return "Exam session details are missing.";
        }
        if (!isValidName(exam.getName())){ 
            return "Invalid name (only letters & spaces allowed).";
        }
        if (!isValidYear(exam.getYear())) {
            return "Invalid year (must be within 1-4).";
        }
        if (!isValidSemester(exam.getSemester())) {
            return "Invalid semester (must be within 1-8).";
        }

        if (!isValidInternalMark(exam.getSub1Internal1()) ||
            !isValidInternalMark(exam.getSub1Internal2()) ||
            !isValidInternalMark(exam.getSub2Internal1()) ||
            !isValidInternalMark(exam.getSub2Internal2()) ||
            !isValidInternalMark(exam.getSub3Internal1()) ||
            !isValidInternalMark(exam.getSub3Internal2())) {
            return "Invalid internal marks (must be within 0-40).";
        }

        if (!isValidSemMark(exam.getSub1Sem()) ||
            !isValidSemMark(exam.getSub2Sem()) ||
            !isValidSemMark(exam.getSub3Sem())){
            return "Invalid semester marks (must be 0-60).";
        }
    
        return null;
    }
}
