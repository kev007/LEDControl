package com.ppp.ledcontrol;

import android.graphics.Color;

public class SingleColor {
	 int t;
	 int r;
	 int g;
	 int b;
	 int l;

	 public int getT() {
		 return t;
	 }
	 public void setT(int t) {
		 this.t = t;
	 }
	 
	 public int getR() {
		 return r;
	 }
	 public void setR(int r) {
		 this.r = r;
	 }
	 
	 public int getG() {
		 return g;
	 }
	 public void setG(int g) {
		 this.g = g;
	 }
	 
	 public int getB() {
		 return b;
	 }
	 public void setB(int b) {
		 this.b = b;
	 }
	 
	 public int getL() {
		 return l;
	 }
	 public void setL(int l) {
		 this.l = l;
	 }
	 
	 public void colorFill(int color) {
		 this.r = Color.red(color);
		 this.g = Color.green(color);
		 this.b = Color.blue(color);
		 this.l = Color.alpha(color);
	 }
	 
	 public int getColor() {		 
		 return Color.argb(l, r, g, b);
		 //return Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
		 //return "0x" + Integer.toHexString(l) + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
	 }

	 public SingleColor(int t, int r, int g, int b, int l) {
		  super();
		  this.t = t;
		  this.r = r;
		  this.g = g;
		  this.b = b;
		  this.l = l;
	 }
}
