package com.eldar.TomSawyer;

import java.awt.Color;

public class CorpBottomUp extends Corp {

	public void Simulate() {
	  Drone drone = corp;
	  while (drone.left != null) drone = drone.left;
	  drone.starter = true;
	  drone.Paint(Color.WHITE);
	}

}
