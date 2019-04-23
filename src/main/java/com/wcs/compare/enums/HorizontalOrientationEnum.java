package com.wcs.compare.enums;

/**
 * 
 * Enum horizontal orientation
 *
 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
 * @version $Revision: 1.1 $
 */
public enum HorizontalOrientationEnum {
	LEFT, RIGHT;
	
	@Override
	public String toString() {
		switch (this) {
		case LEFT:
			return "LEFT";
		case RIGHT:
			return "RIGHT";
		}
		throw new Error("Error occurred to get the correct horizontal orientation.");
	}
}
