package ge.rtb;
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
	double selfVelocity = getVelocity();
	double selfX = getX();
	double selfY = getY();

	double absoluteEnemyBearing = enemyBearing + selfHeading;
	double enemyCompensatedVelocity = enemyVelocity * Math.sin(enemyHeading - absoluteEnemyBearing);

	double enemyX = getX() + enemyDistance * Math.sin(absoluteEnemyBearing);
	double enemyY = getY() + enemyDistance * Math.cos(absoluteEnemyBearing);
	
	double predictedX = enemyX;
	double predictedY = enemyY;
	
	predictedX += Math.sin(enemyHeading) * (enemyVelocity+1.8);
	predictedY += Math.cos(enemyHeading) * (enemyVelocity+1.8);
	
	double theta = robocode.util.Utils.normalAbsoluteAngle(Math.atan2(predictedX - selfX, predictedY - selfY));
	
	setTurnRadarLeftRadians(getRadarTurnRemainingRadians());

	if(enemyDistance > 300) {
	    gunTurnAngle = robocode.util.Utils.normalRelativeAngle(theta - selfGunHeading + enemyCompensatedVelocity/10); 
	    setTurnGunRightRadians(gunTurnAngle);
	    setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfHeading));
	    setAhead((enemyDistance - 60) * moveDirection);
	    setFire(1);
	} else 	if(enemyDistance > 100) {
	    gunTurnAngle = robocode.util.Utils.normalRelativeAngle(theta - selfGunHeading + enemyCompensatedVelocity/10); 
	    setTurnGunRightRadians(gunTurnAngle);
	    setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfHeading));
	    setAhead((enemyDistance - 60) * moveDirection);
	    setFire(1);
	} else {
	    gunTurnAngle = robocode.util.Utils.normalRelativeAngle(theta - selfGunHeading + enemyCompensatedVelocity/10);
	    setTurnGunRightRadians(gunTurnAngle);
	    setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteEnemyBearing - selfHeading));
	    setAhead((enemyDistance - 60) * moveDirection);
	    setFire(3);
	}

	out.println("Bearing = "+enemyBearing);
	out.println("Distace = "+enemyDistance);
	out.println("Heading = "+enemyHeading);
	out.println("Velocity = "+enemyVelocity);
	out.println("Gun Heading = "+selfGunHeading);
	out.println("My Heading = "+selfHeading);
	out.println("My Velocity = "+selfVelocity);
	
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
