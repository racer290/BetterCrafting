package racer290.bettercrafting.util;


public class BetterMathHelper {
	
	public static class CubicMatrix3x3<T> {
		
		public T e000, e100, e200;
		public T e001, e101, e201;
		public T e002, e102, e202;
		
		public T e010, e110, e210;
		public T e011, e111, e211;
		public T e012, e112, e212;
		
		public T e020, e120, e220;
		public T e021, e121, e221;
		public T e022, e122, e222;
		
		public CubicMatrix3x3() {}
		
		public CubicMatrix3x3(T fill) {
			
			this.e000 = fill;
			this.e001 = fill;
			this.e002 = fill;
			
			this.e100 = fill;
			this.e101 = fill;
			this.e102 = fill;
			
			this.e200 = fill;
			this.e201 = fill;
			this.e202 = fill;
			
			this.e010 = fill;
			this.e011 = fill;
			this.e012 = fill;
			
			this.e110 = fill;
			this.e111 = fill;
			this.e112 = fill;
			
			this.e210 = fill;
			this.e211 = fill;
			this.e212 = fill;
			
			this.e020 = fill;
			this.e021 = fill;
			this.e022 = fill;
			
			this.e120 = fill;
			this.e121 = fill;
			this.e122 = fill;
			
			this.e220 = fill;
			this.e221 = fill;
			this.e222 = fill;
			
		}
		
		public CubicMatrix3x3(T[][][] matrix) {
			
			this.e000 = matrix[0][0][0];
			this.e001 = matrix[0][0][1];
			this.e002 = matrix[0][0][2];
			
			this.e100 = matrix[1][0][0];
			this.e101 = matrix[1][0][1];
			this.e102 = matrix[1][0][2];
			
			this.e200 = matrix[2][0][0];
			this.e201 = matrix[2][0][1];
			this.e202 = matrix[2][0][2];
			
			this.e010 = matrix[0][1][0];
			this.e011 = matrix[0][1][1];
			this.e012 = matrix[0][1][2];
			
			this.e110 = matrix[1][1][0];
			this.e111 = matrix[1][1][1];
			this.e112 = matrix[1][1][2];
			
			this.e210 = matrix[2][1][0];
			this.e211 = matrix[2][1][1];
			this.e212 = matrix[2][1][2];
			
			this.e020 = matrix[0][2][0];
			this.e021 = matrix[0][2][1];
			this.e022 = matrix[0][2][2];
			
			this.e120 = matrix[1][2][0];
			this.e121 = matrix[1][2][1];
			this.e122 = matrix[1][2][2];
			
			this.e220 = matrix[2][2][0];
			this.e221 = matrix[2][2][1];
			this.e222 = matrix[2][2][2];
			
		}
		
		public CubicMatrix3x3<T> rotateY(int angle) {
			
			if (Integer.signum(angle) == -1) return this.rotateY(4 + angle);
			
			CubicMatrix3x3<T> matrix = new CubicMatrix3x3<>();
			
			switch (angle) {
				
				case 0: return matrix;
				
				case 1:
					
					matrix.e000 = this.e002;
					matrix.e100 = this.e001;
					matrix.e200 = this.e000;
					matrix.e001 = this.e102;
					matrix.e101 = this.e101;
					matrix.e201 = this.e100;
					matrix.e002 = this.e202;
					matrix.e102 = this.e201;
					matrix.e202 = this.e200;
					
					matrix.e010 = this.e022;
					matrix.e110 = this.e021;
					matrix.e210 = this.e020;
					matrix.e011 = this.e122;
					matrix.e111 = this.e121;
					matrix.e211 = this.e120;
					matrix.e012 = this.e222;
					matrix.e112 = this.e221;
					matrix.e212 = this.e220;
					
					matrix.e020 = this.e022;
					matrix.e120 = this.e021;
					matrix.e220 = this.e020;
					matrix.e021 = this.e122;
					matrix.e121 = this.e121;
					matrix.e221 = this.e120;
					matrix.e022 = this.e222;
					matrix.e122 = this.e221;
					matrix.e222 = this.e220;
					
					break;
					
				case 2:
					
					matrix.e000 = this.e202;
					matrix.e100 = this.e102;
					matrix.e200 = this.e002;
					matrix.e001 = this.e201;
					matrix.e101 = this.e101;
					matrix.e201 = this.e001;
					matrix.e002 = this.e200;
					matrix.e102 = this.e100;
					matrix.e202 = this.e100;
					
					matrix.e010 = this.e212;
					matrix.e110 = this.e112;
					matrix.e210 = this.e012;
					matrix.e011 = this.e211;
					matrix.e111 = this.e111;
					matrix.e211 = this.e011;
					matrix.e012 = this.e210;
					matrix.e112 = this.e110;
					matrix.e212 = this.e110;
					
					matrix.e020 = this.e222;
					matrix.e120 = this.e122;
					matrix.e220 = this.e022;
					matrix.e021 = this.e221;
					matrix.e121 = this.e121;
					matrix.e221 = this.e021;
					matrix.e022 = this.e220;
					matrix.e122 = this.e120;
					matrix.e222 = this.e120;
					
					break;
					
				case 3:
					
					matrix.e000 = this.e200;
					matrix.e100 = this.e201;
					matrix.e200 = this.e202;
					matrix.e001 = this.e100;
					matrix.e101 = this.e101;
					matrix.e201 = this.e102;
					matrix.e002 = this.e000;
					matrix.e102 = this.e001;
					matrix.e202 = this.e002;
					
					matrix.e010 = this.e210;
					matrix.e110 = this.e211;
					matrix.e210 = this.e212;
					matrix.e011 = this.e110;
					matrix.e111 = this.e111;
					matrix.e211 = this.e112;
					matrix.e012 = this.e010;
					matrix.e112 = this.e011;
					matrix.e212 = this.e012;
					
					matrix.e020 = this.e220;
					matrix.e120 = this.e221;
					matrix.e220 = this.e222;
					matrix.e021 = this.e120;
					matrix.e121 = this.e121;
					matrix.e221 = this.e122;
					matrix.e022 = this.e020;
					matrix.e122 = this.e021;
					matrix.e222 = this.e022;
					
					break;
					
				default:
					throw new IndexOutOfBoundsException("Angle must be within 0 and 3! Angle: " + angle);
					
			}
			
			return matrix;
			
		}
		
