package com.jayden.officialandroiddemo.concurrent;

public class Consumer extends  Thread{

    private AbsStorage mAbsStorage;
    private int mNum;

    public Consumer(AbsStorage absStorage){
        this.mAbsStorage = absStorage;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }

    private void consume(int num){
        mAbsStorage.consume(num);
    }

    @Override
    public void run() {
        super.run();
        consume(mNum);
    }
}
