package game2d.utils;

import java.lang.annotation.Repeatable;

public enum Unit{
	NULL,
	
	PIXEL{
		@Override public Unit inverse() {return I_PIXEL;}
	},
	METRE{
		@Override public Unit inverse() {return I_METRE;}
	},
	
	I_PIXEL{
		@Override public Unit inverse() {return PIXEL;}
	},
	I_METRE{
		@Override public Unit inverse() {return METRE;}
	},
	
	RADIAN,DEGREE,
	
	MILLISECOND{
		@Override public Unit inverse() {return I_MILLISECOND;}
	}, 
	SECOND{
		@Override public Unit inverse() {return I_SECOND;}
	},
	
	I_MILLISECOND{
		@Override public Unit inverse() {return MILLISECOND;}
	},
	I_SECOND{
		@Override public Unit inverse() {return SECOND;}
	},
	
	PIXEL_SECOND_VELOCITY(PIXEL,SECOND.inverse()), 
	METRE_SECOND_VELOCITY(METRE,SECOND.inverse()),
	PIXEL_SECOND_ACCELERATION(PIXEL,SECOND.inverse(),SECOND.inverse()), 
	METRE_SECOND_ACCELERATION(METRE,SECOND.inverse(),SECOND.inverse())
	
	;
	
	public Unit inverse() {
		return Unit.NULL;
	}
	
	public final Unit[] baseUnits;
	
	private Unit(Unit...units) {
		this.baseUnits = units;
	}
	
	
	public static abstract class Distance{
		public static final Unit
				PIXEL = Unit.PIXEL,
				METRE = Unit.METRE;
		
		/**
		 * Returns conversion factor
		 * @param from
		 * @param to
		 * @param metreDefinition
		 * @return
		 */
		public static double convert(Unit from, Unit to, int metreDefinition) {
			double scaleToMetreFactor = switch(from) {
				case PIXEL -> 1./metreDefinition;
				case METRE -> 1;
				default -> 0;
			};
			
			double scaleTfromMetreFactor = switch(to) {
				case PIXEL -> metreDefinition;
				case METRE -> 1;
				default -> 0;
			};
			
			return scaleToMetreFactor*scaleTfromMetreFactor;
		}
	}
	public abstract class Rotation{
		public static final Unit
				RADIAN = Unit.RADIAN,
				DEGREE = Unit.DEGREE;
	}
	public abstract class Time{
		public static final Unit
				MILLISECOND = Unit.MILLISECOND,
				SECOND = Unit.SECOND;
	}
	public abstract class Movement{
		public static final Unit
			PIXEL_SECOND_VELOCITY = Unit.MILLISECOND,
			METRE_SECOND_VELOCITY = Unit.METRE_SECOND_VELOCITY,
			PIXEL_SECOND_ACCELERATION = Unit.PIXEL_SECOND_ACCELERATION,
			METRE_SECOND_ACCELERATION = Unit.METRE_SECOND_ACCELERATION;
	}
	
	@Repeatable(Measurements.class)
	public @interface Measurement{
		public Unit unit() default NULL;
		public String var() default "";
	}
	
	public @interface Measurements{
		Measurement[] value();
	}
}