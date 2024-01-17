package frc.robot.resources.math.splines;

import frc.robot.resources.math.Math;

public class SplineGenerator 
{
    
    /**
     * Generates a natural spline that interpolates between the control points. 
     * 
     * @param controlPoints 2-row matrix of the control points, where the first column contains 
     *                      the x coordinates of the control points and the second the y coordinates.
     * 
     * @return              Piece-wise spline that interpolates between the control points.
     */
    public static CubicSpline generateNaturalSpline(double[][] controlPoints)
    {
        //The number of control points
        int n = controlPoints.length;
        
        //The matrix that contains the coefficients of the linear system
        double[][] coefficientsMatrix = new double[4*n-4][4*n-4];
        
        //The matrix that contains the right hand side of the equations
        double[][] rhsMatrix = new double[4*n-4][1];

        
        //First constraint, f(x) = y at all control points
    
        for(int i = 0; i < n-1; i++){
        
            //The x and y coordinate of the control point number i
            double x_i = controlPoints[i][0];
            double y_i = controlPoints[i][1];
            
            //The x and y coordinate of the control point number i+1
            double x_next = controlPoints[i+1][0];
            double y_next = controlPoints[i+1][1];
        
        
            //Each polynomial has two equations, f(x_i)=y_i and f(x_i+1)=y_i+1, therefore, as seen in the pdf document that explains the construction of this class
            //The row 2i and 2i+1 will be used for that matter. 
            
            //The 4i+k represents the kth coefficient of the ith polynomial. For example, i = 2 and k = 0 represents a_i, that is to say, the cubic coefficient of the ith polynomial.  
            coefficientsMatrix[2*i][4*i] = (double)Math.pow(x_i, 3);
            coefficientsMatrix[2*i][4*i+1] = (double)Math.pow(x_i, 2);
            coefficientsMatrix[2*i][4*i+2] = x_i;
            coefficientsMatrix[2*i][4*i+3] = 1;
            
            coefficientsMatrix[2*i+1][4*i] = (double)Math.pow(x_next, 3);
            coefficientsMatrix[2*i+1][4*i+1] = (double)Math.pow(x_next, 2);
            coefficientsMatrix[2*i+1][4*i+2] = x_next;
            coefficientsMatrix[2*i+1][4*i+3] = 1;
            
            rhsMatrix[2*i][0] = y_i;
            rhsMatrix[2*i+1][0] = y_next;
    
        }
        
        //Second constraint f_k'(x)=f_k'(x) for all control points except first and last
        //The first empty row is the row number 2n-2, all the others have been occupied by the first constraint
        int nextEmptyRow = 2*n-2;
        
        for(int i = 0; i < n-2 ; i++){
        
            double x_i = controlPoints[i+1][0];

            coefficientsMatrix[nextEmptyRow+i][4*i] = 3*(double)Math.pow(x_i,2);
            coefficientsMatrix[nextEmptyRow+i][4*i+1] = 2*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+2] = 1;
            coefficientsMatrix[nextEmptyRow+i][4*i+3] = 0;

            coefficientsMatrix[nextEmptyRow+i][4*i+4] = -3*(double)Math.pow(x_i,2);
            coefficientsMatrix[nextEmptyRow+i][4*i+5] = -2*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+6] = -1;
            coefficientsMatrix[nextEmptyRow+i][4*i+7] = 0;


