package frc.robot.vision;

public class ProtocolHandler {
	private String buffer;
	private String lastCompletedString;
	private boolean recievedEmptyPacket;

	public ProtocolHandler() {
		buffer = "";
		recievedEmptyPacket = true;
	}

	/*
	 * Example packet of the protocol we're using: 
	 * >x1,y1|x2,y2#x1,y1|x2,y2
	 * pretty much, the '>' symbols frame the packets, '#' separates objects/targets/convex hulls,
	 *  '|' separates points, and ',' separates the x and y coordinates of one point.
	 */
	public void addToBuffer(String stringToAppend) {
		for (int i = 0; i < stringToAppend.length(); i++) {
			char charToAppend = stringToAppend.charAt(i);
			if (charToAppend == '>') { // is current character opening character?
				if(buffer.length() == 0){ // check for empty packets
					recievedEmptyPacket = true;
				} else {
					if(lastCompletedString == null) { // make sure to discard premature packets
						recievedEmptyPacket = true;
						lastCompletedString = "";
					} else {
						recievedEmptyPacket = false;
						lastCompletedString = buffer;
					}
				}
				buffer = "";
			} else { // otherwise, add current char to the buffer
				buffer += charToAppend;
			}
		}
	}
	
	public String getLastNonEmptyString() {
		return lastCompletedString;
	}
	
	public String getLastString() {
		if(recievedEmptyPacket){
			return "";
		} else {
			return getLastNonEmptyString();
		}
	}
}