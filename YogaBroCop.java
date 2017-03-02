package ge.rtb.YOGABROCO;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * YogaBroCop - a robot by (your name here)
 */
public class YogaBroCop extends AdvancedRobot
{
    
    private double gunTurnAngle;
    private double moveDirection = 1;
    /**
     * run: YogaBroCop's default behavior
     */
    public void run() {
	setColors(Color.black,Color.red,Color.blue);
	setAdjustRadarForRobotTurn(true);
	setAdjustGunForRobotTurn(true); 
	turnRadarRightRadians(Double.POSITIVE_INFINITY);
    }
    
    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
	
	double enemyBearing = e.getBearingRadians();
	double enemyDistance = e.getDistance();
	double enemyHeading = e.getHeadingRadians();
	double enemyVelocity = e.getVelocity();

	double selfGunHeading = getGunHeadingRadians();
	double selfHeading = getHeadingRadians();

	double absoluteEnemyBearing = enemyBearing + selfHeading;
	double enemyCompensatedVelocity = enemyVelocity * Math.sin(enemyHeading - absoluteEnemyBearing); // Verify this calculation

	setTurnRadarLeftRadians(getRadarTurnRemainingRadians());

	// Add velocity change???? Test cases with or without
	//if(Math.random() >.9){
	//    setMaxVelocity((12*Math.random())+12);
	//}

	if(enemyDistance > 300) {
	    gunTurnAngle = robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfGunHeading + enemyCompensatedVelocity/50); //Chech this 22 value, alongside with the enemycompensated variable, it looks strange
	    setTurnGunRightRadians(gunTurnAngle);
	    setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfHeading + enemyCompensatedVelocity/getVelocity()));
	    setAhead((enemyDistance - 60) * moveDirection);
	    setFire(1);
	} else 	if(enemyDistance > 100) {
	    gunTurnAngle = robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfGunHeading + enemyCompensatedVelocity/30); //Chech this 22 value, alongside with the enemycompensated variable, it looks strange
	    setTurnGunRightRadians(gunTurnAngle);
	    setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfHeading + enemyCompensatedVelocity/getVelocity()));
	    setAhead((enemyDistance - 60) * moveDirection);
	    setFire(1);
	} else {
	    gunTurnAngle = robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfGunHeading + enemyCompensatedVelocity/15); //Chech this 22 value, alongside with the enemycompensated variable, it looks strange
	    setTurnGunRightRadians(gunTurnAngle);
	    setTurnLeftRadians(robocode.util.Utils.normalRelativeAngle(Math.PI/2 - enemyBearing));
	    setAhead((enemyDistance - 60) * moveDirection);
	    setFire(3);
	}

	out.println("Bearing = "+enemyBearing);
	out.println("Distace = "+enemyDistance);
	out.println("Heading = "+enemyHeading);
	out.println("Velocity = "+enemyVelocity);
	out.println("Gun Heading = "+selfGunHeading);
	out.println("My Heading = "+selfHeading);
	
    }
    
    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
	// Implement detection of how to run and maybe try to circle the enemy
	// Replace the next line with any behavior you would like
	setTurnLeftRadians(1.8);
	ahead(60*moveDirection);
    }
    
    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
	moveDirection = -1 * moveDirection;
    }	
}
