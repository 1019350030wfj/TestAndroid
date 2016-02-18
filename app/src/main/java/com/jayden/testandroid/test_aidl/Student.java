package com.jayden.testandroid.test_aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * AIDL传输非基本类型的对象，被传输的对象必须序列化
 * readFromParcel : 从parcel中读取对象
 * writeToParcel ：将对象写入parcel
 * describeContents：返回0即可
 * Parcelable.Creator<Student> CREATOR：这个照着上面的代码抄就可以
 * 需要注意的是，readFromParcel和writeToParcel操作数据成员的顺序要一致
 * Created by Jayden on 2016/2/17.
 */
public class Student implements Parcelable {

    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;

    public int sno;
    public String name;
    public int sex;
    public int age;

    public Student() {
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {

        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    private Student(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        sno = in.readInt();
        name = in.readString();
        sex = in.readInt();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sno);
        dest.writeString(name);
        dest.writeInt(sex);
        dest.writeInt(age);
    }
}
