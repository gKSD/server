package resource;

public class TimeSettings implements Resource{
	private static int exitTime;
	
	public static int getExitTime(){
		return exitTime;
	}


    public static  void setExitTime_ForTest(int time){
        exitTime = time;
    }
}
