package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;

import java.util.LinkedList;

public class PushScreenSpaceTransformationFilter implements PushFilter<Face, Face>{

    private PushPipe <Face> outgoingPipe;
    private PipelineData pd;

    public PushScreenSpaceTransformationFilter (PipelineData pd){
        this.pd = pd;
    }


    @Override
    public void write(Face face) {


        //Divide with W - Can only use Multiply, so multiply with 1/W
            Face newFace = new Face(
                    face.getV1().multiply((1 / face.getV1().getW())),
                    face.getV2().multiply((1 / face.getV2().getW())),
                    face.getV3().multiply((1 / face.getV3().getW())),
                    face.getN1().multiply((1 / face.getN1().getW())),
                    face.getN2().multiply((1 / face.getN2().getW())),
                    face.getN3().multiply((1 / face.getN3().getW()))
            );



            outgoingPipe.write(new Face(
                    pd.getViewportTransform().multiply(newFace.getV1()),
                    pd.getViewportTransform().multiply(newFace.getV2()),
                    pd.getViewportTransform().multiply(newFace.getV3()),
                    pd.getViewportTransform().multiply(newFace.getN1()),
                    pd.getViewportTransform().multiply(newFace.getN2()),
                    pd.getViewportTransform().multiply(newFace.getN3())
            ));




    }

    @Override
    public void setOutgoingPipe(PushPipe outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }
}
