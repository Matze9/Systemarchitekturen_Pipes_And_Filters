package at.fhv.sysarch.lab3.pipeline.pull.pullFilter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.pullPipe.PullPipe;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;


public class PullSink implements PullFilter<Pair<Face, Color>, Pair<Face, Color>>{

    private PipelineData pd;
    private PullPipe<Pair<Face, Color>> incomingPipe;

    public PullSink (PipelineData pd){
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {

        while (true) {
            Pair<Face, Color> input = incomingPipe.read();

            if(input != null && input.fst().getV1() != null && input.fst().getV1().equals(Vec4.VEC4_ZERO)){
                break;
            }

            if (input != null && input.fst() != null) {

                Face face = input.fst();
                Color color = input.snd();

                pd.getGraphicsContext().setStroke(color);
                pd.getGraphicsContext().setFill(color);

                    if (pd.getRenderingMode().equals(RenderingMode.POINT)) {

                        pd.getGraphicsContext().fillOval(face.getV1().getX(), face.getV1().getY(), 2, 2);
                        pd.getGraphicsContext().fillOval(face.getV2().getX(), face.getV2().getY(), 2, 2);
                        pd.getGraphicsContext().fillOval(face.getV3().getX(), face.getV3().getY(), 2, 2);

                    } else if (pd.getRenderingMode().equals(RenderingMode.WIREFRAME)) {

                        pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
                        pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV3().getX(), face.getV3().getY());
                        pd.getGraphicsContext().strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());

                    } else if (pd.getRenderingMode().equals(RenderingMode.FILLED)) {

                        double[] x = new double[]{
                                face.getV1().getX(),
                                face.getV2().getX(),
                                face.getV3().getX()
                        };
                        double[] y = new double[]{
                                face.getV1().getY(),
                                face.getV2().getY(),
                                face.getV3().getY()
                        };

                        pd.getGraphicsContext().fillPolygon(x, y, 3);
                        pd.getGraphicsContext().strokePolygon(x, y, 3);

                    }

            }
        }

            return null;

    }

    @Override
    public void setIncomingPipe(PullPipe<Pair<Face, Color>> pipe) {
        this.incomingPipe = pipe;

    }
}
