package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;

public class PushBackfaceCullingFilter implements PushFilter<Face, Face>{

    private PushPipe<Face> outgoingPipe;

    @Override
    public void write(Face face) {

        if(face.getV1().dot(face.getN1())< 0){
            outgoingPipe.write(face);
        }
    }

    @Override
    public void setOutgoingPipe(PushPipe<Face> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }
}
