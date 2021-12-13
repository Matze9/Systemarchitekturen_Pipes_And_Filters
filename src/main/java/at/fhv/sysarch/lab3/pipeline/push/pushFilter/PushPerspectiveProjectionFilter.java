package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import com.hackoeur.jglm.Mat4;

import java.util.LinkedList;

public class PushPerspectiveProjectionFilter implements PushFilter<Face, Face>{

    private PushPipe <Face> outgoingPipe;
    private PipelineData pd;

    public PushPerspectiveProjectionFilter (PipelineData pd){
        this.pd = pd;
    }

    @Override
    public void write (Face face){

        Mat4 projectionTransform = pd.getProjTransform();

            outgoingPipe.write(new Face(
                    projectionTransform.multiply(face.getV1()),
                    projectionTransform.multiply(face.getV2()),
                    projectionTransform.multiply(face.getV3()),
                    projectionTransform.multiply(face.getN1()),
                    projectionTransform.multiply(face.getN2()),
                    projectionTransform.multiply(face.getN3())
            ));

    }

    @Override
    public void setOutgoingPipe(PushPipe<Face> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }




}
