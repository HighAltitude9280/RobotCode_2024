package frc.robot.resources.math.splines;

/**
 * Piece-wise spline, formed by several polynomials.
 */
public class CubicSpline
{

  CubicPolynomial[] polynomials;
  
  /**
   * Creates a new piece-wise cubic spline, determined by the given polynomials.
   * 
   * @param polynomials The polynomials that determine the spline.
   */
  public CubicSpline(CubicPolynomial[] polynomials)
  {
    this.polynomials = polynomials;
  }
  
  /**
   * Evaluates the spline at the given x coordinate.
   * 
   * @param x The x at which the coordinate.
   * @return  The evaluation of the spline at the given x coordinate.  
   */
  public double f(double x)
  {
    if(x < polynomials[0].startingX)
      return polynomials[0].f(x);
  
    for(CubicPolynomial polynomial : polynomials)
    {
      if(polynomial.xInRange(x))
        return polynomial.f(x);
    }
    
    return polynomials[polynomials.length - 1].f(x);
  }
  
  /**
   * Evaluates the first derivative of the spline at the given coordinate.
   * 
   * @param x   The coordinate at which the derivative will be evaluated.
   * @return    The evaluation of the derivative at the given x coordinate.
   */
  public double fPrime(double x)
  {
    if(x < polynomials[0].startingX)
      return polynomials[0].fPrime(x);
  
    for(CubicPolynomial polynomial : polynomials){
    
      if(polynomial.xInRange(x))
        return polynomial.fPrime(x);
      
    }
    
    return polynomials[polynomials.length - 1].fPrime(x);
  }

  public double getFinalXPosition() 
  {
    return polynomials[polynomials.length-1].endingX;
  }
  public double getInitialXPosition() 
  {
    return polynomials[0].startingX;
  }

}