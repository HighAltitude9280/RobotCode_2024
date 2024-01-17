package frc.robot.resources.math.splines;

//Class used to build a piece-wise spline, formed by several cubic polynomials.  
class CubicPolynomial{
  
    double a, b, c, d, startingX, endingX;
  
    /**
     * Cubic polynomial.
     * 
     * @param a             The coefficient of x^3 of the polynomial.
     * @param b             The coefficient of x^2 of the polynomial.
     * @param c             The coefficient of x of the polynomial.
     * @param d             The independent term of the polynomial.
     * @param startingX     The starting x coordinate of the interval inside which the polynomial interpolates.
     * @param endingX       The ending x coordinate of the interval inside which the polynomial interpolates.
     */  
    public CubicPolynomial(double a, double b, double c, double d, double startingX, double endingX)
    {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
      this.startingX = startingX;
      this.endingX = endingX;
    }
    
   /**
     * Evaluates the spline at the given x coordinate.
     * 
     * @param x The x at which the coordinate.
     * @return  The evaluation of the spline at the given x coordinate.  
     */
    public double f(double x){
    
      double cubic = a*x*x*x;
      double quadratic = b*x*x;
      double linear = c*x;
      double independent = d;
      
      
      return cubic+quadratic+linear+independent;
    
    }
    
    /**
     * Evaluates the first derivative of the spline at the given coordinate.
     * 
     * @param x   The coordinate at which the derivative will be evaluated.
     * @return    The evaluation of the derivative at the given x coordinate.
     */
    public double fPrime(double x){
    
      double quadratic = 3*a*x*x;
      double linear = 2*b*x;
      double independent = c;
      
      return quadratic + linear + independent;
    
    }
    /**
     * Checks if the given x is inside the interval that this polomyal interpolates in.
     *  
     * @param x The x that this will be checked. 
     * @return  True if the given x is inside the interval, false otherwise. 
     */
    public boolean xInRange(double x)
    {
    
      return (x >= startingX && x <= endingX);
    
    }
    
    
  
  }
