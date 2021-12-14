package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PushPerspectiveProjectionFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>>{

    private PushPipe <Pair<Face, Color>> outgoingPipe;
    private PipelineData pd;

    public PushPerspectiveProjectionFilter (PipelineData pd){
        this.pd = pd;
    }

    @Override
    public void write (Pair<Face, Color> data){

        Face face = data.fst();

        Mat4 projectionTransform = pd.getProjTransform();

            outgoingPipe.write(new Pair(new Face(projectionTransform.multiply(face.getV1()),
                    projectionTransform.multiply(face.getV2()),
                    projectionTransform.multiply(face.getV3()),
                    projectionTransform.multiply(face.getN1()),
                    projectionTransform.multiply(face.getN2()),
                    projectionTransform.multiply(face.getN3())), pd.getModelColor()

            ));

    }

    @Override
    public void setOutgoingPipe(PushPipe<Pair<Face, Color>> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }




}
