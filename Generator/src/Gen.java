

public class Gen {
    private static int s = -1;
    private static int p = 499;
    private static int w = 999;
    private static int Id = 0;

    public static int genId(String type){

    switch (type) {
        case "socialWorker":
            s++;
            if (s >= 500){
                Id = Integer.parseInt(null);
            }
            else {
            Id = s;}
            break;
        case "pregnant":
            p++;
            if (p >= 1000){
                Id = Integer.parseInt(null);
            }
            else {
                Id = p;}
            break;
        case "wheelchair":
            w++;
            if (w >= 1500){
                Id = Integer.parseInt(null);
            }
            else {
                Id = w;}
            break;
    }
        return Id;
    }
    public static int genKey(){
        return (int)(Math.random()*8999+1000);
    }

}
