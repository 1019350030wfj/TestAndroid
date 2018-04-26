package com.jayden.officialandroiddemo.concurrent;

public class Producer extends Thread {

    private AbsStorage mAbsStorage;
    private int mNum;//生产的数量

    public Producer(AbsStorage absStorage){
        this.mAbsStorage = absStorage;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }

    private void produce(int num){
        mAbsStorage.produce(num);
    }

    @Override
    public void run() {
        super.run();
        produce(mNum);
    }
}
