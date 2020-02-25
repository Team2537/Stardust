package frc.robot.vision;

public class VisionPacketHandler {

	public static String encodeVisionPacket(Point[] visionPackets) {
		String stringToOutput = ">";
		for (int i = 0; i < visionPackets.length; i++) {
			stringToOutput += visionPackets[i].outputPacket();
		}
		stringToOutput += ">";
		return stringToOutput;
	}

	public static Target[] decodeVisionPacket(String packetToDecode) {
		if(packetToDecode == "" || packetToDecode == null){
			return new Target[0];
		}
		String[] stringTargets = packetToDecode.split("#");
		Target[] targets = new Target[stringTargets.length];
		for (int i = 0; i < stringTargets.length; i++) {
			String[] pointArr = stringTargets[i].split("\\|");
			Point[] points = new Point[pointArr.length];
			for (int j = 0; j < pointArr.length; j++) {
				String[] coordinates = pointArr[j].split(",");
				points[j] = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
			}
			targets[i] = new Target(points);
		}
		return targets;
	}
}