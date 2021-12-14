package at.fhv.sysarch.lab3.pipeline.push.pushPipe;

import at.fhv.sysarch.lab3.pipeline.push.pushFilter.PushFilter;

public interface PushPipe <T>{

    void write(T data);

    void setPushFilter(PushFilter<T, ?> pushFilter);
}
