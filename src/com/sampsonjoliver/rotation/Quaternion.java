package com.sampsonjoliver.rotation;

public class Quaternion
{
	// Precision error constant
    private static float epsilon = 0.0001f;
    private float[] q;

    /**
     * Constructor creates a new Quaternion from floats w, x, y, and z.
     * @param w the real-valued scalar part of the Quaternion
     * @param x the x-component of the complex vector part of the Quaternion
     * @param y the y-component of the complex vector part of the Quaternion
     * @param z the z-component of the complex vector part of the Quaternion
     */
    public Quaternion(float w, float x, float y, float z)
    {
        this.q = new float[]{w,x,y,z};
    }

    /**
     * Creates a new Quaternion from a 3- or 4- part rotation vector expressed as
     * [x,y,z,w] where w is an optional angle of rotation.
     * @param rv the rotation vector to convert
     */
    public Quaternion(float[] rv)
    {
		this.q = new float[4];
        getQuaternionFromVector(rv);
    }

    /** Helper function to convert a rotation vector to a normalized quaternion.
     *  Given a rotation vector (presumably from a ROTATION_VECTOR sensor), returns a normalized
     *  quaternion in the array Q.  The quaternion is stored as [w, x, y, z]
     *  @param rv the rotation vector to convert
     */
    private void getQuaternionFromVector(float[] rv) {
        // Take the vector w component if it exists
        if (rv.length == 4) {
            q[0] = rv[3];
        }
        // Calculate the w component as sqrt(1 - |rv|)
        else {
            q[0] = 1 - rv[0]*rv[0] - rv[1]*rv[1] - rv[2]*rv[2];
            q[0] = (q[0] > 0) ? (float)Math.sqrt(q[0]) : 0;
        }
        // Set the vector component
        q[1] = rv[0];
        q[2] = rv[1];
        q[3] = rv[2];
    }

    /**
     * Get the real-valued scalar part of the quaternion
     * @return the w value
     */
    public float W()
    {
        return q[0];
    }

    /**
     * Get the x component of the complex vector part of the quaternion
     * @return the x value
     */
    public float X()
    {
        return q[1];
    }

    /**
     * Get the y component of the complex vector part of the quaternion
     * @return the y value
     */
    public float Y()
    {
        return q[2];
    }

    /**
     * Get the z component of the complex vector part of the quaternion
     * @return the z value
     */
    public float Z()
    {
        return q[3];
    }

    /**
     * Get the real-valued scalar part of the quaternion
     * @return the w value as a float
     */
    public float getScalar()
    {
        return W();
    }

    /**
     * Get the complex vector part of the quaternion
     * @return the [x,y,z] floats as an array
     */
    public float[] getVector()
    {
        return new float[]{q[1], q[2], q[3]};
    }

    /**
     * Sets the rv of the Quaternion from a 3- or 4- part rotation vector expressed as
     * [x,y,z,w] where w is an optional angle of rotation.
     * @param rv
     */
    public void setValues(float[] rv)
    {
        getQuaternionFromVector(rv);
    }

    /**
     * Sets the values of the complex vector part of the quaternion to a new vector expressed as
     * [x,y,z]
     * @param vec the new complex valued vector expressing the axis of rotation
     */
    public void setVector(float[] vec)
    {
        if (vec.length == 3)
        {
            this.q[1] = vec[0];
            this.q[2] = vec[1];
            this.q[3] = vec[2];
        }
    }

    /**
     * Sets the values of the real scalar part of the quaternion to a new float w
     * @param w the new real valued scalar expressing the angle of rotation
     */
    public void setScalar(float w)
    {
        this.q[3] = w;
    }

    /**
     * Calculates the scalar multiplication of the quaternion's parts
     * @param c the constant to scale by
     * @return a new Quaternion scaled by c
     */
    public Quaternion Multiply(float c)
    {
        return new Quaternion(q[0] * c, q[1] * c, q[2] * c, q[3] * c );
    }

    /**
     * Calculates the scalar division of the quaternion's parts
     * @param c the constant to scale by
     * @return a new Quaternion inversely scaled by c
     */
    public Quaternion Divide(float c)
    {
        return new Quaternion(q[0] / c, q[1] / c, q[2] / c, q[3] / c );
    }

    /**
     * Calculates the product of two quaternions using the Hamilton Product given
     * as determined by the distributive law
     * @param q2 the right-hand-side of the product operand
     * @return the new Quaternion result of the product operation
     */
    public Quaternion HamiltonProduct(Quaternion q2)
    {
        /*return new Quaternion(
                q[0] * q2.W() - q[1]*q2.X() - q[2]*q2.Y() - q[3]*q2.Z(),
                q[0] * q2.X() + q[1]*q2.W() + q[2]*q2.Z() + q[3]*q2.Y(),
                q[0] * q2.Y() + q[1]*q2.Z() + q[2]*q2.X() + q[3]*q2.Y(),
                q[0] * q2.Z() + q[1]*q2.Y() + q[2]*q2.X() + q[3]*q2.W()
        );*/
		return new Quaternion(
			-q[1] * q2.X() - q[2] * q2.Y() - q[3] * q2.Z() + q[0] * q2.W(),
			 q[1] * q2.W() + q[2] * q2.Z() - q[3] * q2.Y() + q[0] * q2.X(),
			-q[1] * q2.Z() + q[2] * q2.W() + q[3] * q2.X() + q[0] * q2.Y(),
			 q[1] * q2.Y() - q[2] * q2.X() + q[3] * q2.W() + q[0] * q2.Z());
    }