		public void set(int x, int y, int z, T e) {
			
			if (x == 0) {
				
				if (y == 0) {
					
					if (z == 0) {
						
						this.e000 = e;
						
					} else if (z == 1) {
						
						this.e001 = e;
						
					} else if (z == 2) {
						
						this.e002 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 1) {
					
					if (z == 0) {
						
						this.e010 = e;
						
					} else if (z == 1) {
						
						this.e011 = e;
						
					} else if (z == 2) {
						
						this.e012 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 2) {
					
					if (z == 0) {
						
						this.e020 = e;
						
					} else if (z == 1) {
						
						this.e021 = e;
						
					} else if (z == 2) {
						
						this.e022 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else
					throw new IndexOutOfBoundsException("Y must be within 0 and 2! Y: " + y);
				
			} else if (x == 1) {
				
				if (y == 0) {
					
					if (z == 0) {
						
						this.e100 = e;
						
					} else if (z == 1) {
						
						this.e101 = e;
						
					} else if (z == 2) {
						
						this.e102 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 1) {
					
					if (z == 0) {
						
						this.e110 = e;
						
					} else if (z == 1) {
						
						this.e111 = e;
						
					} else if (z == 2) {
						
						this.e112 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 2) {
					
					if (z == 0) {
						
						this.e120 = e;
						
					} else if (z == 1) {
						
						this.e121 = e;
						
					} else if (z == 2) {
						
						this.e122 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else
					throw new IndexOutOfBoundsException("Y must be within 0 and 2! Y: " + y);
				
			} else if (x == 2) {
				
				if (y == 0) {
					
					if (z == 0) {
						
						this.e200 = e;
						
					} else if (z == 1) {
						
						this.e201 = e;
						
					} else if (z == 2) {
						
						this.e202 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 1) {
					
					if (z == 0) {
						
						this.e210 = e;
						
					} else if (z == 1) {
						
						this.e211 = e;
						
					} else if (z == 2) {
						
						this.e212 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 2) {
					
					if (z == 0) {
						
						this.e220 = e;
						
					} else if (z == 1) {
						
						this.e221 = e;
						
					} else if (z == 2) {
						
						this.e222 = e;
						
					} else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else
					throw new IndexOutOfBoundsException("Y must be within 0 and 2! Y: " + y);
				
			} else
				throw new IndexOutOfBoundsException("X must be within 0 and 2! X: " + x);
			
		}
		
		public T get(int x, int y, int z) {
			
			if (x == 0) {
				
				if (y == 0) {
					
					if (z == 0)
						return this.e000;
					else if (z == 1)
						return this.e001;
					else if (z == 2)
						return this.e002;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 1) {
					
					if (z == 0)
						return this.e010;
					else if (z == 1)
						return this.e011;
					else if (z == 2)
						return this.e012;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 2) {
					
					if (z == 0)
						return this.e020;
					else if (z == 1)
						return this.e021;
					else if (z == 2)
						return this.e022;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else
					throw new IndexOutOfBoundsException("Y must be within 0 and 2! Y: " + y);
				
			} else if (x == 1) {
				
				if (y == 0) {
					
					if (z == 0)
						return this.e100;
					else if (z == 1)
						return this.e101;
					else if (z == 2)
						return this.e102;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 1) {
					
					if (z == 0)
						return this.e110;
					else if (z == 1)
						return this.e111;
					else if (z == 2)
						return this.e112;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 2) {
					
					if (z == 0)
						return this.e120;
					else if (z == 1)
						return this.e121;
					else if (z == 2)
						return this.e122;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else
					throw new IndexOutOfBoundsException("Y must be within 0 and 2! Y: " + y);
				
			} else if (x == 2) {
				
				if (y == 0) {
					
					if (z == 0)
						return this.e200;
					else if (z == 1)
						return this.e201;
					else if (z == 2)
						return this.e202;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 1) {
					
					if (z == 0)
						return this.e210;
					else if (z == 1)
						return this.e211;
					else if (z == 2)
						return this.e212;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else if (y == 2) {
					
					if (z == 0)
						return this.e220;
					else if (z == 1)
						return this.e221;
					else if (z == 2)
						return this.e222;
					else
						throw new IndexOutOfBoundsException("Z must be within 0 and 2! Z: " + z);
					
				} else
					throw new IndexOutOfBoundsException("Y must be within 0 and 2! Y: " + y);
				
			} else
				throw new IndexOutOfBoundsException("X must be within 0 and 2! X: " + x);
			
		}
		
	}
	
}
