package com.jayden.officialandroiddemo.bitoperation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class BitOperationDemo extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int leftBase = 8;
        StringBuilder builder = new StringBuilder();
//        builder.append("Bit Operation =").append(leftBase >>> 1).append("\n");
//        builder.append("Bit Operation =").append(leftBase >>> 2).append("\n");
//        builder.append("Bit Operation =").append(leftBase >>> 4).append("\n");
//        builder.append("Bit Operation =").append(leftBase >>> 8).append("\n");
//        builder.append("Bit Operation =").append(leftBase >>> 16).append("\n");
        /**
         *  8>>>1 = 4; 8>>>2 = 2;   8>>>4 = 0;  8>>>8 = 0; 8>>>16 = 0;
         */

//        builder.append("Bit Operation =").append(leftBase >> 1).append("\n");
//        builder.append("Bit Operation =").append(leftBase >> 2).append("\n");
//        builder.append("Bit Operation =").append(leftBase >> 4).append("\n");
//        builder.append("Bit Operation =").append(leftBase >> 8).append("\n");
//        builder.append("Bit Operation =").append(leftBase >> 16).append("\n");
        /**
         *  8>>1 = 4; 8>>2 = 2;   8>>4 = 0;  8>>8 = 0; 8>>16 = 0;
         */
//        builder.append("Bit Operation =").append(leftBase << 1).append("\n");
//        builder.append("Bit Operation =").append(leftBase << 2).append("\n");
//        builder.append("Bit Operation =").append(leftBase << 4).append("\n");
//        builder.append("Bit Operation =").append(leftBase << 8).append("\n");
//        builder.append("Bit Operation =").append(leftBase << 16).append("\n");
        /**
         *  8<<1 = 8*2^1=16; 8<<2 = 8*2^2=32;   8<<4 = 8*2^4=128;  8<<8 = 8*2^8=2048; 8<<16 = 8*2^16=524288;
         */
//        builder.append("Bit Operation =").append(1 << leftBase ).append("\n");
//        builder.append("Bit Operation =").append(2 << leftBase ).append("\n");
//        builder.append("Bit Operation =").append(4 << leftBase ).append("\n");
//        builder.append("Bit Operation =").append(8 << leftBase ).append("\n");
//        builder.append("Bit Operation =").append(16 << leftBase ).append("\n");
        /**
         *  1<<8 = 1*2^8=256; 2<<8 = 2*2^8=512; 4<<8 = 4*2^8=1024; 8<<8 = 8*2^8=2048; 16<<8 = 16*2^8=4096;
         */
        builder.append("Bit Operation =").append(111 >> 4 ).append("\n");
        TextView textView = new TextView(this);
        setContentView(textView);
        textView.setText(builder.toString());

        textView.setText("非操作+"+(~1));
    }
}