    /**
     * Calculates the addition of two quaternions
     * @param q2 the right-hand-side of the addition operand
     * @return the new Quaternion result of the addition operation
     */
    public Quaternion Add(Quaternion q2)
    {
        return new Quaternion(q[0] + q2.W(),
                              q[1] + q2.X(),
                              q[2] + q2.Y(),
                              q[3] + q2.Z());
    }

    /**
     * Calculates the subtraction of two quaternions
     * @param q2 the right-hand-side of the subtraction operand
     * @return the new Quaternion result of the subtraction operation
     */
    public Quaternion Subtract(Quaternion q2)
    {
        return new Quaternion(q[0] + q2.W(),
                              q[1] + q2.X(),
                              q[2] + q2.Y(),
                              q[3] + q2.Z());
    }

    /**
     * Calculates the length squared of the quaternion.
     * Equivalent to |q|^2, or the complex inner product of the quaternion with itself
     * @return a float representing the squared length of the quaternion
     */
	public float LengthSquared()
	{
		return q[0]*q[0] + q[1]*q[1] + q[2]*q[2] + q[3]*q[3];
	}

    /**
     * Calculates the norm (size, or length) of the quaternion.
     * Equivalent to computing the vector length, or |q|, or the square root of the complex
     * inner product.
     * @return a float representing the norm of the quaternion
     */
    public float Norm()
    {
        // Equivalent mathematical method:
        //return Math.sqrt(this.HamiltonProduct(this.Conjugate())[0]);

        return LengthSquared() > 0
               ? (float)Math.sqrt(LengthSquared())
               : 0;
    }

    /**
     * Determine if the quaternion is unit. That is, if its norm has a unit length of 1.
     * @return true or false
     */
    public boolean isUnit()
    {
        return Math.abs(1.0 - this.Norm()) <= epsilon;
    }

    /**
     * Determine if the quaternion is equal to the zero vector
     * @return true or false
     */
    public boolean isZero()
    {
        return (X() == 0 && Y() == 0 && Z() == 0 && W() == 0);
    }

    /**
     * Calculate the complex conjugate of the quaternion.
     * @return the new quaternion representing the complex conjugate.
     */
    public Quaternion Conjugate()
    {
        return new Quaternion(q[0], -q[1], -q[2], -q[3]);
    }

    /**
     * Calculate the inverse of the quaternion.
     * If it is unit, this is equal to the conjugate.
     * @return the new quaternion representing the inverse.
     */
    public Quaternion Inverse()
    {
        if (this.isUnit())
            return this.Conjugate();

        return this.Conjugate().Divide(this.LengthSquared());
    }

    /**
     * Calculates the equivalent unit-length quaternion.
     * @return the new equivalent quaternion with a norm of 1.
     */
	public Quaternion Versor()
	{
		if (this.isUnit())
			return this;
        else if (this.isZero())
            return this;
        else
		    return this.Divide(this.Norm());
	}

    /**
     * Calculates the difference quaternion that expresses the rotation between this and
     * another quaternion.
     * @param qPrev the quaternion to calculate the difference between.
     * @return a new quaternion expressing the difference.
     */
	public Quaternion Difference(Quaternion qPrev)
	{
        return this.Versor().Conjugate().HamiltonProduct(qPrev.Versor());
        /*
		if (this.isUnit() && qPrev.isUnit())
			return this.Conjugate().HamiltonProduct(qPrev);

		return null;
		*/

        // Equivalent method:
		//return this.Inverse().HamiltonProduct(qPrev);
	}

    /**
     * Calculates the total magnitude of the angular rotation applied by the quaternion.
     * Equivalent to the total rotation of the quaternion.
     * @return a float expressing the theta value of the quaternion in radians.
     */
	public float Magnitude()
	{
		float lenSquared = q[1] * q[1] + q[2] * q[2] + q[3] * q[3];
		float vecNorm = (lenSquared > 0 ? (float)Math.sqrt(lenSquared) : 0);

		return 2 * (float)Math.atan2(vecNorm, q[0]);

        // Equivalent methods:
		//return 2 * (float)Math.atan2(Math.sqrt(1 - q[0]*q[0]), q[0]);
		//return 2 * (float)Math.acos(q[0]);
	}

    public static float getMagnitude(float[] vec)
    {
        if (vec.length == 4)
        {
            return 2 * (float)Math.atan2(vec[1] * vec[1] + vec[2] * vec[2] + vec[3] * vec[3], vec[0]);
        }
        else
        {
            float[] q = new float[4];

            getQuaternionFromVector(q, vec);

            return 2 * (float)Math.atan2(q[1] * q[1] + q[2] * q[2] + q[3] * q[3], q[0]);
        }
    }

