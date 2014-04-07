package utils;

import java.util.HashMap;
import java.util.Map;

import frontend.UserDataImpl;

public class SysInfo implements Runnable{

    static final public String MEMORY_USAGE_FIELD = "MemoryUsage";
    static final public String TOTAL_MEMORY_FIELD = "TotalMemory";
    static final public String TIME_FIELD = "Time";
    static final public String CCU_FIELD = "CCU";

    static final public String MEMORY_USAGE_DATA = "/statistic/memoryUsage";
    static final public String TOTAL_MEMORY_DATA = "/statistic/totalMemory";
    static final public String TIME_DATA = "/statistic/time";
    static final public String CCU_DATA = "/statistic/ccu";

	private Runtime runtime = Runtime.getRuntime();
	private String lastDate;
	private static Map<String, String> data =
			new HashMap<String, String>();

	public SysInfo(){
		data.put(MEMORY_USAGE_FIELD, MEMORY_USAGE_DATA);
		data.put(TOTAL_MEMORY_FIELD, TOTAL_MEMORY_DATA);
		data.put(TIME_FIELD, TIME_DATA);
		data.put(CCU_FIELD, CCU_DATA);
	}

	public static String getStat(String service){
		return ("["+VFS.readFile(data.get(service))+"]");
	}

	public void run(){
		for(String service:data.keySet()){
			lastDate=TimeHelper.getTime();
			if(service.equals("MemoryUsage")){
				VFS.writeToFile(data.get(service), String.valueOf((int) (runtime.totalMemory()-runtime.freeMemory())));
			}
			else if(service.equals("TotalMemory")){
				VFS.writeToFile(data.get(service), String.valueOf((int) (runtime.totalMemory())));
			}
			else if(service.equals("Time")){
				VFS.writeToFile(data.get(service), lastDate);
			}
			else if(service.equals("CCU")){
				VFS.writeToFile(data.get(service), String.valueOf(UserDataImpl.ccu()));
			}
		}
		while (true){
			TimeHelper.sleep(1000);
			for(String service:data.keySet()){
				lastDate=TimeHelper.getTime();
				if(service.equals("MemoryUsage")){
					VFS.writeToEndOfFile(data.get(service), ","+String.valueOf((int) (runtime.totalMemory()-runtime.freeMemory())));
				}
				else if(service.equals("TotalMemory")){
					VFS.writeToEndOfFile(data.get(service), ","+String.valueOf((int) (runtime.totalMemory())));
				}
				else if(service.equals("Time")){
					VFS.writeToEndOfFile(data.get(service), ","+lastDate);
				}
				else if(service.equals("CCU")){
					VFS.writeToEndOfFile(data.get(service), ","+String.valueOf(UserDataImpl.ccu()));
				}
			}
		}
	}
}