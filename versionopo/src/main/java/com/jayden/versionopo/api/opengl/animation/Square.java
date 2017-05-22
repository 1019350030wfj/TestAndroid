package com.jayden.versionopo.api.opengl.animation;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	// Our vertices.
	protected float vertices[] = {
		      -1.0f,  1.0f, 0.0f,  // 0, Top Left
		      -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
		       1.0f, -1.0f, 0.0f,  // 2, Bottom Right
		       1.0f,  1.0f, 0.0f,  // 3, Top Right
		};

	// The order we like to connect them.
	protected short[] indices = { 0, 1, 2, 0, 2, 3 };

	// Our vertex buffer.
	protected FloatBuffer vertexBuffer;

	// Our index buffer.
	protected ShortBuffer indexBuffer;

	public Square() {
		// a float is 4 bytes, therefore we 
		// multiply the number if
		// vertices with 4.
		ByteBuffer vbb 
		  = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		// short is 2 bytes, therefore we multiply 
		//the number if
		// vertices with 2.
		ByteBuffer ibb 
		 = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	/**
	 * This function draws our square on screen.
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// Counter-clockwise winding.
		//指明顺时针和逆时针绘出多边形，哪一个是前面，哪一个是背面。
		//GL_FRONT_AND_BACK,多边形不会被绘出，但是其他图元像点、线会被绘出
		gl.glFrontFace(GL10.GL_CCW); 
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); //如果启用，基于窗口坐标采集多边形
		// What faces to remove with the face culling.
		//指明多边形的前面或后面是否被拣选
		gl.glCullFace(GL10.GL_BACK); 

		// Enabled the vertices buffer for writing 
		//and to be used during rendering.
		//启用客户端的某项功能
//		GL_COLOR_ARRAY——如果启用，颜色矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glColorPointer。
//		GL_NORMAL_ARRAY——如果启用，法线矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glNormalPointer。
//		GL_TEXTURE_COORD_ARRAY——如果启用，纹理坐标矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glTexCoordPointer。
//		GL_VERTEX_ARRAY——如果启用，顶点矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glVertexPointer。
//		GL_POINT_SIZE_ARRAY_OES(OES_point_size_arrayextension)——如果启用，点大小矩阵控制大小以渲染点和点sprites。这时由glPointSize定义的点大小将被忽略，由点大小矩阵提供的大小将被用来渲染点和点sprites。详见glPointSize。
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of 
		//an array of vertex
		// coordinates to use when rendering.定义一个顶点坐标矩阵。
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, 
                                 vertexBuffer);
		//由矩阵数据渲染图元。
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				  GL10.GL_UNSIGNED_SHORT, indexBuffer);

		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); 
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE); 
	}

}
