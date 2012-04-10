public class Main {
    private static String fichier = "plan.txt";
    
    public static void main(String[] args) {
        Plan plan = new Plan(fichier);
        System.out.println(plan);
    }
}