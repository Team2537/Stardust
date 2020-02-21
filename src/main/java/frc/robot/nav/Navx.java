package frc.robot.nav;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;

public class Navx extends AHRS {
	
	private static Navx singleton;
	private double totalAngle = 0;
	
	public static Navx getInstance(){
		if(singleton == null){
			singleton = new Navx(Port.kMXP);
		}
		return singleton;
	}
	
	private Navx(Port port){
		super(port);
	}
	
	/**
	 * @return direction of robot from [-180,180], increasing clockwise, with 0 as positive y
	 */
	@Override
	public double getAngle(){
//		return ((super.getAngle() + 180) % 360 + 360) % 360 - 180;
		return super.getAngle();
    }
    
	
	/**
	 * @return direction of robot from [0,2PI], increasing counter-clockwise, with 0 as positive x
	 */
	public double getRadians(){
		return (360 - ((super.getAngle() + 270) % 360)) * Math.PI / 180;
	}

	public void updateTotalAngle() {
		totalAngle += getYaw();
		if(totalAngle < -180) {
			totalAngle += 180;
		} else if(totalAngle > 180) {
			totalAngle -= 180;
		}
	}

	public void updateAndReset() {
		updateTotalAngle();
		reset();
		reset();
	}
}