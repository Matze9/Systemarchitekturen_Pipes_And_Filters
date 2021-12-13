package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.pushFilter.PushFilter;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;

public class Sink implements PushFilter<Face, Face> {

    private PipelineData pd;


    public Sink( PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write (Face face){

        //Render to Screen
        pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
        pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV3().getX(), face.getV3().getY());
        pd.getGraphicsContext().strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());
        pd.getGraphicsContext().strokeLine(face.getN1().getX(), face.getN1().getY(), face.getN2().getX(), face.getN2().getY());
        pd.getGraphicsContext().strokeLine(face.getN1().getX(), face.getN1().getY(), face.getN3().getX(), face.getN3().getY());
        pd.getGraphicsContext().strokeLine(face.getN2().getX(), face.getN2().getY(), face.getN3().getX(), face.getN3().getY());

    }

    @Override
    public void setOutgoingPipe(PushPipe outgoingPipe) {

    }
}