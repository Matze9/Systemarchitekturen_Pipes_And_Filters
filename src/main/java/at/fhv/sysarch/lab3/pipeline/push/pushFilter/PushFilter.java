package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;

public interface PushFilter<T, S> {


    public void write (T data);

    public void setOutgoingPipe(PushPipe<S> outgoingPipe);

}
