package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pullFilter.*;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipeImpl;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import javax.sql.DataSource;
import java.awt.*;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        //  pull from the source (model)
        Source source = new Source();

        //  1. perform model-view transformation from model to VIEW SPACE coordinates
        PullModelViewTransformationFilter modelViewTransformationFilter = new PullModelViewTransformationFilter(pd);
        PullPipeImpl<Face> p1 = new PullPipeImpl<>(source);
        modelViewTransformationFilter.setIncomingPipe(p1);

        //  2. perform backface culling in VIEW SPACE
        PullBackfaceCullingFilter backfaceCullingFilter = new PullBackfaceCullingFilter();
        PullPipeImpl<Face> p2 = new PullPipeImpl<>(modelViewTransformationFilter);
        backfaceCullingFilter.setIncomingPipe(p2);

        //  3. perform depth sorting in VIEW SPACE
        PullDepthSortingFilter depthSortingFilter = new PullDepthSortingFilter();
        PullPipeImpl<Face> p7 = new PullPipeImpl<>(backfaceCullingFilter);
        depthSortingFilter.setIncomingPipe(p7);


        //  4. add coloring (space unimportant)
        PullColorFilter colorFilter = new PullColorFilter(pd);
        PullPipeImpl<Face> p3 = new PullPipeImpl<>(depthSortingFilter);
        colorFilter.setIncomingPipe(p3);


        // lighting can be switched on/off
        PullPerspectiveProjectionFilter perspectiveProjectionFilter = new PullPerspectiveProjectionFilter(pd);

        if (pd.isPerformLighting()) {
            // 4a.  perform lighting in VIEW SPACE

            PullFlatShadingFilter flatShadingFilter = new PullFlatShadingFilter(pd);
            PullPipeImpl<Pair<Face, Color>> p4 = new PullPipeImpl<>(colorFilter);
            flatShadingFilter.setIncomingPipe(p4);
            
            // 5.  perform projection transformation on VIEW SPACE coordinates

            PullPipeImpl<Pair<Face, Color>> p5 = new PullPipeImpl<>(flatShadingFilter);
            perspectiveProjectionFilter.setIncomingPipe(p5);
        } else {
            // 5.  perform projection transformation
            PullPipeImpl<Pair<Face, Color>> p5 = new PullPipeImpl<>(colorFilter);
            perspectiveProjectionFilter.setIncomingPipe(p5);
        }

        //  6. perform perspective division to screen coordinates
        PullScreenSpaceTransformation screenSpaceTransformation = new PullScreenSpaceTransformation(pd);
        PullPipeImpl<Pair<Face, Color>> p5 = new PullPipeImpl<>(perspectiveProjectionFilter);
        screenSpaceTransformation.setIncomingPipe(p5);

        //  7. feed into the sink (renderer)

        PullSink sink = new PullSink(pd);
        PullPipeImpl<Pair<Face, Color>> p6 = new PullPipeImpl<>(screenSpaceTransformation);
        sink.setIncomingPipe(p6);


        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            //  rotation variable goes in here
            float rotate = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                //  compute rotation in radians
                rotate = rotate + fraction;
//                One rotation converted into radian equals = 6.28 rad
//                1 rot = 6.28 rad
                float radiant = rotate / 6.28f;
                //  create new model rotation matrix using pd.modelRotAxis
                Mat4 rotationMatrix = Matrices.rotate(rotate, pd.getModelRotAxis());
                //  compute updated model-view transformation
                modelViewTransformationFilter.setRotationMatrix(rotationMatrix);

                source.setModel(model);
                sink.read();

            }
        };
    }
}
