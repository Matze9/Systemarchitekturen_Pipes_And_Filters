package at.fhv.sysarch.lab3.pipeline.push.pushPipe;

import at.fhv.sysarch.lab3.pipeline.push.pushFilter.PushFilter;

public class PushPipeImpl<T> implements PushPipe<T>{

    private PushFilter<T, ?> pushFilter;

    public PushPipeImpl (PushFilter<T, ?> pushFilter){
        this.pushFilter = pushFilter;
    }

    @Override
    public void write(T data) {
        this.pushFilter.write(data);
    }

    @Override
    public void setPushFilter(PushFilter<T, ?> pushFilter) {

    }
}
