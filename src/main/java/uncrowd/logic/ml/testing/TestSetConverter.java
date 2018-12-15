package uncrowd.logic.ml.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import uncrowd.logic.entity.LastDayCrowdEntity;
import uncrowd.logic.ml.testing.Record.RECORD_TYPE;

public class TestSetConverter {
	private static final String TEST_DATA_PATH = "ml_test_data/CalIt2.data";
	
	public List<LastDayCrowdEntity> getTestData(int day){
		List<LastDayCrowdEntity> convertedRecords = new ArrayList<>();
		List<Record> fileRecords = readRecordsFromFile(TEST_DATA_PATH);
		fileRecords = fileRecords.stream().filter(rec-> rec.getDayOfWeek() == (day-1)).collect(Collectors.toList());
		// Taking only the last day from the dataset
		fileRecords = fileRecords.subList(Math.max(fileRecords.size() - 80, 0), fileRecords.size());
		int currCrowdCount = 28;
		for(Record record: fileRecords) {
			LastDayCrowdEntity convertedEntity = new LastDayCrowdEntity();
			convertedEntity.setCount(record.getCount());
			if(record.getType() == RECORD_TYPE.INCOMING) {
				currCrowdCount += record.getCount();
			}else {
				currCrowdCount -= record.getCount();
				LastDayCrowdEntity countEntity = new LastDayCrowdEntity();
				
				// Adding a total count as well
				countEntity.setType(LastDayCrowdEntity.COSTUMERS_COUNT_TYPE);
				countEntity.setCount(currCrowdCount);
				countEntity.setTimeId(record.getHour() * 100 + record.getMinute());
				convertedRecords.add(countEntity);
			}
			convertedEntity.setType((record.getType() == RECORD_TYPE.INCOMING)? LastDayCrowdEntity.ENTERING_COSTUMERS_TYPE : 
				LastDayCrowdEntity.EXITING_COSTUMERS_TYPE);
			convertedEntity.setTimeId(record.getHour() * 100 + record.getMinute());
			convertedRecords.add(convertedEntity);
		}
		
		return convertedRecords;
	}
	
	private static List<Record> readRecordsFromFile(String filePath){
		
		int currDayIndex = 0;
		int lastRecordDay = 0;
		
		List<Record> fileRecords = new ArrayList<>();
		
		String regex = "([79]),(\\d\\d)/(\\d\\d)/(\\d\\d),(\\d\\d)\\:(\\d\\d)\\:(\\d\\d),(\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		
		File calt2 = new File(filePath);
		
		try (BufferedReader br = new BufferedReader(new FileReader(calt2))) {
			String line;

			while ((line = br.readLine()) != null) {
				matcher = pattern.matcher(line);
				if (matcher.find()) {
					
					Record reecord = new Record(matcher.group(1),  
							matcher.group(2), 
							matcher.group(3),
							matcher.group(4),
							matcher.group(5),
							matcher.group(6),
							matcher.group(7),
							matcher.group(8));
					
					// Calculating the day (24.07.05 is sunday)
					if(lastRecordDay != 0 && lastRecordDay != reecord.getDay())
					{
						if(++currDayIndex == 7)
						{
							currDayIndex = 0;
						}
					}
					lastRecordDay = reecord.getDay();

					reecord.setDayOfWeek(currDayIndex);
					
					fileRecords.add(reecord);
					
				}else{
					System.out.println("Error parsing line: " + line);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileRecords;
	}

}
