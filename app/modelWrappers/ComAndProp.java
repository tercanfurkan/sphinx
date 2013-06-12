package modelWrappers;

import models.enums.ReadStatus;

public class ComAndProp {
	
	public String component = null;
	public String subComponent = null;
	public String property = null;
	public ReadStatus status = ReadStatus.OK;
	
	
	public ComAndProp (String str) {
		
		if (hasRelevantValue(str)) {
			
			String[] comAndProp = str.split(" ");
			String[] comParts = comAndProp[0].split("-");
			this.component = comParts[0] + comParts[1];
			
			switch (comAndProp.length) {
			
			case 2:
				if (comAndProp[1].contains("P1KA") || comAndProp[1].contains("P2KA") || comAndProp[1].contains("P3KA") || comAndProp[1].contains("P4KA")) {
					this.property = "PKA";
					this.subComponent = comAndProp[1].substring(0, 2);
				}
				else {
					this.property = comAndProp[1];
				}
				break;
				
			case 3:
				this.subComponent = comAndProp[1];
				this.property = comAndProp[2];
				break;

			default:
				break;
			}
		}
			

	}
	
	private boolean hasRelevantValue(String str) {
		if (str == null || str.equals("")) {
			this.status = ReadStatus.EMPTY;
			return false;
		} else 
			return true;
	}

}
