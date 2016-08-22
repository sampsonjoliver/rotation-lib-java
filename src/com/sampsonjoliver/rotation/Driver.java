package com.sampsonjoliver.rotation;

import java.util.*;

public class Driver
{
    public static void main(String[] args)
	{
		SimpleTest();
        //RunEfficiencyBenchmark();
        //AccuracyBenchmark();
	}

    public static void RunEfficiencyBenchmark()
    {
        Scanner kb = new Scanner(System.in);
        long[][][] res = new long[10][10][5];
        long[][] average = new long[10][5];

        // Get the results of the benchmark
        for (int i = 0; i < 10; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                res[i][j] = EfficiencyBenchmark((i+1) * 100000);
            }
        }

        // Print the results
        System.out.println();
        System.out.println("Print the results?");
        kb.next();

        System.out.printf("%15s %15s %15s %15s %15s\n", "tb_qc", "tb_qf", "tb_m", "f_qc", "f_qf");
        for (int i = 0; i < 10; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                System.out.printf("%15d %15d %15d %15d %15d\n", res[i][j][0], res[i][j][1], res[i][j][2], res[i][j][3], res[i][j][4]);
            }
        }

        // Calculate the average
        for (int i = 0; i < 10; ++i)
        {
            // Sum the results for each benchmark
            average[i] = new long[]{0,0,0,0,0};
            for (int j = 0; j < 10; ++j)
            {
                for (int k = 0; k < 5; ++k)
                {
                    average[i][k] += res[i][j][k];
                }
            }
            // Divide each sum by 10 to attain the average
            for (int k = 0; k < 5; ++k)
            {
                average[i][k] /= 10;
            }
        }

        System.out.println();
        System.out.println("Print the average?");
        kb.next();
        System.out.printf("%15s %15s %15s %15s %15s\n", "tb_qc", "tb_qf", "tb_m", "f_qc", "f_qf");
        for (int i = 0; i < 10; ++i)
        {
            System.out.printf("%15d %15d %15d %15d %15d\n", average[i][0], average[i][1], average[i][2], average[i][3], average[i][4]);
        }
    }

    public static void SimpleTest()
    {
        Scanner kb = new Scanner(System.in);

        float[][] vectors = new float[][]{	{0.002387335989624262f,	-0.01195748895406723f,	-0.974600076675415f,			4.203895392974451E-45f,	-1.0f},
                {0.004492554347962141f,	-0.0069081345573067665f,	-0.96992027759552f,			4.203895392974451E-45f,	-1.0f},
                {0.005202930420637131f,	-0.007559414487332106f,	-0.971769392490387f,			4.203895392974451E-45f,	-1.0f},
                {0.0035515432246029377f,	-0.009004449471831322f,	-0.9675705432891846f,		4.203895392974451E-45f,	-1.0f},
                {0.006685215979814529f,	-0.00516918022185564f,	-0.7083200216293335f,			4.203895392974451E-45f,	-1.0f},
                {0.009026379324495792f,	-0.003503114450722933f,	-0.9652470946311951f,			4.203895392974451E-45f,	-1.0f},
                {-0.7575846314430237f,	-0.08783897012472153f,	-0.6413691639900208f,			4.203895392974451E-45f,	-1.0f},
                {-0.7126379609107971f,	-0.043455325067043304f,	-0.6987627744674683f,			4.203895392974451E-45f,	-1.0f},
                {-0.0011933929054066539f,-0.006216671317815781f,	-0.9827842712402344f,		4.203895392974451E-45f,	-1.0f},
                {-0.22100062668323517f,	0.6778379678726196f,		-0.6694568991661072f,		4.203895392974451E-45f,	-1.0f},
                {-0.24234327673912048f,	0.6782501935958862f,		-0.6641238331794739f,		4.203895392974451E-45f,	-1.0f},
                {0.008405731059610844f,	-0.006305025424808264f,	-0.9569173455238342f,			4.203895392974451E-45f,	-1.0f},
                {0.013783924281597137f,	-0.01334476750344038f,	-0.0108346464112401f,			4.203895392974451E-45f,	-1.0f},
                {0.01209157146513462f,	-0.0072930967435240746f,	-0.0016706701135262847f,	4.203895392974451E-45f,	-1.0f}};

        for (int i = 1; i < vectors.length; ++i)
        {
            Quaternion q1 = new Quaternion(vectors[i-1]);
            Quaternion q2 = new Quaternion(vectors[i]);
            float[] v1 = vectors[i-1];
            float[] v2 = vectors[i];
            float[] tb_res = new float[3];
            float f_res;

            System.out.println("v1: " + "x: " + v1[0] + ", y: " + v1[1] + ", z: " + v1[2]);
            System.out.println("v2: " + "x: " + v2[0] + ", y: " + v2[1] + ", z: " + v2[2]);

            System.out.println("q1: " + q1);
            System.out.println("Is Unit: " + q1.isUnit());
            System.out.println("q2: " + q2);
            System.out.println("Is Unit: " + q2.isUnit());

            System.out.println("q1 norm: " + q1.Norm());
            System.out.println("q2 norm: " + q2.Norm());

            System.out.println("q1 conj: " + q1.Conjugate());//0.22362 - 0.00238734i + 0.0119575j + 0.9746k
            System.out.println("q2 conj: " + q2.Conjugate());

            System.out.println("q1 product q2: " + q1.HamiltonProduct(q2));
            System.out.println("q2 product q1: " + q2.HamiltonProduct(q1));

            System.out.println("Difference: " + q1.Difference(q2));
            System.out.println("Difference Magnitude: " + q1.Difference(q2).Magnitude());
            System.out.println("Distance: " + q1.Distance(q2));

            Quaternion.getTaitBryanAngleChange(tb_res, v1, v2);
            System.out.println("QuatFloat Difference: " + tb_res[0] + "," + tb_res[1] + "," + tb_res[2]);
            System.out.println("QuatFloat Distance: " + Quaternion.getDistanceChange(v1, v2));

            float[] tb = q2.Difference(q1).toTaitBryan();
            System.out.println("Diff Quaternion Tait Bryan: " + "z: " + tb[2] + ", x: " + tb[0] + ", y: " + tb[1]);

            float[] r1 = new float[16];
            RotationMatrix.getRotationMatrixFromVector(r1, vectors[i-1]);
            float[] r2 = new float[16];
            RotationMatrix.getRotationMatrixFromVector(r2, vectors[i]);
            float[] delta = new float[3];
            RotationMatrix.getAngleChange(delta, r2, r1);

            System.out.println("Matrix Angle Difference: " + "z: " + delta[0] + ", x: " + delta[1] + ", y: " + delta[2]);

            System.out.println();
            System.out.println("Continue?");
            kb.nextLine();
        }
    }

    public static long[] EfficiencyBenchmark(int num)
    {
        //System.out.println();
        //System.out.println("Generating " + num + " vectors...");
        float[][] vecs = GenerateVectors(num);
        //System.out.println("Done.\n");

        float[] r1 = new float[16];
        float[] r2 = new float[16];
        float[] qf1 = new float[4];
        float[] qf2 = new float[4];
        float[] tb_delta = new float[3];
        float f_delta;
        Quaternion q1;
        Quaternion q2;

        long t0, t1;
        long tb_qc, tb_qf, tb_m, f_qc, f_qf;

        //System.out.println("Running Benchmark 1: Getting angular difference from vectors");

        t0 = System.nanoTime();
        for (int i = 1; i < num; ++i)
        {
            q1 = new Quaternion(vecs[i-1]);
            q2 = new Quaternion(vecs[i]);
            tb_delta = q1.Difference(q2).toTaitBryan();
        }
        t1 = System.nanoTime();

        tb_qc = t1 - t0;

        //System.out.println("Tait-Bryan angles using Quaternion classes calculated in " + tb_qc + "ns");

        t0 = System.nanoTime();
        for (int i = 1; i < num; ++i)
        {
            Quaternion.getQuaternionFromVector(qf1, vecs[i-1]);
            Quaternion.getQuaternionFromVector(qf2, vecs[i]);
            Quaternion.getTaitBryanAngleChange(tb_delta, vecs[i - 1], vecs[i]);
        }
        t1 = System.nanoTime();

        tb_qf = t1 - t0;

        //System.out.println("Tait-Bryan angles using Quaternion floats calculated in " + tb_qf + "ns");

        t0 = System.nanoTime();
        for (int i = 1; i < num; ++i)
        {
            RotationMatrix.getRotationMatrixFromVector(r1, vecs[i - 1]);
            RotationMatrix.getRotationMatrixFromVector(r2, vecs[i]);
            RotationMatrix.getAngleChange(tb_delta, r1, r2);
        }
        t1 = System.nanoTime();

        tb_m = t1 - t0;

        //System.out.println("Tait-Bryan angles using Matrices calculated in " + tb_m + "ns");

        t0 = System.nanoTime();
        for (int i = 1; i < num; ++i)
        {
            q1 = new Quaternion(vecs[i-1]);
            q2 = new Quaternion(vecs[i]);
            f_delta = q1.Distance(q2);
        }
        t1 = System.nanoTime();

        f_qc = t1 - t0;

        //System.out.println("Flexion angle using Quaternion classes calculated in " + f_qc + "ns");

        t0 = System.nanoTime();
        for (int i = 1; i < num; ++i)
        {
            Quaternion.getQuaternionFromVector(qf1, vecs[i - 1]);
            Quaternion.getQuaternionFromVector(qf2, vecs[i]);
            f_delta = Quaternion.getDistanceChange(vecs[i - 1], vecs[i]);
        }
        t1 = System.nanoTime();

        f_qf = t1 - t0;

        //System.out.println("Flexion angle using Quaternion floats calculated in " + f_qf + "ns");

        return new long[]{tb_qc, tb_qf, tb_m, f_qc, f_qf};
    }

    public static void AccuracyBenchmark()
    {

        float[] orig    = {0.000000000000000000f,	     0.000000000000000000f,	     0.000000000000000000f,		0.000000000000000000f,	-1.0f};

        float[][] rot = new float[][]{
                // rot around z/up
                { 1.000000000000000000f,	     0.000000000000000000f,	     0.000000000000000000f,		0.000000000000000000f,	-1.0f},

                // rot around y/north
                { 0.000000000000000000f,	     0.000000000000000000f,	     1.000000000000000000f,		0.000000000000000000f,	-1.0f},

                // rot around x/east
                { 0.000000000000000000f,	     0.000000000000000000f,	     1.000000000000000000f,		 1.000000000000000000f,	-1.0f},
                { 0.330f,	     0.330f,	     0.3300f,		 0.81f,	-1.0f}};

        float[][] expectedRot = new float[][] {
                {1.0f *3.14159265359f, 0.0f, 0.0f},

                {0.0f,  1.0f *3.14159265359f, 0.0f},

                {0.0f, 0.0f,   1.0f*3.14159265359f},
                {1.0f*3.14159265359f, 1.0f*3.14159265359f,   1.0f*3.14159265359f}
        };

        float[] r1 = new float[16];
        float[] r2 = new float[16];
        RotationMatrix.getRotationMatrixFromVector(r1, orig);

        Quaternion qf1 = new Quaternion(orig);
        Quaternion qf2;

        float[] m_tb = new float[3];
        float[] q_tb;
        float[] d_tb = new float[3];

        for (int i = 0; i < rot.length; ++i)
        {
            RotationMatrix.getRotationMatrixFromVector(r2, rot[i]);
            RotationMatrix.getAngleChange(m_tb, r2, r1);

            qf2 = new Quaternion(rot[i]);
            q_tb = qf2.Difference(qf1).toTaitBryan();

            System.out.printf("%30s: z: %10f, x: %10f, y: %10f\n", "Diff Quaternion Tait Bryan: ", q_tb[2], q_tb[0], q_tb[1]);
            System.out.printf("%30s: z: %10f, x: %10f, y: %10f\n", "Matrix Tait Bryan: ", m_tb[0], m_tb[1], m_tb[2]);
            System.out.printf("%30s: z: %10f, x: %10f, y: %10f\n\n", "Expected Tait Bryan: ", expectedRot[i][2], expectedRot[i][0], expectedRot[i][1]);
        }

        // TODO: make sure angles are in the right order! Check rot matrix javadocs, compare against quaternion methods
        //RotationMatrix.getRotationMatrixFromVector(r1, orig);
        //RotationMatrix.getRotationMatrixFromVector(r2, zRot1);
//
        //RotationMatrix.getAngleChange(m_tb, r1, r2);
//
        //qf1 = new Quaternion(orig);
        //qf2 = new Quaternion(zRot1);
//
        //q_tb = qf1.Difference(qf2).toTaitBryan();
//
        //System.out.println("Quaternion Tait Bryan: " + "x: " + q_tb[0] + ", y: " + q_tb[1] + ", z: " + q_tb[2]);
        //System.out.println("Matrix Tait Bryan: " + "z: " + m_tb[0] + ", x: " + m_tb[1] + ", y: " + m_tb[2]);
        //System.out.println("Delta: " + "z: " + (q_tb[0]-m_tb[0]) + ", x: " + (q_tb[1]-m_tb[1]) + ", y: " + (q_tb[2]-m_tb[2]));
    }

    public static float[][] GenerateVectors(int num)
    {
        Random random = new Random();

        float[][] vecs = new float[num][3];

        for (int i = 0; i < num; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                vecs[i][j] = random.nextFloat();
            }
        }

        return vecs;
    }

    public static Quaternion[] GenerateQuats(int num)
    {
        Random random = new Random();

        Quaternion[] quats = new Quaternion[num];

        for (int i = 0; i < num; ++i)
        {
            quats[i] = new Quaternion(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
        }

        return quats;
    }
}