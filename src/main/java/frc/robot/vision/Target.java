package frc.robot.vision;

public class Target {
	private Point[] points;
	private Point[] boundingBox;
	private Point boundingBoxCenter;
	private double boundingBoxArea;

	public Target(Point[] points) {
		this.points = points;
	}

	public Point[] getPoints() {
		return points;
	}

	public Point[] getBoundingBox() {
		// must be run with at least 4 points in the target for no duplicates to occur
		// top left and bottom right
		if(boundingBox == null){
			boundingBox = new Point[2];
			int leftX = Integer.MAX_VALUE;
			int rightX = Integer.MIN_VALUE;
			int upY = Integer.MIN_VALUE;
			int downY = Integer.MAX_VALUE;
			for (int i = 0; i < points.length; i++) {
				if (points[i].getX() < leftX) {
					leftX = points[i].getX();
				}
				if (points[i].getX() > rightX) {
					rightX = points[i].getX();
				}
				if (points[i].getY() > upY) {
					upY = points[i].getY();
				}
				if (points[i].getY() < downY) {
					downY = points[i].getY();
				}
			}
			boundingBox[0] = new Point(leftX, upY); 
			boundingBox[1] = new Point(rightX, downY);
		}
		return boundingBox;
	}

	public double getBoundingBoxLength(){
		if(boundingBox == null){
			getBoundingBox();
		}
		return boundingBox[1].getX() - boundingBox[0].getX();
	}
	
	public double getBoundingBoxHeight(){
		if(boundingBox == null){
			getBoundingBox();
		}
		return boundingBox[1].getY() - boundingBox[0].getY();
	}
	
	public double getBoundingBoxArea(){
		if(boundingBoxArea == 0){
			boundingBoxArea = Math.abs(getBoundingBoxLength()*getBoundingBoxHeight());
		}
		return boundingBoxArea;
	}
	
	public Point getBoundingBoxCenter(){
		if(boundingBoxCenter == null){
			Point topLeft = getBoundingBox()[0];
			boundingBoxCenter = new Point((int)(topLeft.getX() + getBoundingBoxLength()/2),
					(int)(topLeft.getY() + getBoundingBoxHeight()/2));
		}
		return boundingBoxCenter;
	}

	public static Point getMidpoint(Target[] targets){
		if(targets.length < 2) return new Point(0,0);

		double[] boundingBoxAreas = new double[targets.length];

		for(int i = 0; i < boundingBoxAreas.length; i++){
			System.out.println(boundingBoxAreas[i]);
			boundingBoxAreas[i] = targets[i].getBoundingBoxArea();   
		}

		double first = -Double.MAX_VALUE, second = -Double.MAX_VALUE;
		int firstIndex = 0, secondIndex = 0;
		for (int i = 0; i < boundingBoxAreas.length; i++) {
			if (boundingBoxAreas[i] > first) {
				second = first;
				first = boundingBoxAreas[i];
				firstIndex = i;
			}
			// if current element is between first and second, update second to store value of current variable
			else if (boundingBoxAreas[i] > second && boundingBoxAreas[i] != first){
				second = boundingBoxAreas[i];
				secondIndex = i;
			}
		}

		Point box1 = targets[firstIndex].getBoundingBoxCenter();
		Point box2 = targets[secondIndex].getBoundingBoxCenter();
		return new Point((box1.getX() + box2.getX())/2, (box1.getY() + box2.getY())/2);
	}
}