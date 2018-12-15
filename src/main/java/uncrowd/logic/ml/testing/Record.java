package uncrowd.logic.ml.testing;

public class Record {
	public enum RECORD_TYPE {INCOMING, OUTGOING};
	
	private RECORD_TYPE type;
	private int count;
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minute;
	private int second;
	private int dayOfWeek;
	
	public RECORD_TYPE getType() {
		return type;
	}

	public void setType(RECORD_TYPE type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	Record(String type, String month,
						String day,
						String year,
						String hour,
						String minute,
						String second, String count){
		if(type.equals("9")){
			this.type = RECORD_TYPE.INCOMING;
		}else{
			this.type = RECORD_TYPE.OUTGOING;
		}

		this.day = Integer.parseInt(day);
		this.month = Integer.parseInt(month);
		this.year = Integer.parseInt(year);
		this.hour = Integer.parseInt(hour);
		this.minute = Integer.parseInt(minute);
		this.second = Integer.parseInt(second);
		this.count = Integer.parseInt(count);
	}
	
	public String asDate()
	{
		//"MM/dd/yy,HH:mm:ss"
		return String.format("%02d/%02d/%02d,%02d:%02d:%02d", month, day, year, hour, minute, second);
	}
}
