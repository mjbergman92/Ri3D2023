package frc.robot.autons;

import frc.pathplanner.PathPlannerFollower;
import frc.robot.Constants.AUTO;

public enum Path {
	NULL_PATH("");

	String pathName;
	PathPlannerFollower path;

	Path() {
		this.pathName = this.name();
	}

	Path(String pathName) {
		this.pathName = pathName;
	}

	public void loadPath() {
		path = new PathPlannerFollower(pathName, AUTO.kMaxSpeedMetersPerSecond,
		                               AUTO.kMaxAccelerationMetersPerSecondSquared);
	}

	public PathPlannerFollower getPath() {
		return path;
	}
}