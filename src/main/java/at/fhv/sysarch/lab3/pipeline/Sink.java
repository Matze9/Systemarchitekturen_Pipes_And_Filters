package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.pushFilter.PushFilter;
import at.fhv.sysarch.lab3.pipeline.push.pushPipe.PushPipe;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.paint.Color;

public class Sink implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private PipelineData pd;


    public Sink(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void write (Pair<Face, Color> data){

        Face face = data.fst();
        Color color = data.snd();



        pd.getGraphicsContext().setStroke(color);
        pd.getGraphicsContext().setFill(color);

        if(pd.getRenderingMode().equals(RenderingMode.POINT)){

            pd.getGraphicsContext().fillOval(face.getV1().getX(), face.getV1().getY(), 2,2);
            pd.getGraphicsContext().fillOval(face.getV2().getX(), face.getV2().getY(), 2,2);
            pd.getGraphicsContext().fillOval(face.getV3().getX(), face.getV3().getY(), 2,2);

        } else if (pd.getRenderingMode().equals(RenderingMode.WIREFRAME)){

            pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
            pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV3().getX(), face.getV3().getY());
            pd.getGraphicsContext().strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());
//            pd.getGraphicsContext().strokeLine(face.getN1().getX(), face.getN1().getY(), face.getN2().getX(), face.getN2().getY());
//            pd.getGraphicsContext().strokeLine(face.getN1().getX(), face.getN1().getY(), face.getN3().getX(), face.getN3().getY());
//            pd.getGraphicsContext().strokeLine(face.getN2().getX(), face.getN2().getY(), face.getN3().getX(), face.getN3().getY());

        } else if ( pd.getRenderingMode().equals(RenderingMode.FILLED)){

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

            pd.getGraphicsContext().fillPolygon(x, y , 3);
            pd.getGraphicsContext().strokePolygon(x, y, 3);

        }


//        Face face = data.fst();
//
//        pd.getGraphicsContext().setStroke(data.snd());
//
//        //Render to Screen
//        pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV2().getX(), face.getV2().getY());
//        pd.getGraphicsContext().strokeLine(face.getV1().getX(), face.getV1().getY(), face.getV3().getX(), face.getV3().getY());
//        pd.getGraphicsContext().strokeLine(face.getV2().getX(), face.getV2().getY(), face.getV3().getX(), face.getV3().getY());
//        pd.getGraphicsContext().strokeLine(face.getN1().getX(), face.getN1().getY(), face.getN2().getX(), face.getN2().getY());
//        pd.getGraphicsContext().strokeLine(face.getN1().getX(), face.getN1().getY(), face.getN3().getX(), face.getN3().getY());
//        pd.getGraphicsContext().strokeLine(face.getN2().getX(), face.getN2().getY(), face.getN3().getX(), face.getN3().getY());

    }

    @Override
    public void setOutgoingPipe(PushPipe outgoingPipe) {

    }
}
