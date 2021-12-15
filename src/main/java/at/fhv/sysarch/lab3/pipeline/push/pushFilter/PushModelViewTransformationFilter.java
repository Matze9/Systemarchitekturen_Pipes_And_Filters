package at.fhv.sysarch.lab3.pipeline.push.pushFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PushModelViewTransformationFilter implements PushFilter<Face, Face> {

    private PushPipe <Face> outgoingPipe;
    private PipelineData pd;
    private Mat4 rotationMatrix;

    public PushModelViewTransformationFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write(Face face) {

        if (face.getV1().equals(Vec4.VEC4_ZERO)){
            outgoingPipe.write(face);
        }

        Mat4 modelTranslation = pd.getModelTranslation();
        Mat4 viewTransformation = pd.getViewTransform();

        Mat4 transformator = viewTransformation.multiply(rotationMatrix).multiply(modelTranslation);

        outgoingPipe.write(new Face(
                transformator.multiply(face.getV1()),
                transformator.multiply(face.getV2()),
                transformator.multiply(face.getV3()),
                transformator.multiply(face.getN1()),
                transformator.multiply(face.getN2()),
                transformator.multiply(face.getN3())));
    }

    @Override
    public void setOutgoingPipe(PushPipe<Face> outgoingPipe) {
        this.outgoingPipe = outgoingPipe;
    }

    public void setRotationMatrix(Mat4 rotationMatrix){
        this.rotationMatrix = rotationMatrix;
    }


}
