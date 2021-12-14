package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pushFilter.*;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipeImpl;
import com.hackoeur.jglm.Mat3;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: push from the source (model)


        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        //Modelviewtransformation
        PushModelViewTransformationFilter modelViewTransformationFilter = new PushModelViewTransformationFilter(pd);


        // TODO 2. perform backface culling in VIEW SPACE

        PushBackfaceCullingFilter backfaceCullingFilter = new PushBackfaceCullingFilter();
        PushPipe<Face> p4 = new PushPipeImpl<>(backfaceCullingFilter);
        modelViewTransformationFilter.setOutgoingPipe(p4);

        // TODO 3. perform depth sorting in VIEW SPACE

        // TODO 4. add coloring (space unimportant)

        PushColorFilter colorFilter = new PushColorFilter(pd);
        PushPipe <Face> p5 = new PushPipeImpl<>(colorFilter);
        backfaceCullingFilter.setOutgoingPipe(p5);

        // lighting can be switched on/off
        PushPerspectiveProjectionFilter perspectiveProjectionFilter = new PushPerspectiveProjectionFilter(pd);
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE

            PushFlatShadingFilter flatShadingFilter = new PushFlatShadingFilter(pd);
            PushPipeImpl<Pair<Face, Color>> p6 = new PushPipeImpl<>(flatShadingFilter);
            colorFilter.setOutgoingPipe(p6);

            // 5. TODO perform projection transformation on VIEW SPACE coordinates

            PushPipeImpl<Pair<Face, Color>> p1 = new PushPipeImpl(perspectiveProjectionFilter);
            flatShadingFilter.setOutgoingPipe(p1);
        } else {
            // 5. TODO perform projection transformation

            //Perspective Projection
            PushPipeImpl<Pair<Face, Color>> p1 = new PushPipeImpl(perspectiveProjectionFilter);
            colorFilter.setOutgoingPipe(p1);
        }



        // TODO 6. perform perspective division to screen coordinates

        //ScreenSpaceTransformation
        PushScreenSpaceTransformationFilter screenSpaceTransformationFilter = new PushScreenSpaceTransformationFilter(pd);
        PushPipeImpl<Pair<Face, Color>> p2 = new PushPipeImpl<>(screenSpaceTransformationFilter);
        perspectiveProjectionFilter.setOutgoingPipe(p2);


        // TODO 7. feed into the sink (renderer)

        //Sink
        Sink sink = new Sink(pd);
        PushPipeImpl<Pair<Face, Color>> p3 = new PushPipeImpl<>(sink);
        screenSpaceTransformationFilter.setOutgoingPipe(p3);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians

                // TODO create new model rotation matrix using pd.modelRotAxis

                // TODO compute updated model-view transformation

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline


                model.getFaces().forEach(face -> {
                    modelViewTransformationFilter.write(face);
                });


            }
        };
    }
}
