package com.jayden.versionopo.multimedia.image;

/**
 * Created by Administrator on 2017/11/29.
 */

public class Complex {
    public float re;
    public float im;

    public Complex(float re, float im) {
        this.re = re;
        this.im = im;
    }

    public void add(Complex c) {
        re += c.re;
        im += c.im;
    }

    /**
     * c.re := a.re*b.re - a.im*b.im;
     * c.im := a.im*b.re + a.re*b.im;
     */
    public void multiply(Complex c) {
        float temp = re * c.re - im * c.im;
        im = im * c.re + re * c.im;
        re = temp;
    }

    public float abs() {
        return (float) Math.sqrt(re * re + im * im);
    }
}