    /**
     * Calculates the quaternion dot product of this and another quaternion.
     * @param q2 the right-hand-side of the dot product operand
     * @return the result of the dot product operation
     */
	public float DotProduct(Quaternion q2)
	{
		return q[0] * q2.W() + q[1] * q2.X() + q[2] * q2.Y() + q[3] * q2.Z();
	}

    /**
     * Calculates the angular (arc) distance between this and another quaternion.
     * Visualised as the arc distance between two lines that form a 2D plane.
     * @param q2 the quaternion to calculate the distance to.
     * @return a float expressing the angular distance in radians.
     */
	public float Distance(Quaternion q2)
	{
		return 2 * (float)Math.acos(this.DotProduct(q2));
	}

    public float[] toTaitBryan()
    {
        float phi = (float) Math.atan2(W() * X() + Y() * Z(), 0.5f - (X() * X() + Y() * Y()));
        float theta = (float) Math.asin(2.0f * (X()*Z() - W()*Y()));
        float psi = (float) Math.atan2(W() * Z() + X() * Y(), 0.5f - (Y() * Y() + Z() * Z()));

        return new float[] {phi, theta, psi};
    }

    /**
     * Converts the quaternion to a string.
     * @return a string expressing the w,x,y, and z components of the quaternion.
     */
	public String toString()
	{
		return "w: " + q[0] + ", x: " + q[1] + ", y: " + q[2] + ", z: " + q[3];
	}

    public static float getDistanceChange(float[] v1, float[] v2)
    {
        if (v1.length == 4)
        {
            return 2 * (float)Math.acos(v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2] + v1[3] * v2[3]);
        }
        else
        {
            float[] q1 = new float[4];
            float[] q2 = new float[4];

            getQuaternionFromVector(q1, v1);
            getQuaternionFromVector(q2, v2);

            return 2 * (float)Math.acos(q1[0] * q2[0] + q1[1] * q2[1] + q1[2] * q2[2] + q1[3] * q2[3]);
        }
    }

    public static void getTaitBryanAngleChange(float[] res, float[] v1, float[] v2)
    {
        float[] q = new float[4];
        if (v1.length == 4)
        {
            q[0] = -v1[1] * v2[1] - v1[2] * v2[2] - v1[3] * v2[3] + v1[0] * v2[0];
            q[1] =  v1[1] * v2[0] + v1[2] * v2[3] - v1[3] * v2[2] + v1[0] * v2[1];
            q[2] = -v1[1] * v2[3] + v1[2] * v2[0] + v1[3] * v2[1] + v1[0] * v2[2];
            q[3] =  v1[1] * v2[2] - v1[2] * v2[1] + v1[3] * v2[0] + v1[0] * v2[3];

            res[0] = (float) Math.atan2(q[2] * q[3] + q[0] * q[1], 0.5 - (q[1] * q[1] + q[2] * q[2]));
            res[1] = (float) Math.asin(-2.0 * (q[1] * q[3] - q[0] * q[2]));
            res[2] = (float) Math.atan2(q[1] * q[2] + q[0] * q[3], 0.5 - (q[2] * q[2] + q[3] * q[3]));
        }
        else
        {
            v1[0] = -v1[0];
            v1[1] = -v1[1];
            v1[2] = -v1[2];

            float[] q1 = new float[4];
            float[] q2 = new float[4];

            getQuaternionFromVector(q1, v1);
            getQuaternionFromVector(q2, v2);

            q[0] = -q1[1] * q2[1] - q1[2] * q2[2] - q1[3] * q2[3] + q1[0] * q2[0];
            q[1] = q1[1] * q2[0] + q1[2] * q2[3] - q1[3] * q2[2] + q1[0] * q2[1];
            q[2] = -q1[1] * q2[3] + q1[2] * q2[0] + q1[3] * q2[1] + q1[0] * q2[2];
            q[3] = q1[1] * q2[2] - q1[2] * q2[1] + q1[3] * q2[0] + q1[0] * q2[3];

            res[0] = (float) Math.atan2(q[2] * q[3] + q[0] * q[1], 0.5 - (q[1] * q[1] + q[2] * q[2]));
            res[1] = (float) Math.asin(-2.0 * (q[1] * q[3] - q[0] * q[2]));
            res[2] = (float) Math.atan2(q[1] * q[2] + q[0] * q[3], 0.5 - (q[2] * q[2] + q[3] * q[3]));
        }
    }

    public static void getQuaternionFromVector(float[] q, float[] rv) {
        // Take the vector w component if it exists
        if (rv.length == 4) {
            q[0] = rv[3];
        }
        // Calculate the w component as sqrt(1 - |rv|)
        else {
            q[0] = 1 - rv[0]*rv[0] - rv[1]*rv[1] - rv[2]*rv[2];
            q[0] = (q[0] > 0) ? (float)Math.sqrt(q[0]) : 0;
        }
        // Set the vector component
        q[1] = rv[0];
        q[2] = rv[1];
        q[3] = rv[2];
    }
}