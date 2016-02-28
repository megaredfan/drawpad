package joseph.drawpad.model;

import android.view.LayoutInflater;
import android.view.View;
import joseph.drawpad.R;

import java.util.List;

/**
 * Created by 熊纪元 on 2016/2/10.
 */
public class QuadraticCurve extends Calculatable {
    public List<Float> getParameters() {
        return parameters;
    }

    public void setParameters(List<Float> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    @Override
    public View getView(LayoutInflater li) {
        return li.inflate(R.layout.editquadratic, null);
    }

    @Override
    public Point[] calculate(int width) {
        if(parameters == null || parameters.size() !=3)
            return null;

        float Y,A,B,C,X;
        A = parameters.get(0);
        B = parameters.get(1);
        C = parameters.get(2);
        Point[] points = new Point[width];

        float delta = (endX - startX)/(width);
        X = startX;
        for(int i = 0; i < width; i++) {
            Y = A * X * X + B * X + C;
            points[i] = new Point(X,Y);
            X = X + delta;
        }
        return points;

    }

    @Override
    public QuadraticCurve scale(float times, float height, float width) {
        /*for (Point p : points) {
            p.setY((p.getY() - width/2)*times + width/2);
            p.setX((p.getX() - height/2)*times + height/2);
        }
        return this;*/
        int[] area = new int[2];
        int Iwidth = Math.round(width);
        Point[] newPoints = new Point[Iwidth];

        if(times > 1) {
            int Itimes = Math.round(times);
            Itimes = Itimes*2;
            area[0] = Math.round(width/2 - width/Itimes);
            area[1] = Math.round(width/2 + width/Itimes);
            int start = area[0];
            int end = area[1];

            int IntTimes = Math.round(times);
            for(int i = 0; i < newPoints.length; i = i + IntTimes) {
                for(int j = 0; j < IntTimes; j++) {
                    newPoints[i+j] = new Point();
                    newPoints[i+j].setY(i+j);

                    if(j%IntTimes == 0){
                        newPoints[i].setX(points[start+i/IntTimes].getyInCurve()*times+height/2f);
                        newPoints[i].setyInCurve(points[start+i/IntTimes].getyInCurve());
                        newPoints[i].setxInCurve(points[start+i/IntTimes].getxInCurve());
                    } else {
                        float Y,X,A,B,C;
                        float y = points[start+i/IntTimes].getY()+j/times;
                        //Y = (-(width / 2f - y));
                        y = points[start+i/IntTimes].getxInCurve()+j/times*(points[start+i/IntTimes+1].getxInCurve()-points[start+i/IntTimes].getxInCurve());
                        Y = y;
                        A = this.getParameters().get(0);
                        B = this.getParameters().get(1);
                        C = this.getParameters().get(2) + height / 2f;
                        X = A * Y * Y + B * Y + C;
                        newPoints[i+j].setyInCurve((X-height/2)/times);
                        newPoints[i+j].setxInCurve(y);
                        newPoints[i+j].setX(X);
                    }
                }
            }
        }
        setPoints(newPoints);
        return this;
    }
}