            rhsMatrix[nextEmptyRow+i][0] = 0;
        
        }
        //Third constraint f_k''(x)=f_k''(x) for all control points except first and last
        //The first empty row is the row number 3n-4, all the others have been occupied by the first constraint   
        nextEmptyRow = 3*n-4;

        for(int i = 0; i < n-2 ; i++){
        
            double x_i = controlPoints[i+1][0];

            coefficientsMatrix[nextEmptyRow+i][4*i] = 6*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+1] = 2;
            coefficientsMatrix[nextEmptyRow+i][4*i+2] = 0;
            coefficientsMatrix[nextEmptyRow+i][4*i+3] = 0;

            coefficientsMatrix[nextEmptyRow+i][4*i+4] = -6*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+5] = -2;
            coefficientsMatrix[nextEmptyRow+i][4*i+6] = 0;
            coefficientsMatrix[nextEmptyRow+i][4*i+7] = 0;


            rhsMatrix[nextEmptyRow+i][0] = 0;
        
        } 

        //Since it's a natural spline, the two final constraints will be f_0''(x_0)=0 and f_n-1''(x_n)=0
    
        coefficientsMatrix[4*n-6][0] = 6*controlPoints[0][0];
        coefficientsMatrix[4*n-6][1] = 2;
        rhsMatrix[4*n-6][0] = 0;
        
        coefficientsMatrix[4*n-5][4*n-8] = 6*controlPoints[controlPoints.length-1][0];
        coefficientsMatrix[4*n-5][4*n-7] = 2;
        rhsMatrix[4*n-5][0] = 0;
        
        double[][] resultMatrix = Math.multiplyMatrices(Math.inverseMatrix(coefficientsMatrix), rhsMatrix);
        
        CubicPolynomial[] polynomials = new CubicPolynomial[n-1];
        
        for(int i = 0; i < n-1; i++)
        {
            double a = resultMatrix[4*i][0];
            double b = resultMatrix[4*i+1][0];
            double c = resultMatrix[4*i+2][0];
            double d = resultMatrix[4*i+3][0];
            
            double startingX = controlPoints[i][0];
            double endingX = controlPoints[i+1][0];
            
            polynomials[i] = new CubicPolynomial(a,b,c,d, startingX, endingX);
        }
        
        return new CubicSpline(polynomials);
        
    }

    /**
     * Generates a hermite clamped spline that interpolates between the control points and its 
     * derivative at the first and last point is as specified.  
     * 
     * @param controlPoints     2-row matrix of the control points, where the first column contains 
     *                          the x coordinates of the control points and the second the y coordinates.
     * @param initialDerivative The desired derivative at the first contol point.  
     * @param finalDerivative   The desired derivative at the last control point.
     * @return                  Piece-wise spline that interpolates between the control points.
     */
    public static CubicSpline generateHermiteClampedSpline(double[][] controlPoints, double initialDerivative, double finalDerivative)
    {
        //The number of control points
        int n = controlPoints.length;
        
        //The matrix that contains the coefficients of the linear system
        double[][] coefficientsMatrix = new double[4*n-4][4*n-4];
        
        //The matrix that contains the right hand side of the equations
        double[][] rhsMatrix = new double[4*n-4][1];

        //First constraint, f(x) = y at all control points
        
        for(int i = 0; i < n-1; i++){
            
            //The x and y coordinate of the control point number i
            double x_i = controlPoints[i][0];
            double y_i = controlPoints[i][1];
            
            //The x and y coordinate of the control point number i+1
            double x_next = controlPoints[i+1][0];
            double y_next = controlPoints[i+1][1];
        
        
            //Each polynomial has two equations, f(x_i)=y_i and f(x_i+1)=y_i+1, therefore, as seen in the pdf document that explains the construction of this class
            //The row 2i and 2i+1 will be used for that matter. 
            
            //The 4i+k represents the kth coefficient of the ith polynomial. For example, i = 2 and k = 0 represents a_i, that is to say, the cubic coefficient of the ith polynomial.  
            coefficientsMatrix[2*i][4*i] = (double)Math.pow(x_i, 3);
            coefficientsMatrix[2*i][4*i+1] = (double)Math.pow(x_i, 2);
            coefficientsMatrix[2*i][4*i+2] = x_i;
            coefficientsMatrix[2*i][4*i+3] = 1;
            
            coefficientsMatrix[2*i+1][4*i] = (double)Math.pow(x_next, 3);
            coefficientsMatrix[2*i+1][4*i+1] = (double)Math.pow(x_next, 2);
            coefficientsMatrix[2*i+1][4*i+2] = x_next;
            coefficientsMatrix[2*i+1][4*i+3] = 1;
            
            rhsMatrix[2*i][0] = y_i;
            rhsMatrix[2*i+1][0] = y_next;
        
        }
        
        //Second constraint f_k'(x)=f_k'(x) for all control points except first and last
        //The first empty row is the row number 2n-2, all the others have been occupied by the first constraint
        int nextEmptyRow = 2*n-2;
        
        for(int i = 0; i < n-2 ; i++){
        
            double x_i = controlPoints[i+1][0];
            
            coefficientsMatrix[nextEmptyRow+i][4*i] = 3*(double)Math.pow(x_i,2);
            coefficientsMatrix[nextEmptyRow+i][4*i+1] = 2*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+2] = 1;
            coefficientsMatrix[nextEmptyRow+i][4*i+3] = 0;
            
            coefficientsMatrix[nextEmptyRow+i][4*i+4] = -3*(double)Math.pow(x_i,2);
            coefficientsMatrix[nextEmptyRow+i][4*i+5] = -2*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+6] = -1;
            coefficientsMatrix[nextEmptyRow+i][4*i+7] = 0;
            
            
            rhsMatrix[nextEmptyRow+i][0] = 0;
        
        }
        //Third constraint f_k''(x)=f_k''(x) for all control points except first and last
        //The first empty row is the row number 3n-4, all the others have been occupied by the first constraint   
        nextEmptyRow = 3*n-4;
        
        for(int i = 0; i < n-2 ; i++){
        
            double x_i = controlPoints[i+1][0];
            
            coefficientsMatrix[nextEmptyRow+i][4*i] = 6*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+1] = 2;
            coefficientsMatrix[nextEmptyRow+i][4*i+2] = 0;
            coefficientsMatrix[nextEmptyRow+i][4*i+3] = 0;
            
            coefficientsMatrix[nextEmptyRow+i][4*i+4] = -6*x_i;
            coefficientsMatrix[nextEmptyRow+i][4*i+5] = -2;
            coefficientsMatrix[nextEmptyRow+i][4*i+6] = 0;
            coefficientsMatrix[nextEmptyRow+i][4*i+7] = 0;
            
            
            rhsMatrix[nextEmptyRow+i][0] = 0;
        
        } 
        
        //Since it's a hermite clamped spline, the two final constraints will be f_0'(x_0)=initialDerivative and f_n-1'(x_n)=finalDerivative
        
            coefficientsMatrix[4*n-6][0] = 3*Math.pow(controlPoints[0][0], 2);
            coefficientsMatrix[4*n-6][1] = 2*controlPoints[0][0];
            coefficientsMatrix[4*n-6][2] = 1;
            rhsMatrix[4*n-6][0] = initialDerivative;
            
            coefficientsMatrix[4*n-5][4*n-8] = 3*Math.pow(controlPoints[controlPoints.length-1][0], 2);
            coefficientsMatrix[4*n-5][4*n-7] = 2*controlPoints[controlPoints.length-1][0];
            coefficientsMatrix[4*n-5][4*n-6] = 1;
            rhsMatrix[4*n-5][0] = finalDerivative;
            
            double[][] resultMatrix = Math.multiplyMatrices(Math.inverseMatrix(coefficientsMatrix), rhsMatrix);
            
            CubicPolynomial[] polynomials = new CubicPolynomial[n-1];
            
            for(int i = 0; i < n-1; i++){
            
            double a = resultMatrix[4*i][0];
            double b = resultMatrix[4*i+1][0];
            double c = resultMatrix[4*i+2][0];
            double d = resultMatrix[4*i+3][0];
            
            double startingX = controlPoints[i][0];
            double endingX = controlPoints[i+1][0];
            
            polynomials[i] = new CubicPolynomial(a,b,c,d, startingX, endingX);
            
            }
            
            return new CubicSpline(polynomials);
            
    }

    /**
     * This class treats the first column of the control matrix as the independent variable and the
     * second as the dependent variable. Normally, the first column represents x values and the second
     * y values. Therefore, for a vertical spline, that is, when y is the independent variable, axis 
     * must be swapped. This method switches the first and second column of a matrix. Note that this 
     * method only works for matrix with exactly two columns.  
     * 
     * @param controlPoints The n x 2 matrix.
     * @return              The original matrix, but with its columns permutated. 
     */
    public static double[][] swapAxis(double[][] controlPoints)
    {
        double[][] permutationMatrix = {{0,1}, {1,0}};
        return(Math.multiplyMatrices(controlPoints, permutationMatrix));
    }
}

