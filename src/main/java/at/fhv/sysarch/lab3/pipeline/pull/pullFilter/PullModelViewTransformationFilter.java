package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipeImpl;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PullModelViewTransformationFilter implements PullFilter<Face, Face>{

    PipelineData pd;
    PullPipe<Face> incomingPipe;

    public PullModelViewTransformationFilter(PipelineData pd){
        this.pd = pd;
    }

    @Override
    public Face read() {
        Face face = incomingPipe.read();

        if(face.getV1().equals(Vec4.VEC4_ZERO)) {
            System.out.println("Modelview returns END FACE : " + face);
            return face;
        } else if (face != null) {
            Mat4 modelTranslation = pd.getModelTranslation();
            Mat4 viewTransformation = pd.getViewTransform();

            Mat4 transformator = viewTransformation.multiply(modelTranslation);


            return (new Face(
                    transformator.multiply(face.getV1()),
                    transformator.multiply(face.getV2()),
                    transformator.multiply(face.getV3()),
                    transformator.multiply(face.getN1()),
                    transformator.multiply(face.getN2()),
                    transformator.multiply(face.getN3())));
        }
        return null;

    }

    @Override
    public void setIncomingPipe(PullPipe<Face> pipe) {
        this.incomingPipe = pipe;
    }
}
