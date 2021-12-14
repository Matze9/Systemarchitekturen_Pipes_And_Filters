package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import javafx.scene.paint.Color;

public class PushScreenSpaceTransformationFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>>{

    private PushPipe <Pair<Face, Color>> outgoingPipe;
    private PipelineData pd;

    public PushScreenSpaceTransformationFilter (PipelineData pd){
        this.pd = pd;
    }


    @Override
    public void write(Pair<Face, Color> data) {
        Face face = data.fst();


        //Divide with W - Can only use Multiply, so multiply with 1/W
            Face newFace = new Face(
                    face.getV1().multiply((1 / face.getV1().getW())),
                    face.getV2().multiply((1 / face.getV2().getW())),
                    face.getV3().multiply((1 / face.getV3().getW())),
                    face.getN1().multiply((1 / face.getN1().getW())),
                    face.getN2().multiply((1 / face.getN2().getW())),
                    face.getN3().multiply((1 / face.getN3().getW()))
            );

            outgoingPipe.write(new Pair<Face, Color>(new Face(  pd.getViewportTransform().multiply(newFace.getV1()),
                    pd.getViewportTransform().multiply(newFace.getV2()),
                    pd.getViewportTransform().multiply(newFace.getV3()),
                    pd.getViewportTransform().multiply(newFace.getN1()),
                    pd.getViewportTransform().multiply(newFace.getN2()),
                    pd.getViewportTransform().multiply(newFace.getN3())), pd.getModelColor()

            ));

    }

    @Override
    public void setOutgoingPipe(PushPipe<Pair<Face, Color>> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }
}
